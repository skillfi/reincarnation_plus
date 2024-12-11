package com.github.skillfi.reincarnation_plus.core.block.client.automatic_infuser;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.AutomaticMagiculaInfuserBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AutomaticMagiculaInfuserBlockModel extends AnimatedGeoModel<AutomaticMagiculaInfuserBlockEntity> {
    @Override
    public ResourceLocation getModelResource(AutomaticMagiculaInfuserBlockEntity magiculaInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "geo/automatic_infuser_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AutomaticMagiculaInfuserBlockEntity magiculaInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "textures/blocks/automatic_infuser.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AutomaticMagiculaInfuserBlockEntity magiculaInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "animations/automatic_infuser_block.animation.json");
    }
}
