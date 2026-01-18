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
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.Logger;
import faye.rpg.modules.stats.components.AscensionExp;
import faye.rpg.modules.stats.data.AscensionStats;
import faye.rpg.modules.stats.data.StatId;
import faye.rpg.modules.stats.events.AttributePointsAssignmentChangedEvent;
import faye.rpg.ui.CustomHudManager;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class AttributePointPage extends InteractiveCustomUIPage<AttributePointPage.PageData> {
    private final Logger logger;

    public AttributePointPage(@NonNull PlayerRef playerRef, Logger logger) {
        super(playerRef, CustomPageLifetime.CanDismiss, PageData.CODEC);
        this.logger = logger;
    }

    public void updateFromComponent(AscensionExp exp, EntityStatMap stats) {
        UICommandBuilder ui = new UICommandBuilder();
        var total = exp.getTotalAttributePoints();
        var invested = AscensionStats.getInvestedPoints(stats);
        var remaining = total - invested;

        ui.set("#PointCounter.TextSpans", Message.raw("Points Remaining: " + invested + " / " + total + " (" + remaining + ")"));

        for (StatId id : StatId.values()) {
            var index = AscensionStats.get(id);
            if (index == Integer.MIN_VALUE) {
                continue;
            }

            var stat = stats.get(index);
            if (stat == null) {
                continue;
            }

            var points = (int) stat.get();

            var name = id.name();
            var add = "#" + name + "Control #AddButton";
            var sub = "#" + name + "Control #SubButton";
            var label = "#" + name + "Control #Label";

            ui.set(label + ".TextSpans", Message.raw(name + "(" + points + ")"));
            ui.set(sub + ".Disabled", points <= 0);
            ui.set(add + ".Disabled", remaining <= 0 || points >= 30);
        }

        this.sendUpdate(ui);
    }

    @Override
    public void build(@NonNull Ref<EntityStore> ref, @NonNull UICommandBuilder cmd, @NonNull UIEventBuilder ui, @NonNull Store<EntityStore> store) {
        cmd.append("Pages/AttributePointPage.ui");

        ui.addEventBinding(CustomUIEventBindingType.Activating, "#CloseButton", EventData.of("Action", Interaction.Close.name()).put("Delta", "0"));


        for (StatId stat : StatId.values()) {
            String name = stat.name();

            ui.addEventBinding(
                    CustomUIEventBindingType.Activating,
                    "#" + name + "Control #AddButton",
                    new EventData(new HashMap<>() {{
                        put("Action", Interaction.AdjustStat.name());
                        put("Stat", stat.name());
                        put("Delta", "1");
                    }})
            );

            ui.addEventBinding(
                    CustomUIEventBindingType.Activating,
                    "#" + name + "Control #SubButton",
                    new EventData(new HashMap<>() {{
                        put("Action", Interaction.AdjustStat.name());
                        put("Stat", stat.name());
                        put("Delta", "-1");
                    }})
            );
        }

        var stats = store.getComponent(ref, EntityStatMap.getComponentType());
        if (stats == null) {
            return;
        }

        var exp = store.getComponent(ref, AscensionExp.getComponentType());
        if (exp == null) {
            return;
        }

        updateFromComponent(exp, stats);
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
            var delta = data.delta;

            var stats = store.getComponent(ref, EntityStatMap.getComponentType());
            if (stats == null) {
                logger.warn("Could not get EntityStatMap type from player.");
                this.close();
                return;
            }

            var index = AscensionStats.get(data.stat);
            var stat = stats.get(index);
            if (stat == null) {
                logger.warn("Could not get stat " + data.stat + " from EntityStatMap.");
                this.close();
                return;

            }

            var exp = store.getComponent(ref, AscensionExp.getComponentType());
            if (exp == null) {
                logger.warn("Could not get AscensionExp type from player.");
                this.close();
                return;
            }

            var current = (int) stat.get();
            stats.setStatValue(index, current + delta);
            updateFromComponent(exp, stats);

            var playerRef = store.getComponent(ref, PlayerRef.getComponentType());
            if (playerRef == null) {
                return;
            }

            var player = store.getComponent(ref, Player.getComponentType());
            if (player == null) {
                return;
            }

            AttributePointsAssignmentChangedEvent.dispatch(playerRef, store.getExternalData().getWorld());

            if (!(player.getHudManager().getCustomHud() instanceof CustomHudManager manager)) {
                return;
            }

            var expHud = manager.get(ExpBarHud.class);
            if (expHud == null) {
                return;
            }

            expHud.updateFromComponent(exp, stats);

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
                        (d, s) -> {
                            try {
                                d.delta = Integer.parseInt(s);
                            } catch (NumberFormatException e) {
                                d.delta = 0;
                            }
                        },
                        d -> Integer.toString(d.delta)
                ).add()
                .build();

        private Interaction action;
        private StatId stat;
        private int delta;
    }

    private enum Interaction {
        Close,
        AdjustStat;

        public static final Codec<Interaction> CODEC = new EnumCodec<>(Interaction.class, EnumCodec.EnumStyle.CAMEL_CASE);
    }
}
