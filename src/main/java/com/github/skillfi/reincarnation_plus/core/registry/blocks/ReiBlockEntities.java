
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.core.registry.blocks;

import com.github.manasmods.manascore.api.data.gen.annotation.GenerateBlockLoot;
import com.github.manasmods.manascore.api.data.gen.annotation.GenerateItemModels;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.*;
import com.github.skillfi.reincarnation_plus.core.block.entity.*;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.gold.*;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.iron.*;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.copper.*;
import com.github.skillfi.reincarnation_plus.core.block.ores.copper.*;
import com.github.skillfi.reincarnation_plus.core.block.ores.gold.*;
import com.github.skillfi.reincarnation_plus.core.block.ores.iron.*;
import com.mojang.datafixers.types.Type;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReiBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY;

	public static final RegistryObject<BlockEntityType<?>> IRON_MAGIC_ORE;
	public static final RegistryObject<BlockEntityType<?>> COOPER_MAGIC_ORE;
	public static final RegistryObject<BlockEntityType<?>> GOLD_MAGIC_ORE;

	public static final RegistryObject<BlockEntityType<?>> DEEPSLATE_IRON_MAGIC_ORE;
	public static final RegistryObject<BlockEntityType<?>> DEEPSLATE_COOPER_MAGIC_ORE;
	public static final RegistryObject<BlockEntityType<?>> DEEPSLATE_GOLD_MAGIC_ORE;

	//	Iron Ore
	public static final RegistryObject<BlockEntityType<?>> IRON_MAGIC_ORE_AGE1;
	public static final RegistryObject<BlockEntityType<?>> IRON_MAGIC_ORE_AGE2 ;
	public static final RegistryObject<BlockEntityType<?>> IRON_MAGIC_ORE_AGE3;

	//	Copper Ore
	public static final RegistryObject<BlockEntityType<?>> COOPER_MAGIC_ORE_AGE1;
	public static final RegistryObject<BlockEntityType<?>> COOPER_MAGIC_ORE_AGE2;
	public static final RegistryObject<BlockEntityType<?>> COOPER_MAGIC_ORE_AGE3;

	// Gold Ore
	public static final RegistryObject<BlockEntityType<?>> GOLD_MAGIC_ORE_AGE1;
	public static final RegistryObject<BlockEntityType<?>> GOLD_MAGIC_ORE_AGE2;
	public static final RegistryObject<BlockEntityType<?>> GOLD_MAGIC_ORE_AGE3;
	
	public static final RegistryObject<BlockEntityType<MagiculaInfuserBlockEntity>> MAGICAL_INFUSER_ENTITY;
	public static final RegistryObject<BlockEntityType<AutomaticMagiculaInfuserBlockEntity>> AUTOMATIC_MAGICAL_INFUSER_ENTITY;
	public static final RegistryObject<BlockEntityType<MagicAmplifierBlockEntity>> MAGIC_AMPLIFIER;
	public static final RegistryObject<BlockEntityType<InfusionBellowsBlockEntity>> INFUSION_BELLOWS;

	// Ore block entities
	private static final String[] METALS = {"iron", "copper", "gold"};
	private static final int[] AGES = {1, 2, 3};

	static {
		REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ReiMod.MODID);

		IRON_MAGIC_ORE = REGISTRY.register("ore_iron" + "_entity", ()->BlockEntityType.Builder.of(IronOreBlockEntity::new, ReiBlocks.IRON_MAGIC_ORE.get()).build(null));
		COOPER_MAGIC_ORE = REGISTRY.register("ore_copper" + "_entity", ()->BlockEntityType.Builder.of(CooperOreBlockEntity::new, ReiBlocks.COOPER_MAGIC_ORE.get()).build(null));
		GOLD_MAGIC_ORE = REGISTRY.register("ore_gold" + "_entity", ()->BlockEntityType.Builder.of(GoldOreBlockEntity::new, ReiBlocks.GOLD_MAGIC_ORE.get()).build(null));

		DEEPSLATE_IRON_MAGIC_ORE = REGISTRY.register("deepslate_ore_iron" + "_entity", ()->BlockEntityType.Builder.of(IronOreBlockEntity::new, ReiBlocks.DEEPSLATE_IRON_MAGIC_ORE.get()).build(null));
		DEEPSLATE_COOPER_MAGIC_ORE = REGISTRY.register("deepslate_ore_copper" + "_entity", ()->BlockEntityType.Builder.of(CooperOreBlockEntity::new, ReiBlocks.DEEPSLATE_COOPER_MAGIC_ORE.get()).build(null));
		DEEPSLATE_GOLD_MAGIC_ORE = REGISTRY.register("deepslate_ore_gold" + "_entity", ()->BlockEntityType.Builder.of(GoldOreBlockEntity::new, ReiBlocks.DEEPSLATE_GOLD_MAGIC_ORE.get()).build(null));

		IRON_MAGIC_ORE_AGE1 = REGISTRY.register("ore_" + METALS[0] + "_age" + AGES[0] + "_entity", () -> BlockEntityType.Builder.of(
				ReiBlocks.createAge(METALS[0], AGES[0]),
				ReiBlocks.getOreBlockWithAge(METALS[0], AGES[0]).get()
		).build(null));
		IRON_MAGIC_ORE_AGE2 = REGISTRY.register("ore_" + METALS[0] + "_age" + AGES[1] + "_entity", () -> BlockEntityType.Builder.of(
				ReiBlocks.createAge(METALS[0], AGES[1]),
				ReiBlocks.getOreBlockWithAge(METALS[0], AGES[1]).get()
		).build(null));
		IRON_MAGIC_ORE_AGE3 = REGISTRY.register("ore_" + METALS[0] + "_age" + AGES[2] + "_entity", () -> BlockEntityType.Builder.of(
				ReiBlocks.createAge(METALS[0], AGES[2]),
				ReiBlocks.getOreBlockWithAge(METALS[0], AGES[2]).get()
		).build(null));

		COOPER_MAGIC_ORE_AGE1 = REGISTRY.register("ore_" + METALS[1] + "_age" + AGES[0] + "_entity", () -> BlockEntityType.Builder.of(
				ReiBlocks.createAge(METALS[1], AGES[0]),
				ReiBlocks.getOreBlockWithAge(METALS[1], AGES[0]).get()
		).build(null));
		COOPER_MAGIC_ORE_AGE2 = REGISTRY.register("ore_" + METALS[1] + "_age" + AGES[1] + "_entity", () -> BlockEntityType.Builder.of(
				ReiBlocks.createAge(METALS[1], AGES[1]),
				ReiBlocks.getOreBlockWithAge(METALS[1], AGES[1]).get()
		).build(null));
		COOPER_MAGIC_ORE_AGE3 = REGISTRY.register("ore_" + METALS[1] + "_age" + AGES[2] + "_entity", () -> BlockEntityType.Builder.of(
				ReiBlocks.createAge(METALS[2], AGES[2]),
				ReiBlocks.getOreBlockWithAge(METALS[1], AGES[2]).get()
		).build(null));

		GOLD_MAGIC_ORE_AGE1 = REGISTRY.register("ore_" + METALS[2] + "_age" + AGES[0] + "_entity", () -> BlockEntityType.Builder.of(
				ReiBlocks.createAge(METALS[2], AGES[0]),
				ReiBlocks.getOreBlockWithAge(METALS[2], AGES[0]).get()
		).build(null));
		GOLD_MAGIC_ORE_AGE2 = REGISTRY.register("ore_" + METALS[2] + "_age" + AGES[1] + "_entity", () -> BlockEntityType.Builder.of(
				ReiBlocks.createAge(METALS[2], AGES[1]),
				ReiBlocks.getOreBlockWithAge(METALS[2], AGES[1]).get()
		).build(null));
		GOLD_MAGIC_ORE_AGE3 = REGISTRY.register("ore_" + METALS[2] + "_age" + AGES[2] + "_entity", () -> BlockEntityType.Builder.of(
				ReiBlocks.createAge(METALS[2], AGES[2]),
				ReiBlocks.getOreBlockWithAge(METALS[2], AGES[2]).get()
		).build(null));


		MAGICAL_INFUSER_ENTITY  = REGISTRY.register("magicula_infuser", ()->BlockEntityType.Builder.of(MagiculaInfuserBlockEntity::new, ReiBlocks.MAGICAL_INFUSER.get()).build((Type)null));
		AUTOMATIC_MAGICAL_INFUSER_ENTITY = REGISTRY.register("automatic_magicula_infuser", ()->BlockEntityType.Builder.of(AutomaticMagiculaInfuserBlockEntity::new, ReiBlocks.AUTOMATIC_MAGICAL_INFUSER.get()).build((Type)null));
		MAGIC_AMPLIFIER = REGISTRY.register("magic_amplifier", () -> BlockEntityType.Builder.of(MagicAmplifierBlockEntity::new, ReiBlocks.MAGIC_AMPLIFIER.get()).build((Type)null));
		INFUSION_BELLOWS = REGISTRY.register("infusion_bellows", () -> BlockEntityType.Builder.of(InfusionBellowsBlockEntity::new, ReiBlocks.INFUSION_BELLOWS_BLOCK_REGISTRY_OBJECT.get()).build((Type)null));
	}

	public static void init(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
		ReiBlocks.registry.register(modEventBus);
	}

	@GenerateBlockLoot
	public static class ReiBlocks {
		@GenerateBlockLoot.WithLootTables
		private static final DeferredRegister<Block> registry;
		@GenerateBlockLoot.SelfDrop
		public static final RegistryObject<MagicAmplifierBlock> MAGIC_AMPLIFIER;
		@GenerateBlockLoot.SelfDrop
		public static final RegistryObject<MagiculaInfuserBlock> MAGICAL_INFUSER;
		@GenerateBlockLoot.SelfDrop
		public static final RegistryObject<AutomaticMagiculaInfuserBlock> AUTOMATIC_MAGICAL_INFUSER;
		@GenerateBlockLoot.SelfDrop
		public static final RegistryObject<InfusionBellowsBlock> INFUSION_BELLOWS_BLOCK_REGISTRY_OBJECT;
		@GenerateBlockLoot.OreDrop("minecraft:raw_iron")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> IRON_MAGIC_ORE;
		@GenerateBlockLoot.OreDrop("minecraft:raw_copper")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> COOPER_MAGIC_ORE;
		@GenerateBlockLoot.OreDrop("minecraft:raw_gold")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> GOLD_MAGIC_ORE;
		@GenerateBlockLoot.OreDrop("minecraft:raw_iron")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> IRON_MAGIC_ORE_AGE1;
		@GenerateBlockLoot.OreDrop("minecraft:raw_copper")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> COOPER_MAGIC_ORE_AGE1;
		@GenerateBlockLoot.OreDrop("minecraft:raw_gold")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> GOLD_MAGIC_ORE_AGE1;
		@GenerateBlockLoot.OreDrop("minecraft:raw_iron")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> IRON_MAGIC_ORE_AGE2;
		@GenerateBlockLoot.OreDrop("minecraft:raw_copper")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> COOPER_MAGIC_ORE_AGE2;
		@GenerateBlockLoot.OreDrop("minecraft:raw_gold")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> GOLD_MAGIC_ORE_AGE2;
		@GenerateBlockLoot.OreDrop("minecraft:raw_iron")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> IRON_MAGIC_ORE_AGE3;
		@GenerateBlockLoot.OreDrop("minecraft:raw_copper")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> COOPER_MAGIC_ORE_AGE3;
		@GenerateBlockLoot.OreDrop("minecraft:raw_gold")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> GOLD_MAGIC_ORE_AGE3;

		@GenerateBlockLoot.OreDrop("minecraft:raw_iron")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> DEEPSLATE_IRON_MAGIC_ORE;
		@GenerateBlockLoot.OreDrop("minecraft:raw_copper")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> DEEPSLATE_COOPER_MAGIC_ORE;
		@GenerateBlockLoot.OreDrop("minecraft:raw_gold")
		@GenerateItemModels.SingleTextureModel
		public static final RegistryObject<Block> DEEPSLATE_GOLD_MAGIC_ORE;

		public ReiBlocks() {
		}

		public static RegistryObject<Block> getOreBlockWithAge(String metal, int age) {
			switch (metal.toLowerCase()) {
				case "iron":
					return getIronOreBlockByAge(age);
				case "copper":
					return getCopperOreBlockByAge(age);
				case "gold":
					return getGoldOreBlockByAge(age);
				default:
					throw new IllegalArgumentException("Unknown metal: " + metal);
			}
		}

		private static RegistryObject<Block> getIronOreBlockByAge(int age) {
			switch (age) {
				case 1:
					return IRON_MAGIC_ORE_AGE1;
				case 2:
					return IRON_MAGIC_ORE_AGE2;
				case 3:
					return IRON_MAGIC_ORE_AGE3;
				default:
					throw new IllegalArgumentException("Unknown age: " + age);
			}
		}

		private static RegistryObject<Block> getCopperOreBlockByAge(int age) {
			switch (age) {
				case 1:
					return COOPER_MAGIC_ORE_AGE1;
				case 2:
					return COOPER_MAGIC_ORE_AGE2;
				case 3:
					return COOPER_MAGIC_ORE_AGE3;
				default:
					throw new IllegalArgumentException("Unknown age: " + age);
			}
		}

		private static RegistryObject<Block> getGoldOreBlockByAge(int age) {
			switch (age) {
				case 1:
					return GOLD_MAGIC_ORE_AGE1;
				case 2:
					return GOLD_MAGIC_ORE_AGE2;
				case 3:
					return GOLD_MAGIC_ORE_AGE3;
				default:
					throw new IllegalArgumentException("Unknown age: " + age);
			}
		}

		static BlockEntityType.BlockEntitySupplier<?> createAge(String metal, int age) {
			switch (metal) {
				case "iron": return getIronSupplier(age);
				case "copper": return getCopperSupplier(age);
				case "gold": return getGoldSupplier(age);
				default: throw new IllegalArgumentException("Unknown metal: " + metal);
			}
		}

		private static BlockEntityType.BlockEntitySupplier<?> getIronSupplier(int age) {
			switch (age) {
				case 1: return IronOre1BlockEntity::new;
				case 2: return IronOre2BlockEntity::new;
				case 3: return IronOre3BlockEntity::new;
				default: throw new IllegalArgumentException("Unknown age: " + age);
			}
		}

		private static BlockEntityType.BlockEntitySupplier<?> getCopperSupplier(int age) {
			switch (age) {
				case 1: return CooperOre1BlockEntity::new;
				case 2: return CooperOre2BlockEntity::new;
				case 3: return CooperOre3BlockEntity::new;
				default: throw new IllegalArgumentException("Unknown age: " + age);
			}
		}

		private static BlockEntityType.BlockEntitySupplier<?> getGoldSupplier(int age) {
			switch (age) {
				case 1: return GoldOre1BlockEntity::new;
				case 2: return GoldOre2BlockEntity::new;
				case 3: return GoldOre3BlockEntity::new;
				default: throw new IllegalArgumentException("Unknown age: " + age);
			}
		}

		static {
			registry = DeferredRegister.create(ForgeRegistries.BLOCKS, ReiMod.MODID);
			MAGIC_AMPLIFIER = registry.register("magic_amplifier", MagicAmplifierBlock::new);
			IRON_MAGIC_ORE = registry.register("ore_iron", () -> new IronMagicOreBlock(IronOreStage1Block.stoneProperties()));
			COOPER_MAGIC_ORE = registry.register("ore_copper", () -> new CooperMagicOreBlock(IronOreStage1Block.stoneProperties()));
			GOLD_MAGIC_ORE = registry.register("ore_gold", () -> new GoldMagicOreBlock(IronOreStage1Block.stoneProperties()));
			DEEPSLATE_IRON_MAGIC_ORE = registry.register("deepslate_ore_iron", () -> new IronMagicOreBlock(IronOreStage1Block.deepslateProperties()));
			DEEPSLATE_COOPER_MAGIC_ORE = registry.register("deepslate_ore_copper", () -> new CooperMagicOreBlock(IronOreStage1Block.deepslateProperties()));
			DEEPSLATE_GOLD_MAGIC_ORE = registry.register("deepslate_ore_gold", () -> new GoldMagicOreBlock(IronOreStage1Block.deepslateProperties()));
			MAGICAL_INFUSER = registry.register("magicula_infuser", MagiculaInfuserBlock::new);
			AUTOMATIC_MAGICAL_INFUSER = registry.register("automatic_magicula_infuser", AutomaticMagiculaInfuserBlock::new);
			INFUSION_BELLOWS_BLOCK_REGISTRY_OBJECT = registry.register("infusion_bellows", InfusionBellowsBlock::new);
		
//			Iron Ore
			IRON_MAGIC_ORE_AGE1 = registry.register("ore_iron_age1", () -> new IronOreStage1Block(IronOreStage1Block.stoneProperties()));
			IRON_MAGIC_ORE_AGE2 = registry.register("ore_iron_age2", () -> new IronOreStage2Block(IronOreStage1Block.stoneProperties()));
			IRON_MAGIC_ORE_AGE3 = registry.register("ore_iron_age3", () -> new IronOreStage3Block(IronOreStage1Block.stoneProperties()));
		
//			Copper Ore
			COOPER_MAGIC_ORE_AGE1 = registry.register("ore_copper_age1", ()->new CooperMagicOreStage1Block(IronOreStage1Block.stoneProperties()));
			COOPER_MAGIC_ORE_AGE2 = registry.register("ore_copper_age2", ()->new CooperMagicOreStage2Block(IronOreStage1Block.stoneProperties()));
			COOPER_MAGIC_ORE_AGE3 = registry.register("ore_copper_age3", ()->new CooperMagicOreStage3Block(IronOreStage1Block.stoneProperties()));
		
//			Gold Ore
			GOLD_MAGIC_ORE_AGE1 = registry.register("ore_gold_age1", () -> new GoldMagicOreStage1Block(IronOreStage1Block.stoneProperties()));
			GOLD_MAGIC_ORE_AGE2 = registry.register("ore_gold_age2", () -> new GoldMagicOreStage2Block(IronOreStage1Block.stoneProperties()));
			GOLD_MAGIC_ORE_AGE3 = registry.register("ore_gold_age3", () -> new GoldMagicOreStage3Block(IronOreStage1Block.stoneProperties()));
		}
	}
}
