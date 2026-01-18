/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.stats.handlers;

import com.google.inject.Inject;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import faye.rpg.Logger;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.modules.stats.events.LevelUpEvent;

public class LevelUpEventHandler implements IAscensionEventHandler<LevelUpEvent> {
    private final Logger logger;

    @Inject
    public LevelUpEventHandler(Logger logger) {
        this.logger = logger;

    }


    @Override
    public Class<LevelUpEvent> eventType() {
        return LevelUpEvent.class;
    }

    @Override
    public void execute(LevelUpEvent event) {
        EventTitleUtil.showEventTitleToPlayer(
                event.getPlayer(),
                Message.raw("Level Up!"),
                Message.raw("You are now level " + event.getLevel() + "."),
                true,
                null,
                2.0f,
                0.5f,
                0.5f
        );
    }
}
