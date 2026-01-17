package faye.rpg.ui;

import com.hypixel.hytale.server.core.universe.PlayerRef;

public interface IAscensionHudElementFactory {
    IAscensionHudElement create(PlayerRef ref);
}
