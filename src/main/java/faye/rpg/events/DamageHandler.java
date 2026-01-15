package faye.rpg.events;

import com.google.inject.Inject;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import faye.rpg.components.RpgStatsComponent;
import faye.rpg.ui.Hud;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class DamageHandler extends EntityEventSystem<EntityStore, Damage> {
    private final ComponentType<EntityStore, RpgStatsComponent> rpgType;

    @Inject
    protected DamageHandler(ComponentType<EntityStore, RpgStatsComponent> rpgType) {
        super(Damage.class);
        this.rpgType = rpgType;
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
        var rpg = sourceRef.getStore().getComponent(sourceRef, rpgType);
        if (rpg == null) {
            return;
        }

        var player = store.getComponent(sourceRef, Player.getComponentType());
        if (player == null) {
            return;
        }

        rpg.addExp((int) Math.floor(damage.getAmount()));
        if (player.getHudManager().getCustomHud() instanceof Hud hud) {
            hud.updateFromComponent(rpg);
        }
    }
}
