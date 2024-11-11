
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.init;

import com.github.skillfi.reincarnation_plus.item.OgreArmorItem;
import com.github.skillfi.reincarnation_plus.item.GemStoneItem;
import com.github.skillfi.reincarnation_plus.item.GemDustItem;
import com.github.skillfi.reincarnation_plus.item.DiamondSwordItem;
import com.github.skillfi.reincarnation_plus.item.CrystalizedWaterItem;
import com.github.skillfi.reincarnation_plus.ReincarnationPlusMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReincarnationPlusModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ReincarnationPlusMod.MODID);
	public static final RegistryObject<Item> DIAMOND_SWORD = REGISTRY.register("diamond_sword", () -> new DiamondSwordItem());
	public static final RegistryObject<Item> GEM_STONE = REGISTRY.register("gem_stone", () -> new GemStoneItem());
	public static final RegistryObject<Item> GEM_BLOCK = block(ReincarnationPlusModBlocks.GEM_BLOCK, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Item> GEM_STAGE_0 = block(ReincarnationPlusModBlocks.GEM_STAGE_0, null);
	public static final RegistryObject<Item> GEM_STAGE_1 = block(ReincarnationPlusModBlocks.GEM_STAGE_1, null);
	public static final RegistryObject<Item> GEM_STAGE_2 = block(ReincarnationPlusModBlocks.GEM_STAGE_2, null);
	public static final RegistryObject<Item> GEM_STAGE_3 = block(ReincarnationPlusModBlocks.GEM_STAGE_3, null);
	public static final RegistryObject<Item> GEM_STAGE_4 = block(ReincarnationPlusModBlocks.GEM_STAGE_4, null);
	public static final RegistryObject<Item> GEM_STONE_BLOCK = block(ReincarnationPlusModBlocks.GEM_STONE_BLOCK, ReincarnationPlusModTabs.TAB_REINCARNATION_PLUS);
	public static final RegistryObject<Item> GEM_DUST = REGISTRY.register("gem_dust", () -> new GemDustItem());
	public static final RegistryObject<Item> CRYSTALIZED_WATER_BUCKET = REGISTRY.register("crystalized_water_bucket", () -> new CrystalizedWaterItem());
	public static final RegistryObject<Item> OGRE_ARMOR_CHESTPLATE = REGISTRY.register("ogre_armor_chestplate", () -> new OgreArmorItem.Chestplate());
	public static final RegistryObject<Item> OGRE_ARMOR_LEGGINGS = REGISTRY.register("ogre_armor_leggings", () -> new OgreArmorItem.Leggings());
	public static final RegistryObject<Item> OGRE_MAN_SPAWN_EGG = REGISTRY.register("ogre_man_spawn_egg", () -> new ForgeSpawnEggItem(ReincarnationPlusModEntities.OGRE_MAN, -52378, -1, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
}
