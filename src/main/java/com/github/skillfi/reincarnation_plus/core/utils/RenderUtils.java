package com.github.skillfi.reincarnation_plus.core.utils;

import com.github.manasmods.tensura.data.pack.KilnMoltenMaterial;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.menu.MagicInfuserMenu;
import com.github.skillfi.reincarnation_plus.libs.data.pack.MagicInfuserMoltenMaterial;
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

    public static void renderMoltenMaterial(PoseStack stack, MagicInfuserMoltenMaterial moltenMaterial, float progress, float maxProgress) {
        int width = 13;
        int height = (int) progress * 74 /  (int) maxProgress;
        if (height < 1) {
            height = 1;
        }

        int renderX = 18;
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
        bufferbuilder.vertex(pMatrix, (float)renderX + 13.0F, (float)renderY + (float)height, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(1.0F, v).endVertex();
        bufferbuilder.vertex(pMatrix, (float)renderX + 13.0F, (float)renderY, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(pMatrix, (float)renderX, (float)renderY, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(0.0F, 0.0F).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
    }

    public static void renderInfusion(PoseStack stack, MagicInfuserMoltenMaterial moltenMaterial, int progress, int maxProgress) {
        int width = 13; // Встановлює ширину смуги відображення
        int height = progress * 74 / maxProgress;
        if (height < 1) {
            height = 1;
        }

        int renderX = 145; // Встановлює координату X для відображення
        int renderY = 80 - height; // Встановлює координату Y, беручи до уваги висоту прогресу
        float u = 0.07692308F * (float)width; // Розраховує u координату текстури
        float v = 0.013513514F * (float)height; // Розраховує v координату текстури
        Matrix4f pMatrix = stack.last().pose(); // Отримує останню матрицю з позиційного стека

        // Підготовка до малювання об'єкту
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend(); // Увімкнення режиму змішування кольорів
        RenderSystem.defaultBlendFunc(); // Встановлення функції змішування кольорів за замовчуванням
        RenderSystem.setShaderTexture(0, FLUID); // Вибір текстури для відображення
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader); // Установка шейдера для текстури

        // Початок складання буфера
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);

        // Додає вершини для відображення квадрата із відповідним кольором і текстурною координатою
        bufferbuilder.vertex(pMatrix, (float)renderX, (float)renderY + (float)height, 0.0F)
                .color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha())
                .uv(0.0F, v).endVertex();

        bufferbuilder.vertex(pMatrix, (float)renderX + (float)width, (float)renderY + (float)height, 0.0F)
                .color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha())
                .uv(u, v).endVertex();

        bufferbuilder.vertex(pMatrix, (float)renderX + (float)width, (float)renderY, 0.0F)
                .color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha())
                .uv(u, 0.0F).endVertex();

        bufferbuilder.vertex(pMatrix, (float)renderX, (float)renderY, 0.0F)
                .color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha())
                .uv(0.0F, 0.0F).endVertex();

        // Відображення складаного буфера
        BufferUploader.drawWithShader(bufferbuilder.end());

        RenderSystem.disableBlend(); // Вимкнення режиму змішування кольорів
    }

    public static MutableComponent toolTipFromMoltenMaterial(MagicInfuserMoltenMaterial moltenMaterial, float amount, float maxAmount) {
        MutableComponent moltenMaterialName = Component.translatable(String.format("%s.molten.%s.material", moltenMaterial.getMoltenType().getNamespace(), moltenMaterial.getMoltenType().getPath()));
        int textColor = (new Color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue())).getRGB();
        String moltenAmount = amount + "/" + maxAmount;
        return Component.translatable("tooltip.reincarnation_plus.magic_infuser.molten_item", new Object[]{moltenAmount, moltenMaterialName}).withStyle(Style.EMPTY.withColor(textColor));
    }
    public static MutableComponent toolTipInfusionTime(MagicInfuserMoltenMaterial moltenMaterial, int amount, int maxAmount) {
        int textColor = (new Color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue())).getRGB();
        String moltenAmount = amount + "/" + maxAmount;
        return Component.translatable("reincarnation_plus.molten.infusion.time", new Object[]{moltenAmount}).withStyle(Style.EMPTY.withColor(textColor));
    }

    private RenderUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
