/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.events.player_lifecycle;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;

import javax.annotation.Nonnull;

public class SetupPlayerComponentsEvent extends PlayerReadyEvent {
    private final PlayerEventPayload payload;

    public SetupPlayerComponentsEvent(PlayerEventPayload payload) {
        super(payload.entity, payload.player, payload.parent.getReadyId());
        this.payload = payload;
    }

    public PlayerEventPayload getPayload() {
        return payload;
    }

    @Nonnull
    public String toString() {
        return "SetupPlayerComponentsEvent " + super.toString();
    }
}
