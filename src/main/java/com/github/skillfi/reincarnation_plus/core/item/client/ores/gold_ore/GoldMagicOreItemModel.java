package com.github.skillfi.reincarnation_plus.core.item.client.ores.gold_ore;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.CooperMagicOreItem;
import com.github.skillfi.reincarnation_plus.core.item.GoldMagicOreItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GoldMagicOreItemModel extends AnimatedGeoModel<GoldMagicOreItem> {
    @Override
    public ResourceLocation getModelResource(GoldMagicOreItem MagicOreItem) {
        return new ResourceLocation(ReiMod.MODID, "geo/magic_ore.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GoldMagicOreItem MagicOreItem) {
        return new ResourceLocation(ReiMod.MODID, "textures/blocks/gold_ore/age_0.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GoldMagicOreItem MagicOreItem) {
        return new ResourceLocation(ReiMod.MODID, "animations/magic_ore.animation.json");
    }
}
