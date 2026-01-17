package faye.rpg.modules.party.events;

import com.google.inject.Inject;
import faye.rpg.events.IAscensionEventHandler;
import faye.rpg.events.player_lifecycle.SetupPlayerHudEvent;
import faye.rpg.modules.party.PartyRegistry;
import faye.rpg.modules.party.components.PartyMember;
import faye.rpg.modules.party.ui.PartyHud;

public class HandleInitialPartyHud implements IAscensionEventHandler<SetupPlayerHudEvent> {
    private final PartyRegistry registry;

    @Inject
    public HandleInitialPartyHud(PartyRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Class<SetupPlayerHudEvent> eventType() {
        return SetupPlayerHudEvent.class;
    }

    @Override
    public void execute(SetupPlayerHudEvent event) {
        var payload = event.getPayload();
        final var memberType = PartyMember.getComponentType();

        var hud = event.getHud().get(PartyHud.class);
        if (hud == null) {
            return;
        }

        var member = payload.store.getComponent(payload.entity, memberType);
        if (member == null) {
            hud.clear();
            return;
        }

        var party = registry.getParty(member.getPartyUuid());
        if (party == null) {
            hud.clear();
            return;
        }

        hud.updateFromParty(party, payload.world);
    }
}
