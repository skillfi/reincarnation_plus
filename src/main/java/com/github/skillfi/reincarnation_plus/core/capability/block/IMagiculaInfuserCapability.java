package com.github.skillfi.reincarnation_plus.core.capability.block;

import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public interface IMagiculaInfuserCapability {
    int getMagicMaterialAmount();
    void setMagicMaterialAmount(int amount);

    int getMaxMagicMaterialAmount();
    void setMaxMagicMaterialAmount(int amount);

    int getAdditionalMagicMaterialAmount();
    void setAdditionalMagicMaterialAmount(int amount);

    int getMoltenAmount();
    void setMoltenAmount(int amount);

    int getMaxMoltenAmount();
    void setMaxMoltenAmount(int amount);

    Optional<ResourceLocation> getLeftBarId();
    void setLeftBarId(Optional<ResourceLocation> amount);

    Optional<ResourceLocation> getRightBarId();
    void setRightBarId(Optional<ResourceLocation> amount);

    Optional<ResourceLocation> getInfusionBarId();
    void setInfusionBarId(Optional<ResourceLocation> amount);

    void increseMagicAmount(int amount);

    void increseMoltenAmount(int amount);

    void decreseMagicAmount(int amount);

    void decreseMoltenAmount(int amount);

    void decreseFuelTime();

    void decreseInfusionTime();

    void increseFuelProgress();

    void increseInfusionProgress();

    int getInfusionProgress();
    int getMeltingProgress();
    int getFuelTime();
    int getMaxFuelTime();
    int getInfusionTime();
    int getMaxInfusionTime();
    int getBoostDuration();
    void setBoostDuration(int duration);
}

