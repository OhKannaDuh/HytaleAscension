package faye.rpg.commands;

import com.google.inject.Inject;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import faye.rpg.modules.stats.commands.SkillPointsCommand;

import java.util.Set;

public class AscensionCommand extends AbstractCommandCollection  {
    @Inject
    public AscensionCommand(Set<IAscensionSubcommand> subcommands) {
        super("ascension", "Ascension RPG main command group");
        this.addAliases("asc");

        for (var subcommand : subcommands) {
            this.addSubCommand(subcommand.asCommand());
        }
    }
}
