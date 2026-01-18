/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.party.events;

import com.hypixel.hytale.component.system.CancellableEcsEvent;
import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.world.World;

import java.util.UUID;

public class PartyMemberLeaveEvent extends CancellableEcsEvent implements IEvent<String> {
    private final UUID partyUuid;
    private final UUID playerUuid;

    private final World world;

    public PartyMemberLeaveEvent(UUID partyUuid, UUID playerUuid, World world) {
        this.partyUuid = partyUuid;
        this.playerUuid = playerUuid;
        this.world = world;
    }

    public static void dispatch(UUID partyUuid, UUID playerUuid, World world) {
        var dispatcher = HytaleServer.get().getEventBus().dispatchFor(
                PartyMemberLeaveEvent.class,
                world.getName()
        );

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new PartyMemberLeaveEvent(partyUuid, playerUuid, world));
        }
    }

    public UUID getPartyUuid() {
        return partyUuid;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public World getWorld() {
        return world;
    }
}
