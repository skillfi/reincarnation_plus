package com.github.skillfi.reincarnation_plus.core.block.ores.iron;

import com.github.manasmods.tensura.registry.items.TensuraMaterialItems;
import com.github.skillfi.reincarnation_plus.core.procedures.IronOreMagiculeUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IronOreStage1Block extends BaseEntityBlock {
    public static final IntegerProperty AGE;
    public IronOreStage1Block(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().
                setValue(AGE, 1));
    }

    public static Properties stoneProperties(){
        return BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3f, 5f).requiresCorrectToolForDrops().noOcclusion().randomTicks();
    }

    public static Properties deepslateProperties(){
        return BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE).strength(3f, 5f).requiresCorrectToolForDrops().noOcclusion().randomTicks();
    }

    @Override
    public RenderShape getRenderShape(BlockState state){
        return RenderShape.MODEL;
    }

    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        IronOreMagiculeUpdate.execute(world, x, y, z, blockstate);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        if (player.getInventory().getSelected().getItem() instanceof PickaxeItem tieredItem)
            return tieredItem.getTier().getLevel() >= 2;
        return false;
    }


    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty()) {
            return dropsOriginal;
        }

        List<ItemStack> drops = new ArrayList<>();
        // Завжди додаємо основний дроп
        drops.add(new ItemStack(Items.RAW_COPPER));

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
        AGE = IntegerProperty.create("age", 1, 3);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }
}