package io.github.acopier.jbluemap;

import java.util.Map;

record Rotation(double pitch, double roll, double yaw) {


    public static Rotation fromResponse(Map<String, Object> r) {
        Number p = (Number) r.get("pitch");
        Number roll = (Number) r.get("roll");
        Number yaw = (Number) r.get("yaw");
        return new Rotation(p.doubleValue(), roll.doubleValue(), yaw.doubleValue());
    }
}
