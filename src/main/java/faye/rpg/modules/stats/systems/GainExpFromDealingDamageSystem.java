package faye.rpg.modules.stats.systems;

import com.google.inject.Inject;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.component.system.ISystem;
import com.hypixel.hytale.protocol.MovementSettings;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import faye.rpg.modules.stats.events.AddExpEvent;
import faye.rpg.systems.IAscensionEntitySystem;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class GainExpFromDealingDamageSystem extends EntityEventSystem<EntityStore, Damage> implements IAscensionEntitySystem {
    @Inject
    protected GainExpFromDealingDamageSystem() {
        super(Damage.class);
    }

    @Override
    public @Nullable Query getQuery() {
        return NPCEntity.getComponentType();
    }

    @Override
    public void handle(int i, @NonNull ArchetypeChunk<EntityStore> archetypeChunk, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer, @NonNull Damage damage) {
        if (!(damage.getSource() instanceof Damage.EntitySource source)) {
            return;
        }

        var sourceRef = source.getRef();
        var player = store.getComponent(sourceRef, Player.getComponentType());
        if (player == null) {
            return;
        }

        var playerRef = store.getComponent(sourceRef, PlayerRef.getComponentType());
        if (playerRef == null) {
            return;
        }

        var amount = (int) Math.floor(damage.getAmount());
        AddExpEvent.dispatchAndBubbleToParty(new AddExpEvent.Payload(
                amount,
                playerRef,
                player,
                player.getWorld()
        ));
    }

    @Override
    public ISystem<EntityStore> toSystem() {
        return this;
    }
}
