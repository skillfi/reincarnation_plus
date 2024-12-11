
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.core.registry.blocks;

import com.github.manasmods.manascore.api.data.gen.annotation.GenerateBlockLoot;
import com.github.manasmods.manascore.api.data.gen.annotation.GenerateBlockModels;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.*;
import com.github.skillfi.reincarnation_plus.core.block.entity.*;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.CooperOreBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.GoldOreBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.IronOreBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.ores.CooperMagicOreBlock;
import com.github.skillfi.reincarnation_plus.core.block.ores.GoldMagicOreBlock;
import com.github.skillfi.reincarnation_plus.core.block.ores.IronMagicOreBlock;
import com.mojang.datafixers.types.Type;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReiBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ReiMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> GEMS_BLOCK = register("gems_block", com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEMS_BLOCK, GemsBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_0 = register("gem_stage_0", com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STAGE_0, GemStage0BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_1 = register("gem_stage_1", com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STAGE_1, GemStage1BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_2 = register("gem_stage_2", com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STAGE_2, GemStage2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_3 = register("gem_stage_3", com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STAGE_3, GemStage3BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STAGE_4 = register("gem_stage_4", com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STAGE_4, GemStage4BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> GEM_STONE_BLOCK_ENTITY = register("gem_stone_block", com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STONE_BLOCK, GemStoneEntity::new);
	public static final RegistryObject<BlockEntityType<GrassBlockEntity>> GRASS_ENTITY = REGISTRY.register("grass_entity", ()->BlockEntityType.Builder.of(GrassBlockEntity::new, Blocks.GRASS).build(null));
	public static final RegistryObject<BlockEntityType<IronOreBlockEntity>> IRON_MAGIC_ORE = REGISTRY.register("ore_iron", ()->BlockEntityType.Builder.of(IronOreBlockEntity::new, ReiBlocks.IRON_MAGIC_ORE.get()).build(null));
	public static final RegistryObject<BlockEntityType<CooperOreBlockEntity>> COOPER_MAGIC_ORE = REGISTRY.register("ore_copper", ()->BlockEntityType.Builder.of(CooperOreBlockEntity::new, ReiBlocks.COOPER_MAGIC_ORE.get()).build(null));
	public static final RegistryObject<BlockEntityType<GoldOreBlockEntity>> GOLD_MAGIC_ORE = REGISTRY.register("ore_gold", ()->BlockEntityType.Builder.of(GoldOreBlockEntity::new, ReiBlocks.GOLD_MAGIC_ORE.get()).build(null));
	public static final RegistryObject<BlockEntityType<MagiculaInfuserBlockEntity>> MAGICAL_INFUSER_ENTITY = REGISTRY.register("magicula_infuser", ()->BlockEntityType.Builder.of(MagiculaInfuserBlockEntity::new, ReiBlocks.MAGICAL_INFUSER.get()).build((Type)null));
	public static final RegistryObject<BlockEntityType<AutomaticMagiculaInfuserBlockEntity>> AUTOMATIC_MAGICAL_INFUSER_ENTITY = REGISTRY.register("automatic_magicula_infuser", ()->BlockEntityType.Builder.of(AutomaticMagiculaInfuserBlockEntity::new, ReiBlocks.AUTOMATIC_MAGICAL_INFUSER.get()).build((Type)null));
	public static final RegistryObject<BlockEntityType<MagicAmplifierBlockEntity>> MAGIC_AMPLIFIER = REGISTRY.register("magic_amplifier", () -> BlockEntityType.Builder.of(MagicAmplifierBlockEntity::new, ReiBlocks.MAGIC_AMPLIFIER.get()).build((Type)null));
	public static final RegistryObject<BlockEntityType<InfusionBellowsBlockEntity>> INFUSION_BELLOWS = REGISTRY.register("infusion_bellows", () -> BlockEntityType.Builder.of(InfusionBellowsBlockEntity::new, ReiBlocks.INFUSION_BELLOWS_BLOCK_REGISTRY_OBJECT.get()).build((Type)null));


	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<net.minecraft.world.level.block.Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}


	public static void init(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
		ReiBlocks.registry.register(modEventBus);
	}

	@GenerateBlockLoot
	public static class ReiBlocks {
		@GenerateBlockLoot.WithLootTables
		private static final DeferredRegister<net.minecraft.world.level.block.Block> registry;
		@GenerateBlockLoot.SelfDrop
		public static final RegistryObject<MagicAmplifierBlock> MAGIC_AMPLIFIER;
		@GenerateBlockLoot.SelfDrop
		public static final RegistryObject<MagiculaInfuserBlock> MAGICAL_INFUSER;
		@GenerateBlockLoot.SelfDrop
		public static final RegistryObject<AutomaticMagiculaInfuserBlock> AUTOMATIC_MAGICAL_INFUSER;
		@GenerateBlockLoot.SelfDrop
		public static final RegistryObject<InfusionBellowsBlock> INFUSION_BELLOWS_BLOCK_REGISTRY_OBJECT;
		@GenerateBlockLoot.OreDrop("minecraft:raw_iron")
		@GenerateBlockModels.CubeAllModel
		public static final RegistryObject<IronMagicOreBlock> IRON_MAGIC_ORE;
		@GenerateBlockLoot.OreDrop("minecraft:raw_copper")
		@GenerateBlockModels.CubeAllModel
		public static final RegistryObject<CooperMagicOreBlock> COOPER_MAGIC_ORE;
		@GenerateBlockLoot.OreDrop("minecraft:raw_gold")
		@GenerateBlockModels.CubeAllModel
		public static final RegistryObject<GoldMagicOreBlock> GOLD_MAGIC_ORE;

		public ReiBlocks() {
		}

		static {
			registry = DeferredRegister.create(ForgeRegistries.BLOCKS, ReiMod.MODID);
			MAGIC_AMPLIFIER = registry.register("magic_amplifier", MagicAmplifierBlock::new);
			IRON_MAGIC_ORE = registry.register("ore_iron", IronMagicOreBlock::new);
			COOPER_MAGIC_ORE = registry.register("ore_copper", CooperMagicOreBlock::new);
			GOLD_MAGIC_ORE = registry.register("ore_gold", GoldMagicOreBlock::new);
			MAGICAL_INFUSER = registry.register("magicula_infuser", MagiculaInfuserBlock::new);
			AUTOMATIC_MAGICAL_INFUSER = registry.register("automatic_magicula_infuser", AutomaticMagiculaInfuserBlock::new);
			INFUSION_BELLOWS_BLOCK_REGISTRY_OBJECT = registry.register("infusion_bellows", InfusionBellowsBlock::new);
		}
	}
}
