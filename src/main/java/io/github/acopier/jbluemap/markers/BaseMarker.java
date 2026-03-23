package io.github.acopier.jbluemap.markers;

import io.github.acopier.jbluemap.Position;

public abstract class BaseMarker {
    public final String key;
    public final String label;
    public final Position position;


    protected BaseMarker(String key, String label, Position position) {
        this.key = key;
        this.label = label;
        this.position = position;
    }
}
