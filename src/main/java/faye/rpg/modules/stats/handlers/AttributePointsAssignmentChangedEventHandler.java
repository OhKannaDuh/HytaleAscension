package faye.rpg.modules.stats.handlers;

import com.google.inject.Inject;
import com.hypixel.hytale.protocol.MovementSettings;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import faye.rpg.Logger;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.modules.stats.data.AscensionStats;
import faye.rpg.modules.stats.data.effects.AscensionAttributeEffects;
import faye.rpg.modules.stats.data.effects.IAscensionAttributeEffectApplicator;
import faye.rpg.modules.stats.events.AttributePointsAssignmentChangedEvent;
import faye.rpg.modules.stats.data.modifiers.IAscensionAttributeModifier;

import java.util.Set;


public class AttributePointsAssignmentChangedEventHandler implements IAscensionEventHandler<AttributePointsAssignmentChangedEvent> {
    private final Set<IAscensionAttributeModifier> modifiers;

    private final Set<IAscensionAttributeEffectApplicator> effects;

    private final Logger logger;

    @Inject
    public AttributePointsAssignmentChangedEventHandler(Set<IAscensionAttributeModifier> modifiers, Set<IAscensionAttributeEffectApplicator> effects, Logger logger) {
        this.modifiers = modifiers;
        this.effects = effects;
        this.logger = logger;
    }

    @Override
    public Class<AttributePointsAssignmentChangedEvent> eventType() {
        return AttributePointsAssignmentChangedEvent.class;
    }

    @Override
    public void execute(AttributePointsAssignmentChangedEvent event) {
        var entity = event.player().getReference();
        if (entity == null) {
            return;
        }

        var store = event.world().getEntityStore().getStore();
        var stats = store.getComponent(entity, EntityStatMap.getComponentType());
        if (stats == null) {
            return;
        }

        for (var modifier : modifiers) {
            var index = modifier.targetIndex();
            var key = modifier.key();

//            stats.removeModifier(index, key);
            modifier.get(stats).ifPresent(value -> {
                stats.putModifier(
                        index,
                        key,
                        new StaticModifier(
                                modifier.target(),
                                modifier.calculation(),
                                value
                        )
                );
            });
        }

        var controller = store.getComponent(entity, EffectControllerComponent.getComponentType());
        if (controller == null) {
            return;
        }

        AscensionAttributeEffects.clear(entity, controller, store);
        for (var applicator : effects) {
            applicator.get(stats).ifPresent(effect -> {
                controller.addInfiniteEffect(entity, effect.index(), effect.effect(), store);
            });
        }


        var dexterity = stats.get(AscensionStats.dexterity());
        if (dexterity != null && dexterity.get() > 0) {
            var movement = store.getComponent(entity, MovementManager.getComponentType());
            var playerRef = store.getComponent(entity, PlayerRef.getComponentType());
            if (movement != null && playerRef != null) {
                var settings = movement.getSettings();
                var def = movement.getDefaultSettings();

                var mod = 1f + (0.005f * dexterity.get());

                settings.acceleration = def.acceleration * mod;
                settings.baseSpeed = def.baseSpeed * mod;
                settings.forwardWalkSpeedMultiplier = def.forwardWalkSpeedMultiplier * mod;
                settings.backwardWalkSpeedMultiplier = def.backwardWalkSpeedMultiplier * mod;
                settings.strafeWalkSpeedMultiplier = def.strafeWalkSpeedMultiplier * mod;
                settings.forwardRunSpeedMultiplier = def.forwardRunSpeedMultiplier * mod;
                settings.backwardRunSpeedMultiplier = def.backwardRunSpeedMultiplier * mod;
                settings.strafeRunSpeedMultiplier = def.strafeRunSpeedMultiplier * mod;
                settings.forwardCrouchSpeedMultiplier = def.forwardCrouchSpeedMultiplier * mod;
                settings.backwardCrouchSpeedMultiplier = def.backwardCrouchSpeedMultiplier * mod;
                settings.strafeCrouchSpeedMultiplier = def.strafeCrouchSpeedMultiplier * mod;
                settings.forwardSprintSpeedMultiplier = def.forwardSprintSpeedMultiplier * mod;

                movement.update(playerRef.getPacketHandler());
            }
        }
    }
}
