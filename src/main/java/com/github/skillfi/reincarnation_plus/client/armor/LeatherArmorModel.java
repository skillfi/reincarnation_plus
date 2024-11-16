package com.github.skillfi.reincarnation_plus.client.armor;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.item.custom.LeatherArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LeatherArmorModel extends AnimatedGeoModel<LeatherArmorItem> {
    @Override
    public ResourceLocation getModelResource(LeatherArmorItem leatherArmorItem) {
        return new ResourceLocation(RPMod.MODID, "geo/leather_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LeatherArmorItem leatherArmorItem) {
        return new ResourceLocation(RPMod.MODID, "textures/models/armor/leather_armor_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LeatherArmorItem leatherArmorItem) {
        return new ResourceLocation(RPMod.MODID, "animations/armor_animation.json");
    }
}
