package com.github.skillfi.reincarnation_plus.core.item.client;

import com.github.skillfi.reincarnation_plus.core.item.MagiculaInfuserItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MagiculaInfuserItemRenderer extends GeoItemRenderer<MagiculaInfuserItem> {
    public MagiculaInfuserItemRenderer() {
        super(new MagiculaInfuserItemModel());
    }

    @Override
    public RenderType getRenderType(MagiculaInfuserItem animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
