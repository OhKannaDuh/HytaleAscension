/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.stats.handlers;

import com.google.inject.Inject;
import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import faye.rpg.lifecycle.hooks.IOnPreSetup;
import faye.rpg.modules.stats.data.AscensionStats;

public class LoadedEntityStatTypeAssetHandler implements IOnPreSetup {
    private final EventRegistry events;
    private final AscensionStats stats;

    @Inject
    public LoadedEntityStatTypeAssetHandler(EventRegistry register, AscensionStats stats) {
        this.events = register;
        this.stats = stats;
    }

    public void execute(LoadedAssetsEvent<String, EntityStatType, ?> _event) {
        stats.initialize();
    }

    @Override
    public void preSetup() {
        events.register(LoadedAssetsEvent.class, EntityStatType.class, this::execute);
    }
}
