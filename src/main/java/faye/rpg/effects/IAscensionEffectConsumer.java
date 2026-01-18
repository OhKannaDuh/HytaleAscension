package faye.rpg.effects;

import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;

public interface IAscensionEffectConsumer {
    void consume(LoadedAssetsEvent<String, EntityEffect, ?> event);
}
