package faye.rpg.events.player_lifecycle;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import faye.rpg.ui.CustomHudManager;

import javax.annotation.Nonnull;

public class SetupPlayerDoneEvent extends PlayerReadyEvent {
    private final PlayerEventPayload payload;

    public SetupPlayerDoneEvent(PlayerEventPayload payload) {
        super(payload.entity, payload.player, payload.parent.getReadyId());

        this.payload = payload;
    }

    public PlayerEventPayload getPayload() {
        return payload;
    }

    @Nonnull
    public String toString() {
        return "SetupPlayerHudEvent " + super.toString();
    }
}
