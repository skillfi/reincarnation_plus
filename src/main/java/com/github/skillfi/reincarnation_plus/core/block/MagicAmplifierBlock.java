package com.github.skillfi.reincarnation_plus.core.block;

import com.github.skillfi.reincarnation_plus.core.block.entity.MagicAmplifierBlockEntity;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class MagicAmplifierBlock extends BaseEntityBlock {
    public MagicAmplifierBlock() {
        super(Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).noOcclusion());
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MagicAmplifierBlockEntity(pos, state);
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof MagicAmplifierBlockEntity infuserBlock) {
            CompoundTag tag = pStack.getTagElement("BlockEntityTag");
            if (tag != null) {
                infuserBlock.load(tag);
            }
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MagicAmplifierBlockEntity infuserBlock) {
                // Зберігаємо NBT дані у випадаючий предмет
                ItemStack stack = new ItemStack(this);
                CompoundTag tag = infuserBlock.saveWithFullMetadata();
                if (!tag.isEmpty()) {
                    stack.getOrCreateTag().put("BlockEntityTag", tag);
                }
                Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), stack);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }


    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ReiBlockEntities.MAGIC_AMPLIFIER.get(), MagicAmplifierBlockEntity::tick);
    }
}
