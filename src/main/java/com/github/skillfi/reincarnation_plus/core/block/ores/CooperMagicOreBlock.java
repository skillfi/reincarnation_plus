package com.github.skillfi.reincarnation_plus.core.block.ores;

import com.github.manasmods.tensura.registry.items.TensuraMaterialItems;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.CooperOreBlockEntity;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.libs.block.state.properties.MagicInfuserPart;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CooperMagicOreBlock extends BaseEntityBlock {
    public static final IntegerProperty AGE;
    public CooperMagicOreBlock() {
        super(Properties.of(Material.STONE).sound(SoundType.STONE).strength(3f, 5f).requiresCorrectToolForDrops().noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().
                setValue(AGE, 0));
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CooperOreBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState state){
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ReiBlockEntities.COOPER_MAGIC_ORE.get(), CooperOreBlockEntity::tick);
    }

    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1;
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        if (player.getInventory().getSelected().getItem() instanceof PickaxeItem tieredItem)
            return tieredItem.getTier().getLevel() >= 2;
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty()) {
            return dropsOriginal;
        }

        List<ItemStack> drops = new ArrayList<>();
        // Завжди додаємо основний дроп
        drops.add(new ItemStack(Items.RAW_GOLD));

        // Отримуємо вік (age) блока
        int age = state.getValue(AGE);
        double baseChance = 0.25; // Базовий шанс 25%
        double chance = baseChance + (age * 0.25); // Збільшуємо шанс на 10% за кожний рівень age

        // Генеруємо випадкове число
        if (builder.getLevel().random.nextDouble() < chance) {
            drops.add(new ItemStack(TensuraMaterialItems.MAGIC_ORE.get())); // Замініть на вашу магічну руду
        }

        return drops;
    }

    static {
        AGE = IntegerProperty.create("age", 0, 3);
    }
}
