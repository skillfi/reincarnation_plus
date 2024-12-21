package com.github.skillfi.reincarnation_plus.core.utils;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.data.pack.MagicInfuserMoltenMaterial;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class RenderUtils {

    private static final ResourceLocation FLUID = new ResourceLocation(ReiMod.MODID, "textures/gui/magic_infuser/molten.png");

    public static void renderMoltenMaterial(PoseStack stack, MagicInfuserMoltenMaterial moltenMaterial, int progress, int maxProgress) {
        int width = 13;
        int height = progress * 74 / maxProgress;
        if (height < 1) {
            height = 1;
        }

        int renderX = moltenMaterial.isRightBar() ? 145 : 18;
        int renderY = 80 - height;
        float u = 1.0F;
        float v = 0.013513514F * (float)height;
        Matrix4f pMatrix = stack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, FLUID);
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        bufferbuilder.vertex(pMatrix, (float)renderX, (float)renderY + (float)height, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(0.0F, v).endVertex();
        bufferbuilder.vertex(pMatrix, (float)renderX + width, (float)renderY + (float)height, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(u, v).endVertex();
        bufferbuilder.vertex(pMatrix, (float)renderX + width, (float)renderY, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(u, 0.0F).endVertex();
        bufferbuilder.vertex(pMatrix, (float)renderX, (float)renderY, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(0.0F, 0.0F).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
    }

    public static MutableComponent toolTipFromMoltenMaterial(MagicInfuserMoltenMaterial moltenMaterial, int amount, int maxAmount) {
        MutableComponent moltenMaterialName = Component.translatable(String.format("%s.molten.%s.material", moltenMaterial.getMoltenType().getNamespace(), moltenMaterial.getMoltenType().getPath()));
        int textColor = (new Color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue())).getRGB();
        String moltenAmount = amount + "/" + maxAmount;
        return Component.translatable("tooltip.reincarnation_plus.magicula_infuser.molten_item", new Object[]{moltenAmount, moltenMaterialName}).withStyle(Style.EMPTY.withColor(textColor));
    }

    public static MutableComponent toolTipInfusionTime(MagicInfuserMoltenMaterial moltenMaterial, int amount) {
        int textColor = (new Color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue())).getRGB();
        String moltenAmount = String.valueOf(amount);
        return Component.translatable("reincarnation_plus.infusion.time", new Object[]{moltenAmount}).withStyle(Style.EMPTY.withColor(textColor));
    }

    private RenderUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
