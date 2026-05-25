package com.pipphone.phone;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Reflection bridge for CarLib UI types:
 * HudOverlay, MenuScreenDefinition, ButtonElement, ProgressBarElement,
 * RpgInterfaceDefinition and UiApi.
 */
public class CarLibUiBridge {
    private static final String UI_PACKAGE = "com.carlib.api.ui.";

    /**
     * Registers phone UI/HUD objects in static UiApi from CarLib when present.
     */
    public void registerPhoneHud(CarPhoneController controller) {
        try {
            Class<?> uiApiClass = Class.forName(UI_PACKAGE + "UiApi");
            Class<?> hudOverlayClass = Class.forName(UI_PACKAGE + "HudOverlay");
            Class<?> menuClass = Class.forName(UI_PACKAGE + "MenuScreenDefinition");
            Class<?> buttonClass = Class.forName(UI_PACKAGE + "ButtonElement");
            Class<?> progressClass = Class.forName(UI_PACKAGE + "ProgressBarElement");
            Class<?> rpgClass = Class.forName(UI_PACKAGE + "RpgInterfaceDefinition");
            Class<?> uiElementClass = Class.forName(UI_PACKAGE + "UiElement");

            Object speed = newInstance(progressClass, "pipphone.speed", toProgress(controller.rawSpeed(), 260.0));
            Object fuel = newInstance(progressClass, "pipphone.fuel", controller.fuel() / 100.0f);
            Object battery = newInstance(progressClass, "pipphone.battery", controller.battery() / 100.0f);

            List<Object> elements = new ArrayList<>();
            addIfType(elements, uiElementClass, speed);
            addIfType(elements, uiElementClass, fuel);
            addIfType(elements, uiElementClass, battery);

            for (PhoneApp app : controller.apps()) {
                Object button = newInstance(buttonClass, app.name().toLowerCase(), app.name(), app.onOpen());
                addIfType(elements, uiElementClass, button);
            }

            Object overlay = Proxy.newProxyInstance(
                    hudOverlayClass.getClassLoader(),
                    new Class<?>[]{hudOverlayClass},
                    (proxy, method, args) -> {
                        if ("id".equals(method.getName())) return "pipphone.hud";
                        return null;
                    }
            );

            Object menu = newInstance(menuClass, "pipphone.menu", "PIPphone", elements);
            Object rpg = newInstance(rpgClass, "pipphone.rpg", listIfType(hudOverlayClass, overlay), listIfType(menuClass, menu));

            invokeStatic(uiApiClass, "registerHudOverlay", hudOverlayClass, overlay);
            invokeStatic(uiApiClass, "registerMenu", menuClass, menu);
            // requested type support: construct it even if UiApi has no register method in this version
            if (rpg != null) {
                // no-op: constructed for compatibility and future use
            }
        } catch (ReflectiveOperationException ignored) {
            // CarLib not present or signature mismatch; keep app runnable.
        }
    }

    private static float toProgress(double current, double max) {
        if (max <= 0) return 0f;
        double value = Math.max(0.0, Math.min(current / max, 1.0));
        return (float) value;
    }

    private static Object newInstance(Class<?> type, Object... args) {
        for (Constructor<?> c : type.getConstructors()) {
            if (c.getParameterCount() != args.length) continue;
            try {
                return c.newInstance(args);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private static void invokeStatic(Class<?> owner, String method, Class<?> argType, Object arg) {
        if (arg == null) return;
        try {
            Method m = owner.getMethod(method, argType);
            m.invoke(null, arg);
        } catch (Exception ignored) {
        }
    }

    private static void addIfType(List<Object> out, Class<?> type, Object value) {
        if (value != null && type.isInstance(value)) out.add(value);
    }

    private static List<Object> listIfType(Class<?> type, Object value) {
        List<Object> out = new ArrayList<>();
        addIfType(out, type, value);
        return out;
    }
}
