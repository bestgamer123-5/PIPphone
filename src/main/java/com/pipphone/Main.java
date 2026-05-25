package com.pipphone;

import com.pipphone.phone.CarPhoneController;
import com.pipphone.phone.CarPhoneHud;
import com.pipphone.phone.CarTelemetryProvider;
import com.pipphone.phone.ReflectionCarLibTelemetryProvider;
import com.pipphone.phone.CarLibUiBridge;

import javax.swing.SwingUtilities;

/**
 * Demo launcher for a car-integrated phone HUD.
 *
 * CarLib is used through reflection in {@link ReflectionCarLibTelemetryProvider}
 * so this sample can still run even when a CarLib runtime object is not provided.
 */
public final class Main {

    private Main() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CarTelemetryProvider telemetry = new ReflectionCarLibTelemetryProvider(null);
            CarPhoneController controller = new CarPhoneController(telemetry);
            CarPhoneHud hud = new CarPhoneHud(controller);
            hud.show();

            // Optional CarLib UI API integration (auto-detected via reflection).
            CarLibUiBridge uiBridge = new CarLibUiBridge();
            uiBridge.registerPhoneHud(controller);
        });
    }
}
