package com.github.skillfi.reincarnation_plus.core.block.entity.ores.copper;

import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CooperOre2BlockEntity extends BlockEntity {
    @Getter @Setter private double magicules;
    @Getter @Setter private int tickCounter;


    public CooperOre2BlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ReiBlockEntities.COOPER_MAGIC_ORE_AGE2.get(), pPos, pBlockState);
        this.magicules = 0.0;
        this.tickCounter = 0;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        setMagicules(tag.getDouble("Magicules"));
        setTickCounter(tag.getInt("tickCounter"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putDouble("Magicules", getMagicules());
        tag.putInt("tickCounter", getTickCounter());
    }
}
