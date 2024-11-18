package com.github.skillfi.reincarnation_plus;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = RPMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent.Loading configEvent){
        final ModConfig config = configEvent.getConfig();
        RPMod.LOGGER.debug("Loaded Reincarnation Plus config file {}", config.getFileName());
    }

    public void init() {}

    public void postInit() {}

    public void setup() {}
}
