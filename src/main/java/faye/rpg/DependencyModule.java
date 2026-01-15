package faye.rpg;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import faye.rpg.lifecycle.hooks.*;

public abstract class DependencyModule extends AbstractModule {
    protected  <T> void autowire(Class<T> impl) {
        bind(impl).in(Scopes.SINGLETON);

        if (IOnPreSetup.class.isAssignableFrom(impl)) {
            Multibinder.newSetBinder(binder(), IOnPreSetup.class)
                    .addBinding()
                    .to(impl.asSubclass(IOnPreSetup.class));
        }

        if (IOnSetup.class.isAssignableFrom(impl)) {
            Multibinder.newSetBinder(binder(), IOnSetup.class)
                    .addBinding()
                    .to(impl.asSubclass(IOnSetup.class));
        }

        if (IOnPostSetup.class.isAssignableFrom(impl)) {
            Multibinder.newSetBinder(binder(), IOnPostSetup.class)
                    .addBinding()
                    .to(impl.asSubclass(IOnPostSetup.class));
        }

        if (IOnStart.class.isAssignableFrom(impl)) {
            Multibinder.newSetBinder(binder(), IOnStart.class)
                    .addBinding()
                    .to(impl.asSubclass(IOnStart.class));
        }

        if (IOnShutdown.class.isAssignableFrom(impl)) {
            Multibinder.newSetBinder(binder(), IOnShutdown.class)
                    .addBinding()
                    .to(impl.asSubclass(IOnShutdown.class));
        }
    }
}
