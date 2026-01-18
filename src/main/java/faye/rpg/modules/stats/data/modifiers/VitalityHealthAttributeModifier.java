/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.stats.data.modifiers;

import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
import faye.rpg.modules.stats.data.AscensionStats;

import java.util.Optional;

public class VitalityHealthAttributeModifier implements IAscensionAttributeModifier {
    private final static float HEALTH_PER_VITALITY_POINT = 10f;

    @Override
    public int targetIndex() {
        return DefaultEntityStatTypes.getHealth();
    }

    @Override
    public int sourceIndex() {
        return AscensionStats.vitality();
    }

    @Override
    public String key() {
        return "Ascension:Vitality:Health";
    }

    @Override
    public Modifier.ModifierTarget target() {
        return Modifier.ModifierTarget.MAX;
    }

    @Override
    public StaticModifier.CalculationType calculation() {
        return StaticModifier.CalculationType.ADDITIVE;
    }

    @Override
    public float getPointsPerSource() {
        return HEALTH_PER_VITALITY_POINT;
    }
}
