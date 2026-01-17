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
