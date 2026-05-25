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
    }

    @Override
    protected void init() {
        int gridTop = 96;
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

        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(status), this.width / 2, this.height - 48, 0xFF8A94A8);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
