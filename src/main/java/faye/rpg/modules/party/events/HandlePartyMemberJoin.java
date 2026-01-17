package faye.rpg.modules.party.events;

import com.google.inject.Inject;
import com.hypixel.hytale.server.core.entity.entities.Player;
import faye.rpg.Logger;
import faye.rpg.events.IAscensionEventHandler;
import faye.rpg.modules.party.PartyRegistry;
import faye.rpg.modules.party.ui.PartyHud;
import faye.rpg.ui.CustomHudManager;

public class HandlePartyMemberJoin implements IAscensionEventHandler<PartyMemberJoin> {
    private final PartyRegistry registry;

    private final Logger logger;

    @Inject
    public HandlePartyMemberJoin(PartyRegistry registry, Logger logger) {
        this.registry = registry;
        this.logger = logger;
    }

    @Override
    public Class<PartyMemberJoin> eventType() {
        return PartyMemberJoin.class;
    }

    @Override
    public void execute(PartyMemberJoin event) {
        var party = registry.getParty(event.getPartyUuid());
        if (party == null) {
            logger.warn("[HandlePartyMemberJoin] Could not get party from registry.");
            return;
        }

        var world = event.getWorld();
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
