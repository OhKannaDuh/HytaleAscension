package faye.rpg.modules.party.events;

import com.hypixel.hytale.component.system.CancellableEcsEvent;
import com.hypixel.hytale.event.IBaseEvent;
import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.event.IEventDispatcher;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.world.World;

import java.util.UUID;
import java.util.function.Supplier;

public class PartyMemberJoin implements IEvent<String> {
    private final UUID partyUuid;
    private final UUID playerUuid;

    private final World world;

    public PartyMemberJoin(UUID partyUuid, UUID playerUuid, World world) {
        this.partyUuid = partyUuid;
        this.playerUuid = playerUuid;
        this.world = world;
    }

    public static void dispatch(UUID partyUuid, UUID playerUuid, World world) {
        var dispatcher = HytaleServer.get().getEventBus().dispatchFor(
                PartyMemberJoin.class,
                world.getName()
        );

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new PartyMemberJoin(partyUuid, playerUuid, world));
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