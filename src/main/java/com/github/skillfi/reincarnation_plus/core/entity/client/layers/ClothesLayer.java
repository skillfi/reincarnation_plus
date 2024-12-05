package com.github.skillfi.reincarnation_plus.core.entity.client.layers;

import com.github.skillfi.reincarnation_plus.core.entity.OgreEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class ClothesLayer<T extends OgreEntity> extends GeoLayerRenderer<T> {

    public ClothesLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ResourceLocation texture = entity.getClothing().getTextureLocation(entity);
        RenderType renderType = RenderType.entityCutout(texture);

        // Переконайтеся, що RenderType правильно працює
        if (renderType == null) {
            return;
        }

        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);

        // Рендеримо модель із врахуванням всіх параметрів
        this.getRenderer().render(
                this.getEntityModel().getModel(this.getEntityModel().getModelResource(entity)),
                entity, partialTicks, renderType, poseStack, multiBufferSource, vertexConsumer,
                light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F
        );
    }
}
