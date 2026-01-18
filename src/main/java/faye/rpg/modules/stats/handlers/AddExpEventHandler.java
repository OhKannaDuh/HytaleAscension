/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.stats.handlers;

import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.modules.stats.components.AscensionExp;
import faye.rpg.modules.stats.events.AddExpEvent;
import faye.rpg.modules.stats.events.LevelUpEvent;
import faye.rpg.modules.stats.ui.ExpBarHud;
import faye.rpg.ui.CustomHudManager;

public class AddExpEventHandler implements IAscensionEventHandler<AddExpEvent> {
    @Override
    public void execute(AddExpEvent event) {
        var payload = event.payload();

        var entity = payload.playerRef().getReference();
        if (entity == null) {
            return;
        }

        var store = payload.world().getEntityStore().getStore();
        if (store == null) {
            return;
        }

        var exp = store.getComponent(entity, AscensionExp.getComponentType());
        if (exp == null) {
            return;
        }

        var levelledUp = exp.addExp(payload.exp());
        if (payload.player().getHudManager().getCustomHud() instanceof CustomHudManager wrapper) {
            var hud = wrapper.get(ExpBarHud.class);
            if (hud != null) {
                var stats = store.getComponent(entity, EntityStatMap.getComponentType());
                if (stats != null) {
                    hud.updateFromComponent(exp, stats);
                }

            }
        }

        if (levelledUp) {
            LevelUpEvent.dispatch(exp.getLevel(), payload.playerRef(), payload.world());
        }
    }
}
