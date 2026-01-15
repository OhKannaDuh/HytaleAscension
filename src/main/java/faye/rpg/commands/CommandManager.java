package faye.rpg.commands;

import com.google.inject.Inject;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import faye.rpg.lifecycle.hooks.IOnPostSetup;

import java.util.Set;

public class CommandManager implements IOnPostSetup {
    private final CommandRegistry registry;

    private final Set<AbstractCommand> commands;

    @Inject
    public CommandManager(CommandRegistry registry, Set<AbstractCommand> commands) {
        this.registry = registry;
        this.commands = commands;
    }

    @Override
    public void postSetup() {
        commands.forEach(registry::registerCommand);
    }
}
