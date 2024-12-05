package com.github.skillfi.reincarnation_plus.core.entity.client.layers;

import com.github.skillfi.reincarnation_plus.core.entity.OgreEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class SkinLayer<T extends OgreEntity> extends GeoLayerRenderer<T> {


    public SkinLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T t, float v, float v1, float v2, float v3, float v4, float v5) {
        RenderType renderType = RenderType.entityCutout(t.getSkin().getTextureLocation(t));
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
        this.getRenderer().render(
                this.getEntityModel().getModel(this.getEntityModel().getModelResource(t)),
                t, v, renderType, poseStack, multiBufferSource, vertexConsumer,
                i, OverlayTexture.NO_OVERLAY, v2, v3, v4, v5
        );
    }
}
