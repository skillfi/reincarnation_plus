package com.github.skillfi.reincarnation_plus.libs.capability.subordinates;

import com.github.manasmods.tensura.capability.race.TensuraPlayerCapability;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SubordinatesCapabilityProvider implements ICapabilityProvider {
    private final ISubordinateCapability defaultCapability = new SubordinateCapability();
    private final LazyOptional<ISubordinateCapability> cap = LazyOptional.of(() -> this.defaultCapability);

    public SubordinatesCapabilityProvider(){}

    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == TensuraPlayerCapability.CAPABILITY ? this.cap.cast() : LazyOptional.empty();
    }

    public CompoundTag serializeNBT() {
        return (CompoundTag)this.defaultCapability.serializeNBT();
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.defaultCapability.deserializeNBT(nbt);
    }
}
