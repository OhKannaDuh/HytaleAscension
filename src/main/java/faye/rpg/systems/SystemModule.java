/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.systems;

import faye.rpg.DependencyModule;

public class SystemModule extends DependencyModule {
    @Override
    protected void register() {
        autowire(SystemManager.class);
    }
}
