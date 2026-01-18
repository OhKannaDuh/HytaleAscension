/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.stats.data.effects;

import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import faye.rpg.effects.EffectEntry;
import faye.rpg.modules.stats.data.AscensionStats;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VitalityHealthRegenEffectApplicator implements IAscensionAttributeEffectApplicator {

    private static final Map<Integer, String> EFFECT_THRESHOLDS = new HashMap<>() {{
        put(5, "Ascension.Vitality.HealthRegen.T1");
        put(15, "Ascension.Vitality.HealthRegen.T2");
        put(25, "Ascension.Vitality.HealthRegen.T3");
    }};

    @Override
    public Optional<EffectEntry> get(EntityStatMap stats) {
        var stat = stats.get(AscensionStats.vitality());
        if (stat == null) {
            return Optional.empty();
        }

        var level = (int) stat.get();
        return EFFECT_THRESHOLDS.entrySet().stream()
                .filter(e -> level >= e.getKey())
                .max(Map.Entry.comparingByKey())
                .map(e -> AscensionAttributeEffects.get(e.getValue()));
    }
}
