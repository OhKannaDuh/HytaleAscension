package faye.rpg.modules.party;

import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import com.hypixel.hytale.server.core.HytaleServer;
import faye.rpg.DependencyModule;
import faye.rpg.commands.IAscensionSubcommand;
import faye.rpg.components.IAscensionComponent;
import faye.rpg.events.IAscensionEventHandler;
import faye.rpg.modules.party.commands.PartyCommand;
import faye.rpg.modules.party.components.PartyComponentManager;
import faye.rpg.modules.party.components.PartyLeader;
import faye.rpg.modules.party.components.PartyMember;
import faye.rpg.modules.party.events.HandleInitialPartyHud;
import faye.rpg.modules.party.events.HandleInitialPartyState;
import faye.rpg.modules.party.events.HandlePartyMemberJoin;
import faye.rpg.modules.party.events.HandlePartyMemberLeave;
import faye.rpg.modules.party.systems.PartyUpdateSystem;
import faye.rpg.modules.party.ui.PartyHud;
import faye.rpg.systems.IAscensionEntitySystem;
import faye.rpg.ui.IAscensionHudElementFactory;

import java.util.concurrent.TimeUnit;

public class PartyModule extends DependencyModule {
    @Override
    protected void register() {
        bind(PartyRegistry.class).in(Scopes.SINGLETON);
        bind(PartyComponentManager.class).in(Scopes.SINGLETON);

        autowire(PartyUpdater.class);
    }

    @Override
    protected void registerSubcommands(Multibinder<IAscensionSubcommand> binder) {
        binder.addBinding().to(PartyCommand.class).in(Scopes.SINGLETON);
    }

    @Override
    protected void registerComponents(Multibinder<IAscensionComponent> binder) {
        binder.addBinding().to(PartyMember.class);
        binder.addBinding().to(PartyLeader.class);
    }

    @Override
    protected void registerHudElementFactories(Multibinder<IAscensionHudElementFactory> binder) {
        binder.addBinding().to(PartyHud.Factory.class);
    }

    @Override
    protected void registerEventHandlers(Multibinder<IAscensionEventHandler> binder) {
        binder.addBinding().to(HandleInitialPartyState.class);
        binder.addBinding().to(HandleInitialPartyHud.class);
        binder.addBinding().to(HandlePartyMemberJoin.class);
        binder.addBinding().to(HandlePartyMemberLeave.class);
    }

    @Override
    protected void registerEntitySystems(Multibinder<IAscensionEntitySystem> binder) {
        binder.addBinding().to(PartyUpdateSystem.class);
    }
}
