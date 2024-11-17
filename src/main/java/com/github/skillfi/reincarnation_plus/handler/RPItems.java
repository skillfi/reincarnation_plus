
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.handler;

import com.github.skillfi.reincarnation_plus.entity.RPEntities;
import com.github.skillfi.reincarnation_plus.item.*;
import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.item.custom.JacketWomanArmorItem;
import com.github.skillfi.reincarnation_plus.item.custom.KimonoWomanArmorItem;
import com.github.skillfi.reincarnation_plus.item.custom.LeatherArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RPItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RPMod.MODID);
	public static final RegistryObject<Item> GEM_STONE = ITEMS.register("gem_stone", () -> new GemStoneItem(
			RPBlocks.GEM_STONE_BLOCK.get(),
			new Item.Properties().stacksTo(1).tab(RPTabs.TAB_REINCARNATION_PLUS)
	));
	public static final RegistryObject<Item> GEMS_BLOCK = block(RPBlocks.GEMS_BLOCK, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Item> GEM_STAGE_0 = block(RPBlocks.GEM_STAGE_0, null);
	public static final RegistryObject<Item> GEM_STAGE_1 = block(RPBlocks.GEM_STAGE_1, null);
	public static final RegistryObject<Item> GEM_STAGE_2 = block(RPBlocks.GEM_STAGE_2, null);
	public static final RegistryObject<Item> GEM_STAGE_3 = block(RPBlocks.GEM_STAGE_3, null);
	public static final RegistryObject<Item> GEM_STAGE_4 = block(RPBlocks.GEM_STAGE_4, null);
	public static final RegistryObject<Item> GEM_DUST = ITEMS.register("gem_dust", GemDustItem::new);
	public static final RegistryObject<Item> CRYSTALIZED_WATER_BUCKET = ITEMS.register("crystalized_water_bucket", CrystalizedWaterItem::new);
	public static final RegistryObject<Item> LEATHER_CHESTPLATE = ITEMS.register("leather_chestplate",
			() -> new LeatherArmorItem(RPArmorMaterials.OGRE1, EquipmentSlot.CHEST,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> LEATHER_LEGGINGS = ITEMS.register("leather_leggings",
			() -> new LeatherArmorItem(RPArmorMaterials.OGRE1, EquipmentSlot.LEGS,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> LEATHER_WOMAN_LEGGINGS = ITEMS.register("shoes",
			() -> new JacketWomanArmorItem(RPArmorMaterials.OGRE1, EquipmentSlot.LEGS,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> JACKET_ARMOR = ITEMS.register("woman_jacket",
			() -> new JacketWomanArmorItem(RPArmorMaterials.OGRE1, EquipmentSlot.CHEST,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> SANDALS = ITEMS.register("sandals",
			() -> new JacketWomanArmorItem(RPArmorMaterials.OGRE1, EquipmentSlot.FEET,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> KIMONO = ITEMS.register("woman_kimono",
			() -> new KimonoWomanArmorItem(RPArmorMaterials.OGRE1, EquipmentSlot.CHEST,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> YUKATA = ITEMS.register("woman_skirt",
			() -> new KimonoWomanArmorItem(RPArmorMaterials.OGRE1, EquipmentSlot.LEGS,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));


	public static final RegistryObject<Item> KIJIN_LEATHER_CHESTPLATE = ITEMS.register("kijin_leather_chestplate",
			() -> new LeatherArmorItem(RPArmorMaterials.KIJIN, EquipmentSlot.CHEST,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> KIJIN_LEATHER_LEGGINGS = ITEMS.register("kijin_leather_leggings",
			() -> new LeatherArmorItem(RPArmorMaterials.KIJIN, EquipmentSlot.LEGS,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> KIJIN_LEATHER_WOMAN_LEGGINGS = ITEMS.register("kijin_shoes",
			() -> new JacketWomanArmorItem(RPArmorMaterials.KIJIN, EquipmentSlot.LEGS,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> KIJIN_JACKET_ARMOR = ITEMS.register("kijin_woman_jacket",
			() -> new JacketWomanArmorItem(RPArmorMaterials.KIJIN, EquipmentSlot.CHEST,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> KIJIN_SANDALS = ITEMS.register("kijin_sandals",
			() -> new JacketWomanArmorItem(RPArmorMaterials.KIJIN, EquipmentSlot.FEET,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> KIJIN_KIMONO = ITEMS.register("kijin_woman_kimono",
			() -> new KimonoWomanArmorItem(RPArmorMaterials.KIJIN, EquipmentSlot.CHEST,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));
	public static final RegistryObject<Item> KIJIN_YUKATA = ITEMS.register("kijin_woman_skirt",
			() -> new KimonoWomanArmorItem(RPArmorMaterials.KIJIN, EquipmentSlot.LEGS,
					new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));

	public static final RegistryObject<Item> OGRE_SPAWN_EGG = ITEMS.register("ogre_spawn_egg", () -> new ForgeSpawnEggItem(RPEntities.OGRE, -52378, -1, new Item.Properties().tab(RPTabs.TAB_REINCARNATION_PLUS)));


	private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
		return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
}
