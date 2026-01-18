/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.events.player_lifecycle;

import com.hypixel.hytale.server.core.event.events.player.PlayerEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import faye.rpg.ui.CustomHudManager;

import javax.annotation.Nonnull;

public class SetupPlayerHudEvent extends PlayerReadyEvent {
    private final PlayerEventPayload payload;
    private final CustomHudManager hud;

    public SetupPlayerHudEvent(PlayerEventPayload payload, CustomHudManager hud) {
        super(payload.entity, payload.player, payload.parent.getReadyId());

        this.payload = payload;
        this.hud = hud;
    }

    public CustomHudManager getHud() {
        return hud;
    }

    public PlayerEventPayload getPayload() {
        return payload;
    }

    @Nonnull
    public String toString() {
        return "SetupPlayerHudEvent " + super.toString();
    }
}
