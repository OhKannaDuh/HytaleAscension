package faye.rpg.modules.party.events;

import com.hypixel.hytale.component.system.CancellableEcsEvent;
import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.world.World;

import java.util.UUID;

public class PartyMemberLeave extends CancellableEcsEvent implements IEvent<String> {
    private final UUID partyUuid;
    private final UUID playerUuid;

    private final World world;

    public PartyMemberLeave(UUID partyUuid, UUID playerUuid, World world) {
        this.partyUuid = partyUuid;
        this.playerUuid = playerUuid;
        this.world = world;
    }

    public static void dispatch(UUID partyUuid, UUID playerUuid, World world) {
        var dispatcher = HytaleServer.get().getEventBus().dispatchFor(
                PartyMemberLeave.class,
                world.getName()
        );

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new PartyMemberLeave(partyUuid, playerUuid, world));
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
