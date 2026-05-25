package com.pipphone.client;

import com.pipphone.phone.CarPhoneController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * Draws the in-game phone status panel (also invoked from {@link PipphoneHudOverlay#render}).
 */
public final class PipphoneHudRenderer {

    private static DrawContext drawContext;

    private PipphoneHudRenderer() {
    }

    public static void setDrawContext(DrawContext context) {
        drawContext = context;
    }

    public static void render(CarPhoneController controller, int screenWidth, int screenHeight, float tickDelta) {
        if (drawContext == null) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden || client.player == null || client.currentScreen != null) {
            return;
        }

        TextRenderer textRenderer = client.textRenderer;
        int panelWidth = 150;
        int panelHeight = 72;
        int x = screenWidth - panelWidth - 8;
        int y = 8;

        drawContext.fill(x, y, x + panelWidth, y + panelHeight, 0xC010141C);
        drawContext.fill(x, y, x + panelWidth, y + 1, 0xFF81DAFF);

        int textY = y + 6;
        drawContext.drawText(textRenderer, Text.literal(controller.time()), x + 8, textY, 0xFFFFFF, true);
        textY += 12;
        drawContext.drawText(textRenderer, Text.literal(controller.speedText()), x + 8, textY, 0xFF81DAFF, true);
        textY += 12;
        drawContext.drawText(textRenderer, Text.literal("Gear " + controller.gear() + "  Engine "
                + (controller.engineOn() ? "ON" : "OFF")), x + 8, textY, 0xFFE0E6F0, true);
        textY += 12;
        drawContext.drawText(textRenderer, Text.literal("Fuel " + controller.fuel() + "%  Batt " + controller.battery() + "%"),
                x + 8, textY, 0xFFB3BCCC, true);
        textY += 12;
        drawContext.drawText(textRenderer, Text.literal("Press P for apps"), x + 8, textY, 0xFF8A94A8, true);
    }
}
