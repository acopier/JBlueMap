package io.github.acopier.jbluemap;

import java.util.Map;

public record Position(double x, double y, double z) {


    public static Position fromResponse(Map<String, Object> r) {
        // Python sets y = 0 if missing
        Number nx = (Number) r.get("x");
        Number ny = (Number) r.getOrDefault("y", 0);
        Number nz = (Number) r.get("z");
        return new Position(nx.doubleValue(), ny.doubleValue(), nz.doubleValue());
    }
}
