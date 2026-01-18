package faye.rpg.handlers;

import com.google.inject.Inject;
import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import faye.rpg.Logger;
import faye.rpg.effects.IAscensionEffectConsumer;
import faye.rpg.lifecycle.hooks.IOnPreSetup;

import java.util.Set;

public class LoadedEntityEffectAssetHandler implements IOnPreSetup {
    private final EventRegistry events;

    private final Set<IAscensionEffectConsumer> consumers;

    private final Logger logger;

    @Inject
    public LoadedEntityEffectAssetHandler(EventRegistry register, Set<IAscensionEffectConsumer> consumers, Logger logger) {
        this.events = register;
        this.consumers = consumers;
        this.logger = logger;
    }

    public void execute(LoadedAssetsEvent<String, EntityEffect, ?> event) {
        for (var entry : event.getLoadedAssets().entrySet()) {
            logger.info("Effect loaded: " + entry.getKey());
        }

        consumers.forEach(c -> c.consume(event));
    }

    @Override
    public void preSetup() {
        events.register(LoadedAssetsEvent.class, EntityEffect.class, this::execute);
    }
}
