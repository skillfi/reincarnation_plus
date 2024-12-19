package com.github.skillfi.reincarnation_plus.core.utils;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.data.pack.MagicInfuserMoltenMaterial;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

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
        bufferbuilder.vertex(pMatrix, (float)renderX + 13.0F, (float)renderY + (float)height, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(1.0F, v).endVertex();
        bufferbuilder.vertex(pMatrix, (float)renderX + 13.0F, (float)renderY, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(pMatrix, (float)renderX, (float)renderY, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(0.0F, 0.0F).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
    }

    public static MutableComponent toolTipFromMoltenMaterial(MagicInfuserMoltenMaterial moltenMaterial, float amount, float maxAmount) {
        MutableComponent moltenMaterialName = Component.translatable(String.format("%s.molten.%s.material", moltenMaterial.getMoltenType().getNamespace(), moltenMaterial.getMoltenType().getPath()));
        int textColor = (new Color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue())).getRGB();
        String moltenAmount = amount + "/" + maxAmount;
        return Component.translatable("tooltip.reincarnation_plus.magicula_infuser.molten_item", new Object[]{moltenAmount, moltenMaterialName}).withStyle(Style.EMPTY.withColor(textColor));
    }

    public static MutableComponent toolTipFromMoltenMaterial(MagicInfuserMoltenMaterial moltenMaterial, int amount, int maxAmount) {
        MutableComponent moltenMaterialName = Component.translatable(String.format("%s.molten.%s.material", moltenMaterial.getMoltenType().getNamespace(), moltenMaterial.getMoltenType().getPath()));
        int textColor = (new Color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue())).getRGB();
        String moltenAmount = amount + "/" + maxAmount;
        return Component.translatable("tooltip.reincarnation_plus.magicula_infuser.molten_item", new Object[]{moltenAmount, moltenMaterialName}).withStyle(Style.EMPTY.withColor(textColor));
    }

    public static void randomEnchantments(Ingredient stack) {
        if (stack.isEmpty()) {
            return; // Перевіряємо, чи предмет існує
        }
        ItemStack itemStack = stack.getItems()[0];
        RandomSource random = RandomSource.create();

        // Визначаємо кількість зачарувань (від 1 до 3)
        int enchantmentCount = random.nextInt(3) + 1;

        // Отримуємо список усіх доступних зачарувань
        ArrayList<Enchantment> allEnchantments = new ArrayList<>();
        for (Enchantment enchantment : EnchantmentHelper.getEnchantments(itemStack).keySet()) {
            if (enchantment.canEnchant(itemStack)) { // Перевіряємо, чи може зачарування застосовуватись до предмета
                allEnchantments.add(enchantment);
            }
        }

        if (allEnchantments.isEmpty()) {
            return; // Якщо доступних зачарувань немає, завершуємо
        }

        // Створюємо мапу зачарувань для предмета
        Map<Enchantment, Integer> enchantmentsToApply = EnchantmentHelper.getEnchantments(itemStack);

        for (int i = 0; i < enchantmentCount; i++) {
            // Вибираємо випадкове зачарування
            Enchantment randomEnchantment = allEnchantments.get(random.nextInt(allEnchantments.size()));

            // Випадковий рівень зачарування (від 1 до максимального рівня)
            int level = random.nextInt(randomEnchantment.getMaxLevel()) + 1;

            // Додаємо зачарування до списку
            enchantmentsToApply.put(randomEnchantment, level);
        }

        // Застосовуємо зачарування до предмета
        EnchantmentHelper.setEnchantments(enchantmentsToApply, itemStack);




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
