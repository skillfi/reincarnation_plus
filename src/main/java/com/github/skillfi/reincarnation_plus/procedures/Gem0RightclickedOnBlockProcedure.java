package com.github.skillfi.reincarnation_plus.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusBlocks;

public class Gem0RightclickedOnBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		world.setBlock(new BlockPos(x, y + 1, z), ReincarnationPlusBlocks.GEM_STONE_BLOCK.get().defaultBlockState(), 3);
	}
}
