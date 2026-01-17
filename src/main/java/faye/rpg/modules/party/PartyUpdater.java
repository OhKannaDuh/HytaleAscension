package faye.rpg.modules.party;

import com.google.inject.Inject;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import faye.rpg.Logger;
import faye.rpg.lifecycle.hooks.IOnStart;
import faye.rpg.modules.party.components.PartyMember;
import faye.rpg.modules.party.ui.PartyHud;
import faye.rpg.ui.CustomHudManager;

import java.util.concurrent.TimeUnit;

public class PartyUpdater implements IOnStart {
    private final PartyRegistry registry;

    private final Logger logger;

    @Inject
    public PartyUpdater(PartyRegistry registry, Logger logger) {
        this.registry = registry;
        this.logger = logger;
    }


    @Override
    public void start() {
//        HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(() -> {
//            logger.info("Updating party lists");
//            for (var world : Universe.get().getWorlds().values()) {
//                var store = world.getEntityStore().getStore();
//
//                for (var playerRef : world.getPlayerRefs()) {
//                    var entity = playerRef.getReference();
//                    if (entity == null) {
//                        logger.warn("Could not get entity from player ref");
//                        continue;
//                    }
//
//                    var member = store.getComponent(entity, PartyMember.getComponentType());
//                    if (member == null) {
//                        logger.warn("Player was not in a party");
//                        continue;
//                    }
//
//                    var party = registry.getParty(member.getPartyUuid());
//                    if (party == null) {
//                        logger.warn("Party was not found in registry");
//                        continue;
//                    }
//
//                    var player = store.getComponent(entity, Player.getComponentType());
//                    if (player == null) {
//                        logger.warn("Could not get player component");
//                        continue;
//                    }
//
//                    if (!(player.getHudManager().getCustomHud() instanceof CustomHudManager manager)) {
//                        logger.warn("Custom hud was not manager type");
//                        continue;
//                    }
//
//                    var hud = manager.get(PartyHud.class);
//                    if (hud == null) {
//                        logger.warn("Could not get party hud.");
//                        continue;
//                    }
//
////                    hud.updateFromParty(party, world);
//                }
//            }
//        }, 3, 3, TimeUnit.SECONDS);
    }
}
