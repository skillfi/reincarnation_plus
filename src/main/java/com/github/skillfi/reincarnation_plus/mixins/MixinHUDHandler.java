package com.github.skillfi.reincarnation_plus.mixins;

import com.github.manasmods.tensura.handler.client.HUDHandler;
import com.github.skillfi.reincarnation_plus.libs.api.aura.AuraAPI;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.List;
import java.util.Optional;
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

    private static int getAgeForCrop(BlockState state, boolean maxAge) {
        net.minecraft.world.level.block.Block property = state.getBlock();
        if (property instanceof CropBlock crop) {
            return maxAge ? crop.getMaxAge() : (Integer)state.getValue(crop.getAgeProperty());
        } else {
            Optional<Property<?>> age = state.getProperties().stream().filter((propertyx) -> propertyx.getName().equals("age") && propertyx instanceof IntegerProperty).findFirst();
            if (age.isEmpty()) {
                return -1;
            } else if (maxAge) {
                List<?> _property = ((Property)age.get()).getPossibleValues().stream().toList();
                return (Integer)_property.get(_property.size() - 1);
            } else {
                return (Integer)state.getValue((IntegerProperty)age.get());
            }
        }
    }

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
        int maxAge = getAgeForCrop(blockState, true);
        int age = getAgeForCrop(blockState, false);
        AtomicReference<String> text = new AtomicReference<>(block.getName().getString());
        BlockEntity entity = level.getBlockEntity(blockPos);
        // Отримання значення Aura
        double auraValue = AuraAPI.getAura(level, blockPos);

        // Округлення значення до 1000
        double roundedAuraValue = Math.round(auraValue / 1000.0);
        if (maxAge == -1 && age == -1){
            text.set(Component.translatable("reincarantion_plus.attribute.in_chunk").getString());
            font.draw(poseStack, text.get(), (float) (left + 7), (float) textY.get(), Color.ORANGE.getRGB());
            textY.addAndGet(10);
            text.set("-> " + roundedAuraValue + "K");
            font.draw(poseStack, text.get(), (float) (left + 7), (float) textY.get(), Color.WHITE.getRGB());
        }

    }
}
