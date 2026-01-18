package faye.rpg.systems;

import faye.rpg.DependencyModule;

public class SystemModule extends DependencyModule {
    @Override
    protected void register() {
        autowire(SystemManager.class);
    }
}
