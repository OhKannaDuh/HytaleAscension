package faye.rpg.systems;

import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.hypixel.hytale.component.system.ISystem;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.DependencyModule;

public class SystemModule extends DependencyModule {    @Override
protected void configure() {
    Multibinder<ISystem<EntityStore>> handlers = Multibinder.newSetBinder(binder(), new TypeLiteral<ISystem<EntityStore>>() {});
    handlers.addBinding().to(PlayerDamageEntitySystem.class).in(Scopes.SINGLETON);
    handlers.addBinding().to(PlayerDeathSystem.class).in(Scopes.SINGLETON);

    autowire(SystemManager.class);
}
}
