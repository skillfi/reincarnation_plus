package com.github.skillfi.reincarnation_plus.procedures;

import com.github.skillfi.reincarnation_plus.network.ReincarnationPlusVariables;
import com.github.skillfi.reincarnation_plus.registry.RPClasses;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import java.util.Map;
import java.util.Iterator;

import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusBlocks;

public class GemDustItemIsDroppedByPlayerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((world.getBlockState(new BlockPos(x, y, z + 1))).getBlock() instanceof LiquidBlock) {
			world.scheduleTick(new BlockPos(x, y, z + 1), world.getBlockState(new BlockPos(x, y, z + 1)).getBlock(), 66);
			{
				BlockPos _bp = new BlockPos(x, y, z + 1);
				BlockState _bs = ReincarnationPlusBlocks.CRYSTALIZED_WATER.get().defaultBlockState();
				BlockState _bso = world.getBlockState(_bp);
				for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
					Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
					if (_property != null && _bs.getValue(_property) != null)
						try {
							_bs = _bs.setValue(_property, (Comparable) entry.getValue());
						} catch (Exception e) {
						}
				}
				world.setBlock(_bp, _bs, 3);
			}
			if (entity instanceof ServerPlayer _player) {
				Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("reincarnation_plus:black_smith"));
				AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
				if (!_ap.isDone()) {
					Iterator _iterator = _ap.getRemainingCriteria().iterator();
					while (_iterator.hasNext())
						_player.getAdvancements().award(_adv, (String) _iterator.next());
				}
				_player.getCapability(ReincarnationPlusVariables.CAP).ifPresent(cap -> {
					cap.learnClass(RPClasses.BLACK_SMITH_CLASS.get());
					cap.syncPlayer(_player);
				});
			}
			if (!world.isClientSide()) {
				BlockPos _bp = new BlockPos(x, y, z + 1);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("Tick", 0);
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			{
				BlockPos _pos = new BlockPos(x, y, z + 1);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("Crystalization") instanceof BooleanProperty _booleanProp)
					world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
			}
		}
	}
}
