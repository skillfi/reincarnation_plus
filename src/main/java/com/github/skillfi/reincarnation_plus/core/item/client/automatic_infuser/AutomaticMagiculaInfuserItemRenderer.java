package com.github.skillfi.reincarnation_plus.core.item.client.automatic_infuser;

import com.github.skillfi.reincarnation_plus.core.item.AutomaticMagiculaInfuserItem;
import com.github.skillfi.reincarnation_plus.core.item.MagiculaInfuserItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AutomaticMagiculaInfuserItemRenderer extends GeoItemRenderer<AutomaticMagiculaInfuserItem> {
    public AutomaticMagiculaInfuserItemRenderer() {
        super(new AutomaticMagiculaInfuserItemModel());
    }

    @Override
    public RenderType getRenderType(AutomaticMagiculaInfuserItem animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
