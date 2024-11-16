package com.github.skillfi.reincarnation_plus.entity.client;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.entity.OgreEntity;
import com.github.skillfi.reincarnation_plus.entity.variant.OgreVariant;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OgreModel<T extends OgreEntity> extends AnimatedGeoModel<T> {
    @Override
    public ResourceLocation getModelResource(T enity) {
        return (ResourceLocation) OgreVariant.Gender.getModelLocation(enity);
    }

    @Override
    public ResourceLocation getTextureResource(T ogreEntity) {
        return (ResourceLocation) OgreVariant.Skin.getTextureLocation(ogreEntity);
    }

    @Override
    public ResourceLocation getAnimationResource(T ogreEntity) {
        return new ResourceLocation(RPMod.MODID, "animations/ogre.animation.json");
    }
}
