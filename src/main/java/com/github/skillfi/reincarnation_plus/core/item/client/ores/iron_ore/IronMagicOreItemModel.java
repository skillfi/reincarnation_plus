package com.github.skillfi.reincarnation_plus.core.item.client.ores.iron_ore;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.GoldMagicOreItem;
import com.github.skillfi.reincarnation_plus.core.item.IronMagicOreItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IronMagicOreItemModel extends AnimatedGeoModel<IronMagicOreItem> {
    @Override
    public ResourceLocation getModelResource(IronMagicOreItem MagicOreItem) {
        return new ResourceLocation(ReiMod.MODID, "geo/magic_ore.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IronMagicOreItem MagicOreItem) {
        return new ResourceLocation(ReiMod.MODID, "textures/blocks/iron_ore/age_0.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IronMagicOreItem MagicOreItem) {
        return new ResourceLocation(ReiMod.MODID, "animations/magic_ore.animation.json");
    }
}
