/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.stats.data.effects;

import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.effects.EffectEntry;
import faye.rpg.effects.IAscensionEffectConsumer;

import java.util.*;

public class AscensionAttributeEffects implements IAscensionEffectConsumer {
    private static final Set<String> TRACKED_KEYS = Set.of(
            "Ascension.Vitality.HealthRegen.T1",
            "Ascension.Vitality.HealthRegen.T2",
            "Ascension.Vitality.HealthRegen.T3"
    );

    private static final Map<String, EffectEntry> TRACKED_EFFECTS = new HashMap<>();

    @Override
    public void consume(LoadedAssetsEvent<String, EntityEffect, ?> event) {
        for (var entry : event.getLoadedAssets().entrySet()) {
            if (!TRACKED_KEYS.contains(entry.getKey())) {
                continue;
            }

            TRACKED_EFFECTS.put(entry.getKey(), new EffectEntry(entry.getValue(), EntityEffect.getAssetMap().getIndex(entry.getKey())));
        }
    }

    public static void clear(Ref<EntityStore> owner, EffectControllerComponent controller, ComponentAccessor<EntityStore> accessor) {
        for (var effect : TRACKED_EFFECTS.values()) {
            controller.removeEffect(owner, effect.index(), accessor);
        }
    }

    public static EffectEntry get(String key) {
        var effect = TRACKED_EFFECTS.get(key);
        if (effect == null) {
            throw new NoSuchElementException(
                    "Tracked Effect '" + key + "' not loaded"
            );
        }

        return effect;
    }

    public static EffectEntry vitalityHealthRegenTier1() {
        return get("Ascension.Vitality.HealthRegen.T1");
    }

    public static EffectEntry vitalityHealthRegenTier2() {
        return get("Ascension.Vitality.HealthRegen.T2");
    }

    public static EffectEntry vitalityHealthRegenTier3() {
        return get("Ascension.Vitality.HealthRegen.T3");
    }
}
