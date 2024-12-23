package com.github.skillfi.reincarnation_plus.core.block;

import com.github.skillfi.reincarnation_plus.core.block.entity.SiphonBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.Nullable;

public class SiphonBlock extends BaseEntityBlock {

    public SiphonBlock() {
        super(Properties.of(Material.METAL, MaterialColor.METAL).strength(50.0F, 1200.0F).sound(SoundType.METAL).noOcclusion());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SiphonBlockEntity(blockPos, blockState);
    }
}
