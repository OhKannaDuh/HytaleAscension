package faye.rpg.components;

import com.google.inject.Inject;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.lifecycle.hooks.IOnPreSetup;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ComponentManager implements IOnPreSetup {
    public static ComponentManager INSTANCE;

    private final ComponentRegistryProxy<EntityStore> store;

    private final Set<IAscensionComponent> components;

    private final Map<Class<?>, ComponentType<EntityStore, ?>> types = new HashMap<>();

    @Inject
    public ComponentManager(ComponentRegistryProxy<EntityStore> store, Set<IAscensionComponent> components) {
        this.store = store;
        this.components = components;
        INSTANCE = this;
    }

    @Override
    public void preSetup() {
        components.forEach(this::register);
    }


    private <T extends Component<EntityStore>> void register(IAscensionComponent<T> component) {
        var type = store.registerComponent(
                component.componentClass(),
                component.id(),
                component.codec()
        );

        types.put(component.componentClass(), type);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Component<EntityStore>> ComponentType<EntityStore, T> get(Class<T> componentClass) {
        return (ComponentType<EntityStore, T>) INSTANCE.types.get(componentClass);
    }
}
