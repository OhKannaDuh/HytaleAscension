package faye.rpg.components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.entity.Frozen;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.data.LevelManager;

///  A component for tracking level and exp
public class RpgStatsComponent implements Component<EntityStore> {
    public static final BuilderCodec<RpgStatsComponent> CODEC = BuilderCodec
            .builder(RpgStatsComponent.class, RpgStatsComponent::new)
            .append(
                    new KeyedCodec<>("TotalExp", Codec.INTEGER),
                    (d, v) -> d.totalExp = v,
                    d -> d.totalExp
            ).add()
            .append(
                    new KeyedCodec<>("Vitality", Codec.INTEGER),
                    (d, v) -> d.vitality = v,
                    d -> d.vitality
            ).add()
            .append(
                    new KeyedCodec<>("Strength", Codec.INTEGER),
                    (d, v) -> d.strength = v,
                    d -> d.strength
            ).add()
            .append(
                    new KeyedCodec<>("Wisdom", Codec.INTEGER),
                    (d, v) -> d.wisdom = v,
                    d -> d.wisdom
            ).add()
            .append(
                    new KeyedCodec<>("Intelligence", Codec.INTEGER),
                    (d, v) -> d.intelligence = v,
                    d -> d.intelligence
            ).add()
            .append(
                    new KeyedCodec<>("Defense", Codec.INTEGER),
                    (d, v) -> d.defense = v,
                    d -> d.defense
            ).add()
            .append(
                    new KeyedCodec<>("Dexterity", Codec.INTEGER),
                    (d, v) -> d.dexterity = v,
                    d -> d.dexterity
            ).add()
            .append(
                    new KeyedCodec<>("Luck", Codec.INTEGER),
                    (d, v) -> d.luck = v,
                    d -> d.luck
            ).add()
            .build();

    private int totalExp = 0;

    // Max Hp and regen
    private int vitality = 0;

    // Physical Damage
    private int strength = 0;

    // max mana and regen
    private int wisdom = 0;

    // magic damage
    private int intelligence = 0;

    // Damage reduction
    private int defense = 0;

    // crit chance
    private int dexterity = 0;

    // changce to doulbe loot, crit damage
    private int luck = 0;

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int amount) {
        totalExp = amount;
    }

    public void addExp(int amount) {
        if (amount <= 0) {
            return;
        }

        totalExp += amount;
    }

    public int getVitality() {
        return vitality;
    }

    public void addVitality(int amount) {
        vitality += amount;
    }

    public int getStrength() {
        return strength;
    }

    public void addStrength(int amount) {
        strength += amount;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void addWisdom(int amount) {
        wisdom += amount;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void addIntelligence(int amount) {
        intelligence += amount;
    }

    public int getDefense() {
        return defense;
    }

    public void addDefense(int amount) {
        defense += amount;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void addDexterity(int amount) {
        dexterity += amount;
    }

    public int getLuck() {
        return luck;
    }

    public void addLuck(int amount) {
        luck += amount;
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

    public int getTotalSkillPoints() {
        return LevelManager.getTotalSkillPointsForLevel(getLevel());
    }

    public int getInvestedSkillPoints() {
        return this.vitality +
                this.strength +
                this.wisdom +
                this.intelligence +
                this.defense +
                this.dexterity +
                this.luck;
    }

    public int getRemainingSkillPoints() {
        return Math.max(0, getTotalSkillPoints() - getInvestedSkillPoints());
    }

    @Override
    public Component<EntityStore> clone() {
        RpgStatsComponent copy = new RpgStatsComponent();
        copy.totalExp = this.totalExp;
        copy.vitality = this.vitality;
        copy.strength = this.strength;
        copy.wisdom = this.wisdom;
        copy.intelligence = this.intelligence;
        copy.defense = this.defense;
        copy.dexterity = this.dexterity;
        copy.luck = this.luck;
        return copy;
    }
}
