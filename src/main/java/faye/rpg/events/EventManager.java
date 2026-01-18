/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.events;

import com.google.inject.Inject;
import com.hypixel.hytale.event.EventRegistry;
import faye.rpg.Logger;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.lifecycle.hooks.IOnPostSetup;

import java.util.Set;

public final class EventManager implements IOnPostSetup {
    private final EventRegistry registry;
    private final Set<IAscensionEventHandler> handlers;

    private final Logger logger;

    @Inject
    public EventManager(EventRegistry registry, Set<IAscensionEventHandler> handlers, Logger logger) {
        this.registry = registry;
        this.handlers = handlers;
        this.logger = logger;
    }

    @Override
    public void postSetup() {
        logger.info("Adding " + (long) handlers.size() + " event handlers");
        handlers.forEach(this::register);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> void register(IAscensionEventHandler<?> raw) {
        IAscensionEventHandler<T> h = (IAscensionEventHandler) raw;
        registry.registerGlobal(h.eventType(), h::execute);
    }
}