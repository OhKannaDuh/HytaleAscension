/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *  
 */

package faye.rpg.commands;

import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import faye.rpg.DependencyModule;

public class CommandModule extends DependencyModule {
    @Override
    protected void register() {
        Multibinder<AbstractCommand> commands = Multibinder.newSetBinder(binder(), AbstractCommand.class);
        commands.addBinding().to(AscensionCommand.class).in(Scopes.SINGLETON);

        autowire(CommandManager.class);
    }
}
