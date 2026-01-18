/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.events.player_lifecycle;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class PlayerEventPayload {
    public final PlayerReadyEvent parent;
    public final Ref<EntityStore> entity;
    public final Store<EntityStore> store;
    public final World world;
    public final PlayerRef ref;
    public final Player player;

    public PlayerEventPayload(PlayerReadyEvent parent, Ref<EntityStore> entity, Store<EntityStore> store, World world, PlayerRef ref, Player player) {
        this.parent = parent;
        this.entity = entity;
        this.store = store;
        this.world = world;
        this.ref = ref;
        this.player = player;
    }
}
