package faye.rpg.modules.stats.events;

import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;

public record AttributePointsAssignmentChangedEvent(PlayerRef player, World world) implements IEvent<String> {

    public static void dispatch(PlayerRef player, World world) {
        var dispatcher = HytaleServer.get().getEventBus().dispatchFor(
                AttributePointsAssignmentChangedEvent.class,
                world.getName()
        );

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new AttributePointsAssignmentChangedEvent(player, world));
        }
    }
}
