package faye.rpg.modules.party.components;

import com.google.inject.Inject;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class PartyComponentManager {
    private static ComponentRegistryProxy<EntityStore> store;

    private static ComponentType<EntityStore, PartyMember> member;

    private static ComponentType<EntityStore, PartyLeader> leader;

    @Inject
    public PartyComponentManager(ComponentRegistryProxy<EntityStore> store) {
        initialize(store);
    }

    public static void initialize(ComponentRegistryProxy<EntityStore> store) {
        PartyComponentManager.store = store;
    }

    public static synchronized ComponentType<EntityStore, PartyMember> partyMemberType() {
        if (member == null) {
            member = store.registerComponent(PartyMember.class, "PartyMember", PartyMember.CODEC);
        }

        return member;
    }

    public static synchronized ComponentType<EntityStore, PartyLeader> partyLeaderType() {
        if (leader == null) {
            leader = store.registerComponent(PartyLeader.class, "PartyLeader", BuilderCodec.builder(PartyLeader.class, PartyLeader::new).build());
        }

        return leader;
    }
}
