package faye.rpg.events;

import com.google.inject.Inject;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.Logger;
import faye.rpg.components.RpgStatsComponent;
import faye.rpg.ui.Hud;

public class OnPlayerReadyHandler implements IEventHandler<PlayerReadyEvent> {
    private final ComponentType<EntityStore, RpgStatsComponent> type;

    private final Logger logger;

    @Inject
    public OnPlayerReadyHandler(ComponentType<EntityStore, RpgStatsComponent> type, Logger logger) {
        this.type = type;
        this.logger = logger;
    }

    @Override
    public Class<PlayerReadyEvent> eventType() {
        return PlayerReadyEvent.class;
    }

    @Override
    public void execute(PlayerReadyEvent event) {
        logger.info("Executing player ready event.");
        Ref<EntityStore> player = event.getPlayerRef();
        if (!player.isValid()) {
            return;
        }

        Store<EntityStore> store = player.getStore();
        World world = store.getExternalData().getWorld();

        PlayerRef playerRef = store.getComponent(player, PlayerRef.getComponentType());
        if (playerRef == null) {
            logger.warn("Unable to get PlayerRef Component.");
            return;
        }

        Player playerComponent = store.getComponent(player, Player.getComponentType());
        if (playerComponent == null) {
            logger.warn("Unable to get Player Component.");
            return;
        }

        HudManager hud = playerComponent.getHudManager();
        Hud rpgHud = new Hud(playerRef);
        hud.setCustomHud(playerRef, rpgHud);

        world.execute(() -> {
            var rpg = store.getComponent(player, type);
            if (rpg == null) {
                rpg = new RpgStatsComponent();
                store.addComponent(player, type, rpg);
            }

            rpgHud.updateFromComponent(rpg);
        });
    }
}
