package com.github.skillfi.reincarnation_plus.core.item.client.ores.cooper_ore;

import com.github.skillfi.reincarnation_plus.core.item.CooperMagicOreItem;
import com.github.skillfi.reincarnation_plus.core.item.InfusionBellowsItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class CooperMagicOreItemRenderer extends GeoItemRenderer<CooperMagicOreItem> {
    public CooperMagicOreItemRenderer() {
        super(new CooperMagicOreItemModel());
    }

    @Override
    public RenderType getRenderType(CooperMagicOreItem animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
