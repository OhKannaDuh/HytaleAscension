package faye.rpg.modules.stats;

import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import faye.rpg.DependencyModule;
import faye.rpg.commands.IAscensionSubcommand;
import faye.rpg.components.IAscensionComponent;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.modules.stats.commands.OpenAttributePointsPageCommand;
import faye.rpg.modules.stats.components.AscensionExp;
import faye.rpg.modules.stats.handlers.*;
import faye.rpg.modules.stats.systems.PlayerDamageEntitySystem;
import faye.rpg.modules.stats.systems.PlayerDeathSystem;
import faye.rpg.systems.IAscensionEntitySystem;
import faye.rpg.modules.stats.ui.ExpBarHud;
import faye.rpg.ui.IAscensionHudElementFactory;

public class StatsModule extends DependencyModule {
    @Override
    protected void register() {
//        var modifiers = Multibinder.newSetBinder(binder(), IAscensionModifier.class);

        autowire(faye.rpg.modules.stats.data.AscensionStats.class);
        autowire(LoadedEntityStatTypeAssetHandler.class);
    }

    @Override
    protected void registerComponents(Multibinder<IAscensionComponent> binder) {
        binder.addBinding().to(AscensionExp.class);
    }

    @Override
    protected void registerEntitySystems(Multibinder<IAscensionEntitySystem> binder) {
        binder.addBinding().to(PlayerDamageEntitySystem.class);
        binder.addBinding().to(PlayerDeathSystem.class);
    }

    @Override
    protected void registerSubcommands(Multibinder<IAscensionSubcommand> binder) {
        binder.addBinding().to(OpenAttributePointsPageCommand.class).in(Scopes.SINGLETON);
    }

    @Override
    protected void registerEventHandlers(Multibinder<IAscensionEventHandler> binder) {
        binder.addBinding().to(AddRpgStatsHandler.class);
        binder.addBinding().to(AddExpBarHudHandler.class);
        binder.addBinding().to(LevelUpEventHandler.class);
        binder.addBinding().to(AttributePointsAssignmentChangedEventHandler.class);
    }

    @Override
    protected void registerHudElementFactories(Multibinder<IAscensionHudElementFactory> binder) {
        binder.addBinding().to(ExpBarHud.Factory.class);
    }
}
