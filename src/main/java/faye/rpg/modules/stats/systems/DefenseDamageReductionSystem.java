/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.stats.systems;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.component.system.ISystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.modules.stats.data.AscensionStats;
import faye.rpg.systems.IAscensionEntitySystem;

public class DefenseDamageReductionSystem extends EntityEventSystem<EntityStore, Damage> implements IAscensionEntitySystem {

    protected DefenseDamageReductionSystem() {
        super(Damage.class);
    }

    @Override
    public Query<EntityStore> getQuery() {
        return Player.getComponentType();
    }

    @Override
    public void handle(int index, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer, Damage damage) {
        var stats = archetypeChunk.getComponent(index, EntityStatMap.getComponentType());
        if (stats == null) {
            return;
        }

        var defense = stats.get(AscensionStats.defense());
        if (defense == null) {
            return;
        }

        var level = (int) defense.get();
        if (level <= 0) {
            return;
        }

        var amount = (float) (damage.getAmount() * (1 - 0.025 * level));
        damage.setAmount(amount);
    }

    @Override
    public ISystem<EntityStore> toSystem() {
        return this;
    }
}
