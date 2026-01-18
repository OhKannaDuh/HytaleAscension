package faye.rpg.events;

import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import faye.rpg.DependencyModule;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.handlers.OnPlayerReadyHandler;

public class EventModule extends DependencyModule {
    @Override
    protected void register() {
        autowire(EventManager.class);
    }

    protected void registerEventHandlers(Multibinder<IAscensionEventHandler> binder) {
        binder.addBinding().to(OnPlayerReadyHandler.class).in(Scopes.SINGLETON);
    }
}
