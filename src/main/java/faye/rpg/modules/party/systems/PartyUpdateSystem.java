package faye.rpg.modules.party.systems;

import com.google.inject.Inject;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.ISystem;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.Logger;
import faye.rpg.modules.party.PartyRegistry;
import faye.rpg.modules.party.components.PartyMember;
import faye.rpg.modules.party.ui.PartyHud;
import faye.rpg.systems.IAscensionEntitySystem;
import faye.rpg.ui.CustomHudManager;

public class PartyUpdateSystem extends EntityTickingSystem<EntityStore> implements IAscensionEntitySystem {

    private final PartyRegistry registry;

    private final Logger logger;

    @Inject
    public PartyUpdateSystem(PartyRegistry registry, Logger logger) {
        this.registry = registry;
        this.logger = logger;
    }


    @Override
    public Query<EntityStore> getQuery() {
        return PartyMember.getComponentType();
    }

    @Override
    public void tick(float delta, int index, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer) {
        var member = archetypeChunk.getComponent(index, PartyMember.getComponentType());
        if (member == null) {
            return;
        }

        var party = registry.getParty(member.getPartyUuid());
        if (party == null) {
            return;
        }

        var entity = archetypeChunk.getReferenceTo(index);
        var world = store.getExternalData().getWorld();

        var player = store.getComponent(entity, Player.getComponentType());
        if (player == null) {
            logger.warn("Could not get player component");
            return;
        }

        if (!(player.getHudManager().getCustomHud() instanceof CustomHudManager manager)) {
            logger.warn("Custom hud was not manager type");
            return;
        }

        var hud = manager.get(PartyHud.class);
        if (hud == null) {
            logger.warn("Could not get party hud.");
            return;
        }

        hud.updateFromParty(party, world);
    }

    @Override
    public ISystem<EntityStore> toSystem() {
        return this;
    }
}
