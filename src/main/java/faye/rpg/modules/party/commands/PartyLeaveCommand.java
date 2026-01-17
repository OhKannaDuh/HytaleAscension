package faye.rpg.modules.party.commands;

import com.google.inject.Inject;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.Logger;
import faye.rpg.modules.party.PartyRegistry;
import faye.rpg.modules.party.components.PartyLeader;
import faye.rpg.modules.party.components.PartyMember;
import faye.rpg.modules.party.ui.PartyHud;
import faye.rpg.ui.CustomHudManager;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PartyLeaveCommand extends AbstractPlayerCommand {
    private final PartyRegistry registry;

    private final Logger logger;

    @Inject
    public PartyLeaveCommand(PartyRegistry registry, Logger logger) {
        super("leave", "Leave your party");
        this.addAliases("l");

        this.registry = registry;
        this.logger = logger;
    }

    @Override
    protected void execute(@NonNull CommandContext commandContext, @NonNull Store<EntityStore> store, @NonNull Ref<EntityStore> ref, @NonNull PlayerRef playerRef, @NonNull World world) {
        var partyMember = store.getComponent(ref, PartyMember.getComponentType());
        if (partyMember == null) {
            logger.warn("Player " + playerRef + "was not in party");
            // @TODO: We'll send a toast warning about this later
            // Player not in a party
            return;
        }

        var party = this.registry.getParty(partyMember.getPartyUuid());
        if (party == null) {
            logger.warn("Could not get party from registry");
            store.removeComponent(ref, PartyMember.getComponentType());
            store.removeComponent(ref, PartyLeader.getComponentType());
            // @TODO: We'll send a toast warning about this later
            // Player not in a party
            return;
        }

        var playerUuid = playerRef.getUuid();

        if (party.isLeader(playerUuid)) {
            party.removeMember(playerUuid);

            var nextLeader = party.getFirstNonLeaderMember();
            if (nextLeader != null) {
                party.transferLeadership(nextLeader);
            } else {
                registry.disbandParty(party.getUuid());
            }

            store.removeComponent(ref, PartyMember.getComponentType());
            store.removeComponent(ref, PartyLeader.getComponentType());
        } else {
            party.removeMember(playerUuid);
            store.removeComponent(ref, PartyMember.getComponentType());
        }

        var player = store.getComponent(ref, Player.getComponentType());
        if (player == null) {
            return;
        }

        var hud = player.getHudManager().getCustomHud();
        if (hud instanceof CustomHudManager wrapper) {
            var partyHud = wrapper.get(PartyHud.class);
            if (partyHud != null) {
                partyHud.clear();
            }
        }
    }
}
