package io.github.acopier.jbluemap.collections;

import io.github.acopier.jbluemap.MarkerSet;
import io.github.acopier.jbluemap.utilities.MultipleMatchesException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record MarkerCollection(List<MarkerSet> markerSets) {
    public MarkerCollection(List<MarkerSet> markerSets) {
        this.markerSets = markerSets != null ? markerSets : Collections.emptyList();
    }


    @SuppressWarnings("unchecked")
    public static MarkerCollection fromResponse(Map<String, Object> response) {
        List<MarkerSet> sets = response.entrySet().stream()
                .map(e -> MarkerSet.fromResponse((String) e.getKey(), (Map<String, Object>) e.getValue()))
                .collect(Collectors.toList());
        return new MarkerCollection(sets);
    }


    public MarkerSet fromKey(String key) {
        List<MarkerSet> matches = markerSets.stream().filter(s -> s.key().equals(key)).toList();
        if (matches.isEmpty()) return null;
        if (matches.size() > 1) throw new MultipleMatchesException(key);
        return matches.getFirst();
    }
}
