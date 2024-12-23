package com.github.skillfi.reincarnation_plus.core.item.client.siphon;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.SiphonItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SiphonItemModel extends AnimatedGeoModel<SiphonItem> {
    @Override
    public ResourceLocation getModelResource(SiphonItem siphonItem) {
        return new ResourceLocation(ReiMod.MODID, "geo/siphon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SiphonItem siphonItem) {
        return new ResourceLocation(ReiMod.MODID, "textures/blocks/siphon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SiphonItem siphonItem) {
        return new ResourceLocation(ReiMod.MODID, "animations/siphon.animation.json");
    }
}
