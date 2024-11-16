package com.github.skillfi.reincarnation_plus.client.armor;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.item.custom.JacketWomanArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JacketWomanArmorModel extends AnimatedGeoModel<JacketWomanArmorItem> {
    @Override
    public ResourceLocation getModelResource(JacketWomanArmorItem ogre1ArmorItem) {
        return new ResourceLocation(RPMod.MODID, "geo/jacket_woman_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(JacketWomanArmorItem ogre1ArmorItem) {
        return new ResourceLocation(RPMod.MODID, "textures/models/armor/jacket_woman_armor_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(JacketWomanArmorItem ogre1ArmorItem) {
        return new ResourceLocation(RPMod.MODID, "animations/armor_animation.json");
    }
}
