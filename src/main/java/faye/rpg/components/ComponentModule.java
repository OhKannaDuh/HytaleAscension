package faye.rpg.components;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.DependencyModule;

public class ComponentModule extends DependencyModule {
    @Override
    protected void register() {
        autowire(ComponentManager.class);
    }
}
