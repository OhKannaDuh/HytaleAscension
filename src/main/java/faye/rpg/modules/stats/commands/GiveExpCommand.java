package faye.rpg.modules.stats.commands;

import com.google.inject.Inject;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.commands.IAscensionSubcommand;
import faye.rpg.modules.stats.events.AddExpEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class GiveExpCommand extends AbstractPlayerCommand implements IAscensionSubcommand {
    RequiredArg<Integer> amount = this.withRequiredArg("argument_name", "Argument Description", ArgTypes.INTEGER);

    @Inject
    public GiveExpCommand() {
        super("exp", "Opens the Skill Points ui");
        this.addAliases("e");
    }

    @Override
    protected void execute(@NonNull CommandContext context, @NonNull Store<EntityStore> store, @NonNull Ref<EntityStore> ref, @NonNull PlayerRef playerRef, @NonNull World world) {
        int amount = this.amount.get(context);
        if (amount <= 0) {
            return;
        }

        var player = store.getComponent(ref, Player.getComponentType());
        if (player == null) {
            return;
        }

        AddExpEvent.dispatchAndBubbleToParty(new AddExpEvent.Payload(
                amount,
                playerRef,
                player,
                world
        ));

    }

    @Override
    public AbstractCommand asCommand() {
        return this;
    }
}
