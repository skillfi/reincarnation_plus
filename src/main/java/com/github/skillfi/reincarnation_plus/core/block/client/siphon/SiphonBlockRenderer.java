package com.github.skillfi.reincarnation_plus.core.block.client.siphon;

import com.github.skillfi.reincarnation_plus.core.block.entity.SiphonBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class SiphonBlockRenderer extends GeoBlockRenderer<SiphonBlockEntity> {
    public SiphonBlockRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(rendererProvider, new SiphonBlockModel());
    }

    @Override
    public RenderType getRenderType(SiphonBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
