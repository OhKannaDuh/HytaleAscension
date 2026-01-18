package faye.rpg.modules.stats.data.effects;

import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import faye.rpg.effects.EffectEntry;

import java.util.Optional;

public interface IAscensionAttributeEffectApplicator {
    Optional<EffectEntry> get(EntityStatMap stats);
}
