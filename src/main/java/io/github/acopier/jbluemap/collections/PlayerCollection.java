package io.github.acopier.jbluemap.collections;

import io.github.acopier.jbluemap.Player;
import io.github.acopier.jbluemap.utilities.MultipleMatchesException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record PlayerCollection(List<Player> players) {
    public PlayerCollection(List<Player> players) {
        this.players = players != null ? players : Collections.emptyList();
    }


    @SuppressWarnings("unchecked")
    public static PlayerCollection fromResponse(Map<String, Object> response) {
        List<Map<String, Object>> raw = (List<Map<String, Object>>) response.get("players");
        if (raw == null) return new PlayerCollection(Collections.emptyList());
        List<Player> players = raw.stream().map(Player::fromResponse).collect(Collectors.toList());
        return new PlayerCollection(players);
    }


    public List<Player> isForeign() {
        return players.stream().filter(Player::foreign).collect(Collectors.toList());
    }


    public List<Player> notForeign() {
        return players.stream().filter(p -> !p.foreign()).collect(Collectors.toList());
    }


    public Player fromUuid(String uuid) {
        List<Player> matches = players.stream().filter(p -> p.uuid().equals(uuid)).toList();
        if (matches.isEmpty()) return null;
        if (matches.size() > 1) throw new MultipleMatchesException(uuid);
        return matches.getFirst();
    }


    public Player fromName(String name) {
        List<Player> matches = players.stream().filter(p -> p.name().equalsIgnoreCase(name)).toList();
        if (matches.isEmpty()) return null;
        if (matches.size() > 1) throw new MultipleMatchesException(name);
        return matches.getFirst();
    }
}
