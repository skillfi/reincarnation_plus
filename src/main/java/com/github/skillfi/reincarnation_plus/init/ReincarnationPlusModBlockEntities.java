
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.init;

import com.github.skillfi.reincarnation_plus.block.entity.GemStage4BlockEntity;
import com.github.skillfi.reincarnation_plus.block.entity.GemStage3BlockEntity;
import com.github.skillfi.reincarnation_plus.block.entity.GemStage2BlockEntity;
import com.github.skillfi.reincarnation_plus.block.entity.GemStage1BlockEntity;
import com.github.skillfi.reincarnation_plus.block.entity.GemStage0BlockEntity;
import com.github.skillfi.reincarnation_plus.block.entity.GemBlockBlockEntity;
import com.github.skillfi.reincarnation_plus.ReincarnationPlusMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReincarnationPlusModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ReincarnationPlusMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> GEM_BLOCK = register("gem_block", ReincarnationPlusModBlocks.GEM_BLOCK, GemBlockBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_0 = register("gem_stage_0", ReincarnationPlusModBlocks.GEM_STAGE_0, GemStage0BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_1 = register("gem_stage_1", ReincarnationPlusModBlocks.GEM_STAGE_1, GemStage1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_2 = register("gem_stage_2", ReincarnationPlusModBlocks.GEM_STAGE_2, GemStage2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_3 = register("gem_stage_3", ReincarnationPlusModBlocks.GEM_STAGE_3, GemStage3BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_4 = register("gem_stage_4", ReincarnationPlusModBlocks.GEM_STAGE_4, GemStage4BlockEntity::new);

	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}
