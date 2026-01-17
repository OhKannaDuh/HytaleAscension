package faye.rpg.modules.stats.ui;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.EnumCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.Logger;
import faye.rpg.modules.stats.components.AscensionStats;
import faye.rpg.modules.stats.data.StatId;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;

public class SkillPointPage extends InteractiveCustomUIPage<SkillPointPage.PageData> {
    private final Logger logger;

    public SkillPointPage(@NonNull PlayerRef playerRef, Logger logger) {
        super(playerRef, CustomPageLifetime.CanDismiss, PageData.CODEC);
        this.logger = logger;
    }

    public void updateFromComponent(AscensionStats rpg) {
        UICommandBuilder ui = new UICommandBuilder();
        
        ui.set("#PointCounter.TextSpans", Message.raw("Points Remaining: " + rpg.getRemainingSkillPoints() + " / " + rpg.getTotalSkillPoints()));

        var headroom = rpg.getRemainingSkillPoints();
        for (StatId stat : StatId.values()) {
            var points = rpg.getStat(stat);

            var name = stat.name();
            var add = "#" + name + "Control #AddButton";
            var sub = "#" + name + "Control #SubButton";
            var label = "#" + name + "Control #Label";

            ui.set(label + ".TextSpans", Message.raw(name + "(" + points + ")"));
            ui.set(sub + ".Disabled", points <= 0);
            ui.set(add + ".Disabled", headroom <= 0);
        }

        this.sendUpdate(ui);
    }

    @Override
    public void build(@NonNull Ref<EntityStore> ref, @NonNull UICommandBuilder cmd, @NonNull UIEventBuilder ui, @NonNull Store<EntityStore> store) {
        cmd.append("Pages/SkillPointsPage.ui");

        ui.addEventBinding(CustomUIEventBindingType.Activating, "#CloseButton", EventData.of("Action", Interaction.Close.name()));

        for (StatId stat : StatId.values()) {
            String name = stat.name();

            ui.addEventBinding(
                    CustomUIEventBindingType.Activating,
                    "#" + name + "Control #AddButton",
                    EventData.of("Action", Interaction.AdjustStat.name())
                            .append("Stat", stat)
                            .put("Delta", "1")
            );

            ui.addEventBinding(
                    CustomUIEventBindingType.Activating,
                    "#" + name + "Control #SubButton",
                    EventData.of("Action", Interaction.AdjustStat.name())
                            .append("Stat", stat)
                            .put("Delta", "-1")
            );
        }

        var rpg = store.getComponent(ref, AscensionStats.getComponentType());
        if (rpg != null) {
            updateFromComponent(rpg);
        }
    }

    @Override
    public void handleDataEvent(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull Store<EntityStore> store,
            @Nonnull PageData data
    ) {
        if (data.action == Interaction.Close) {
            this.close();
            return;
        }

        if (data.action == Interaction.AdjustStat) {
            int delta = 0;
            try {
                delta = Integer.parseInt(data.delta == null ? "0" : data.delta);
            } catch (NumberFormatException ignored) {
                logger.warn("Unable to parse delta: " + data.delta);
                this.close();
                return;
            }

            var rpg = store.getComponent(ref, AscensionStats.getComponentType());
            if (rpg == null) {
                logger.warn("Could not get RpgStats type from player.");
                this.close();
                return;
            }

            rpg.adjustStat(data.stat, delta);
            updateFromComponent(rpg);
            return;
        }

        this.sendUpdate();
    }

    public static class PageData {
        public static final BuilderCodec<PageData> CODEC = BuilderCodec
                .<PageData>builder(PageData.class, PageData::new)
                .append(
                        new KeyedCodec<>("Action", Interaction.CODEC),
                        (d, s) -> d.action = s,
                        d -> d.action
                ).add()
                .append(new KeyedCodec<>("Stat", StatId.CODEC, false),
                        (d, s) -> d.stat = s,
                        d -> d.stat
                ).add()
                .append(new KeyedCodec<>("Delta", Codec.STRING, false),
                        (d, s) -> d.delta = s,
                        d -> d.delta
                ).add()
                .build();

        private Interaction action;
        private StatId stat;
        private String delta;
    }

    private enum Interaction {
        Close,
        AdjustStat;

        public static final Codec<Interaction> CODEC = new EnumCodec<>(Interaction.class, EnumCodec.EnumStyle.CAMEL_CASE);
    }
}
