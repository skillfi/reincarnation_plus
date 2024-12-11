package com.github.skillfi.reincarnation_plus.core.item.client;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.variant.MagiculaInfuser;
import com.github.skillfi.reincarnation_plus.core.item.MagiculaInfuserItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MagiculaInfuserItemModel extends AnimatedGeoModel<MagiculaInfuserItem> {
    @Override
    public ResourceLocation getModelResource(MagiculaInfuserItem magicInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "geo/magicula_infuser.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MagiculaInfuserItem magicInfuserBlockEntity) {
        return MagiculaInfuser.getTextureLocation(0);
    }

    @Override
    public ResourceLocation getAnimationResource(MagiculaInfuserItem magicInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "animations/magicule_infuser.animation.json");
    }
}
