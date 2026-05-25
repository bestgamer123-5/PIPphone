package com.pipphone.client;

import com.carlib.api.ui.ButtonElement;
import com.carlib.api.ui.MenuScreenDefinition;
import com.carlib.api.ui.ProgressBarElement;
import com.carlib.api.ui.UiApi;
import com.carlib.api.ui.UiElement;
import com.pipphone.phone.CarPhoneController;
import com.pipphone.phone.PhoneApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers PIPphone HUD and menu definitions with CarLib {@link UiApi}.
 */
public final class CarLibPhoneRegistration {

    public static final String MENU_ID = "pipphone:menu";

    private CarLibPhoneRegistration() {
    }

    public static void register(CarPhoneController controller) {
        UiApi.registerHudOverlay(new PipphoneHudOverlay(controller));
        UiApi.registerMenu(buildMenu(controller));
    }

    private static MenuScreenDefinition buildMenu(CarPhoneController controller) {
        List<UiElement> elements = new ArrayList<>();
        elements.add(new ProgressBarElement("pipphone:speed", toProgress(controller.rawSpeed(), 260.0)));
        elements.add(new ProgressBarElement("pipphone:fuel", controller.fuel() / 100.0f));
        elements.add(new ProgressBarElement("pipphone:battery", controller.battery() / 100.0f));

        for (PhoneApp app : controller.apps()) {
            elements.add(new ButtonElement(
                    "pipphone:app:" + app.name().toLowerCase(),
                    app.name(),
                    app.onOpen()
            ));
        }

        return new MenuScreenDefinition(MENU_ID, "PIPphone", List.copyOf(elements));
    }

    private static float toProgress(double current, double max) {
        if (max <= 0) {
            return 0f;
        }
        return (float) Math.max(0.0, Math.min(current / max, 1.0));
    }
}
