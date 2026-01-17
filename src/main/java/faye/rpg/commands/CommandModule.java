package faye.rpg.commands;

import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import faye.rpg.DependencyModule;
import faye.rpg.modules.stats.commands.SkillPointsCommand;

public class CommandModule extends DependencyModule {
    @Override
    protected void register() {
        Multibinder<AbstractCommand> commands = Multibinder.newSetBinder(binder(), AbstractCommand.class);
        commands.addBinding().to(AscensionCommand.class).in(Scopes.SINGLETON);

        autowire(CommandManager.class);
    }
}
