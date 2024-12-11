package com.github.skillfi.reincarnation_plus.core.block.client.magic_infuser;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.MagiculaInfuserBlock;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.variant.MagiculaInfuser;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MagiculaInfuserBlockModel extends AnimatedGeoModel<MagiculaInfuserBlockEntity> {
    @Override
    public ResourceLocation getModelResource(MagiculaInfuserBlockEntity magiculaInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "geo/magicula_infuser.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MagiculaInfuserBlockEntity magiculaInfuserBlockEntity) {
        return MagiculaInfuser.getTextureLocation(magiculaInfuserBlockEntity);
    }

    @Override
    public ResourceLocation getAnimationResource(MagiculaInfuserBlockEntity magiculaInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "animations/magicule_infuser.animation.json");
    }
}
