package faye.rpg.modules.stats.data.modifiers;

import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
import faye.rpg.modules.stats.data.AscensionStats;

import java.util.Optional;

public class IntelligenceManaAttributeModifier implements IAscensionAttributeModifier {
    private final static float MANA_PER_INTELLIGENCE_POINT = 15f;

    @Override
    public int targetIndex() {
        return DefaultEntityStatTypes.getMana();
    }

    @Override
    public int sourceIndex() {
        return AscensionStats.intelligence();
    }

    @Override
    public String key() {
        return "Ascension:Intelligence:Mana";
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
        return MANA_PER_INTELLIGENCE_POINT;
    }
}
