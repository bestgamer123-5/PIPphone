package com.pipphone.phone;

import java.lang.reflect.Method;

/**
 * Builds telemetry from the local player context when no vehicle mod is present.
 */
public final class PipphoneTelemetry {

    public static final String FEATURE_PROVIDER_ID = "pipphone";

    private PipphoneTelemetry() {
    }

    public static CarTelemetryProvider fromContext(Object context) {
        if (context == null) {
            return TelemetrySnapshot.demo();
        }
        try {
            Method hasVehicle = context.getClass().getMethod("hasVehicle");
            Method getVelocity = context.getClass().getMethod("getVelocity");
            Object velocity = getVelocity.invoke(context);
            double dx = readAxis(velocity, "getX", "x");
            double dz = readAxis(velocity, "getZ", "z");
            double speedMs = Math.sqrt(dx * dx + dz * dz);
            double speedKmh = speedMs * 20.0 * 3.6;
            boolean riding = Boolean.TRUE.equals(hasVehicle.invoke(context));
            if (!riding && speedKmh < 1.0) {
                return TelemetrySnapshot.idle();
            }
            return new TelemetrySnapshot(
                    speedKmh,
                    riding ? 72.0 : 100.0,
                    88.0,
                    riding ? "D" : "P",
                    riding || speedKmh > 3.0
            );
        } catch (ReflectiveOperationException ignored) {
            return TelemetrySnapshot.demo();
        }
    }

    public static Object findVehicle(Object context) {
        if (context == null) {
            return null;
        }
        try {
            Method getVehicle = context.getClass().getMethod("getVehicle");
            return getVehicle.invoke(context);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static double readAxis(Object vector, String getter, String field) {
        if (vector == null) {
            return 0.0;
        }
        try {
            return toDouble(vector.getClass().getMethod(getter).invoke(vector));
        } catch (ReflectiveOperationException ignored) {
            try {
                return toDouble(vector.getClass().getField(field).get(vector));
            } catch (ReflectiveOperationException ignored2) {
                return 0.0;
            }
        }
    }

    private static double toDouble(Object value) {
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        return 0.0;
    }
}
