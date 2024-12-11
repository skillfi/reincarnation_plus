package com.github.skillfi.reincarnation_plus.core.item.client;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.InfusionBellowsItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class InfusionBellowsItemModel extends AnimatedGeoModel<InfusionBellowsItem> {
    @Override
    public ResourceLocation getModelResource(InfusionBellowsItem infusionBellowsItem) {
        return new ResourceLocation(ReiMod.MODID, "geo/infusion_bellows.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(InfusionBellowsItem infusionBellowsItem) {
        return new ResourceLocation(ReiMod.MODID, "textures/blocks/infusion_bellows.png");
    }

    @Override
    public ResourceLocation getAnimationResource(InfusionBellowsItem infusionBellowsItem) {
        return new ResourceLocation(ReiMod.MODID, "animations/infusion_bellows.animation.json");
    }
}
