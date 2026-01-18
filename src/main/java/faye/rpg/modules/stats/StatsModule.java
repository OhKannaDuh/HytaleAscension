package faye.rpg.modules.stats;

import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import faye.rpg.DependencyModule;
import faye.rpg.commands.IAscensionSubcommand;
import faye.rpg.components.IAscensionComponent;
import faye.rpg.effects.IAscensionEffectConsumer;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.handlers.LoadedEntityEffectAssetHandler;
import faye.rpg.modules.stats.commands.GiveExpCommand;
import faye.rpg.modules.stats.commands.OpenAttributePointsPageCommand;
import faye.rpg.modules.stats.components.AscensionExp;
import faye.rpg.modules.stats.data.AscensionStats;
import faye.rpg.modules.stats.data.effects.AscensionAttributeEffects;
import faye.rpg.modules.stats.data.effects.IAscensionAttributeEffectApplicator;
import faye.rpg.modules.stats.data.effects.VitalityHealthRegenEffectApplicator;
import faye.rpg.modules.stats.data.modifiers.IAscensionAttributeModifier;
import faye.rpg.modules.stats.data.modifiers.IntelligenceManaAttributeModifier;
import faye.rpg.modules.stats.data.modifiers.VitalityHealthAttributeModifier;
import faye.rpg.modules.stats.data.modifiers.VitalityOxygenAttributeModifier;
import faye.rpg.modules.stats.handlers.*;
import faye.rpg.modules.stats.systems.DefenseDamageReductionSystem;
import faye.rpg.modules.stats.systems.DexterityCriticalHitSystem;
import faye.rpg.modules.stats.systems.GainExpFromDealingDamageSystem;
import faye.rpg.modules.stats.systems.PlayerDeathSystem;
import faye.rpg.systems.IAscensionEntitySystem;
import faye.rpg.modules.stats.ui.ExpBarHud;
import faye.rpg.ui.IAscensionHudElementFactory;

public class StatsModule extends DependencyModule {
    @Override
    protected void register() {
        var modifiers = Multibinder.newSetBinder(binder(), IAscensionAttributeModifier.class);
        modifiers.addBinding().to(VitalityHealthAttributeModifier.class).in(Scopes.SINGLETON);
        modifiers.addBinding().to(VitalityOxygenAttributeModifier.class).in(Scopes.SINGLETON);
        modifiers.addBinding().to(IntelligenceManaAttributeModifier.class).in(Scopes.SINGLETON);

        var effects = Multibinder.newSetBinder(binder(), IAscensionAttributeEffectApplicator.class);
        effects.addBinding().to(VitalityHealthRegenEffectApplicator.class).in(Scopes.SINGLETON);

        bind(AscensionStats.class);
        bind(AscensionAttributeEffects.class);

        autowire(LoadedEntityStatTypeAssetHandler.class);
        autowire(LoadedEntityEffectAssetHandler.class);
    }

    @Override
    protected void registerComponents(Multibinder<IAscensionComponent> binder) {
        binder.addBinding().to(AscensionExp.class);
    }

    @Override
    protected void registerEntitySystems(Multibinder<IAscensionEntitySystem> binder) {
        binder.addBinding().to(GainExpFromDealingDamageSystem.class);
        binder.addBinding().to(PlayerDeathSystem.class);
        binder.addBinding().to(DefenseDamageReductionSystem.class);
        binder.addBinding().to(DexterityCriticalHitSystem.class);
    }

    @Override
    protected void registerSubcommands(Multibinder<IAscensionSubcommand> binder) {
        binder.addBinding().to(OpenAttributePointsPageCommand.class).in(Scopes.SINGLETON);
        binder.addBinding().to(GiveExpCommand.class).in(Scopes.SINGLETON);
    }

    @Override
    protected void registerEventHandlers(Multibinder<IAscensionEventHandler> binder) {
        binder.addBinding().to(SetupPlayerComponentsEventHandler.class);
        binder.addBinding().to(SetupPlayerHudEventHandler.class);
        binder.addBinding().to(AddExpEventHandler.class);
        binder.addBinding().to(LevelUpEventHandler.class);
    }

    @Override
    protected void registerHudElementFactories(Multibinder<IAscensionHudElementFactory> binder) {
        binder.addBinding().to(ExpBarHud.Factory.class);
    }

    protected void registerEffectConsumers(Multibinder<IAscensionEffectConsumer> binder) {
        binder.addBinding().to(AscensionAttributeEffects.class);
    }
}
