package faye.rpg.events;

import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import faye.rpg.DependencyModule;

public class EventModule extends DependencyModule {
    @Override
    protected void configure() {
        Multibinder<IEventHandler<?>> handlers = Multibinder.newSetBinder(binder(), new TypeLiteral<IEventHandler<?>>() {});
        handlers.addBinding().to(OnPlayerReadyHandler.class).in(Scopes.SINGLETON);

        bind(DamageHandler.class).in(Scopes.SINGLETON);

        autowire(EventManager.class);
    }
}
