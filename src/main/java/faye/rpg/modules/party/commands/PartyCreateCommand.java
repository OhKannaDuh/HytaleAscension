package faye.rpg.modules.party.commands;

import com.google.inject.Inject;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import com.hypixel.hytale.server.core.util.NotificationUtil;
import faye.rpg.Logger;
import faye.rpg.modules.party.PartyRegistry;
import faye.rpg.modules.party.components.PartyLeader;
import faye.rpg.modules.party.components.PartyMember;
import faye.rpg.modules.party.events.PartyMemberJoin;
import faye.rpg.modules.party.ui.PartyHud;
import faye.rpg.ui.CustomHudManager;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PartyCreateCommand extends AbstractPlayerCommand {
    private final PartyRegistry registry;

    private final Logger logger;

    @Inject
    public PartyCreateCommand(PartyRegistry registry, Logger logger) {
        super("create", "Create a new party");
        this.addAliases("c");

        this.registry = registry;
        this.logger = logger;
    }

    @Override
    protected void execute(@NonNull CommandContext commandContext, @NonNull Store<EntityStore> store, @NonNull Ref<EntityStore> ref, @NonNull PlayerRef playerRef, @NonNull World world) {
        var partyMember = store.getComponent(ref, PartyMember.getComponentType());
        if (partyMember != null) {
            logger.warn("Player " + playerRef + " is already in a party");
            // @TODO: We'll send a toast warning about this later
            // Player already in a party
            return;
        }

        var party = this.registry.createParty(playerRef.getUuid());
        store.addComponent(ref, PartyMember.getComponentType(), PartyMember.fromParty(party));
        store.addComponent(ref, PartyLeader.getComponentType(), new PartyLeader());

        logger.info("Dispatching party member join event");
        PartyMemberJoin.dispatch(party.getUuid(), playerRef.getUuid(), world);


    }
}
