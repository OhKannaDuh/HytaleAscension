package faye.rpg.modules.stats.events;

import faye.rpg.events.IAscensionEventHandler;
import faye.rpg.events.player_lifecycle.SetupPlayerHudEvent;
import faye.rpg.modules.stats.components.AscensionStats;
import faye.rpg.modules.stats.ui.ExpBarHud;

public class AddExpBarHudHandler implements IAscensionEventHandler<SetupPlayerHudEvent> {
    @Override
    public Class<SetupPlayerHudEvent> eventType() {
        return SetupPlayerHudEvent.class;
    }

    @Override
    public void execute(SetupPlayerHudEvent event) {
        var payload = event.getPayload();
        final var type = AscensionStats.getComponentType();

        var rpgStats = payload.store.getComponent(payload.entity, type);
        if (rpgStats == null) {
            return;
        }

        var hud = event.getHud().get(ExpBarHud.class);
        if (hud == null) {
            return;
        }

        hud.updateFromComponent(rpgStats);
    }
}
