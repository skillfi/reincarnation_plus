package com.github.skillfi.reincarnation_plus.core.block.client.magicule_block;

import net.minecraft.resources.ResourceLocation;

import com.github.skillfi.reincarnation_plus.core.block.entity.EPCoreTileEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MagiculeCoreBlockModel extends AnimatedGeoModel<EPCoreTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(EPCoreTileEntity animatable) {
		return new ResourceLocation("reincarnation_plus", "animations/magicule_core.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(EPCoreTileEntity animatable) {
		return new ResourceLocation("reincarnation_plus", "geo/magicule_core.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(EPCoreTileEntity entity) {
		return entity.getTexture();
	}
}
