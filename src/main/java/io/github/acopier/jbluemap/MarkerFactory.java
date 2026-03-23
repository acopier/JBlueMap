package io.github.acopier.jbluemap;

import io.github.acopier.jbluemap.markers.*;

import java.util.Map;

public class MarkerFactory {
    public static BaseMarker getMarker(String key, Map<String, Object> r) {
        String type = (String) r.get("type");
        return switch (type) {
            case "poi" -> POIMarker.fromResponse(key, r);
            case "html" -> HTMLMarker.fromResponse(key, r);
            case "line" -> LineMarker.fromResponse(key, r);
            case "shape" -> ShapeMarker.fromResponse(key, r);
            case "extrude" -> ExtrudeMarker.fromResponse(key, r);
            default -> throw new IllegalArgumentException("Unknown marker type: " + type);
        };
    }
}
