package io.github.acopier.jbluemap.markers;

import io.github.acopier.jbluemap.Position;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtrudeMarker extends BaseMarker {
    public final List<Position> shape;
    public final int shapeMinY;
    public final int shapeMaxY;
    public final String detail;


    public ExtrudeMarker(String key, String label, Position position, List<Position> shape, int shapeMinY, int shapeMaxY, String detail) {
        super(key, label, position);
        this.shape = shape != null ? shape : Collections.emptyList();
        this.shapeMinY = shapeMinY;
        this.shapeMaxY = shapeMaxY;
        this.detail = detail;
    }


    @SuppressWarnings("unchecked")
    public static ExtrudeMarker fromResponse(String key, Map<String, Object> r) {
        String label = (String) r.get("label");
        Position pos = Position.fromResponse((Map<String, Object>) r.get("position"));
        List<Map<String, Object>> rawShape = (List<Map<String, Object>>) r.get("shape");
        List<Position> shape = rawShape == null ? Collections.emptyList() : rawShape.stream().map(Position::fromResponse).collect(Collectors.toList());
        Number minY = (Number) r.getOrDefault("shape-min-y", 0);
        Number maxY = (Number) r.getOrDefault("shape-max-y", 0);
        String detail = (String) r.get("detail");
        return new ExtrudeMarker(key, label, pos, shape, minY.intValue(), maxY.intValue(), detail);
    }
}
