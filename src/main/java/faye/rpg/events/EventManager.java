package faye.rpg.events;

import com.google.inject.Inject;
import com.hypixel.hytale.event.EventRegistry;
import faye.rpg.lifecycle.hooks.IOnPostSetup;

import java.util.Set;

public final class EventManager implements IOnPostSetup {
    private final EventRegistry registry;
    private final Set<IEventHandler<?>> handlers;

    @Inject
    public EventManager(EventRegistry registry, Set<IEventHandler<?>> handlers) {
        this.registry = registry;
        this.handlers = handlers;
    }

    @Override
    public void postSetup() {
        for (IEventHandler<?> h : handlers) {
            registerOne(registry, h);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T> void registerOne(EventRegistry registry, IEventHandler<?> raw) {
        IEventHandler<T> h = (IEventHandler) raw;
        registry.registerGlobal(h.eventType(), h::execute);
    }
}