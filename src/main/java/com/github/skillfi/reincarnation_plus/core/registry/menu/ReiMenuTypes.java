package com.github.skillfi.reincarnation_plus.core.registry.menu;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.menu.MagicInfuserMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReiMenuTypes {
    private static final DeferredRegister<MenuType<?>> registry;
    public static final RegistryObject<MenuType<MagicInfuserMenu>> MAGIC_INFUSER;

    public ReiMenuTypes() {
    }

    public static void init(IEventBus modEventBus) {
        registry.register(modEventBus);
    }

    static {
        registry = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ReiMod.MODID);
        MAGIC_INFUSER = registry.register("magic_infuser_menu", () -> IForgeMenuType.create(MagicInfuserMenu::new));
    }
}
