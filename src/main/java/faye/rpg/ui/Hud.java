package faye.rpg.ui;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import faye.rpg.components.RpgStatsComponent;
import faye.rpg.data.LevelManager;

import javax.annotation.Nonnull;

public final class Hud extends CustomUIHud {
    private int level = 1;
    private int exp = 0;
    private int expToNextLevel = 100;

    private int unspentSkillPoints = 0;

    public Hud(@Nonnull PlayerRef playerRef) {
        super(playerRef);
        this.expToNextLevel = LevelManager.getRequiredExpForLevelUpAt(level);
    }

    @Override
    protected void build(@Nonnull UICommandBuilder ui) {
        ui.append( "Hud/FayesRpgHud/Hud.ui");
        updateUiValues(ui);
    }

    public void updateFromComponent(RpgStatsComponent rpg) {
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
        ui.set("#Content #Level.TextSpans", Message.raw("Level: " + level));
        ui.set("#Content #Exp.TextSpans", Message.raw(exp + " / " + expToNextLevel));
        ui.set("#Content #ExpBar.Value", getExpProgress());
        ui.set("#Content #ExpBarEffect.Value", getExpProgress());
    }

    private float getExpProgress() {
        return  (expToNextLevel <= 0) ? 0f : Math.min(1f, exp / (float) expToNextLevel);
    }
}



