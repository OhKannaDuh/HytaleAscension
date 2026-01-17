package faye.rpg.systems;

import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.hypixel.hytale.component.system.ISystem;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.DependencyModule;
import faye.rpg.modules.stats.systems.PlayerDamageEntitySystem;
import faye.rpg.modules.stats.systems.PlayerDeathSystem;

public class SystemModule extends DependencyModule {
    @Override
    protected void register() {
        autowire(SystemManager.class);
    }
}
