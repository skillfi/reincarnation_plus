package com.github.skillfi.reincarnation_plus.core.item.client.ores.gold_ore;

import com.github.skillfi.reincarnation_plus.core.item.GoldMagicOreItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GoldMagicOreItemRenderer extends GeoItemRenderer<GoldMagicOreItem> {
    public GoldMagicOreItemRenderer() {
        super(new GoldMagicOreItemModel());
    }

    @Override
    public RenderType getRenderType(GoldMagicOreItem animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
