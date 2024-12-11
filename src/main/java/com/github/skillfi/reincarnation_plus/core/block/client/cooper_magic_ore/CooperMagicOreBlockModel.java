package com.github.skillfi.reincarnation_plus.core.block.client.cooper_magic_ore;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.CooperOreBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.GoldOreBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.variant.MagicOreVariant;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CooperMagicOreBlockModel extends AnimatedGeoModel<CooperOreBlockEntity> {
    @Override
    public ResourceLocation getModelResource(CooperOreBlockEntity magiculaInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "geo/magic_ore.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CooperOreBlockEntity magiculaInfuserBlockEntity) {
        return MagicOreVariant.OreAge.getTextureLocation(magiculaInfuserBlockEntity);
    }

    @Override
    public ResourceLocation getAnimationResource(CooperOreBlockEntity magiculaInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "animations/magic_ore.animation.json");
    }
}
