package com.github.skillfi.reincarnation_plus.core.client;

import com.github.manasmods.tensura.client.screen.KilnScreen;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.client.screen.MagicInfuserScreen;
import com.github.skillfi.reincarnation_plus.core.registry.menu.ReiMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = ReiMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = {Dist.CLIENT}
)
public class ReiClient {

    public ReiClient() {}

    @SubscribeEvent
    public static void init(FMLClientSetupEvent e) {
        menuRegister();
    }

    public static void menuRegister() {
        MenuScreens.register((MenuType) ReiMenuTypes.MAGIC_INFUSER.get(), MagicInfuserScreen::new);
    }
}
