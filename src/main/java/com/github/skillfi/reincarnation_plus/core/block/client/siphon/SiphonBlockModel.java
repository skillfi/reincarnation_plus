package com.github.skillfi.reincarnation_plus.core.block.client.siphon;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.SiphonBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SiphonBlockModel extends AnimatedGeoModel<SiphonBlockEntity> {
    @Override
    public ResourceLocation getModelResource(SiphonBlockEntity siphonBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "geo/siphon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SiphonBlockEntity siphonBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "textures/blocks/siphon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SiphonBlockEntity siphonBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "animations/siphon.animation.json");
    }
}
