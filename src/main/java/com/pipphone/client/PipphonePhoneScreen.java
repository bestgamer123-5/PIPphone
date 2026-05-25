package com.pipphone.client;

import com.pipphone.phone.CarPhoneController;
import com.pipphone.phone.PhoneApp;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class PipphonePhoneScreen extends Screen {

    private final CarPhoneController controller;
    private String status = "Select an app";

    public PipphonePhoneScreen(CarPhoneController controller) {
        super(Text.literal("PIPphone"));
        this.controller = controller;
        PipphonePhoneUiState.INSTANCE.home();
    }

    @Override
    protected void init() {
        int gridTop = 164;
        int buttonWidth = 72;
        int buttonHeight = 20;
        int gap = 6;
        int columns = 4;
        int index = 0;

        for (PhoneApp app : controller.apps()) {
            int col = index % columns;
            int row = index / columns;
            int x = this.width / 2 - (columns * (buttonWidth + gap)) / 2 + col * (buttonWidth + gap);
            int y = gridTop + row * (buttonHeight + gap);

            addDrawableChild(ButtonWidget.builder(Text.literal(app.icon() + " " + app.name()), button -> {
                app.onOpen().run();
                status = "Opened: " + app.name();
            }).dimensions(x, y, buttonWidth, buttonHeight).build());

            index++;
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("Close"), button -> close())
                .dimensions(this.width / 2 - 40, this.height - 32, 80, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.fill(this.width / 2 - 110, 24, this.width / 2 + 110, 88, 0xC010141C);

        context.drawCenteredTextWithShadow(this.textRenderer, controller.time(), this.width / 2, 32, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, controller.speedText(), this.width / 2, 46, 0xFF81DAFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                "Gear " + controller.gear() + "  Engine " + (controller.engineOn() ? "ON" : "OFF"),
                this.width / 2, 60, 0xFFE0E6F0);
        context.drawCenteredTextWithShadow(this.textRenderer,
                "Fuel " + controller.fuel() + "%  Battery " + controller.battery() + "%",
                this.width / 2, 74, 0xFFB3BCCC);

        // Draw the interactive widgets first; afterwards we paint the app panel.
        super.render(context, mouseX, mouseY, delta);

        renderAppPanel(context);

        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(status), this.width / 2, this.height - 48, 0xFF8A94A8);
    }

    private void renderAppPanel(DrawContext context) {
        PipphonePhoneUiState uiState = PipphonePhoneUiState.INSTANCE;
        int panelLeft = this.width / 2 - 110;
        int panelTop = 96;
        int panelRight = this.width / 2 + 110;
        int panelBottom = 156;

        context.fill(panelLeft, panelTop, panelRight, panelBottom, 0xC010141C);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(uiState.currentApp()), this.width / 2, panelTop + 8, 0xFFFFFFFF);
        context.drawText(this.textRenderer, Text.literal(uiState.line1()), panelLeft + 10, panelTop + 28, 0xFFE0E6F0, false);
        context.drawText(this.textRenderer, Text.literal(uiState.line2()), panelLeft + 10, panelTop + 42, 0xFFE0E6F0, false);
        context.drawText(this.textRenderer, Text.literal(uiState.line3()), panelLeft + 10, panelTop + 56, 0xFFE0E6F0, false);
        context.drawText(this.textRenderer, Text.literal(uiState.line4()), panelLeft + 10, panelTop + 70, 0xFFE0E6F0, false);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
