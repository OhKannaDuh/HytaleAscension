package faye.rpg.modules.stats.commands;

import com.google.inject.Inject;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.Logger;
import faye.rpg.commands.IAscensionSubcommand;
import faye.rpg.modules.stats.ui.SkillPointPage;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SkillPointsCommand extends AbstractPlayerCommand implements IAscensionSubcommand {
    private final Logger logger;

    @Inject
    public SkillPointsCommand(Logger logger) {
        super("skillpoints", "Opens the Skill Points ui");
        this.addAliases("sp");
        this.logger = logger;
    }

    @Override
    protected void execute(@NonNull CommandContext context, @NonNull Store<EntityStore> store, @NonNull Ref<EntityStore> ref, @NonNull PlayerRef playerRef, @NonNull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) {
            return;
        }

        player.getPageManager().openCustomPage(ref, store, new SkillPointPage(playerRef, logger));
    }

    @Override
    public AbstractCommand asCommand() {
        return this;
    }
}
