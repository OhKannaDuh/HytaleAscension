package faye.rpg.modules.stats.ui;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import faye.rpg.modules.stats.components.AscensionStats;
import faye.rpg.ui.IAscensionHudElement;
import faye.rpg.ui.IAscensionHudElementFactory;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;

public final class ExpBarHud extends CustomUIHud implements IAscensionHudElement {
    private int level = 1;
    private int exp = 0;
    private int expToNextLevel = 100;

    private int unspentSkillPoints = 0;

    public ExpBarHud(PlayerRef playerRef) {
        super(playerRef);
    }

    @Override
    protected void build(@Nonnull UICommandBuilder ui) {
        ui.append("Hud/ExpBar/ExpBar.ui");
        updateUiValues(ui);
    }

    public void updateFromComponent(AscensionStats rpg) {
        this.level = rpg.getLevel();
        this.exp = rpg.getExpIntoLevel();
        this.expToNextLevel = rpg.getRequiredExpForLevelUpAt();
        this.unspentSkillPoints = rpg.getRemainingSkillPoints();

        UICommandBuilder ui = new UICommandBuilder();
        updateUiValues(ui);
        update(false, ui);
    }

    private void updateUiValues(UICommandBuilder ui) {
        ui.set("#Content #SkillPoints.TextSpans", Message.raw("Unspent Skill Points: " + unspentSkillPoints));
        ui.set("#Content #SkillPoints.Visible", unspentSkillPoints > 0);
        ui.set("#Content #Level.TextSpans", Message.raw("Level: " + level));
        ui.set("#Content #Exp.TextSpans", Message.raw(exp + " / " + expToNextLevel));
        ui.set("#Content #ExpBar.Value", getExpProgress());
        ui.set("#Content #ExpBarEffect.Value", getExpProgress());
    }

    private float getExpProgress() {
        return (expToNextLevel <= 0) ? 0f : Math.min(1f, exp / (float) expToNextLevel);
    }

    @Override
    public void buildHud(@NonNull UICommandBuilder ui) {
        this.build(ui);
    }

    public static class Factory implements IAscensionHudElementFactory {
        @Override
        public IAscensionHudElement create(PlayerRef ref) {
            return new ExpBarHud(ref);
        }
    }
}

