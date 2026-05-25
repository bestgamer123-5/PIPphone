package com.pipphone.mod;

import com.carlib.api.CarLibApi;
import com.pipphone.PipphoneState;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PipphoneMod implements ModInitializer {
    public static final String MOD_ID = "pipphone";
    public static final Identifier PHONE_ID = Identifier.of(MOD_ID, "phone");
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CarLibApi.registerProvider(PipphoneState.CAR_PROVIDER);
        PipphoneState.CAR_PROVIDER.update(PipphoneState.TELEMETRY);
        LOGGER.info("PIPphone initialized with CarLib provider and phone controller");
    }
}
