package com.github.skillfi.reincarnation_plus.core.block.client.infusion_bellows;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.InfusionBellowsBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class InfusionBellowsBlockModel extends AnimatedGeoModel<InfusionBellowsBlockEntity> {
    @Override
    public ResourceLocation getModelResource(InfusionBellowsBlockEntity magicInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "geo/infusion_bellows.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(InfusionBellowsBlockEntity magicInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "textures/blocks/infusion_bellows.png");
    }

    @Override
    public ResourceLocation getAnimationResource(InfusionBellowsBlockEntity magicInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "animations/infusion_bellows.animation.json");
    }
}
