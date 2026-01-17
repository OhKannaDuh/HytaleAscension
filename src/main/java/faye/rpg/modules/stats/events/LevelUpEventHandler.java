package faye.rpg.modules.stats.events;

import com.google.inject.Inject;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import faye.rpg.Logger;
import faye.rpg.events.IAscensionEventHandler;

public class LevelUpEventHandler implements IAscensionEventHandler<LevelUpEvent> {
    private final Logger logger;

    @Inject
    public LevelUpEventHandler(Logger logger) {
        this.logger = logger;

    }


    @Override
    public Class<LevelUpEvent> eventType() {
        return LevelUpEvent.class;
    }

    @Override
    public void execute(LevelUpEvent event) {
        logger.info("Handling level up event...");

        EventTitleUtil.showEventTitleToPlayer(
                event.getPlayer(),
                Message.raw("Level Up!"),
                Message.raw("You are now level " + event.getLevel() + "."),
                true,
                null,
                2.0f,
                0.5f,
                0.5f
        );
    }
}
