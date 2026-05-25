package com.pipphone.client;

import com.pipphone.PipphoneState;
import com.pipphone.mod.PipphoneMod;
import com.pipphone.phone.CarPhoneController;
import com.pipphone.phone.PhoneApp;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class PipphoneClientMod implements ClientModInitializer {

    private static final String KEY_CATEGORY = "category.pipphone.phone";
    private static KeyBinding openPhoneKey;

    @Override
    public void onInitializeClient() {
        configureApps(PipphoneState.CONTROLLER);
        CarLibPhoneRegistration.register(PipphoneState.CONTROLLER);

        openPhoneKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pipphone.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                KEY_CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            PipphoneState.TELEMETRY.refresh(client.player);
            PipphoneState.CAR_PROVIDER.update(PipphoneState.TELEMETRY);

            while (openPhoneKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new PipphonePhoneScreen(PipphoneState.CONTROLLER));
                }
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            PipphoneHudRenderer.setDrawContext(drawContext);
            float tickDelta = tickCounter.getTickDelta(false);
            int width = drawContext.getScaledWindowWidth();
            int height = drawContext.getScaledWindowHeight();
            PipphoneHudRenderer.render(PipphoneState.CONTROLLER, width, height, tickDelta);
        });

        PipphoneMod.LOGGER.info("PIPphone client HUD and keybind (P) ready");
    }

    private static void configureApps(CarPhoneController controller) {
        controller.setApps(List.of(
                new PhoneApp("Navigation", "NAV", () -> PipphoneClientActions.openApp("Navigation")),
                new PhoneApp("Radio", "RAD", () -> PipphoneClientActions.openApp("Radio")),
                new PhoneApp("Camera", "CAM", () -> PipphoneClientActions.openApp("Camera")),
                new PhoneApp("Messages", "MSG", () -> PipphoneClientActions.openApp("Messages")),
                new PhoneApp("Garage", "GAR", () -> PipphoneClientActions.openApp("Garage")),
                new PhoneApp("Weather", "WTH", () -> PipphoneClientActions.openApp("Weather")),
                new PhoneApp("Calls", "CALL", () -> PipphoneClientActions.openApp("Calls")),
                new PhoneApp("Settings", "SET", () -> PipphoneClientActions.openApp("Settings"))
        ));
    }
}
