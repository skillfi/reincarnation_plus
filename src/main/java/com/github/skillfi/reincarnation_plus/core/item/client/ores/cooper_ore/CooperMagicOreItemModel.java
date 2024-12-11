package com.github.skillfi.reincarnation_plus.core.item.client.ores.cooper_ore;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.CooperMagicOreItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CooperMagicOreItemModel extends AnimatedGeoModel<CooperMagicOreItem> {
    @Override
    public ResourceLocation getModelResource(CooperMagicOreItem cooperMagicOreItem) {
        return new ResourceLocation(ReiMod.MODID, "geo/magic_ore.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CooperMagicOreItem cooperMagicOreItem) {
        return new ResourceLocation(ReiMod.MODID, "textures/blocks/cooper_ore/age_0.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CooperMagicOreItem cooperMagicOreItem) {
        return new ResourceLocation(ReiMod.MODID, "animations/magic_ore.animation.json");
    }
}
