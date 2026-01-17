package faye.rpg.modules.stats;

import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import faye.rpg.DependencyModule;
import faye.rpg.commands.IAscensionSubcommand;
import faye.rpg.components.IAscensionComponent;
import faye.rpg.events.IAscensionEventHandler;
import faye.rpg.modules.stats.commands.SkillPointsCommand;
import faye.rpg.modules.stats.components.AscensionStats;
import faye.rpg.modules.stats.events.AddExpBarHudHandler;
import faye.rpg.modules.stats.events.AddRpgStatsHandler;
import faye.rpg.modules.stats.events.LevelUpEventHandler;
import faye.rpg.modules.stats.systems.PlayerDamageEntitySystem;
import faye.rpg.modules.stats.systems.PlayerDeathSystem;
import faye.rpg.systems.IAscensionEntitySystem;
import faye.rpg.modules.stats.ui.ExpBarHud;
import faye.rpg.ui.IAscensionHudElementFactory;

public class StatsModule extends DependencyModule {
    @Override
    protected void register() {
    }

    @Override
    protected void registerComponents(Multibinder<IAscensionComponent> binder) {
        binder.addBinding().to(AscensionStats.class);
    }

    @Override
    protected void registerEntitySystems(Multibinder<IAscensionEntitySystem> binder) {
        binder.addBinding().to(PlayerDamageEntitySystem.class);
        binder.addBinding().to(PlayerDeathSystem.class);
    }

    @Override
    protected void registerSubcommands(Multibinder<IAscensionSubcommand> binder) {
        binder.addBinding().to(SkillPointsCommand.class).in(Scopes.SINGLETON);
    }

    @Override
    protected void registerEventHandlers(Multibinder<IAscensionEventHandler> binder) {
        binder.addBinding().to(AddRpgStatsHandler.class);
        binder.addBinding().to(AddExpBarHudHandler.class);
        binder.addBinding().to(LevelUpEventHandler.class);
    }

    @Override
    protected void registerHudElementFactories(Multibinder<IAscensionHudElementFactory> binder) {
        binder.addBinding().to(ExpBarHud.Factory.class);
    }
}
