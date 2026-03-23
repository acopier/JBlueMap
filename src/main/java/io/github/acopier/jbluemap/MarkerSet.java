package io.github.acopier.jbluemap;

import io.github.acopier.jbluemap.markers.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record MarkerSet(String key, String label, List<BaseMarker> markers) {
    public MarkerSet(String key, String label, List<BaseMarker> markers) {
        this.key = key;
        this.label = label;
        this.markers = markers != null ? markers : Collections.emptyList();
    }


    @SuppressWarnings("unchecked")
    public static MarkerSet fromResponse(String key, Map<String, Object> responseJson) {
        if (responseJson == null || responseJson.isEmpty()) return null;
        String label = (String) responseJson.get("label");
        Map<String, Object> unformattedMarkers = (Map<String, Object>) responseJson.get("markers");
        if (unformattedMarkers == null) return new MarkerSet(key, label, Collections.emptyList());
        List<BaseMarker> markers = unformattedMarkers.entrySet().stream()
                .map(e -> MarkerFactory.getMarker((String) e.getKey(), (Map<String, Object>) e.getValue()))
                .collect(Collectors.toList());
        return new MarkerSet(key, label, markers);
    }


    public List<ExtrudeMarker> extrudeMarkers() {
        return markers.stream().filter(m -> m instanceof ExtrudeMarker).map(m -> (ExtrudeMarker) m).collect(Collectors.toList());
    }

    public List<HTMLMarker> htmlMarkers() {
        return markers.stream().filter(m -> m instanceof HTMLMarker).map(m -> (HTMLMarker) m).collect(Collectors.toList());
    }

    public List<LineMarker> lineMarkers() {
        return markers.stream().filter(m -> m instanceof LineMarker).map(m -> (LineMarker) m).collect(Collectors.toList());
    }

    public List<POIMarker> poiMarkers() {
        return markers.stream().filter(m -> m instanceof POIMarker).map(m -> (POIMarker) m).collect(Collectors.toList());
    }

    public List<ShapeMarker> shapeMarkers() {
        return markers.stream().filter(m -> m instanceof ShapeMarker).map(m -> (ShapeMarker) m).collect(Collectors.toList());
    }
}
