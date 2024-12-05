package com.github.skillfi.reincarnation_plus.core.item.armor.client;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.armor.KimonoWomanArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class KimonoWomanArmorModel extends AnimatedGeoModel<KimonoWomanArmorItem> {
    public KimonoWomanArmorModel(){}

    public ResourceLocation getModelResource(KimonoWomanArmorItem kimonoWomanArmorItem) {
        return new ResourceLocation(ReiMod.MODID, "geo/kimono_woman_armor.geo.json");
    }


    public ResourceLocation getTextureResource(KimonoWomanArmorItem kimonoWomanArmorItem) {
        return new ResourceLocation(ReiMod.MODID, "textures/models/armor/kimono_woman_armor_texture.png");
    }


    public ResourceLocation getAnimationResource(KimonoWomanArmorItem kimonoWomanArmorItem) {
        return new ResourceLocation(ReiMod.MODID, "animations/armor.animation.json");
    }
}
