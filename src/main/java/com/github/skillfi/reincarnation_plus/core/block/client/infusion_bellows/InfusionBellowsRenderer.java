package com.github.skillfi.reincarnation_plus.core.block.client.infusion_bellows;

import com.github.skillfi.reincarnation_plus.core.block.entity.InfusionBellowsBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class InfusionBellowsRenderer extends GeoBlockRenderer<InfusionBellowsBlockEntity> {
    public InfusionBellowsRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new InfusionBellowsBlockModel());
    }

    @Override
    public RenderType getRenderType(InfusionBellowsBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
