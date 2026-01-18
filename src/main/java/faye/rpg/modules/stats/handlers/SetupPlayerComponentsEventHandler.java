package faye.rpg.modules.stats.handlers;

import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.events.player_lifecycle.SetupPlayerComponentsEvent;
import faye.rpg.modules.stats.components.AscensionExp;
import faye.rpg.modules.stats.events.AttributePointsAssignmentChangedEvent;

public class SetupPlayerComponentsEventHandler implements IAscensionEventHandler<SetupPlayerComponentsEvent> {
    @Override
    public Class<SetupPlayerComponentsEvent> eventType() {
        return SetupPlayerComponentsEvent.class;
    }

    @Override
    public void execute(SetupPlayerComponentsEvent event) {
        var payload = event.getPayload();

        payload.world.execute(() -> {
            final var type = AscensionExp.getComponentType();
            if (payload.store.getComponent(payload.entity, type) == null) {
                payload.store.addComponent(payload.entity, type, new AscensionExp());
            }

            AttributePointsAssignmentChangedEvent.dispatch(payload.ref, payload.world);
        });
    }
}
