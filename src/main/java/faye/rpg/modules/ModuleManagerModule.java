/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules;

import faye.rpg.DependencyModule;
import faye.rpg.modules.party.PartyModule;
import faye.rpg.modules.stats.StatsModule;

public class ModuleManagerModule extends DependencyModule {
    @Override
    protected void register() {
        install(new PartyModule());
        install(new StatsModule());
    }
}
