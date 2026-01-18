/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.ui;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomHudManager extends CustomUIHud {
    private final List<IAscensionHudElement> candidates = new ArrayList<>();

    public CustomHudManager(@NonNull PlayerRef playerRef, Set<IAscensionHudElementFactory> factories) {
        super(playerRef);

        factories.forEach(f -> candidates.add(f.create(playerRef)));
    }

    @Override
    protected void build(@NonNull UICommandBuilder ui) {
        for (var candidate : candidates) {
            candidate.buildHud(ui);
        }
    }

    public <T extends IAscensionHudElement> T get(Class<T> type) {
        for (IAscensionHudElement element : candidates) {
            if (type.isInstance(element)) {
                return type.cast(element);
            }
        }

        return null;
    }
}
