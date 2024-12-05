package com.github.skillfi.reincarnation_plus.mixins;

import com.github.manasmods.tensura.handler.client.HUDHandler;
import com.github.skillfi.reincarnation_plus.libs.api.aura.AuraAPI;
import com.github.skillfi.reincarnation_plus.core.block.entity.EPCoreTileEntity;
import com.github.skillfi.reincarnation_plus.libs.capability.magicule.EPCapability;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(HUDHandler.class)
public class MixinHUDHandler {
    @Shadow(remap = false)
    private static Font font;
    @Shadow(remap = false)
    private static PoseStack poseStack;
    @Shadow(remap = false)
    private static int appraisalPosX;
    @Shadow(remap = false)
    private static int screenY;

    /**
     * Вставка коду після блоку {@code if (age instanceof CharybdisCoreBlockEntity core) {...}}.
     */
    @Inject(method = "renderBlockAnalysis",
            at = @At(value = "TAIL"), remap = false)
    private static void renderBlockAnalysis(BlockState blockState, BlockPos blockPos, Level level, CallbackInfo ci){
        int left = appraisalPosX;
        int top = screenY / 4;
        AtomicInteger textY = new AtomicInteger(top + 110);
        Block block = blockState.getBlock();
        AtomicReference<String> text = new AtomicReference<>(block.getName().getString());
        BlockEntity age = level.getBlockEntity(blockPos);
        // Отримання значення Aura
        double auraValue = AuraAPI.getAura(level, blockPos);

        // Округлення значення до 1000
        double roundedAuraValue = Math.round(auraValue / 1000.0);
        if (age instanceof EPCoreTileEntity core) {
            // Отримання кількості Magicules через Optional
            core.getCapability(EPCapability.CAPABILITY).ifPresent(storage -> {
                double EP = storage.getMagiculesStored();
                double MaxEP = storage.getMaxMagiculesStored();

                // Рендер інформації про Magicules
                text.set(Component.translatable("reincarantion_plus.attribute.ep_storage").getString());
                font.draw(poseStack, text.get(), (float) (left + 7), (float) textY.get(), Color.ORANGE.getRGB());
                textY.addAndGet(10);
                text.set("-> " + EP);
                font.draw(poseStack, text.get(), (float) (left + 7), (float) textY.get(), Color.WHITE.getRGB());
                textY.addAndGet(10);
                text.set(Component.translatable("reincarantion_plus.attribute.max_ep").getString());
                font.draw(poseStack, text.get(), (float) (left + 7), (float) textY.get(), Color.ORANGE.getRGB());
                textY.addAndGet(10);
                text.set("-> " + MaxEP);
                font.draw(poseStack, text.get(), (float) (left + 7), (float) textY.get(), Color.WHITE.getRGB());
            });
        }
        text.set(Component.translatable("reincarantion_plus.attribute.in_chunk").getString());
        font.draw(poseStack, text.get(), (float) (left + 7), (float) textY.get(), Color.ORANGE.getRGB());
        textY.addAndGet(10);
        text.set("-> " + roundedAuraValue + "K");
        font.draw(poseStack, text.get(), (float) (left + 7), (float) textY.get(), Color.WHITE.getRGB());
    }
}
