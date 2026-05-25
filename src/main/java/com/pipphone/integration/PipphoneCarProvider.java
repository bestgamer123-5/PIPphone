package com.pipphone.integration;

import com.carlib.api.CarIntegrationProvider;
import com.pipphone.phone.CarTelemetryProvider;
import com.pipphone.phone.PipphoneTelemetry;
import com.pipphone.phone.TelemetrySnapshot;

import java.util.Set;

/**
 * Exposes PIPphone telemetry through CarLib for other mods to discover.
 */
public final class PipphoneCarProvider implements CarIntegrationProvider {

    private volatile CarTelemetryProvider telemetry = TelemetrySnapshot.demo();

    @Override
    public String providerId() {
        return PipphoneTelemetry.FEATURE_PROVIDER_ID;
    }

    @Override
    public Set<String> supportedFeatures() {
        return Set.of("telemetry");
    }

    @Override
    public Object invoke(String featureKey, Object input) {
        if (!"telemetry".equals(featureKey)) {
            return null;
        }
        return telemetry;
    }

    public void update(CarTelemetryProvider provider) {
        this.telemetry = provider;
    }
}
