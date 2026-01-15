package faye.rpg.lifecycle;

import com.google.inject.Inject;
import faye.rpg.lifecycle.hooks.*;

import java.util.List;

public class LifecycleManager {

    private final List<IOnPreSetup> preSetup;
    private final List<IOnSetup> setup;
    private final List<IOnPostSetup> postSetup;
    private final List<IOnStart> start;
    private final List<IOnShutdown> shutdown;

    @Inject
    public LifecycleManager(
            List<IOnPreSetup> preSetup,
            List<IOnSetup> setup,
            List<IOnPostSetup> postSetup,
            List<IOnStart> start,
            List<IOnShutdown> shutdown
    ) {
        this.preSetup = preSetup;
        this.setup = setup;
        this.postSetup = postSetup;
        this.start = start;
        this.shutdown = shutdown;
    }

    public void preSetup() {
        preSetup.forEach(IOnPreSetup::preSetup);
    }

    public void setup() {
        setup.forEach(IOnSetup::setup);
    }

    public void postSetup() {
        postSetup.forEach(IOnPostSetup::postSetup);
    }

    public void start() {
        start.forEach(IOnStart::start);
    }

    public void shutdown() {
        shutdown.reversed().forEach(IOnShutdown::shutdown);
    }
}
