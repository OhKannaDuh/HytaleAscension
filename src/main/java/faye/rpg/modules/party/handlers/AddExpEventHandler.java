package faye.rpg.modules.party.handlers;

import com.google.inject.Inject;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.modules.party.PartyRegistry;
import faye.rpg.modules.party.components.PartyMember;
import faye.rpg.modules.stats.events.AddExpEvent;

public class AddExpEventHandler implements IAscensionEventHandler<AddExpEvent> {
    private final PartyRegistry parties;

    @Inject
    public AddExpEventHandler(PartyRegistry parties) {
        this.parties = parties;
    }

    @Override
    public void execute(AddExpEvent event) {
        if (event.shareType() != AddExpEvent.ShareType.WithParty) {
            return;
        }

        var payload = event.payload();
        var entity = payload.playerRef().getReference();
        if (entity == null) {
            return;
        }

        var store = payload.world().getEntityStore().getStore();

        var partyMember = store.getComponent(entity, PartyMember.getComponentType());
        if (partyMember == null) {
            return;
        }

        var party = parties.getParty(partyMember.getPartyUuid());
        if (party == null) {
            return;
        }

        // We'll get this from a config in the future
        final float sharedAmountPercentage = 1f;
        var sharedExp = (int) Math.floor(payload.exp() * sharedAmountPercentage);

        for (var member : party.getMembersExcluding(payload.playerRef().getUuid())) {
            var memberEntity = payload.world().getEntityRef(member);
            if (memberEntity == null) {
                continue;
            }

            var memberPlayerRef = store.getComponent(memberEntity, PlayerRef.getComponentType());
            if (memberPlayerRef == null) {
                continue;
            }

            var memberPlayer = store.getComponent(memberEntity, Player.getComponentType());
            if (memberPlayer == null) {
                continue;
            }

            AddExpEvent.dispatchWithNoBubbleToParty(new AddExpEvent.Payload(
                    sharedExp,
                    memberPlayerRef,
                    memberPlayer,
                    payload.world()
            ));
        }
    }
}
