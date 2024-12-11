package com.github.skillfi.reincarnation_plus.core.item.client.automatic_infuser;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.AutomaticMagiculaInfuserItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AutomaticMagiculaInfuserItemModel extends AnimatedGeoModel<AutomaticMagiculaInfuserItem> {
    @Override
    public ResourceLocation getModelResource(AutomaticMagiculaInfuserItem magicInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "geo/automatic_infuser_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AutomaticMagiculaInfuserItem magicInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "textures/blocks/automatic_infuser.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AutomaticMagiculaInfuserItem magicInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "animations/automatic_infuser_block.animation.json");
    }
}
