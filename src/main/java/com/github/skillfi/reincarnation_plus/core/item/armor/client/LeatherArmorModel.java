package com.github.skillfi.reincarnation_plus.core.item.armor.client;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.armor.LeatherArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LeatherArmorModel extends AnimatedGeoModel<LeatherArmorItem> {
    public LeatherArmorModel(){}

    public ResourceLocation getModelResource(LeatherArmorItem leatherArmorItem) {
        return new ResourceLocation(ReiMod.MODID, "geo/leather_armor.geo.json");
    }


    public ResourceLocation getTextureResource(LeatherArmorItem leatherArmorItem) {
        return new ResourceLocation(ReiMod.MODID, "textures/models/armor/leather_armor_texture.png");
    }


    public ResourceLocation getAnimationResource(LeatherArmorItem leatherArmorItem) {
        return new ResourceLocation(ReiMod.MODID, "animations/armor.animation.json");
    }
}
