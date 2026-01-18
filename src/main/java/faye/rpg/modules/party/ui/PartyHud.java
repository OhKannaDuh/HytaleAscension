/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.party.ui;

import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import faye.rpg.modules.party.Party;
import faye.rpg.modules.stats.components.AscensionExp;
import faye.rpg.ui.IAscensionHudElement;
import faye.rpg.ui.IAscensionHudElementFactory;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PartyHud extends CustomUIHud implements IAscensionHudElement {
    public PartyHud(@NonNull PlayerRef playerRef) {
        super(playerRef);
    }

    @Override
    protected void build(@NonNull UICommandBuilder ui) {
        ui.append("Hud/Party/PartyHud.ui");
    }

    public void clear() {
        var ui = new UICommandBuilder();
        ui.clear("#PartyList #Members");
        this.update(false, ui);
    }

    public void updateFromParty(Party party, World world) {
        var ui = new UICommandBuilder();
        ui.clear("#PartyList #Members");

        var store = world.getEntityStore().getStore();

        var current = this.getPlayerRef().getUuid();
        for (var member : party.getOrderedMembers(current)) {
            var entity = world.getEntityRef(member);
            if (entity == null) {
                party.removeMember(member);
                continue;
            }

            var player = store.getComponent(entity, Player.getComponentType());
            if (player == null) {
                continue;
            }

            var rpg = store.getComponent(entity, AscensionExp.getComponentType());
            if (rpg == null) {
                continue;
            }

            var stats = store.getComponent(entity, EntityStatMap.getComponentType());
            if (stats == null) {
                continue;
            }

            var health = stats.get(DefaultEntityStatTypes.getHealth());
            if (health == null) {
                continue;
            }

            String name = player.getDisplayName();
            String jobName = "Adventurer";
            int level = rpg.getLevel();
            float healthPercentage = health.asPercentage();

            ui.appendInline("#PartyList #Members", """
                        Group {
                           LayoutMode: Top;
                           Anchor: (Width: 325);
                           Padding: (Bottom: 6);
                    
                           Group {
                               Label {
                                   Anchor: (Left: 0);
                                   Style: LabelStyle(FontSize: 16);
                                   Text: "%1$s (%2$s)";
                               }
                    
                               Label {
                                   Anchor: (Right: 0);
                                   Style: LabelStyle(FontSize: 16, HorizontalAlignment: End);
                                   Text: "Level: %3$d";
                               }
                           }
                    
                           Group { Anchor: (Height: 6); }
                    
                           Group {
                               Anchor: (Width: 325, Height: 12, Left: 0);
                                Background: "Hud/LeftBarBackground.png";
                    
                               ProgressBar #HealthBar {
                                   BarTexturePath: "Hud/Party/HealthBarFill.png";
                                   Value: %4$f;
                               }
                               ProgressBar #HealthBarEffect {
                                   BarTexturePath: "Hud/LeftBarEffect.png";
                                   Value: %4$f;
                               }
                           }
                        }
                    """.formatted(
                    name,
                    jobName,
                    level,
                    healthPercentage
            ));
        }
        this.update(false, ui);
    }

    @Override
    public void buildHud(@NonNull UICommandBuilder ui) {
        this.build(ui);
    }

    public static class Factory implements IAscensionHudElementFactory {
        @Override
        public IAscensionHudElement create(PlayerRef ref) {
            return new PartyHud(ref);
        }
    }
}
