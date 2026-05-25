package com.pipphone.client;

import com.carlib.api.ui.HudOverlay;
import com.pipphone.phone.CarPhoneController;

/**
 * CarLib HUD overlay entry that delegates drawing to {@link PipphoneHudRenderer}.
 */
public final class PipphoneHudOverlay implements HudOverlay {

    public static final String OVERLAY_ID = "pipphone:hud";

    private final CarPhoneController controller;

    public PipphoneHudOverlay(CarPhoneController controller) {
        this.controller = controller;
    }

    @Override
    public String id() {
        return OVERLAY_ID;
    }

    @Override
    public void render(int screenWidth, int screenHeight, float tickDelta) {
        PipphoneHudRenderer.render(controller, screenWidth, screenHeight, tickDelta);
    }
}
