/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.stats.components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.components.ComponentManager;
import faye.rpg.components.IAscensionComponent;
import faye.rpg.modules.stats.data.LevelManager;
import faye.rpg.modules.stats.data.StatId;

///  A component for tracking level and exp
public class AscensionExp implements IAscensionComponent<AscensionExp>, Component<EntityStore> {
    public static final BuilderCodec<AscensionExp> CODEC = BuilderCodec
            .builder(AscensionExp.class, AscensionExp::new)
            .append(
                    new KeyedCodec<>("TotalExp", Codec.INTEGER),
                    (d, v) -> d.totalExp = v,
                    d -> d.totalExp
            ).add()
            .build();

    private int totalExp = 0;

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int amount) {
        totalExp = amount;
    }

    public boolean addExp(int amount) {
        if (amount <= 0) {
            return false;
        }

        var before = getLevel();
        totalExp += amount;

        return getLevel() > before;
    }

    public int getLevel() {
        return LevelManager.getLevelFromTotalExp(totalExp);
    }

    public int getExpIntoLevel() {
        return LevelManager.getExpIntoLevel(totalExp);
    }

    public int getRequiredExpForLevelUpAt() {
        return LevelManager.getRequiredExpForLevelUpAt(getLevel());
    }

    public int getTotalAttributePoints() {
        return LevelManager.getTotalAttributePointsForLevel(getLevel());
    }

//    public int getRemainingAttributePoints() {
//        return Math.max(0, getTotalAttributePoints() - getInvestedAttributePoints());
//    }

    @Override
    public Component<EntityStore> clone() {
        AscensionExp copy = new AscensionExp();
        copy.totalExp = this.totalExp;
        return copy;
    }

    private static ComponentType<EntityStore, AscensionExp> TYPE;
    public static ComponentType<EntityStore, AscensionExp> getComponentType() {
        if (TYPE == null) {
            TYPE = ComponentManager.get(AscensionExp.class);
        }

        return TYPE;
    }

    @Override
    public Class<AscensionExp> componentClass() {
        return AscensionExp.class;
    }

    @Override
    public String id() {
        return "Ascension:Exp";
    }

    @Override
    public BuilderCodec<AscensionExp> codec() {
        return AscensionExp.CODEC;
    }
}
