package com.github.skillfi.reincarnation_plus.core.block.entity;

import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MagicAmplifierBlockEntity extends BlockEntity {
    @Getter
    @Setter
    private float maxMagicMaterialAmount;


    public MagicAmplifierBlockEntity(BlockPos pos, BlockState state) {
        super(ReiBlockEntities.MAGIC_AMPLIFIER.get(), pos, state);
        maxMagicMaterialAmount = 200.0F;
    }

}
