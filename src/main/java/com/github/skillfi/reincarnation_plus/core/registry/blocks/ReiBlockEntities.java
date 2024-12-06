
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.core.registry.blocks;

import com.github.manasmods.manascore.api.data.gen.annotation.GenerateBlockLoot;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.MagicAmplifierBlock;
import com.github.skillfi.reincarnation_plus.core.block.MagicInfuserBlock;
import com.github.skillfi.reincarnation_plus.core.block.MagicMechBlock;
import com.github.skillfi.reincarnation_plus.core.block.entity.*;
import com.mojang.datafixers.types.Type;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReiBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ReiMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> GEMS_BLOCK = register("gems_block", ReiBlocks.GEMS_BLOCK, GemsBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_0 = register("gem_stage_0", ReiBlocks.GEM_STAGE_0, GemStage0BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_1 = register("gem_stage_1", ReiBlocks.GEM_STAGE_1, GemStage1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_2 = register("gem_stage_2", ReiBlocks.GEM_STAGE_2, GemStage2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_3 = register("gem_stage_3", ReiBlocks.GEM_STAGE_3, GemStage3BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_4 = register("gem_stage_4", ReiBlocks.GEM_STAGE_4, GemStage4BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STONE_BLOCK_ENTITY = register("gem_stone_block", ReiBlocks.GEM_STONE_BLOCK, GemStoneEntity::new);
	public static final RegistryObject<BlockEntityType<?>> CORE_BLOCK_ENTITY = register("magicule_core", ReiBlocks.EP_CORE, EPCoreTileEntity::new);
	public static final RegistryObject<BlockEntityType<MagicInfuserBlockEntity>> MAGICAL_INFUSER_ENTITY = REGISTRY.register("magic_infuser", ()->BlockEntityType.Builder.of(MagicInfuserBlockEntity::new, new Block[]{(Block) Blocks.MAGICAL_INFUSER.get()}).build((Type)null));
	public static final RegistryObject<BlockEntityType<MagicAmplifierBlockEntity>> MAGIC_AMPLIFIER = REGISTRY.register("magic_amplifier", () -> BlockEntityType.Builder.of(MagicAmplifierBlockEntity::new, new Block[]{(Block) Blocks.MAGIC_AMPLIFIER.get()}).build((Type)null));
	public static final RegistryObject<BlockEntityType<MagicMehBlockEntity>> MAGIC_MECH = REGISTRY.register("magic_mech", () -> BlockEntityType.Builder.of(MagicMehBlockEntity::new, new Block[]{(Block) Blocks.MAGIC_MECH.get()}).build((Type)null));

	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}

	public static void init(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
		Blocks.registry.register(modEventBus);
	}

	@GenerateBlockLoot
	public static class Blocks {
		@GenerateBlockLoot.WithLootTables
		private static final DeferredRegister<Block> registry;
		@GenerateBlockLoot.SelfDrop
		public static final RegistryObject<MagicAmplifierBlock> MAGIC_AMPLIFIER;
		@GenerateBlockLoot.SelfDrop
		public static final RegistryObject<MagicInfuserBlock> MAGICAL_INFUSER;
		@GenerateBlockLoot.SelfDrop
		public static final RegistryObject<MagicMechBlock> MAGIC_MECH;

		public Blocks() {
		}

		static {
			registry = DeferredRegister.create(ForgeRegistries.BLOCKS, ReiMod.MODID);
			MAGIC_AMPLIFIER = registry.register("magic_amplifier", MagicAmplifierBlock::new);
			MAGICAL_INFUSER = registry.register("magic_infuser", MagicInfuserBlock::new);
			MAGIC_MECH = registry.register("magic_mech", MagicMechBlock::new);
		}
	}
}
