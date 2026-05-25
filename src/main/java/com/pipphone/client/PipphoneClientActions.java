package com.pipphone.client;

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
}
