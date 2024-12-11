package com.github.skillfi.reincarnation_plus.core.registry;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.menu.AutomaticMagicInfuserMenu;
import com.github.skillfi.reincarnation_plus.core.menu.MagicInfuserMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReiMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY;
	public static final RegistryObject<MenuType<MagicInfuserMenu>> MAGIC_INFUSER_MENU;
	public static final RegistryObject<MenuType<AutomaticMagicInfuserMenu>> AUTOMATIC_MAGIC_INFUSER_MENU;

	static {
		REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ReiMod.MODID);
		MAGIC_INFUSER_MENU = REGISTRY.register("magic_infuser_menu", () -> IForgeMenuType.create(MagicInfuserMenu::new));
		AUTOMATIC_MAGIC_INFUSER_MENU = REGISTRY.register("automatic_magic_infuser_menu", () -> IForgeMenuType.create(AutomaticMagicInfuserMenu::new));
	}
}
