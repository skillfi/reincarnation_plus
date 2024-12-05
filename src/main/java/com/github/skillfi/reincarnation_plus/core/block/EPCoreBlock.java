package com.github.skillfi.reincarnation_plus.core.block;

import com.github.skillfi.reincarnation_plus.core.block.entity.EPCoreTileEntity;
import com.github.skillfi.reincarnation_plus.libs.capability.magicule.EPCapability;
import com.github.skillfi.reincarnation_plus.libs.capability.magicule.IBlockEPStorage;
import com.github.skillfi.reincarnation_plus.core.procedures.EPUpdateTickProcedure;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.Containers;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Collections;

/**
 * EPCoreBlock - блок з підтримкою {@link EPCoreTileEntity}, енергії EP,
 * взаємодії з рунами та анімацій.
 */
public class EPCoreBlock extends BaseEntityBlock {
    public static final IntegerProperty ANIMATION = IntegerProperty.create("animation", 0, 1);

    /**
     * Конструктор. Встановлює основні властивості блоку.
     */
    public EPCoreBlock() {
        super(BlockBehaviour.Properties.of(Material.GLASS)
                .sound(SoundType.GLASS)
                .strength(1.5f, 10f)
                .lightLevel(state -> 7)); // Додаємо світло від блоку
    }

    /**
     * Встановлює рендеринг блоку як анімований.
     */
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    /**
     * Створює нову плиткову сутність для блоку.
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return ReiBlockEntities.CORE_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    /**
     * Вказує, що блок пропускає світло вниз.
     */
    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    /**
     * Вказує кількість світла, яке блокує блок.
     */
    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    /**
     * Додає властивості блоку до його стану.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ANIMATION);
    }

    /**
     * Встановлює початковий стан блоку при розміщенні.
     */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(ANIMATION, 0);
    }

    /**
     * Обробка випадіння предметів із блоку.
     */
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        return dropsOriginal.isEmpty() ? Collections.singletonList(new ItemStack(this, 1)) : dropsOriginal;
    }

    /**
     * Повертає провайдера меню для блоку.
     */
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    /**
     * Логіка роботи з EP (отримання та використання енергії).
     */
    private int getEPFromBlock(Level world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof EPCoreTileEntity epCoreEntity) {
            return epCoreEntity.getCapability(EPCapability.CAPABILITY)
                    .map(IBlockEPStorage::getMagiculesStored)
                    .orElse(0);
        }
        return 0;
    }

    private void receiveEPToBlock(Level world, BlockPos pos, int amount) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof EPCoreTileEntity epCoreEntity) {
            epCoreEntity.getCapability(EPCapability.CAPABILITY).ifPresent(storage -> {
                storage.receiveMagicules(amount);
            });
        }
    }

    /**
     * Обробка оновлення стану блоку (тик-логіка).
     */
    @Override
    public void tick(BlockState blockState, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockState, world, pos, random);

        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof EPCoreTileEntity epCoreEntity) {
            epCoreEntity.getCapability(EPCapability.CAPABILITY).ifPresent(storage -> {
                // Логіка енергії
                int storedMagicules = storage.getMagiculesStored();

                // Виклик додаткових процедур
                EPUpdateTickProcedure.execute(world, pos, storedMagicules);

                // Взаємодія з рунами (приклад)
                updateRunes(world, pos);
            });
        }

        // Розклад наступного тика
        world.scheduleTick(pos, this, 20);
    }

    /**
     * Оновлення взаємодії з рунами (приклад логіки).
     */
    private void updateRunes(Level world, BlockPos corePos) {
        // Перевірка блоків навколо та взаємодія з рунами
        for (BlockPos pos : BlockPos.betweenClosed(corePos.offset(-1, -1, -1), corePos.offset(1, 1, 1))) {
            BlockState state = world.getBlockState(pos);
        }
    }

    /**
     * Викликається при видаленні блоку. Скидає вміст плиткової сутності.
     */
    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof EPCoreTileEntity be) {
                Containers.dropContents(world, pos, be);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    /**
     * Підтримка сигналів Redstone.
     */
    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        return tileEntity instanceof EPCoreTileEntity be
                ? AbstractContainerMenu.getRedstoneSignalFromContainer(be)
                : 0;
    }
}
