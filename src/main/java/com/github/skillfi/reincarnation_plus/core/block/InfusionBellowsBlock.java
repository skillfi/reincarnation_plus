package com.github.skillfi.reincarnation_plus.core.block;

import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.InfusionBellowsBlockEntity;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.libs.block.state.properties.MagicInfuserPart;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class InfusionBellowsBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static BooleanProperty IDLE;
    public static BooleanProperty USE;
    public static BooleanProperty BOOST;

    public InfusionBellowsBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL)
                .strength(1f, 10f).noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().
                setValue(FACING, Direction.NORTH).
                setValue(IDLE, Boolean.TRUE).
                setValue(BOOST, Boolean.FALSE).
                setValue(USE, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, IDLE, USE, BOOST);
    }

    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ReiBlockEntities.INFUSION_BELLOWS.get(), InfusionBellowsBlockEntity::tick);
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        if (!world.isClientSide()) {
            Direction directionToInfuser = findClosestInfuser(world, pos);
            world.setBlock(pos, blockstate.setValue(FACING, directionToInfuser), 2);
        }
    }

    private Direction findClosestInfuser(Level world, BlockPos pos) {
        Direction[] directions = Direction.Plane.HORIZONTAL.stream().toArray(Direction[]::new); // Сусідні горизонтальні сторони

        for (Direction direction : directions) {
            BlockPos neighborPos = pos.relative(direction);
            BlockEntity neighborEntity = world.getBlockEntity(neighborPos);

            if (neighborEntity instanceof MagiculaInfuserBlockEntity) {
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
        if (!world.isClientSide()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof InfusionBellowsBlockEntity mehBlockEntity) {
                mehBlockEntity.setBoostduration(40);
                Direction[] directions = Direction.values();
                for (Direction direction : directions) {
                    BlockPos neighborPos = pos.relative(direction);
                    BlockEntity neighborEntity = world.getBlockEntity(neighborPos);
                    if (neighborEntity instanceof MagiculaInfuserBlockEntity infuser) {
                        if (infuser.getInfusionProgress() < 90 && infuser.getInfusionProgress() >= 0) {
                            infuser.boost(mehBlockEntity.speedModifier);
                            infuser.setBoostDuration(40);
                            BlockState newstate = blockstate.setValue(USE, true);
                            world.sendBlockUpdated(pos, newstate, newstate, 2);
                        }
                        BlockState newstate = blockstate.setValue(USE, false);
                        world.sendBlockUpdated(pos, newstate, newstate, 2);
                    }
                }
            }
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
        return new InfusionBellowsBlockEntity(pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
    }

    @Override
    public RenderShape getRenderShape(BlockState state){
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    static {
        IDLE = BooleanProperty.create("idle");
        USE = BooleanProperty.create("use");
        BOOST = BooleanProperty.create("boost");
    }
}
