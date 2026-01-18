/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import faye.rpg.commands.IAscensionSubcommand;
import faye.rpg.components.IAscensionComponent;
import faye.rpg.effects.IAscensionEffectConsumer;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.lifecycle.hooks.*;
import faye.rpg.systems.IAscensionEntitySystem;
import faye.rpg.ui.IAscensionHudElementFactory;

public abstract class DependencyModule extends AbstractModule {
    @Override
    protected void configure() {
        registerSubcommands(Multibinder.newSetBinder(binder(), IAscensionSubcommand.class));
        registerComponents(Multibinder.newSetBinder(binder(), IAscensionComponent.class));
        registerEventHandlers(Multibinder.newSetBinder(binder(), IAscensionEventHandler.class));
        registerEntitySystems(Multibinder.newSetBinder(binder(), IAscensionEntitySystem.class));
        registerHudElementFactories(Multibinder.newSetBinder(binder(), IAscensionHudElementFactory.class));
        registerEffectConsumers(Multibinder.newSetBinder(binder(), IAscensionEffectConsumer.class));
        register();
    }

    protected void registerSubcommands(Multibinder<IAscensionSubcommand> binder) {
    }

    protected void registerComponents(Multibinder<IAscensionComponent> binder) {
    }

    protected void registerEventHandlers(Multibinder<IAscensionEventHandler> binder) {
    }

    protected void registerEntitySystems(Multibinder<IAscensionEntitySystem> binder) {
    }

    protected void registerHudElementFactories(Multibinder<IAscensionHudElementFactory> binder) {
    }

    protected void registerEffectConsumers(Multibinder<IAscensionEffectConsumer> binder) {
    }

    protected abstract void register();

    protected <T> void autowire(Class<T> impl) {
        bind(impl).in(Scopes.SINGLETON);

        if (IOnPreSetup.class.isAssignableFrom(impl)) {
            Multibinder.newSetBinder(binder(), IOnPreSetup.class)
                    .addBinding()
                    .to(impl.asSubclass(IOnPreSetup.class));
        }

        if (IOnSetup.class.isAssignableFrom(impl)) {
            Multibinder.newSetBinder(binder(), IOnSetup.class)
                    .addBinding()
                    .to(impl.asSubclass(IOnSetup.class));
        }

        if (IOnPostSetup.class.isAssignableFrom(impl)) {
            Multibinder.newSetBinder(binder(), IOnPostSetup.class)
                    .addBinding()
                    .to(impl.asSubclass(IOnPostSetup.class));
        }

        if (IOnStart.class.isAssignableFrom(impl)) {
            Multibinder.newSetBinder(binder(), IOnStart.class)
                    .addBinding()
                    .to(impl.asSubclass(IOnStart.class));
        }

        if (IOnShutdown.class.isAssignableFrom(impl)) {
            Multibinder.newSetBinder(binder(), IOnShutdown.class)
                    .addBinding()
                    .to(impl.asSubclass(IOnShutdown.class));
        }
    }
}
