package faye.rpg.handlers;

import com.google.inject.Inject;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.event.IEventDispatcher;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.Logger;
import faye.rpg.events.player_lifecycle.PlayerEventPayload;
import faye.rpg.events.player_lifecycle.SetupPlayerComponentsEvent;
import faye.rpg.events.player_lifecycle.SetupPlayerDoneEvent;
import faye.rpg.events.player_lifecycle.SetupPlayerHudEvent;
import faye.rpg.ui.CustomHudManager;
import faye.rpg.ui.IAscensionHudElementFactory;

import java.util.Set;
import java.util.function.Supplier;

public class OnPlayerReadyHandler implements IAscensionEventHandler<PlayerReadyEvent> {
    private final Logger logger;

    private final Set<IAscensionHudElementFactory> factories;

    @Inject
    public OnPlayerReadyHandler(Logger logger, Set<IAscensionHudElementFactory> factories) {
        this.logger = logger;
        this.factories = factories;
    }

    @Override
    public Class<PlayerReadyEvent> eventType() {
        return PlayerReadyEvent.class;
    }

    @Override
    public void execute(PlayerReadyEvent event) {
        logger.info("Executing player ready event.");
        Ref<EntityStore> entity = event.getPlayerRef();
        if (!entity.isValid()) {
            return;
        }

        Store<EntityStore> store = entity.getStore();
        World world = store.getExternalData().getWorld();
        Player player = event.getPlayer();

        PlayerRef ref = store.getComponent(entity, PlayerRef.getComponentType());
        if (ref == null) {
            logger.warn("Unable to get PlayerRef Component.");
            return;
        }

        var hud = player.getHudManager();
        var manager = new CustomHudManager(ref, factories);
        hud.setCustomHud(ref, manager);

        var payload = new PlayerEventPayload(event, entity, store, world, ref, player);

        dispatchEvent(SetupPlayerComponentsEvent.class, world.getName(), () -> new SetupPlayerComponentsEvent(payload));
        dispatchEvent(SetupPlayerHudEvent.class, world.getName(), () -> new SetupPlayerHudEvent(payload, manager));
        dispatchEvent(SetupPlayerDoneEvent.class, world.getName(), () -> new SetupPlayerDoneEvent(payload));
    }

    private <TKeyType, EventType extends IEvent<TKeyType>> void dispatchEvent(
            Class<EventType> eventClass,
            TKeyType key,
            Supplier<EventType> eventSupplier
    ) {
        IEventDispatcher<EventType, EventType> dispatcher =
                HytaleServer.get().getEventBus().dispatchFor(eventClass, key);

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(eventSupplier.get());
        }
    }
}
