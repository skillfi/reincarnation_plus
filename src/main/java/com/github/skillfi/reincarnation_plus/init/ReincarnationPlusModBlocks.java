
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.init;

import com.github.skillfi.reincarnation_plus.block.GemStoneBlockBlock;
import com.github.skillfi.reincarnation_plus.block.GemStage4Block;
import com.github.skillfi.reincarnation_plus.block.GemStage3Block;
import com.github.skillfi.reincarnation_plus.block.GemStage2Block;
import com.github.skillfi.reincarnation_plus.block.GemStage1Block;
import com.github.skillfi.reincarnation_plus.block.GemStage0Block;
import com.github.skillfi.reincarnation_plus.block.GemBlockBlock;
import com.github.skillfi.reincarnation_plus.block.CrystalizedWaterBlock;
import com.github.skillfi.reincarnation_plus.ReincarnationPlusMod;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReincarnationPlusModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, ReincarnationPlusMod.MODID);
	public static final RegistryObject<Block> GEM_BLOCK = REGISTRY.register("gem_block", () -> new GemBlockBlock());
	public static final RegistryObject<Block> GEM_STAGE_0 = REGISTRY.register("gem_stage_0", () -> new GemStage0Block());
	public static final RegistryObject<Block> GEM_STAGE_1 = REGISTRY.register("gem_stage_1", () -> new GemStage1Block());
	public static final RegistryObject<Block> GEM_STAGE_2 = REGISTRY.register("gem_stage_2", () -> new GemStage2Block());
	public static final RegistryObject<Block> GEM_STAGE_3 = REGISTRY.register("gem_stage_3", () -> new GemStage3Block());
	public static final RegistryObject<Block> GEM_STAGE_4 = REGISTRY.register("gem_stage_4", () -> new GemStage4Block());
	public static final RegistryObject<Block> GEM_STONE_BLOCK = REGISTRY.register("gem_stone_block", () -> new GemStoneBlockBlock());
	public static final RegistryObject<Block> CRYSTALIZED_WATER = REGISTRY.register("crystalized_water", () -> new CrystalizedWaterBlock());
}
