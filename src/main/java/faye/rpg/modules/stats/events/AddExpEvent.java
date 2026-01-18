package faye.rpg.modules.stats.events;

import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;

public record AddExpEvent(Payload payload, ShareType shareType) implements IEvent<String> {
    public static void dispatchAndBubbleToParty(Payload payload) {
        var dispatcher = HytaleServer.get().getEventBus().dispatchFor(
                AddExpEvent.class,
                payload.world().getName()
        );

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new AddExpEvent(payload, ShareType.WithParty));
        }
    }

    public static void dispatchWithNoBubbleToParty(Payload payload) {
        var dispatcher = HytaleServer.get().getEventBus().dispatchFor(
                AddExpEvent.class,
                payload.world().getName()
        );

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new AddExpEvent(payload, ShareType.None));
        }
    }

    public record Payload(int exp, PlayerRef playerRef, Player player, World world) {
    }

    public enum ShareType {
        WithParty,
        None,
    }
}
