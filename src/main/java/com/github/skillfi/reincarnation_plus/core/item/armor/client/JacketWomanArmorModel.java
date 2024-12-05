package com.github.skillfi.reincarnation_plus.core.item.armor.client;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.armor.JacketWomanArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JacketWomanArmorModel extends AnimatedGeoModel<JacketWomanArmorItem> {
    public JacketWomanArmorModel(){}
    public ResourceLocation getModelResource(JacketWomanArmorItem jacketWomanArmorItem) {
        return new ResourceLocation(ReiMod.MODID, "geo/jacket_woman_armor.geo.json");
    }

    public ResourceLocation getTextureResource(JacketWomanArmorItem jacketWomanArmorItem) {
        return new ResourceLocation(ReiMod.MODID, "textures/models/armor/jacket_woman_armor_texture.png");
    }

    public ResourceLocation getAnimationResource(JacketWomanArmorItem jacketWomanArmorItem) {
        return new ResourceLocation(ReiMod.MODID, "animations/armor.animation.json");
    }
}
