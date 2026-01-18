package faye.rpg.modules.stats.handlers;

import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.events.player_lifecycle.SetupPlayerHudEvent;
import faye.rpg.modules.stats.components.AscensionExp;
import faye.rpg.modules.stats.ui.ExpBarHud;

public class AddExpBarHudHandler implements IAscensionEventHandler<SetupPlayerHudEvent> {
    @Override
    public Class<SetupPlayerHudEvent> eventType() {
        return SetupPlayerHudEvent.class;
    }

    @Override
    public void execute(SetupPlayerHudEvent event) {
        var payload = event.getPayload();

        var exp = payload.store.getComponent(payload.entity, AscensionExp.getComponentType());
        if (exp == null) {
            return;
        }

        var stats = payload.store.getComponent(payload.entity, EntityStatMap.getComponentType());
        if (stats == null) {
            return;
        }

        var hud = event.getHud().get(ExpBarHud.class);
        if (hud == null) {
            return;
        }

        hud.updateFromComponent(exp, stats);
    }
}
