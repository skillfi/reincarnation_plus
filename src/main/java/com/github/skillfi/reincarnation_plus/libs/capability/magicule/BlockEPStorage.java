package com.github.skillfi.reincarnation_plus.libs.capability.magicule;

import net.minecraft.nbt.CompoundTag;

public class BlockEPStorage implements IBlockEPStorage {
    private int magicules;
    private int capacity;
    private int tier;

    public BlockEPStorage() {
        this.capacity = 10000;
        this.magicules = 0;
        this.tier = 1;
    }

    @Override
    public int getMagiculesStored() {
        return magicules;
    }

    @Override
    public int getMaxMagiculesStored() {
        return capacity;
    }

    @Override
    public int getMaxTier() {
        return 10;
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void receiveMagicules(int magicules) {
        this.magicules = Math.min(this.magicules + magicules, this.capacity);
    }

    @Override
    public int extractMagicules(int magicules) {
        int magiculesToExtract = Math.min(magicules, this.magicules);
        this.magicules -= magiculesToExtract;
        return magiculesToExtract;
    }

    @Override
    public void updateCapacity(int newCapacity) {
        if (newCapacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }

        this.capacity = newCapacity;
        this.magicules = Math.min(this.magicules, this.capacity);
    }

    @Override
    public void updateTier(int tier, int magicules) {
        this.tier = tier;
        this.capacity += magicules;
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("magicules", this.magicules);
        tag.putInt("capacity", this.capacity);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        this.capacity = Math.max(0, tag.getInt("capacity"));
        this.magicules = Math.min(tag.getInt("magicules"), this.capacity);
    }
}