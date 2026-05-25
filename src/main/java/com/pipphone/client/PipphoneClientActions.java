package com.pipphone.client;

import com.pipphone.PipphoneState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public final class PipphoneClientActions {

    private PipphoneClientActions() {
    }

    public static void notify(String appName) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return;
        }
        client.player.sendMessage(Text.literal("PIPphone: opened " + appName), false);
    }

    public static void openApp(String appName) {
        MinecraftClient client = MinecraftClient.getInstance();
        PipphonePhoneUiState uiState = PipphonePhoneUiState.INSTANCE;

        switch (appName) {
            case "Navigation" -> uiState.setNavigation(client);
            case "Radio" -> uiState.setRadio();
            case "Camera" -> uiState.setCamera();
            case "Messages" -> uiState.setMessages();
            case "Garage" -> uiState.setGarage(PipphoneState.CONTROLLER);
            case "Weather" -> uiState.setWeather(client);
            case "Calls" -> uiState.setCalls();
            case "Settings" -> uiState.setSettings(client);
            default -> uiState.home();
        }

        notify(appName);
    }
}
