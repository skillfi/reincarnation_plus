package com.github.skillfi.reincarnation_plus.core.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;

public class PipeConect {
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
        if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.CHEST) {
            {
                BlockEntity _ent = world.getBlockEntity(new BlockPos(x, y, z));
                if (_ent != null) {
                    final int _slotid = 0;
                    final ItemStack _setstack = new ItemStack(Blocks.POLISHED_GRANITE_STAIRS);
                    _setstack.setCount(1);
                    _ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        if (capability instanceof IItemHandlerModifiable)
                            ((IItemHandlerModifiable) capability).setStackInSlot(_slotid, _setstack);
                    });
                }
            }
        }
        if ((world.getBlockState(new BlockPos(x, y, z))) == blockstate) {
            {
                BlockEntity _ent = world.getBlockEntity(new BlockPos(x, y, z));
                if (_ent != null) {
                    final int _slotid = 0;
                    final ItemStack _setstack = new ItemStack(Blocks.POLISHED_GRANITE_STAIRS);
                    _setstack.setCount(1);
                    _ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        if (capability instanceof IItemHandlerModifiable)
                            ((IItemHandlerModifiable) capability).setStackInSlot(_slotid, _setstack);
                    });
                }
            }
            {
                BlockEntity _ent = world.getBlockEntity(new BlockPos(x, y, z));
                if (_ent != null) {
                    final int _slotid = 1;
                    final ItemStack _setstack = new ItemStack(Blocks.POLISHED_GRANITE_STAIRS);
                    _setstack.setCount(1);
                    _ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        if (capability instanceof IItemHandlerModifiable)
                            ((IItemHandlerModifiable) capability).setStackInSlot(_slotid, _setstack);
                    });
                }
            }
        }
    }
}
