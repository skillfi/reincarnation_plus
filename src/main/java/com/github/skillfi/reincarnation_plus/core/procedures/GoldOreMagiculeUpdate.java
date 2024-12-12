package com.github.skillfi.reincarnation_plus.core.procedures;

import com.github.manasmods.tensura.registry.blocks.TensuraBlocks;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class GoldOreMagiculeUpdate {

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
        if (world.isClientSide()) {
            return;
        }

        BlockPos pos = new BlockPos(x, y, z);
        double magicules = 500;
        double growthChance = 0.05;

        double currentMagicules = getBlockEntityTagValue(world, pos, "Magicules");
        double tickCounter = getBlockEntityTagValue(world, pos, "tickCounter");

        updateBlockEntityTag(world, pos, "Magicules", currentMagicules + magicules);
        updateBlockEntityTag(world, pos, "tickCounter", tickCounter + 1);

        int age = getBlockStateAge(blockstate);

        if (tickCounter == 100) {
            ReiMod.LOGGER.debug("Gold Ore Tick at" + pos);
            if ((age == 0 && magicules >= 10000 && Math.random() < growthChance) ||
                    (age == 1 && magicules >= 25000) ||
                    (age == 2 && magicules >= 50000)) {

                updateBlockEntityTag(world, pos, "tickCounter", 0);
                setBlockStateAge(world, pos, blockstate, age + 1);
                updateToNextAgeBlock(world, pos, age);
            }

        }
    }

    private static double getBlockEntityTagValue(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            return blockEntity.getPersistentData().getDouble(tag);
        }
        return 0;
    }

    private static void updateBlockEntityTag(LevelAccessor world, BlockPos pos, String tag, double value) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            blockEntity.getPersistentData().putDouble(tag, value);
            if (world instanceof Level level) {
                level.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
        }
    }

    private static int getBlockStateAge(BlockState blockState) {
        Property<?> ageProperty = blockState.getBlock().getStateDefinition().getProperty("age");
        if (ageProperty instanceof IntegerProperty integerProperty) {
            return blockState.getValue(integerProperty);
        }
        return -1;
    }

    private static void setBlockStateAge(LevelAccessor world, BlockPos pos, BlockState blockState, int age) {
        Property<?> ageProperty = blockState.getBlock().getStateDefinition().getProperty("age");
        if (ageProperty instanceof IntegerProperty integerProperty && integerProperty.getPossibleValues().contains(age)) {
            world.setBlock(pos, blockState.setValue(integerProperty, age), 3);
        }
    }

    private static void updateToNextAgeBlock(LevelAccessor world, BlockPos pos, int currentAge) {
        BlockState newBlockState;
        switch (currentAge) {
            case 0 -> newBlockState = ReiBlockEntities.ReiBlocks.GOLD_MAGIC_ORE_AGE1.get().defaultBlockState();
            case 1 -> newBlockState = ReiBlockEntities.ReiBlocks.GOLD_MAGIC_ORE_AGE2.get().defaultBlockState();
            case 2 -> newBlockState = TensuraBlocks.MAGIC_ORE.get().defaultBlockState();
            default -> throw new IllegalArgumentException("Invalid age: " + currentAge);
        }

        replaceBlockState(world, pos, newBlockState);
    }

    private static void replaceBlockState(LevelAccessor world, BlockPos pos, BlockState newBlockState) {
        BlockEntity oldBlockEntity = world.getBlockEntity(pos);
        CompoundTag blockEntityTag = null;
        if (oldBlockEntity != null) {
            blockEntityTag = oldBlockEntity.saveWithFullMetadata();
            oldBlockEntity.setRemoved();
        }

        world.setBlock(pos, newBlockState, 3);

        if (blockEntityTag != null) {
            BlockEntity newBlockEntity = world.getBlockEntity(pos);
            if (newBlockEntity != null) {
                try {
                    newBlockEntity.load(blockEntityTag);
                } catch (Exception ignored) {
                }
            }
        }
    }
}