package com.github.skillfi.reincarnation_plus.core.block.client.gold_magic_ore;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.GoldOreBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.variant.MagicOreVariant;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GoldMagicOreBlockModel extends AnimatedGeoModel<GoldOreBlockEntity> {
    @Override
    public ResourceLocation getModelResource(GoldOreBlockEntity magiculaInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "geo/magic_ore.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GoldOreBlockEntity magiculaInfuserBlockEntity) {
        return MagicOreVariant.OreAge.getTextureLocation(magiculaInfuserBlockEntity);
    }

    @Override
    public ResourceLocation getAnimationResource(GoldOreBlockEntity magiculaInfuserBlockEntity) {
        return new ResourceLocation(ReiMod.MODID, "animations/magic_ore.animation.json");
    }
}
