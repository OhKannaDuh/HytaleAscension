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
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import faye.rpg.modules.stats.components.AscensionExp;
import faye.rpg.modules.stats.events.LevelUpEvent;
import faye.rpg.systems.IAscensionEntitySystem;
import faye.rpg.ui.CustomHudManager;
import faye.rpg.modules.stats.ui.ExpBarHud;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PlayerDamageEntitySystem extends EntityEventSystem<EntityStore, Damage> implements IAscensionEntitySystem {
    @Inject
    protected PlayerDamageEntitySystem() {
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
        var exp = sourceRef.getStore().getComponent(sourceRef, AscensionExp.getComponentType());
        if (exp == null) {
            return;
        }

        var player = store.getComponent(sourceRef, Player.getComponentType());
        if (player == null) {
            return;
        }
        var levelledUp = exp.addExp((int) Math.floor(damage.getAmount()));
        if (player.getHudManager().getCustomHud() instanceof CustomHudManager wrapper) {
            var hud = wrapper.get(ExpBarHud.class);
            if (hud != null) {
                var stats = store.getComponent(sourceRef, EntityStatMap.getComponentType());
                if (stats != null) {
                    hud.updateFromComponent(exp, stats);
                }

            }
        }

        if (levelledUp) {
            var playerRef = store.getComponent(sourceRef, PlayerRef.getComponentType());
            if (playerRef == null) {
                return;
            }

            var world = player.getWorld();
            if (world == null) {
                return;
            }

            LevelUpEvent.dispatch(exp.getLevel(), playerRef, world);
        }
    }

    @Override
    public ISystem<EntityStore> toSystem() {
        return this;
    }
}
