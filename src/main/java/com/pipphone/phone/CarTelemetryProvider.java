package com.pipphone.phone;

/**
 * Provides vehicle state used by the phone HUD.
 */
public interface CarTelemetryProvider {
    double speedKmh();
    double fuelPercent();
    double batteryPercent();
    String gear();
    boolean engineOn();
}
