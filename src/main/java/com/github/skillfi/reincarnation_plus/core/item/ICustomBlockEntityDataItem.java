package com.github.skillfi.reincarnation_plus.core.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface ICustomBlockEntityDataItem {
    CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag nbt);
}
