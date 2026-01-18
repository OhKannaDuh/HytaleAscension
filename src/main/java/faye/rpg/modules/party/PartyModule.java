/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.party;

import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import faye.rpg.DependencyModule;
import faye.rpg.commands.IAscensionSubcommand;
import faye.rpg.components.IAscensionComponent;
import faye.rpg.handlers.IAscensionEventHandler;
import faye.rpg.modules.party.commands.PartyCommand;
import faye.rpg.modules.party.components.PartyComponentManager;
import faye.rpg.modules.party.components.PartyLeader;
import faye.rpg.modules.party.components.PartyMember;
import faye.rpg.modules.party.handlers.*;
import faye.rpg.modules.party.systems.PartyUpdateSystem;
import faye.rpg.modules.party.ui.PartyHud;
import faye.rpg.systems.IAscensionEntitySystem;
import faye.rpg.ui.IAscensionHudElementFactory;

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
        binder.addBinding().to(SetupPlayerComponentsEventHandler.class);
        binder.addBinding().to(SetupPlayerHudEventHandler.class);
        binder.addBinding().to(PartyMemberJoinEventHandler.class);
        binder.addBinding().to(PartyMemberLeaveEventHandler.class);

        binder.addBinding().to(AddExpEventHandler.class);
    }

    @Override
    protected void registerEntitySystems(Multibinder<IAscensionEntitySystem> binder) {
        binder.addBinding().to(PartyUpdateSystem.class);
    }
}
