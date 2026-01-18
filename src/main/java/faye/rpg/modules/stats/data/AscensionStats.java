package faye.rpg.modules.stats.data;

import com.hypixel.hytale.protocol.OverlapBehavior;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;

public class AscensionStats {
    private static AscensionStats INSTANCE;

    private int vitality;
    private int strength;
    private int wisdom;
    private int intelligence;
    private int defense;
    private int dexterity;
    private int luck;

    public void initialize() {
        vitality = EntityStatType.getAssetMap().getIndex("Ascension.Vitality");
        strength = EntityStatType.getAssetMap().getIndex("Ascension.Strength");
        wisdom = EntityStatType.getAssetMap().getIndex("Ascension.Wisdom");
        intelligence = EntityStatType.getAssetMap().getIndex("Ascension.Intelligence");
        defense = EntityStatType.getAssetMap().getIndex("Ascension.Defense");
        dexterity = EntityStatType.getAssetMap().getIndex("Ascension.Dexterity");
        luck = EntityStatType.getAssetMap().getIndex("Ascension.Luck");
        INSTANCE = this;
    }

    public static int get(StatId id) {
        return switch (id) {
            case Vitality -> vitality();
            case Strength -> strength();
            case Wisdom -> wisdom();
            case Intelligence -> intelligence();
            case Defense -> defense();
            case Dexterity -> dexterity();
            case Luck -> luck();
        };
    }

    public static int vitality() {
        return INSTANCE.vitality;
    }

    public static int strength() {
        return INSTANCE.strength;
    }

    public static int wisdom() {
        return INSTANCE.wisdom;
    }

    public static int intelligence() {
        return INSTANCE.intelligence;
    }

    public static int defense() {
        return INSTANCE.defense;
    }

    public static int dexterity() {
        return INSTANCE.dexterity;
    }

    public static int luck() {
        return INSTANCE.luck;
    }


    public static int getInvestedPoints(EntityStatMap stats) {
        var total = 0;
        for (StatId id : StatId.values()) {
            var index = get(id);
            var stat = stats.get(index);
            if (stat == null) {
                continue;
            }

            total += (int) stat.get();
        }

        return total;
    }
}
