/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.ui;

import com.hypixel.hytale.server.core.universe.PlayerRef;

public interface IAscensionHudElementFactory {
    IAscensionHudElement create(PlayerRef ref);
}
