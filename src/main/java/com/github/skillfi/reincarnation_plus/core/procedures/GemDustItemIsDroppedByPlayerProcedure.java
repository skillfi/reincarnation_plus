package com.github.skillfi.reincarnation_plus.core.procedures;

import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class GemDustItemIsDroppedByPlayerProcedure {

	public static List<BlockPos> getPositionsInRadius(BlockPos center, int radius) {
		List<BlockPos> positions = new ArrayList<>();

		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					BlockPos pos = center.offset(x, y, z);
					positions.add(pos);
				}
			}
		}
		return positions;
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack stack) {
		if (entity == null)
			return;
		BlockPos blockPos = new BlockPos(x, y, z);
		List<BlockPos> positions = getPositionsInRadius(blockPos, 3);
		for (BlockPos pos : positions) {
			if ((world.getBlockState(pos)).getBlock() instanceof LiquidBlock water){
				world.scheduleTick(pos, world.getBlockState(pos).getBlock(), 66);
				{
					BlockState _bs = ReiBlocks.CRYSTALIZED_WATER.get().defaultBlockState();
					BlockState _bso = world.getBlockState(pos);
					for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
						Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
						if (_property != null && _bs.getValue(_property) != null)
							try {
								_bs = _bs.setValue(_property, (Comparable) entry.getValue());
							}catch (Exception e) {
							}
					}
					world.setBlock(pos, _bs, 3);
				}
				if (entity instanceof ServerPlayer _player) {
					Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("reincarnation_plus:black_smith"));
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						Iterator _iterator = _ap.getRemainingCriteria().iterator();
						while (_iterator.hasNext())
							_player.getAdvancements().award(_adv, (String) _iterator.next());
					}
				}
			}
		}
	}
}