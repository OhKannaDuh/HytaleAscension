/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg;

import com.google.inject.Guice;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.Universe;
import faye.rpg.components.ComponentManager;
import faye.rpg.lifecycle.LifecycleManager;
import faye.rpg.modules.party.components.PartyComponentManager;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class Plugin extends JavaPlugin {
    private LifecycleManager lifecycle;

    public Plugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        var injector = Guice.createInjector(new Bootstrapper(this));

        lifecycle = injector.getInstance(LifecycleManager.class);

        injector.getInstance(ComponentManager.class);
        injector.getInstance(PartyComponentManager.class);

        lifecycle.preSetup();
        lifecycle.setup();
        lifecycle.postSetup();
    }

    @Override
    protected void start() {
        lifecycle.start();
    }

    @Override
    protected void shutdown() {
        lifecycle.shutdown();
    }
}