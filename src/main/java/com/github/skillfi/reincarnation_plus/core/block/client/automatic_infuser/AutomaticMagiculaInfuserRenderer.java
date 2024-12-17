package com.github.skillfi.reincarnation_plus.core.block.client.automatic_infuser;

import com.github.skillfi.reincarnation_plus.core.block.AutomaticMagiculaInfuserBlock;
import com.github.skillfi.reincarnation_plus.core.block.MagiculaInfuserBlock;
import com.github.skillfi.reincarnation_plus.core.block.entity.AutomaticMagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class AutomaticMagiculaInfuserRenderer extends GeoBlockRenderer<AutomaticMagiculaInfuserBlockEntity> {
    public AutomaticMagiculaInfuserRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new AutomaticMagiculaInfuserBlockModel());
    }

    @Override
    public RenderType getRenderType(AutomaticMagiculaInfuserBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    public void renderEarly(AutomaticMagiculaInfuserBlockEntity animatable, PoseStack poseStack, float partialTick, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        // Знаходимо кістку Infusion
        IBone infusion = this.getGeoModelProvider().getBone("Infusion");
        IBone fire = this.getGeoModelProvider().getBone("Fire");
        if (animatable.getBlockState().getValue(AutomaticMagiculaInfuserBlock.INFUSION)){
            infusion.setHidden(false);
            fire.setHidden(true);
            // Отримуємо трансформації кістки
            poseStack.pushPose();
            poseStack.scale(0.5f, 0.5f, 0.5f); // Масштабування до 1/4 (4 рази менше)
            // Переміщення вгору по осі Y на 0.5 блоків
            poseStack.translate(0, 0.7, -0.5);

            // Додаємо ваш рендеринг ItemStack
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            ItemStack itemStack = animatable.getRenderStack(); // Перевірте, щоб `getRenderStack()` був реалізований у вашій `MagicInfuserBlockEntity`

            if (!itemStack.isEmpty()) {
                itemRenderer.renderStatic(
                        itemStack,
                        ItemTransforms.TransformType.GUI,
                        getLightLevel(animatable.getLevel(), animatable.getBlockPos()),
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        bufferSource,
                        1 // RenderLayer
                );
            }
            poseStack.popPose();
        }
        if (animatable.getBlockState().getValue(AutomaticMagiculaInfuserBlock.LIT)){
            fire.setHidden(false);
            infusion.setHidden(true);
        }
        infusion.setHidden(true);
        fire.setHidden(true);
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
