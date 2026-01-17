package faye.rpg;

import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import faye.rpg.commands.CommandModule;
import faye.rpg.components.ComponentModule;
import faye.rpg.events.EventModule;
import faye.rpg.events.IAscensionEventHandler;
import faye.rpg.lifecycle.LifecycleModule;
import faye.rpg.modules.ModuleManagerModule;
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
}
