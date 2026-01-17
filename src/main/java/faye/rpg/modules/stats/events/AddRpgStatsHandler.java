package faye.rpg.modules.stats.events;

import faye.rpg.events.IAscensionEventHandler;
import faye.rpg.events.player_lifecycle.SetupPlayerComponentsEvent;
import faye.rpg.modules.stats.components.AscensionStats;

public class AddRpgStatsHandler implements IAscensionEventHandler<SetupPlayerComponentsEvent> {
    @Override
    public Class<SetupPlayerComponentsEvent> eventType() {
        return SetupPlayerComponentsEvent.class;
    }

    @Override
    public void execute(SetupPlayerComponentsEvent event) {
        var payload = event.getPayload();

        payload.world.execute(() -> {
            final var type = AscensionStats.getComponentType();
            if (payload.store.getComponent(payload.entity, type) == null) {
                payload.store.addComponent(payload.entity, type, new AscensionStats());
            }
        });
    }
}
