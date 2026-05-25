package com.pipphone.client;

import com.pipphone.phone.CarPhoneController;
import net.minecraft.client.MinecraftClient;

public final class PipphonePhoneUiState {
    public static final PipphonePhoneUiState INSTANCE = new PipphonePhoneUiState();

    private String currentApp = "Home";
    private String line1 = "Select an app";
    private String line2 = "";
    private String line3 = "";
    private String line4 = "";

    private PipphonePhoneUiState() {
    }

    public void home() {
        setView("Home", "Select an app", "", "", "");
    }

    public void setNavigation(MinecraftClient client) {
        if (client.player == null || client.world == null) {
            setView("Navigation", "GPS unavailable", "Player data is not loaded yet", "", "");
            return;
        }

        String weather = client.world.isThundering() ? "Storm"
                : client.world.isRaining() ? "Rain"
                : "Clear";

        String coords = String.format("X %.0f  Y %.0f  Z %.0f",
                client.player.getX(), client.player.getY(), client.player.getZ());

        setView(
                "Navigation",
                "GPS lock: active",
                coords,
                "Weather context: " + weather,
                "Waypoint: " + client.player.getBlockPos().getX() + ", " + client.player.getBlockPos().getZ()
        );
    }

    public void setRadio() {
        setView(
                "Radio",
                "Station: Pulse 88.1",
                "Auto-play: enabled",
                "Now: evening synth mix",
                "Signal: strong"
        );
    }

    public void setCamera() {
        setView(
                "Camera",
                "Photo mode: armed",
                "Preview: front dash view",
                "Use the quick capture button to save a shot",
                "Storage: local screenshots"
        );
    }

    public void setMessages() {
        setView(
                "Messages",
                "Inbox: 3 unread",
                "Alex: ETA 10 min",
                "Noah: meet at garage",
                "Driver: route update"
        );
    }

    public void setGarage(CarPhoneController controller) {
        setView(
                "Garage",
                "Vehicle status",
                "Speed: " + controller.speedText(),
                "Fuel: " + controller.fuel() + "%  Battery: " + controller.battery() + "%",
                "Gear: " + controller.gear() + "  Engine: " + (controller.engineOn() ? "ON" : "OFF")
        );
    }

    public void setWeather(MinecraftClient client) {
        if (client.world == null) {
            setView("Weather", "Weather unavailable", "World data is not ready", "", "");
            return;
        }

        String weather = client.world.isThundering() ? "Stormy"
                : client.world.isRaining() ? "Rainy"
                : "Clear";
        setView(
                "Weather",
                "Current conditions",
                "Forecast: " + weather,
                "Sky: " + (client.world.isDay() ? "Day" : "Night"),
                "Tip: keep the windows closed"
        );
    }

    public void setCalls() {
        setView(
                "Calls",
                "Recent contacts",
                "Alex: 2 missed calls",
                "Mom: voicemail",
                "Tap to dial a contact"
        );
    }

    public void setSettings(MinecraftClient client) {
        String hudStatus = client.options.hudHidden ? "hidden" : "visible";
        setView(
                "Settings",
                "Phone preferences",
                "HUD: " + hudStatus,
                "Notifications: enabled",
                "Use the game options menu for deeper tweaks"
        );
    }

    public String currentApp() {
        return currentApp;
    }

    public String line1() {
        return line1;
    }

    public String line2() {
        return line2;
    }

    public String line3() {
        return line3;
    }

    public String line4() {
        return line4;
    }

    private void setView(String currentApp, String line1, String line2, String line3, String line4) {
        this.currentApp = currentApp;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
    }
}
