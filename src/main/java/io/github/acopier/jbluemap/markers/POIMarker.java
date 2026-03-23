package io.github.acopier.jbluemap.markers;

import io.github.acopier.jbluemap.Position;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class POIMarker extends BaseMarker {
    public final String detail;
    public final String icon;
    public final List<String> classes;


    public POIMarker(String key, String label, Position position, String detail, String icon, List<String> classes) {
        super(key, label, position);
        this.detail = detail;
        this.icon = icon;
        this.classes = classes != null ? classes : Collections.emptyList();
    }


    @SuppressWarnings("unchecked")
    public static POIMarker fromResponse(String key, Map<String, Object> r) {
        String label = (String) r.get("label");
        Position pos = Position.fromResponse((Map<String, Object>) r.get("position"));
        String detail = (String) r.get("detail");
        String icon = (String) r.get("icon");
        List<String> classes = (List<String>) r.get("classes");
        return new POIMarker(key, label, pos, detail, icon, classes);
    }
}
