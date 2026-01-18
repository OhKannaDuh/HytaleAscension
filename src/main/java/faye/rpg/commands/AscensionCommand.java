/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.commands;

import com.google.inject.Inject;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

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
