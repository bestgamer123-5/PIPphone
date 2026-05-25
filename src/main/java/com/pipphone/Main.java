package com.pipphone;

import com.pipphone.integration.PipphoneCarProvider;
import com.pipphone.phone.CarPhoneController;
import com.pipphone.phone.CarTelemetryAggregator;
import com.pipphone.phone.CarPhoneHud;

/**
 * Optional desktop Swing demo. The Fabric mod uses {@link com.pipphone.client.PipphoneClientMod} instead.
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        CarTelemetryAggregator telemetry = new CarTelemetryAggregator();
        telemetry.refresh(null);
        PipphoneCarProvider provider = new PipphoneCarProvider();
        provider.update(telemetry);
        CarPhoneController controller = new CarPhoneController(telemetry);
        new CarPhoneHud(controller).show();
    }
}
