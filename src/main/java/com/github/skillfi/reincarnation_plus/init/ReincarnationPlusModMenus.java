
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.init;

import com.github.skillfi.reincarnation_plus.world.inventory.BlacksmithGemsMenu;
import com.github.skillfi.reincarnation_plus.ReincarnationPlusMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReincarnationPlusModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ReincarnationPlusMod.MODID);
	public static final RegistryObject<MenuType<BlacksmithGemsMenu>> BLACKSMITH_GEMS = REGISTRY.register("blacksmith_gems", () -> IForgeMenuType.create(BlacksmithGemsMenu::new));
}
