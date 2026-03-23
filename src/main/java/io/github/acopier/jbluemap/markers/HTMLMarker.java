package io.github.acopier.jbluemap.markers;

import io.github.acopier.jbluemap.Position;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HTMLMarker extends BaseMarker {
    public final String html;
    public final List<String> classes;

    public HTMLMarker(String key, String label, Position position, String html, List<String> classes) {
        super(key, label, position);
        this.html = html;
        this.classes = classes != null ? classes : Collections.emptyList();
    }


    @SuppressWarnings("unchecked")
    public static HTMLMarker fromResponse(String key, Map<String, Object> r) {
        String label = (String) r.get("label");
        Position pos = Position.fromResponse((Map<String, Object>) r.get("position"));
        String html = (String) r.get("html");
        List<String> classes = (List<String>) r.get("classes");
        return new HTMLMarker(key, label, pos, html, classes);
    }
}
