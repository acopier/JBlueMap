package io.github.acopier.jbluemap.collections;

import java.util.Map;

public record Collection(MarkerCollection markerCollection, PlayerCollection playerCollection) {


    public static Collection fromResponse(Map<String, Object> markerResponse, Map<String, Object> playerResponse) {
        MarkerCollection mc = MarkerCollection.fromResponse(markerResponse);
        PlayerCollection pc = PlayerCollection.fromResponse(playerResponse);
        return new Collection(mc, pc);
    }
}
