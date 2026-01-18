/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.stats.events;

import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;

public class LevelUpEvent implements IEvent<String> {

    private final int level;

    private final PlayerRef player;

    public LevelUpEvent(int level, PlayerRef player) {
        this.level = level;
        this.player = player;
    }

    public static void dispatch(int level, PlayerRef player, World world) {
        var dispatcher = HytaleServer.get().getEventBus().dispatchFor(
                LevelUpEvent.class,
                world.getName()
        );

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new LevelUpEvent(level, player));
        }
    }

    public int getLevel() {
        return level;
    }

    public PlayerRef getPlayer() {
        return player;
    }
}
