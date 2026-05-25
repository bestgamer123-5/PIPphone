package com.pipphone.phone;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CarPhoneController {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private final CarTelemetryProvider telemetry;
    private final List<PhoneApp> apps;

    public CarPhoneController(CarTelemetryProvider telemetry) {
        this.telemetry = telemetry;
        this.apps = List.of(
                new PhoneApp("Navigation", "🧭", () -> {}),
                new PhoneApp("Radio", "📻", () -> {}),
                new PhoneApp("Camera", "📷", () -> {}),
                new PhoneApp("Messages", "💬", () -> {}),
                new PhoneApp("Garage", "🛠", () -> {}),
                new PhoneApp("Weather", "☁️", () -> {}),
                new PhoneApp("Calls", "📞", () -> {}),
                new PhoneApp("Settings", "⚙️", () -> {})
        );
    }

    public String time() { return LocalTime.now().format(TIME_FORMAT); }
    public double rawSpeed() { return telemetry.speedKmh(); }
    public String speedText() { return String.format("%.0f km/h", rawSpeed()); }
    public int battery() { return (int) Math.round(telemetry.batteryPercent()); }
    public int fuel() { return (int) Math.round(telemetry.fuelPercent()); }
    public String gear() { return telemetry.gear(); }
    public boolean engineOn() { return telemetry.engineOn(); }
    public List<PhoneApp> apps() { return apps; }
}
