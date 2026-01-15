package faye.rpg;

import com.google.inject.Scopes;
import faye.rpg.commands.CommandModule;
import faye.rpg.components.ComponentModule;
import faye.rpg.events.EventModule;
import faye.rpg.lifecycle.LifecycleModule;
import faye.rpg.systems.SystemModule;

public class Bootstrapper extends DependencyModule {
    private final Plugin plugin;

    public Bootstrapper(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        // Core
        install(new PluginModule(plugin));
        install(new LifecycleModule());
        install(new CommandModule());
        install(new ComponentModule());
        install(new EventModule());
        install(new SystemModule());

        // @todo put in module manager
//        install(new DebugModule());

        bind(Plugin.class).toInstance(plugin);
        bind(Logger.class).in(Scopes.SINGLETON);
    }
}
