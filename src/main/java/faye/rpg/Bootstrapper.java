/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg;

import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import faye.rpg.commands.CommandModule;
import faye.rpg.commands.IAscensionSubcommand;
import faye.rpg.commands.SourceCommand;
import faye.rpg.components.ComponentModule;
import faye.rpg.events.EventModule;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.lifecycle.LifecycleModule;
import faye.rpg.modules.ModuleManagerModule;
import faye.rpg.modules.stats.handlers.AttributePointsAssignmentChangedEventHandler;
import faye.rpg.systems.SystemModule;

public class Bootstrapper extends DependencyModule {
    private final Plugin plugin;

    public Bootstrapper(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void register() {
        // Core
        install(new PluginModule(plugin));
        install(new LifecycleModule());
        install(new CommandModule());
        install(new ComponentModule());
        install(new EventModule());
        install(new SystemModule());
        install(new ModuleManagerModule());

        bind(Plugin.class).toInstance(plugin);
        bind(Logger.class).in(Scopes.SINGLETON);
    }

    protected void registerEventHandlers(Multibinder<IAscensionEventHandler> binder) {
        binder.addBinding().to(AttributePointsAssignmentChangedEventHandler.class);
    }

    protected void registerSubcommands(Multibinder<IAscensionSubcommand> binder) {
        binder.addBinding().to(SourceCommand.class);
    }
}
