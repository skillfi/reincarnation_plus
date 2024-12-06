package com.github.skillfi.reincarnation_plus.core.block;

import com.github.skillfi.reincarnation_plus.core.block.entity.MagicInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagicMehBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Collections;
import java.util.List;

public class MagicMechBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MagicMechBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL)
                .strength(1f, 10f));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        if (!world.isClientSide()) {
            Direction directionToInfuser = findClosestInfuser(world, pos);
            if (directionToInfuser != null) {
                world.setBlock(pos, blockstate.setValue(FACING, directionToInfuser), 3);
            }
        }
    }

    private Direction findClosestInfuser(Level world, BlockPos pos) {
        Direction[] directions = Direction.Plane.HORIZONTAL.stream().toArray(Direction[]::new); // Сусідні горизонтальні сторони

        for (Direction direction : directions) {
            BlockPos neighborPos = pos.relative(direction);
            BlockEntity neighborEntity = world.getBlockEntity(neighborPos);

            if (neighborEntity instanceof MagicInfuserBlockEntity) {
                return direction;
            }
        }

        return null; // Якщо інфузор не знайдено
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    @Override
    public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        super.use(blockstate, world, pos, player, hand, hit);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        // Увімкнення boost
        if (!world.isClientSide()) {
            if (blockEntity instanceof MagicMehBlockEntity mehBlockEntity) {
                mehBlockEntity.setBoost(true);
                // Масив напрямків для перевірки сусідніх блоків
                Direction[] directions = Direction.values();

                for (Direction direction : directions) {
                    BlockPos neighborPos = pos.relative(direction);
                    BlockEntity neighborEntity = world.getBlockEntity(neighborPos);

                    // Якщо сусідній блок є MagicInfuserBlockEntity, активуємо його
                    if (neighborEntity instanceof MagicInfuserBlockEntity infuser) {
                        infuser.boost(mehBlockEntity.speedModifier);
                        infuser.setBoostDuration(60);
                    }
                }
            }



            // Додавання частинок для візуального ефекту
            double d0 = (double) pos.getX() + 0.5F;
            double d1 = (double) pos.getY();
            double d2 = (double) pos.getZ() + 0.5F;

            world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0F, 0.0F, 0.0F);
            world.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0F, 0.0F, 0.0F);
        }

        return InteractionResult.SUCCESS;
    }


    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MagicMehBlockEntity(pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
    }
}
