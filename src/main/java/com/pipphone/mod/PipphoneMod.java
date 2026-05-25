package com.pipphone.mod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PipphoneMod implements ModInitializer {
    public static final String MOD_ID = "pipphone";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("PIPphone initialized for Minecraft 1.21.1");
    }
}
