package faye.rpg;

import com.google.inject.Guice;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import faye.rpg.lifecycle.LifecycleManager;
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