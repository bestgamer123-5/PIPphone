package com.pipphone.phone;

import java.lang.reflect.Method;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Adapter that reads telemetry from a CarLib vehicle object using reflection.
 *
 * Expected methods on the provided object (if available):
 * getSpeedKmh(), getFuelPercent(), getBatteryPercent(), getGear(), isEngineOn().
 */
public class ReflectionCarLibTelemetryProvider implements CarTelemetryProvider {

    private final Object carLibVehicle;

    public ReflectionCarLibTelemetryProvider(Object carLibVehicle) {
        this.carLibVehicle = carLibVehicle;
    }

    @Override
    public double speedKmh() {
        return readDouble("getSpeedKmh", 35.0, 125.0);
    }

    @Override
    public double fuelPercent() {
        return readDouble("getFuelPercent", 10.0, 100.0);
    }

    @Override
    public double batteryPercent() {
        return readDouble("getBatteryPercent", 20.0, 100.0);
    }

    @Override
    public String gear() {
        Object value = read("getGear");
        if (value != null) {
            return String.valueOf(value);
        }
        String[] gears = {"P", "R", "N", "D"};
        return gears[ThreadLocalRandom.current().nextInt(gears.length)];
    }

    @Override
    public boolean engineOn() {
        Object value = read("isEngineOn");
        if (value instanceof Boolean b) {
            return b;
        }
        return true;
    }

    private Object read(String methodName) {
        if (carLibVehicle == null) return null;
        try {
            Method method = carLibVehicle.getClass().getMethod(methodName);
            return method.invoke(carLibVehicle);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private double readDouble(String methodName, double minFallback, double maxFallback) {
        Object value = read(methodName);
        if (value instanceof Number n) {
            return n.doubleValue();
        }
        return ThreadLocalRandom.current().nextDouble(minFallback, maxFallback);
    }
}
