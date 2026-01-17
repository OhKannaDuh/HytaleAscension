package faye.rpg.systems;

import com.google.inject.Inject;
import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.component.system.ISystem;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.lifecycle.hooks.IOnPostSetup;

import java.util.Set;

public class SystemManager implements IOnPostSetup {
    private final ComponentRegistryProxy<EntityStore> registry;
    private final Set<IAscensionEntitySystem> systems;

    @Inject
    public SystemManager(ComponentRegistryProxy<EntityStore> registry, Set<IAscensionEntitySystem> systems) {
        this.registry = registry;
        this.systems = systems;
    }

    @Override
    public void postSetup() {
        systems.forEach(s -> registry.registerSystem(s.toSystem()));
    }
}
