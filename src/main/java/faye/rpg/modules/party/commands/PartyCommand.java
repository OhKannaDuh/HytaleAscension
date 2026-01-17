package faye.rpg.modules.party.commands;

import com.google.inject.Inject;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import faye.rpg.commands.IAscensionSubcommand;

public class PartyCommand extends AbstractCommandCollection implements IAscensionSubcommand  {
    @Inject
    public PartyCommand(PartyCreateCommand create, PartyLeaveCommand leave) {
        super("party", "Ascension party management");
        this.addAliases("p");

        this.addSubCommand(create);
        this.addSubCommand(leave);
    }

    @Override
    public AbstractCommand asCommand() {
        return this;
    }
}
