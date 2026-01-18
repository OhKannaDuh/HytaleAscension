/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.party.events;

import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.world.World;

import java.util.UUID;

public record PartyMemberJoinEvent(UUID partyUuid, UUID playerUuid, World world) implements IEvent<String> {

    public static void dispatch(UUID partyUuid, UUID playerUuid, World world) {
        var dispatcher = HytaleServer.get().getEventBus().dispatchFor(
                PartyMemberJoinEvent.class,
                world.getName()
        );

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new PartyMemberJoinEvent(partyUuid, playerUuid, world));
        }
    }
}