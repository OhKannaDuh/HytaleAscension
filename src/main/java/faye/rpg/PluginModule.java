package faye.rpg;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class PluginModule extends AbstractModule {
    private final Plugin plugin;

    public PluginModule(Plugin plugin) {
        this.plugin = plugin;
    }

    @Provides
    @Singleton
    HytaleLogger providesHytaleLogger() {
        return plugin.getLogger();
    }

    @Provides
    @Singleton
    ComponentRegistryProxy<EntityStore> provideEntityStoreRegistry() {
        return plugin.getEntityStoreRegistry();
    }

    @Provides
    @Singleton
    EventRegistry provideEventRegistry() {
        return plugin.getEventRegistry();
    }

    @Provides
    @Singleton
    CommandRegistry provideCommandRegistry() {
        return plugin.getCommandRegistry();
    }
}
