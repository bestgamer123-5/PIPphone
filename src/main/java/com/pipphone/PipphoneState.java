package com.pipphone;

import com.pipphone.integration.PipphoneCarProvider;
import com.pipphone.phone.CarPhoneController;
import com.pipphone.phone.CarTelemetryAggregator;

/**
 * Shared runtime state between common and client entrypoints.
 */
public final class PipphoneState {

    public static final CarTelemetryAggregator TELEMETRY = new CarTelemetryAggregator();
    public static final PipphoneCarProvider CAR_PROVIDER = new PipphoneCarProvider();
    public static final CarPhoneController CONTROLLER = new CarPhoneController(TELEMETRY);

    private PipphoneState() {
    }
}
