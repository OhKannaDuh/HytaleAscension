package faye.rpg.components;

import com.google.inject.Inject;
import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class ComponentManager
{
    private final ComponentRegistryProxy<EntityStore> store;

    private ComponentType<EntityStore, RpgStatsComponent> rpg;

    @Inject
    public ComponentManager(ComponentRegistryProxy<EntityStore> store) {
        this.store = store;
    }

    public synchronized ComponentType<EntityStore, RpgStatsComponent> rpgType() {
        if (rpg == null) {
            rpg = store.registerComponent(RpgStatsComponent.class, "Rpg", RpgStatsComponent.CODEC);
        }

        return rpg;
    }
}
