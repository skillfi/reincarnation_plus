
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.init;

import com.github.skillfi.reincarnation_plus.ReincarnationPlus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.BlockItem;

import com.github.skillfi.reincarnation_plus.item.GemStoneItem;
import com.github.skillfi.reincarnation_plus.item.GemDustItem;
import com.github.skillfi.reincarnation_plus.item.DiamondSwordItem;
import com.github.skillfi.reincarnation_plus.item.CrystalizedWaterItem;

public class ReincarnationPlusItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ReincarnationPlus.MODID);
	public static final RegistryObject<Item> DIAMOND_SWORD = REGISTRY.register("diamond_sword", () -> new DiamondSwordItem());
	public static final RegistryObject<Item> GEM_STONE = REGISTRY.register("gem_stone", () -> new GemStoneItem());
	public static final RegistryObject<Item> GEM_BLOCK = block(ReincarnationPlusBlocks.GEM_BLOCK, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Item> GEM_STAGE_0 = block(ReincarnationPlusBlocks.GEM_STAGE_0, null);
	public static final RegistryObject<Item> GEM_STAGE_1 = block(ReincarnationPlusBlocks.GEM_STAGE_1, null);
	public static final RegistryObject<Item> GEM_STAGE_2 = block(ReincarnationPlusBlocks.GEM_STAGE_2, null);
	public static final RegistryObject<Item> GEM_STAGE_3 = block(ReincarnationPlusBlocks.GEM_STAGE_3, null);
	public static final RegistryObject<Item> GEM_STAGE_4 = block(ReincarnationPlusBlocks.GEM_STAGE_4, null);
	public static final RegistryObject<Item> GEM_STONE_BLOCK = block(ReincarnationPlusBlocks.GEM_STONE_BLOCK, ReincarnationPlusTabs.TAB_REINCARNATION_PLUS);
	public static final RegistryObject<Item> GEM_DUST = REGISTRY.register("gem_dust", () -> new GemDustItem());
	public static final RegistryObject<Item> CRYSTALIZED_WATER_BUCKET = REGISTRY.register("crystalized_water_bucket", () -> new CrystalizedWaterItem());

	private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
}
