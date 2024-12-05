package com.github.skillfi.reincarnation_plus.core.block.client.magicule_block;

import com.github.skillfi.reincarnation_plus.core.block.entity.EPCoreTileEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class MagiculeCoreRenderer extends GeoBlockRenderer<EPCoreTileEntity> {
	public MagiculeCoreRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
		super(rendererDispatcherIn, new MagiculeCoreBlockModel());
	}

	@Override
	public RenderType getRenderType(EPCoreTileEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
