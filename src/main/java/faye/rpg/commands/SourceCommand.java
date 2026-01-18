/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;

import java.util.concurrent.CompletableFuture;

public class SourceCommand extends AbstractCommand implements IAscensionSubcommand {
    protected SourceCommand() {
        super("source", "Get a link to the mods source code");
    }

    @Override
    protected CompletableFuture<Void> execute(CommandContext commandContext) {
        commandContext.sendMessage(Message.raw("Hytale Ascension Source Code: https://github.com/OhKannaDuh/HytaleAscension"));

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public AbstractCommand asCommand() {
        return this;
    }
}
