package faye.rpg.modules.party.components;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.components.IAscensionComponent;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PartyLeader implements IAscensionComponent<PartyLeader>, Component<EntityStore> {
    @Override
    public @Nullable Component<EntityStore> clone() {
        return new PartyLeader();
    }

    public static ComponentType<EntityStore, PartyLeader> getComponentType() {
        return PartyComponentManager.partyLeaderType();
    }

    @Override
    public Class<PartyLeader> componentClass() {
        return PartyLeader.class;
    }

    @Override
    public String id() {
        return "Ascension:PartyLeader";
    }

    @Override
    public BuilderCodec<PartyLeader> codec() {
        return BuilderCodec.builder(PartyLeader.class, PartyLeader::new).build();
    }
}
