package com.github.skillfi.reincarnation_plus.core.registry.blocks;

import com.github.manasmods.manascore.api.data.gen.annotation.GenerateBlockLoot;
import com.github.manasmods.manascore.api.data.gen.annotation.GenerateBlockModels;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.*;
import com.github.skillfi.reincarnation_plus.core.registry.ReiGems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@GenerateBlockModels
@GenerateBlockLoot
public class ReiBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ReiMod.MODID);
	@GenerateBlockLoot.SelfDrop
	public static final RegistryObject<Block> GEMS_BLOCK = BLOCKS.register("gems_block", GemsBlock::new);
	public static final RegistryObject<Block> GEM_STAGE_0 = BLOCKS.register("gem_stage_0", GemStage0Block::new);
	public static final RegistryObject<Block> GEM_STAGE_1 = BLOCKS.register("gem_stage_1", GemStage1Block::new);
	public static final RegistryObject<Block> GEM_STAGE_2 = BLOCKS.register("gem_stage_2", GemStage2Block::new);
	public static final RegistryObject<Block> GEM_STAGE_3 = BLOCKS.register("gem_stage_3", GemStage3Block::new);
	public static final RegistryObject<Block> GEM_STAGE_4 = BLOCKS.register("gem_stage_4", ()-> new GemStage4Block(ReiGems.STATICAL,
			ReiGems.GEM));
	public static final RegistryObject<Block> GEM_STONE_BLOCK = BLOCKS.register("gem_stone_block", () -> new GemStoneBlock(
			ReiGems.STATICAL,
			ReiGems.GEM, BlockBehaviour.Properties.of(Material.AMETHYST).sound(SoundType.AMETHYST_CLUSTER).strength(1f, 10f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
	public static final RegistryObject<Block> CRYSTALIZED_WATER = BLOCKS.register("crystalized_water", CrystalizedWaterBlock::new);
}
