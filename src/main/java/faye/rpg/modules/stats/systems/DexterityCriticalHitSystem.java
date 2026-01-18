/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.stats.systems;

import com.google.inject.Inject;
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
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import faye.rpg.modules.stats.data.AscensionStats;
import faye.rpg.systems.IAscensionEntitySystem;

import java.util.Random;

public class DexterityCriticalHitSystem extends EntityEventSystem<EntityStore, Damage> implements IAscensionEntitySystem {
    @Inject
    protected DexterityCriticalHitSystem() {
        super(Damage.class);
    }

    @Override
    public Query<EntityStore> getQuery() {
        return NPCEntity.getComponentType();
    }

    @Override
    public void handle(int i, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer, Damage damage) {
        if (!(damage.getSource() instanceof Damage.EntitySource source)) {
            return;
        }

        var sourceRef = source.getRef();
        var player = store.getComponent(sourceRef, Player.getComponentType());
        if (player == null) {
            return;
        }

        var stats = store.getComponent(sourceRef, EntityStatMap.getComponentType());
        if (stats == null) {
            return;
        }

        var dexterity = stats.get(AscensionStats.dexterity());
        if (dexterity == null) {
            return;
        }

        var chance = Math.min(0.02f * dexterity.get(), 1f);
        var roll = new Random().nextFloat();
        if (roll >= chance) {
            return;
        }

        final float criticalStrikeBonus = 1.5f;
        damage.setAmount(damage.getAmount() * criticalStrikeBonus);
    }

    @Override
    public ISystem<EntityStore> toSystem() {
        return this;
    }
}
