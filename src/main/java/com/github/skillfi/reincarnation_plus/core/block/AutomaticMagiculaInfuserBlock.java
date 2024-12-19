package com.github.skillfi.reincarnation_plus.core.block;

import com.github.skillfi.reincarnation_plus.core.block.entity.AutomaticMagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.libs.block.state.properties.AutomaticMagicInfuserPart;
import com.github.skillfi.reincarnation_plus.libs.block.state.properties.ReiBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class AutomaticMagiculaInfuserBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING;
    public static final BooleanProperty LIT;
    public static final BooleanProperty INFUSION;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty CONSUMPTION;
    public static final EnumProperty<AutomaticMagicInfuserPart> PART;
    private static final VoxelShape TOP_SHAPE;
    private static final VoxelShape BASE_SHAPE;
    private static final VoxelShape AMPLIFIER_SHAPE;
    private static final VoxelShape BELLOWS_SHAPE;


    public AutomaticMagiculaInfuserBlock() {
        super(Properties.of(Material.METAL, MaterialColor.DEEPSLATE).strength(50.0F, 1200.0F).sound(SoundType.STONE).noOcclusion().lightLevel(litBlockEmission(13)));
        this.registerDefaultState(this.getStateDefinition().any().
                setValue(FACING, Direction.NORTH).
                setValue(PART, AutomaticMagicInfuserPart.BASE).
                setValue(LIT, Boolean.FALSE).
                setValue(INFUSION, Boolean.FALSE).
                setValue(CONSUMPTION, Boolean.FALSE).
                setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 15;
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide()) {
            BlockPos blockpos = this.getOtherPartPosition(pPos, pState.getValue(PART));
            pLevel.setBlock(blockpos, pState.setValue(PART, AutomaticMagicInfuserPart.TOP).
                    setValue(WATERLOGGED, this.isWaterAtPosition(pLevel, blockpos)), 3);
            pLevel.blockUpdated(pPos, Blocks.AIR);
            pState.updateNeighbourShapes(pLevel, pPos, 3);
        }
    }

    private BlockPos getOtherPartPosition(BlockPos sourcePos, AutomaticMagicInfuserPart part) {
        return part == AutomaticMagicInfuserPart.BASE ? sourcePos.above() : sourcePos.below();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT, PART, WATERLOGGED, INFUSION, CONSUMPTION);
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
        if (!pLevel.isClientSide()) {
            BlockPos blockpos = this.getOtherPartPosition(pPos,pState.getValue(PART));
            pLevel.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
        }
    }

    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
        if (!pLevel.isClientSide()) {
            BlockState pState = pLevel.getBlockState(pPos);
            BlockPos blockpos = this.getOtherPartPosition(pPos, (AutomaticMagicInfuserPart) pState.getValue(PART));
            pLevel.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
        }
    }

    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        return blockpos.getY() <= level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext) ? (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite())).setValue(WATERLOGGED, this.isWaterAtPosition(level, blockpos)) : null;
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if ((Boolean)pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    private boolean isWaterAtPosition(Level level, BlockPos blockPos) {
        return level.getFluidState(blockPos).is(Fluids.WATER);
    }



    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return (BlockState)pState.setValue(FACING, pRotation.rotate((Direction)pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation((Direction)pState.getValue(FACING)));
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if ((Boolean)pState.getValue(LIT) && ((AutomaticMagicInfuserPart)pState.getValue(PART)).equals(AutomaticMagicInfuserPart.BASE)) {
            double d0 = (double)pPos.getX() + (double)0.5F;
            double d1 = (double)pPos.getY();
            double d2 = (double)pPos.getZ() + (double)0.5F;
            if (pRandom.nextDouble() < 0.1) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = (Direction)pState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = pRandom.nextDouble() * 0.6 - 0.3;
            double d4 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : d3;
            double d5 = pRandom.nextDouble() * (double)6.0F / (double)16.0F;
            double d6 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : d3;
            pLevel.addParticle(ParticleTypes.SMOKE, d0 + d4, d1 + d5, d2 + d6, (double)0.0F, (double)0.0F, (double)0.0F);
            pLevel.addParticle(ParticleTypes.FLAME, d0 + d4, d1 + d5, d2 + d6, (double)0.0F, (double)0.0F, (double)0.0F);
        } else if ((Boolean)pState.getValue(INFUSION) && ((AutomaticMagicInfuserPart)pState.getValue(PART)).equals(AutomaticMagicInfuserPart.BASE)) {
            double d0 = (double)pPos.getX() + (double)0.5F;
            double d1 = (double)pPos.getY();
            double d2 = (double)pPos.getZ() + (double)0.5F;
            if (pRandom.nextDouble() < 0.1) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = (Direction)pState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = pRandom.nextDouble() * 0.6 - 0.3;
            double d4 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : d3;
            double d5 = pRandom.nextDouble() * (double)6.0F / (double)16.0F;
            double d6 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : d3;
            pLevel.addParticle(ParticleTypes.SMOKE, d0 + d4, d1 + d5, d2 + d6, (double)0.0F, (double)0.0F, (double)0.0F);
            pLevel.addParticle(ParticleTypes.FLAME, d0 + d4, d1 + d5, d2 + d6, (double)0.0F, (double)0.0F, (double)0.0F);}

    }

    public static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return (state) -> (Boolean)state.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState state){
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof AutomaticMagiculaInfuserBlockEntity) {
                AutomaticMagiculaInfuserBlockEntity infuserBlock = (AutomaticMagiculaInfuserBlockEntity)blockEntity;
                infuserBlock.drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof AutomaticMagiculaInfuserBlockEntity) {
            AutomaticMagiculaInfuserBlockEntity infuserBlock = (AutomaticMagiculaInfuserBlockEntity)blockEntity;
        }
        return pLevel.isClientSide() ? InteractionResult.sidedSuccess(true) : this.openMenu((ServerPlayer)pPlayer, pLevel, ((AutomaticMagicInfuserPart)pState.getValue(PART)).equals(AutomaticMagicInfuserPart.BASE) ? pPos : pPos.below());
    }

    private InteractionResult openMenu(ServerPlayer player, Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof AutomaticMagiculaInfuserBlockEntity infuserBlockEntity) {
            NetworkHooks.openScreen(player, infuserBlockEntity, pos);
            return InteractionResult.sidedSuccess(false);
        } else {
            throw new IllegalStateException(String.format("Container provider for Magic Infuser block is missing!\n Position: %s; World: %s", pos, level.dimension()));
        }
    }

    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART) == AutomaticMagicInfuserPart.BASE ? new AutomaticMagiculaInfuserBlockEntity(pos, state) : null;
    }

    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return state.getValue(PART) == AutomaticMagicInfuserPart.BASE ? createTickerHelper(type, (BlockEntityType) ReiBlockEntities.AUTOMATIC_MAGICAL_INFUSER_ENTITY.get(), AutomaticMagiculaInfuserBlockEntity::tick) : null;
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        LIT = BlockStateProperties.LIT;
        INFUSION = BooleanProperty.create("infuse");
        CONSUMPTION = BooleanProperty.create("consumption");
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        PART = ReiBlockStateProperties.AUTOMATIC_MAGIC_INFUSER_PART;
        TOP_SHAPE = Shapes.or(box((double)3.0F, (double)0.0F, (double)3.0F, (double)13.0F, (double)2.0F, (double)13.0F), new VoxelShape[]{box((double)4.0F, (double)2.0F, (double)4.0F, (double)12.0F, (double)12.0F, (double)12.0F), box((double)3.0F, (double)12.0F, (double)3.0F, (double)13.0F, (double)13.0F, (double)13.0F), box((double)2.0F, (double)13.0F, (double)2.0F, (double)14.0F, (double)15.0F, (double)14.0F), box((double)3.0F, (double)15.0F, (double)3.0F, (double)13.0F, (double)16.0F, (double)13.0F)});
        BASE_SHAPE = Shapes.or(box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)12.0F, (double)16.0F), new VoxelShape[]{box((double)1.0F, (double)12.0F, (double)1.0F, (double)15.0F, (double)14.0F, (double)15.0F), box((double)2.0F, (double)14.0F, (double)2.0F, (double)14.0F, (double)16.0F, (double)14.0F)});
        AMPLIFIER_SHAPE = Shapes.or(
                box(-32.0F, 0.0F, 0.0F, -16.0F, 12.0F, 16.0F), // Основна коробка зміщена вліво
                new VoxelShape[]{
                        box(-31.0F, 12.0F, 1.0F, -17.0F, 14.0F, 15.0F), // Верхній шар 1
                        box(-30.0F, 14.0F, 2.0F, -18.0F, 16.0F, 14.0F)  // Верхній шар 2
                }
        );
        BELLOWS_SHAPE = Shapes.or(
                box(0.0F, 0.0F, 0.0F, 16.0F, 12.0F, 16.0F), // Основна коробка зміщена вправо
                new VoxelShape[]{
                        box(1.0F, 12.0F, 1.0F, 15.0F, 14.0F, 15.0F), // Верхній шар 1
                        box(2.0F, 14.0F, 2.0F, 14.0F, 16.0F, 14.0F)  // Верхній шар 2
                }
        );
    }
}
