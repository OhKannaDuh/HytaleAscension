package faye.rpg.modules.stats.systems;

import com.google.inject.Inject;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.ISystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.modules.stats.components.AscensionStats;
import faye.rpg.modules.stats.data.LevelManager;
import faye.rpg.systems.IAscensionEntitySystem;
import faye.rpg.ui.CustomHudManager;
import faye.rpg.modules.stats.ui.ExpBarHud;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PlayerDeathSystem extends DeathSystems.OnDeathSystem implements IAscensionEntitySystem {

    public final static double EXP_LOSS_PERCENTAGE = 0.1;

    @Inject
    public PlayerDeathSystem() {
    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Player.getComponentType();
    }

    @Override
    public void onComponentAdded(@NonNull Ref<EntityStore> ref, @NonNull DeathComponent deathComponent, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer) {
        var rpgStats = store.getComponent(ref, AscensionStats.getComponentType());
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

        int levelBar = LevelManager.getRequiredExpForLevelUpAt(level);

        int loss = (int) Math.floor(levelBar * EXP_LOSS_PERCENTAGE);

        int newTotal = Math.max(levelFloorTotal, total - loss);

        rpgStats.setTotalExp(newTotal);
        if (player.getHudManager().getCustomHud() instanceof CustomHudManager wrapper) {
            var exp = wrapper.get(ExpBarHud.class);
            if (exp != null) {
                exp.updateFromComponent(rpgStats);
            }
        }
    }


    @Override
    public ISystem<EntityStore> toSystem() {
        return this;
    }
}
