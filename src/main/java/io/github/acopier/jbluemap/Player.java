package io.github.acopier.jbluemap;

import java.util.Map;

public record Player(String uuid, String name, boolean foreign, Position position, Rotation rotation) {

    @SuppressWarnings("unchecked")
    public static Player fromResponse(Map<String, Object> r) {
        String uuid = (String) r.get("uuid");
        String name = (String) r.get("name");
        boolean foreign = Boolean.TRUE.equals(r.get("foreign"));
        Map<String, Object> pos = (Map<String, Object>) r.get("position");
        Map<String, Object> rot = (Map<String, Object>) r.get("rotation");
        return new Player(uuid, name, foreign, Position.fromResponse(pos), Rotation.fromResponse(rot));
    }
}
