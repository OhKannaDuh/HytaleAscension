package faye.rpg.modules.party.handlers;

import com.google.inject.Inject;
import com.hypixel.hytale.server.core.entity.entities.Player;
import faye.rpg.Logger;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.modules.party.PartyRegistry;
import faye.rpg.modules.party.events.PartyMemberJoinEvent;
import faye.rpg.modules.party.ui.PartyHud;
import faye.rpg.ui.CustomHudManager;

public class PartyMemberJoinEventHandler implements IAscensionEventHandler<PartyMemberJoinEvent> {
    private final PartyRegistry registry;

    private final Logger logger;

    @Inject
    public PartyMemberJoinEventHandler(PartyRegistry registry, Logger logger) {
        this.registry = registry;
        this.logger = logger;
    }

    @Override
    public Class<PartyMemberJoinEvent> eventType() {
        return PartyMemberJoinEvent.class;
    }

    @Override
    public void execute(PartyMemberJoinEvent event) {
        var party = registry.getParty(event.partyUuid());
        if (party == null) {
            logger.warn("[HandlePartyMemberJoin] Could not get party from registry.");
            return;
        }

        var world = event.world();
        for (var member : party.getMembers()) {
            var ref = world.getEntityRef(member);
            if (ref == null) {
                party.removeMember(member);
                continue;
            }

            var player = world.getEntityStore().getStore().getComponent(ref, Player.getComponentType());
            if (player == null) {
                continue;
            }

            if (!(player.getHudManager().getCustomHud() instanceof CustomHudManager manager)) {
                continue;
            }

            var hud = manager.get(PartyHud.class);
            if (hud == null) {
                continue;
            }

            hud.updateFromParty(party, world);
        }
    }
}
