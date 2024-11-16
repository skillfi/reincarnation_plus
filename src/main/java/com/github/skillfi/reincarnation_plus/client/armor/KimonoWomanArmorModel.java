package com.github.skillfi.reincarnation_plus.client.armor;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.item.custom.KimonoWomanArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class KimonoWomanArmorModel extends AnimatedGeoModel<KimonoWomanArmorItem> {
    @Override
    public ResourceLocation getModelResource(KimonoWomanArmorItem ogre1ArmorItem) {
        return new ResourceLocation(RPMod.MODID, "geo/kimono_woman_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KimonoWomanArmorItem ogre1ArmorItem) {
        return new ResourceLocation(RPMod.MODID, "textures/models/armor/kimono_woman_armor_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KimonoWomanArmorItem ogre1ArmorItem) {
        return new ResourceLocation(RPMod.MODID, "animations/armor_animation.json");
    }
}
