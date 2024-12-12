package com.github.skillfi.reincarnation_plus.core.registry.blocks;

import com.github.manasmods.manascore.api.data.gen.annotation.GenerateBlockLoot;
import com.github.manasmods.manascore.api.data.gen.annotation.GenerateBlockModels;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@GenerateBlockModels
@GenerateBlockLoot
public class ReiBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ReiMod.MODID);
	public static final RegistryObject<Block> CRYSTALIZED_WATER = BLOCKS.register("crystalized_water", CrystalizedWaterBlock::new);
}
