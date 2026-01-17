package faye.rpg.modules.party.events;

import com.google.inject.Inject;
import faye.rpg.events.IAscensionEventHandler;
import faye.rpg.events.player_lifecycle.SetupPlayerComponentsEvent;
import faye.rpg.modules.party.PartyRegistry;
import faye.rpg.modules.party.components.PartyLeader;
import faye.rpg.modules.party.components.PartyMember;

public class HandleInitialPartyState implements IAscensionEventHandler<SetupPlayerComponentsEvent> {
    private final PartyRegistry registry;

    @Inject
    public HandleInitialPartyState(PartyRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Class<SetupPlayerComponentsEvent> eventType() {
        return SetupPlayerComponentsEvent.class;
    }

    @Override
    public void execute(SetupPlayerComponentsEvent event) {
        var payload = event.getPayload();
        final var memberType = PartyMember.getComponentType();
        final var leaderType = PartyLeader.getComponentType();

        var member = payload.store.getComponent(payload.entity, memberType);
        if (member == null) {
            payload.store.removeComponent(payload.entity, leaderType);
            return;
        }

        var party = registry.getParty(member.getPartyUuid());
        if (party == null) {
            payload.store.removeComponent(payload.entity, memberType);
            payload.store.removeComponent(payload.entity, leaderType);
        }
    }
}
