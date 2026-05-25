package com.pipphone.phone;

import com.carlib.api.CarIntegrationProvider;
import com.carlib.api.CarLibApi;

/**
 * Resolves telemetry from CarLib providers, ridden entities, then demo fallbacks.
 */
public final class CarTelemetryAggregator implements CarTelemetryProvider {

    public static final String TELEMETRY_FEATURE = "telemetry";

    private volatile CarTelemetryProvider active = TelemetrySnapshot.demo();

    public void refresh(Object context) {
        for (CarIntegrationProvider provider : CarLibApi.providers()) {
            if (!provider.providerId().equals(PipphoneTelemetry.FEATURE_PROVIDER_ID)
                    && provider.supportedFeatures().contains(TELEMETRY_FEATURE)) {
                Object result = provider.invoke(TELEMETRY_FEATURE, context);
                CarTelemetryProvider resolved = resolve(result);
                if (resolved != null) {
                    active = resolved;
                    return;
                }
            }
        }

        Object vehicle = PipphoneTelemetry.findVehicle(context);
        if (vehicle != null) {
            CarTelemetryProvider reflected = new ReflectionCarLibTelemetryProvider(vehicle);
            if (reflected.speedKmh() > 0 || reflected.engineOn()) {
                active = reflected;
                return;
            }
        }

        active = PipphoneTelemetry.fromContext(context);
    }

    @Override
    public double speedKmh() {
        return active.speedKmh();
    }

    @Override
    public double fuelPercent() {
        return active.fuelPercent();
    }

    @Override
    public double batteryPercent() {
        return active.batteryPercent();
    }

    @Override
    public String gear() {
        return active.gear();
    }

    @Override
    public boolean engineOn() {
        return active.engineOn();
    }

    private static CarTelemetryProvider resolve(Object result) {
        if (result instanceof CarTelemetryProvider provider) {
            return provider;
        }
        if (result instanceof TelemetrySnapshot snapshot) {
            return snapshot;
        }
        return null;
    }
}
