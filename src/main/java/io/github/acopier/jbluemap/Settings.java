package io.github.acopier.jbluemap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public record Settings(String version, List<String> maps) {
    public Settings(String version, List<String> maps) {
        this.version = version;
        this.maps = maps != null ? maps : Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    public static Settings fromResponse(Map<String, Object> json) {
        String version = (String) json.get("version");
        List<String> maps = (List<String>) json.get("maps");
        return new Settings(version, maps);
    }
}
