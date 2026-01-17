package faye.rpg.systems;

import com.hypixel.hytale.component.system.ISystem;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public interface IAscensionEntitySystem {
    ISystem<EntityStore> toSystem();
}
