package faye.rpg.data;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class LevelManager {
    public final static int MAX_LEVEL = 30;

    public static final NavigableMap<Integer, Double> POWER_SCALING =
            new TreeMap<>(Map.of(
                    1, 1.0,
                    10, 1.6,
                    20, 2.2,
                    25, 2.8
            ));

    private static final int[] EXP_TABLE = new int[MAX_LEVEL + 2];

    static {
        EXP_TABLE[1] = 0;
        for (int level = 2; level <= MAX_LEVEL + 1; level++) {
            EXP_TABLE[level] = getRequiredExpForLevelUpAt(level - 1)
                    + EXP_TABLE[level - 1];
        }
    }

    public static int getLevelFromTotalExp(int exp) {
        for (int level = 1; level <= MAX_LEVEL; level++) {
            if (exp < EXP_TABLE[level + 1]) {
                return level;
            }
        }
        return MAX_LEVEL;
    }

    public static int getExpIntoLevel(int totalExp) {
        int level = getLevelFromTotalExp(totalExp);
        return totalExp - EXP_TABLE[level];
    }

    public static int getTotalExpRequiredForLevel(int totalExp) {
        int level = getLevelFromTotalExp(totalExp);
        return EXP_TABLE[level + 1] - totalExp;
    }

    public static int getRequiredExpForLevelUpAt(int level) {
        double s = Math.pow(level, getExponent(level));
        return (int) (100 * s);
    }

    public static int getTotalSkillPointsForLevel(int level) {
        if (level <= 1) {
            return 0;
        }

        int points = 0;

        for (int lvl = 2; lvl <= level; lvl++) {
            if (lvl % 10 == 0) {
                points += 10;
            } else if (lvl % 5 == 0) {
                points += 5;
            } else {
                points += 2;
            }
        }

        return points;
    }

    private static double getExponent(int level) {
        return POWER_SCALING.floorEntry(level).getValue();
    }
}
