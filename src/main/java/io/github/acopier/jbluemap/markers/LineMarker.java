package io.github.acopier.jbluemap.markers;

import io.github.acopier.jbluemap.Position;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LineMarker extends BaseMarker {
    public final List<Position> line;
    public final String detail;

    public LineMarker(String key, String label, Position position, List<Position> line, String detail) {
        super(key, label, position);
        this.line = line != null ? line : Collections.emptyList();
        this.detail = detail;
    }

    @SuppressWarnings("unchecked")
    public static LineMarker fromResponse(String key, Map<String, Object> r) {
        String label = (String) r.get("label");
        Position pos = Position.fromResponse((Map<String, Object>) r.get("position"));
        List<Map<String, Object>> rawLine = (List<Map<String, Object>>) r.get("line");
        List<Position> line = rawLine == null ? Collections.emptyList() : rawLine.stream().map(Position::fromResponse).collect(Collectors.toList());
        String detail = (String) r.get("detail");
        return new LineMarker(key, label, pos, line, detail);
    }
}
