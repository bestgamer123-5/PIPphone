package com.pipphone.phone;

/**
 * Immutable vehicle telemetry payload for CarLib provider responses.
 */
public record TelemetrySnapshot(
        double speedKmh,
        double fuelPercent,
        double batteryPercent,
        String gear,
        boolean engineOn
) implements CarTelemetryProvider {

    public static TelemetrySnapshot demo() {
        return new TelemetrySnapshot(48.0, 65.0, 82.0, "D", true);
    }

    public static TelemetrySnapshot idle() {
        return new TelemetrySnapshot(0.0, 100.0, 95.0, "P", false);
    }

    @Override
    public double speedKmh() {
        return speedKmh;
    }

    @Override
    public double fuelPercent() {
        return fuelPercent;
    }

    @Override
    public double batteryPercent() {
        return batteryPercent;
    }

    @Override
    public String gear() {
        return gear;
    }

    @Override
    public boolean engineOn() {
        return engineOn;
    }
}
