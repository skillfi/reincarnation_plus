package com.github.skillfi.reincarnation_plus.core.procedures;

import com.github.skillfi.reincarnation_plus.core.block.entity.MagicInfuserBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class PipeTick {
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
        BlockPos pos = new BlockPos(x, y, z);

        // 1. Перевірка наявності скрині поруч
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            if (world.getBlockState(neighborPos).getBlock() instanceof ChestBlock) {
                BlockEntity chestEntity = world.getBlockEntity(neighborPos);
                if (chestEntity != null) {
                    chestEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(chestHandler -> {
                        for (int slot = 0; slot < chestHandler.getSlots(); slot++) {
                            ItemStack stack = chestHandler.getStackInSlot(slot);
                            if (stack.getItem() == Items.RAW_IRON) {
                                chestHandler.extractItem(slot, 1, false);
                                BlockEntity blockEntity = world.getBlockEntity(pos);
                                if (blockEntity != null) {
                                    blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(blockHandler -> {
                                        blockHandler.insertItem(1, new ItemStack(Items.RAW_IRON, 1), false);
                                    });
                                }
                                break;
                            }
                        }
                    });
                }
            }
        }

        // 2. Перевірка наявності такого ж блоку поруч
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            if (world.getBlockState(neighborPos).equals(blockstate)) {
                BlockEntity neighborEntity = world.getBlockEntity(neighborPos);
                BlockEntity currentEntity = world.getBlockEntity(pos);

                if (neighborEntity != null && currentEntity != null) {
                    neighborEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(neighborHandler -> {
                        currentEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(currentHandler -> {
                            for (int slot = 0; slot < 2; slot++) {
                                ItemStack stack = currentHandler.getStackInSlot(slot);
                                if (!stack.isEmpty()) {
                                    ItemStack remainder = neighborHandler.insertItem(slot, stack.copy(), false);
                                    currentHandler.extractItem(slot, stack.getCount() - remainder.getCount(), false);
                                }
                            }
                        });
                    });
                }
            }
        }

        // 3. Перевірка наявності блоку Магічного інфузора поруч
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockEntity neighborEntity = world.getBlockEntity(neighborPos);

            if (neighborEntity instanceof MagicInfuserBlockEntity infuser) {
                BlockEntity currentEntity = world.getBlockEntity(pos);
                if (currentEntity != null) {
                    currentEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(currentHandler -> {
                        ItemStack inputStack = currentHandler.getStackInSlot(0);
                        ItemStack fuelStack = currentHandler.getStackInSlot(1);

                        if (!inputStack.isEmpty()) {
                            infuser.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(infuserHandler -> {
                                ItemStack remainder = infuserHandler.insertItem(1, inputStack.copy(), false);
                                currentHandler.extractItem(0, inputStack.getCount() - remainder.getCount(), false);
                            });
                        }

                        if (!fuelStack.isEmpty()) {
                            infuser.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(infuserHandler -> {
                                ItemStack remainder = infuserHandler.insertItem(2, fuelStack.copy(), false);
                                currentHandler.extractItem(1, fuelStack.getCount() - remainder.getCount(), false);
                            });
                        }
                    });
                }
            }
        }
    }

}
