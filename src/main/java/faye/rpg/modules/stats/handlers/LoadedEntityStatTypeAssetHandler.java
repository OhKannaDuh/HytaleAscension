package faye.rpg.modules.stats.handlers;

import com.google.inject.Inject;
import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import faye.rpg.Logger;
import faye.rpg.lifecycle.hooks.IOnPreSetup;
import faye.rpg.modules.stats.data.AscensionStats;

public class LoadedEntityStatTypeAssetHandler implements IOnPreSetup {
    private final EventRegistry events;
    private final AscensionStats stats;

    private final Logger logger;

    @Inject
    public LoadedEntityStatTypeAssetHandler(EventRegistry register, AscensionStats stats, Logger logger) {
        this.events = register;
        this.stats = stats;
        this.logger = logger;
    }

    public void execute(LoadedAssetsEvent<String, EntityStatType, ?> _event) {

        logger.info("Entity Stat Type Assets Loaded");
        for (var key : _event.getLoadedAssets().keySet()) {
            logger.info("Loaded Stat: " + key);
        }
        stats.initialize();
    }

    @Override
    public void preSetup() {
        events.register(LoadedAssetsEvent.class, EntityStatType.class, this::execute);
    }
}
