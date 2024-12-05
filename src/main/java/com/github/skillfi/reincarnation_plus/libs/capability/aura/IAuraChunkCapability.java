package com.github.skillfi.reincarnation_plus.libs.capability.aura;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.INBTSerializable;

public interface IAuraChunkCapability extends INBTSerializable<CompoundTag> {
    void sync(ServerPlayer player);

    double getMaxAura();

    double getRegenerationRate();

    double getAura();

    double getAura(BlockPos position);

    double getMaxAura(BlockPos position);

    boolean consumeAura(BlockPos position, double amount);

    void setAura(BlockPos position, double amount);
}
