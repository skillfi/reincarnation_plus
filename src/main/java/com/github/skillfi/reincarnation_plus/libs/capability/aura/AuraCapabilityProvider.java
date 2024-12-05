package com.github.skillfi.reincarnation_plus.libs.capability.aura;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AuraCapabilityProvider implements ICapabilityProvider {
    private final AuraChunkCapabilityImpl capability = new AuraChunkCapabilityImpl();
    private final LazyOptional<IAuraChunkCapability> lazyCapability = LazyOptional.of(() -> capability);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return AuraChunkCapabilityImpl.CAPABILITY.orEmpty(cap, lazyCapability);
    }

    public CompoundTag serializeNBT() {
        return capability.serializeNBT();
    }

    public void deserializeNBT(CompoundTag nbt) {
        capability.deserializeNBT(nbt);
    }
}
