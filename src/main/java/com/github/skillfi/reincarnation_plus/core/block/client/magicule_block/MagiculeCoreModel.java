package com.github.skillfi.reincarnation_plus.core.block.client.magicule_block;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.MagiculeCoreItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class MagiculeCoreModel extends AnimatedGeoModel<MagiculeCoreItem> {
	@Override
	public ResourceLocation getAnimationResource(MagiculeCoreItem animatable) {
		return new ResourceLocation(ReiMod.MODID, "animations/magicule_core.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MagiculeCoreItem animatable) {
		return new ResourceLocation(ReiMod.MODID, "geo/magicule_core.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MagiculeCoreItem entity) {
		return entity.getTexture();
	}
}
