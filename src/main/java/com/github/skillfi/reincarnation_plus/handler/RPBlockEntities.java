
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.handler;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.block.entity.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RPBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RPMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> GEMS_BLOCK = register("gems_block", RPBlocks.GEMS_BLOCK, GemsBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_0 = register("gem_stage_0", RPBlocks.GEM_STAGE_0, GemStage0BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_1 = register("gem_stage_1", RPBlocks.GEM_STAGE_1, GemStage1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_2 = register("gem_stage_2", RPBlocks.GEM_STAGE_2, GemStage2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_3 = register("gem_stage_3", RPBlocks.GEM_STAGE_3, GemStage3BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_4 = register("gem_stage_4", RPBlocks.GEM_STAGE_4, GemStage4BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STONE_BLOCK = register("gem_stone_block", RPBlocks.GEM_STONE_BLOCK, GemStoneEntity::new);

	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}
