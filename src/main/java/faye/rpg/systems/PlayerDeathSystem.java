package faye.rpg.systems;

import com.google.inject.Inject;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.components.RpgStatsComponent;
import faye.rpg.data.LevelManager;
import faye.rpg.ui.Hud;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PlayerDeathSystem extends DeathSystems.OnDeathSystem {

    public final static double EXP_LOSS_PERCENTAGE = 0.1;


    private final ComponentType<EntityStore, RpgStatsComponent> rpgStatsType;

    @Inject
    public PlayerDeathSystem(ComponentType<EntityStore, RpgStatsComponent> rpgStatsType) {
        this.rpgStatsType = rpgStatsType;
    }


    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Player.getComponentType();
    }

    @Override
    public void onComponentAdded(@NonNull Ref<EntityStore> ref, @NonNull DeathComponent deathComponent, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer) {
        var rpgStats = store.getComponent(ref, rpgStatsType);
        if (rpgStats == null) {
            return;
        }

        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) {
            return;
        }

        int total = rpgStats.getTotalExp();
        int level = LevelManager.getLevelFromTotalExp(total);

        int levelFloorTotal = LevelManager.getTotalExpRequiredForLevel(level);
        int into = total - levelFloorTotal;

        int levelBar = LevelManager.getRequiredExpForLevelUpAt(level);

        int loss = (int) Math.floor(levelBar * EXP_LOSS_PERCENTAGE);

        int newTotal = Math.max(levelFloorTotal, total - loss);

        rpgStats.setTotalExp(newTotal);
        if (player.getHudManager().getCustomHud() instanceof Hud hud) {
            hud.updateFromComponent(rpgStats);
        }

    }
}
