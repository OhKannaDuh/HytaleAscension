package faye.rpg.modules.stats.data.modifiers;

import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.RegeneratingValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
import faye.rpg.modules.stats.data.AscensionStats;

import java.util.Optional;

public class VitalityOxygenAttributeModifier implements IAscensionAttributeModifier {
    private final static float OXYGEN_PER_VITALITY_POINT = 5f;

    @Override
    public int targetIndex() {
        return DefaultEntityStatTypes.getOxygen();
    }

    @Override
    public int sourceIndex() {
        return AscensionStats.vitality();
    }

    @Override
    public String key() {
        return "Ascension:Vitality:Oxygen";
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
        return OXYGEN_PER_VITALITY_POINT;
    }
}
