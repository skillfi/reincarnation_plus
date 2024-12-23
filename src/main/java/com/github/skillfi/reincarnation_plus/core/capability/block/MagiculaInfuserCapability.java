package com.github.skillfi.reincarnation_plus.core.capability.block;

import com.github.skillfi.reincarnation_plus.core.data.recipe.auto.AutoInfuserEvolvingRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.auto.AutoInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.auto.AutoMagicInfusionRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.InfuserEvolvingRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.MagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.MagicInfusionRecipe;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class MagiculaInfuserCapability implements IMagiculaInfuserCapability {

    @Getter @Setter private int magicMaterialAmount;
    @Getter @Setter private int maxMagicMaterialAmount;
    @Getter @Setter private int additionalMagicMaterialAmount;
    @Getter @Setter private int moltenAmount;
    @Getter @Setter private int maxMoltenAmount;
    @Getter @Setter private Optional<ResourceLocation> leftBarId;
    @Getter @Setter private Optional<ResourceLocation> rightBarId;
    @Getter @Setter private Optional<ResourceLocation> infusionBarId;
    @Getter @Setter private int infusionProgress;
    @Getter @Setter private int meltingProgress;
    @Getter @Setter private int fuelTime;
    @Getter @Setter private int maxFuelTime;
    @Getter @Setter private int infusionTime;
    @Getter @Setter private int maxInfusionTime;
    @Getter @Setter private int lastMagiculesAmount;
    @Getter @Setter private ItemStack lastInputStack;
    @Getter @Setter private ItemStack lastFuelStack;
    @Getter @Setter private ItemStack lastCatalystStack;
    @Getter @Setter private MagicInfuserMeltingRecipe lastMeltingRecipe;
    @Getter @Setter private MagicInfusionRecipe lastInfusionRecipe;
    @Getter @Setter private InfuserEvolvingRecipe lastEvolvingRecipe;
    @Getter @Setter private AutoInfuserMeltingRecipe lastAutoMeltingRecipe;
    @Getter @Setter private AutoMagicInfusionRecipe lastAutoInfusionRecipe;
    @Getter @Setter private AutoInfuserEvolvingRecipe lastAutoEvolvingRecipe;
    @Getter @Setter private boolean canHopperInfusing;
    @Getter @Setter private int boostDuration;
    @Getter @Setter private double speedModifier;
    @Getter @Setter private int state;
    @Getter @Setter private boolean auto;

    public void increseMagicAmount(int amount){
        magicMaterialAmount+=amount;
    }

    public void increseMoltenAmount(int amount){
        moltenAmount+=amount;
    }

    public void decreseMagicAmount(int amount){
        magicMaterialAmount-=amount;
    }

    public void decreseMoltenAmount(int amount){
        moltenAmount-=amount;
    }

    public void decreseFuelTime(){
        fuelTime--;
    }

    public void decreseInfusionTime(){
        infusionTime--;
    }

    public void increseFuelProgress(){
        meltingProgress++;
    }

    public void increseInfusionProgress(){
        infusionProgress++;
    }

    public MagiculaInfuserCapability(){
        magicMaterialAmount=0;
        maxMagicMaterialAmount=35000;
        additionalMagicMaterialAmount=0;
        moltenAmount=0;
        maxMoltenAmount=35000;
    }

}

