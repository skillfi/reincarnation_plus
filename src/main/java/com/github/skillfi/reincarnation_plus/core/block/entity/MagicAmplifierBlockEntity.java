package com.github.skillfi.reincarnation_plus.core.block.entity;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.capability.block.IMagiculaInfuserCapability;
import com.github.skillfi.reincarnation_plus.core.capability.block.MagiculaInfuserCapability;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicAmplifierBlockEntity extends BlockEntity {
    public static String NBT_KEY = "magic_container";
    private final MagiculaInfuserCapability capability = new MagiculaInfuserCapability();
    private final LazyOptional<IMagiculaInfuserCapability> capabilityLazyOptional = LazyOptional.of(() -> capability);
    public boolean needUpdate;


    public MagicAmplifierBlockEntity(BlockPos pos, BlockState state) {
        super(ReiBlockEntities.MAGIC_AMPLIFIER.get(), pos, state);
        this.capability.setMagicMaterialAmount(0);
        this.capability.setMaxMagicMaterialAmount(200000);
        this.capability.setMoltenAmount(0);
        this.capability.setMaxMoltenAmount(200000);
        this.capability.setAdditionalMagicMaterialAmount(0);
        needUpdate = false;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagicAmplifierBlockEntity pEntity) {
        if (!level.isClientSide()) {
            // Оновлення блоку, якщо потрібне
            if (pEntity.needUpdate) {
                pEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 2);
                pEntity.needUpdate = false;
            }
        }
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == ReiMod.MAGICULA_INFUSER_CAPABILITY) {
            return capabilityLazyOptional.cast();
        } else {
            return super.getCapability(capability, facing);
        }
    }

    public void invalidateCaps() {
        super.invalidateCaps();
        capabilityLazyOptional.invalidate();

    }

    public void reviveCaps() {
        super.reviveCaps();
        capabilityLazyOptional.invalidate();
    }

    // region Molten Amount
    public void addMagicMaterialAmount(int moltenAmount) {
        this.capability.increseMagicAmount(moltenAmount);
        this.needUpdate = true;
    }

    public void addMoltenMaterialAmount(int points) {
        this.capability.increseMoltenAmount(points);
        this.needUpdate = true;
    }

    public void removeMagicMaterialAmount(int moltenAmount) {
        this.capability.decreseMagicAmount(moltenAmount);
        this.needUpdate = true;
    }

    public void removeMoltenMaterialAmount(int moltenAmount) {
        this.capability.decreseMoltenAmount(moltenAmount);
        this.needUpdate = true;
    }

    public int getMoltenAmount() {
        return capability.getMoltenAmount();
    }

    public int getMaxMoltenAmount() {
        return capability.getMaxMoltenAmount();
    }

    public int getMagicMaterialAmount() {
        return capability.getMagicMaterialAmount();
    }
    // endregion

    public int getMaxMagicMaterialAmount() {
        return capability.getMaxMagicMaterialAmount();
    }

    // region NBT
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt(NBT_KEY + ".magicules", capability.getMagicMaterialAmount());
        nbt.putInt(NBT_KEY + ".maxMagicules", capability.getMaxMagicMaterialAmount());
        nbt.putInt(NBT_KEY + ".addMolten", capability.getAdditionalMagicMaterialAmount());
        nbt.putInt(NBT_KEY + ".existencePointsAmount", capability.getMoltenAmount());
        nbt.putInt(NBT_KEY + ".maxExistencePointsAmount", capability.getMaxMoltenAmount());
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        capability.setMagicMaterialAmount(nbt.getInt(NBT_KEY + ".magicules"));
        capability.setMaxMagicMaterialAmount(nbt.getInt(NBT_KEY + ".maxMagicules"));
        capability.setAdditionalMagicMaterialAmount(nbt.getInt(NBT_KEY + ".addMolten"));
        capability.setMoltenAmount(nbt.getInt(NBT_KEY + ".existencePointsAmount"));
        if (nbt.getInt(NBT_KEY + ".maxExistencePointsAmount") == 0)
            capability.setMaxMoltenAmount(200000);
        else
            capability.setMaxMoltenAmount(nbt.getInt(NBT_KEY + ".maxExistencePointsAmount"));
    }

    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    // endregion

    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }
}
