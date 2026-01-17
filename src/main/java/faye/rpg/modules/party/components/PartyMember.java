package faye.rpg.modules.party.components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.components.IAscensionComponent;
import faye.rpg.modules.party.Party;
import faye.rpg.modules.party.PartyModule;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public class PartyMember implements IAscensionComponent<PartyMember>, Component<EntityStore> {
    public static final BuilderCodec<PartyMember> CODEC = BuilderCodec
            .builder(PartyMember.class, PartyMember::new)
            .append(
                    new KeyedCodec<>("UUID", Codec.UUID_STRING),
                    (d, v) -> d.partyUuid = v,
                    d -> d.partyUuid
            ).add()
            .build();


    private UUID partyUuid;

    public static PartyMember fromParty(Party party) {
        var comp = new PartyMember();
        comp.partyUuid = party.getUuid();
        return comp;
    }

    public static PartyMember fromUui(UUID partyUuid) {
        var comp = new PartyMember();
        comp.partyUuid = partyUuid;
        return comp;
    }

    public UUID getPartyUuid() {
        return partyUuid;
    }

    @Override
    public @Nullable Component<EntityStore> clone() {
        var copy = new PartyMember();
        copy.partyUuid = this.partyUuid;
        return copy;
    }

    public static ComponentType<EntityStore, PartyMember> getComponentType() {
        return PartyComponentManager.partyMemberType();
    }

    @Override
    public Class<PartyMember> componentClass() {
        return PartyMember.class;
    }

    @Override
    public String id() {
        return "Ascension:PartyMember";
    }

    @Override
    public BuilderCodec<PartyMember> codec() {
        return PartyMember.CODEC;
    }
}
