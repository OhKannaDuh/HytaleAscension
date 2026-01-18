package faye.rpg.modules.stats.handlers;

import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.modules.stats.components.AscensionExp;
import faye.rpg.modules.stats.events.AttributePointsAssignmentChangedEvent;
//import faye.rpg.modules.stats.data.modifiers.IAscensionModifier;


public class AttributePointsAssignmentChangedEventHandler implements IAscensionEventHandler<AttributePointsAssignmentChangedEvent> {
//    private final Set<IAscensionModifier> modifiers;

//    @Inject
//    public AttributePointsAssignmentChangedEventHandler(Set<IAscensionModifier> modifiers) {
//        this.modifiers = modifiers;
//    }

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
        var ascension = store.getComponent(entity, AscensionExp.getComponentType());
        if (ascension == null) {
            return;
        }

        var stats = store.getComponent(entity, EntityStatMap.getComponentType());
        if (stats == null) {
            return;
        }

//        for (var modifier : modifiers) {
//            var index = modifier.index();
//            var key  = modifier.key();
//
//            stats.removeModifier(index, key);
//            if (modifier.shouldApply(ascension)) {
//                stats.putModifier(index, key, modifier.get(ascension));
//            }
//        }
    }
}
