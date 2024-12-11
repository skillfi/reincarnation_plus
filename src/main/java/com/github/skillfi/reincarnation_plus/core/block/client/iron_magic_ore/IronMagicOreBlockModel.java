package com.github.skillfi.reincarnation_plus.core.block.client.iron_magic_ore;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.IronOreBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.variant.MagicOreVariant;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IronMagicOreBlockModel extends AnimatedGeoModel<IronOreBlockEntity> {
    @Override
    public ResourceLocation getModelResource(IronOreBlockEntity magiculaInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "geo/magic_ore.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IronOreBlockEntity magiculaInfuserBlockEntity) {
        return MagicOreVariant.OreAge.getTextureLocation(magiculaInfuserBlockEntity);
    }

    @Override
    public ResourceLocation getAnimationResource(IronOreBlockEntity magiculaInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "animations/magic_ore.animation.json");
    }
}
