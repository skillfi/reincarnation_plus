package com.github.skillfi.reincarnation_plus.core.block.client.gold_magic_ore;

import com.github.skillfi.reincarnation_plus.core.block.entity.ores.GoldOreBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class GoldMagicOreBlockRenderer extends GeoBlockRenderer<GoldOreBlockEntity> {
    public GoldMagicOreBlockRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new GoldMagicOreBlockModel());
    }

    @Override
    public RenderType getRenderType(GoldOreBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

}
