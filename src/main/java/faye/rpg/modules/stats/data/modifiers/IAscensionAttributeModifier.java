package faye.rpg.modules.stats.data.modifiers;

import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;

import java.util.Optional;

public interface IAscensionAttributeModifier {
    /**
     * @return The index of the Entity Stat this modifier applies to.
     */
    int targetIndex();

    /**
     * @return The index of the Entity Stat this modifier derives from..
     */
    int sourceIndex();

    /**
     * @return The unique identifier for this modifier.
     */
    String key();

    /**
     * @return The target for this modifier.
     */
    Modifier.ModifierTarget target();

    /**
     * @return The calculation type for this modifier
     */
    StaticModifier.CalculationType calculation();

    float getPointsPerSource();

    /**
     * @param stats The entities current stats.
     * @return Maybe the modifier value to apply to the stats.
     */
    default Optional<Float> get(EntityStatMap stats)  {
        var target = stats.get(targetIndex());
        var source = stats.get(sourceIndex());
        if (target == null || source == null) {
            return Optional.empty();
        }

        return Optional.of(getPointsPerSource() * source.get());
    }
}
