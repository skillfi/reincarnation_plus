package com.github.skillfi.reincarnation_plus.core.block.entity;

import com.github.manasmods.tensura.registry.blocks.TensuraBlocks;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.libs.api.aura.AuraAPI;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GrassBlockEntity extends BlockEntity {
    @Getter
    @Setter
    private double magicules;
    @Getter
    @Setter
    private int tickCounter;
    public boolean needUpdate;
    @Getter
    private int maxMagicules;

    private static final int TICKS_PER_ATTEMPT = 100; // Кількість тіків між спробами
    private static final double GROWTH_CHANCE = 0.05; // Шанс перетворення (5%)

    public GrassBlockEntity(BlockPos pos, BlockState state) {
        super(ReiBlockEntities.GRASS_ENTITY.get(), pos, state);
        this.magicules = 0.0;
        this.tickCounter = 0;
        this.needUpdate = false;
        this.maxMagicules = 10000;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        setMagicules(tag.getDouble("Magicules"));
        setTickCounter(tag.getInt("TickCounter"));
    }

    private void addMagicules(double magicules){
        this.magicules += magicules;
        this.needUpdate = true;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putDouble("Magicules", getMagicules());
        tag.putInt("TickCounter", getTickCounter());
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GrassBlockEntity pEntity) {
        if (!level.isClientSide()) {
            pEntity.tickCounter++;
            if (pEntity.tickCounter >= TICKS_PER_ATTEMPT) {
                pEntity.tickCounter = 0;// Скидання лічильника
                if (AuraAPI.getAura(level, pos) > 0.0){
                    if (AuraAPI.consumeAura(level, pos, 500.0)){
                        if (pEntity.getMagicules() < pEntity.getMaxMagicules())
                            pEntity.addMagicules(500);
                        if (level.random.nextDouble() < GROWTH_CHANCE && pEntity.getMagicules() > 10000) {
                            level.setBlock(pEntity.worldPosition, TensuraBlocks.HIPOKUTE_GRASS.get().defaultBlockState(), 3);
                        }
                    }
                }

            }
            if (pEntity.needUpdate) {
                pEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 2);
                pEntity.needUpdate = false;
            }
        }

    }
}
