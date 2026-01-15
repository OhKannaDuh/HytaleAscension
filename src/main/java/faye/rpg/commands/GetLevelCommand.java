package faye.rpg.commands;

import com.google.inject.Inject;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.components.RpgStatsComponent;
import faye.rpg.data.LevelManager;
import org.checkerframework.checker.nullness.qual.NonNull;

public class GetLevelCommand extends AbstractPlayerCommand {
    private final ComponentType<EntityStore, RpgStatsComponent> type;

    @Inject
    public GetLevelCommand(ComponentType<EntityStore, RpgStatsComponent> type) {
        super("level", "Get the level of the current player");
        this.type = type;
    }


    @Override
    protected void execute(@NonNull CommandContext context, @NonNull Store<EntityStore> store, @NonNull Ref<EntityStore> ref, @NonNull PlayerRef playerRef, @NonNull World world) {
        var rpg = store.getComponent(ref, type);
        if (rpg == null) {
            context.sendMessage(Message.raw("Player did not have the rpg component on them"));
            return;
        }

        var total = rpg.getTotalExp();
        var level = LevelManager.getLevelFromTotalExp(total);
        var into = LevelManager.getExpIntoLevel(total);
        var next = LevelManager.getRequiredExpForLevelUpAt(level);

        context.sendMessage(Message.raw("Player is Level {" + level + "}, ({" + into + "}/{" + next + "} exp)"));
    }
}
