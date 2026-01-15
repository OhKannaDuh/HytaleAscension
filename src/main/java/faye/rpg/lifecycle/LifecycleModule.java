package faye.rpg.lifecycle;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import faye.rpg.lifecycle.hooks.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LifecycleModule  extends AbstractModule {
    @Override
    protected void configure() {
        bind(LifecycleManager.class).in(Scopes.SINGLETON);

        Multibinder.newSetBinder(binder(), IOnPreSetup.class);
        Multibinder.newSetBinder(binder(), IOnSetup.class);
        Multibinder.newSetBinder(binder(), IOnPostSetup.class);
        Multibinder.newSetBinder(binder(), IOnStart.class);
        Multibinder.newSetBinder(binder(), IOnShutdown.class);
    }

    @Provides
    @Singleton
    private static List<IOnPreSetup> providePreSetupList(Set<IOnPreSetup> set) {
        return new ArrayList<>(set);
    }

    @Provides
    @Singleton
    private static List<IOnSetup> provideSetupList(Set<IOnSetup> set) {
        return new ArrayList<>(set);
    }

    @Provides
    @Singleton
    private static List<IOnPostSetup> providePostSetupList(Set<IOnPostSetup> set) {
        return new ArrayList<>(set);
    }

    @Provides
    @Singleton
    private static List<IOnStart> providesStartList(Set<IOnStart> set) {
        return new ArrayList<>(set);
    }

    @Provides
    @Singleton
    private static List<IOnShutdown> provideShutdownList(Set<IOnShutdown> set) {
        return new ArrayList<>(set);
    }
}
