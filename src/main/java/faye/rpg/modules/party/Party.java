package faye.rpg.modules.party;

import java.util.*;
import java.util.stream.Collectors;

public class Party {
    private final UUID uuid;

    private UUID leader;

    private final Set<UUID> members = new LinkedHashSet<>();

    public Party(UUID id, UUID leader) {
        this.uuid = id;
        this.leader = leader;
        members.add(leader);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void addMember(UUID uuid) {
        members.add(uuid);
//        PartyMemberJoin.dispatch(this.uuid, uuid, "");
    }

    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }

    public boolean isLeader(UUID uuid) {
        return leader.equals(uuid);
    }

    public UUID getFirstNonLeaderMember() {

        for (UUID member : members) {
            if (!member.equals(leader)) {
                return member;
            }
        }

        return null;
    }

    public void transferLeadership(UUID uuid) {
        if (this.isLeader(uuid)) {
            return;
        }

        if (!members.contains(uuid)) {
            return;
        }

        leader = uuid;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public Set<UUID> getMembersExcluding(UUID uuid) {
        return members.stream().filter(m -> !m.equals(uuid)).collect(Collectors.toSet());
    }

    public List<UUID> getOrderedMembers(UUID clientUuid) {
        return members.stream()
                .sorted((a, b) -> {
                    if (a.equals(clientUuid)) return -1;
                    if (b.equals(clientUuid)) return 1;

                    if (a.equals(leader)) return -1;
                    if (b.equals(leader)) return 1;

                    return a.compareTo(b);
                })
                .toList();
    }

}
