/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.modules.party;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PartyRegistry {
    private final Map<UUID, Party> parties = new HashMap<>();

    public Party createParty(UUID leader) {
        UUID id = UUID.randomUUID();
        Party party = new Party(id, leader);
        parties.put(id, party);
        return party;
    }

    public Party getParty(UUID id) {
        return parties.get(id);
    }

    public void disbandParty(UUID id) {
        parties.remove(id);
    }
}
