package com.github.skillfi.reincarnation_plus.mixins;

import cy.jdkdigital.productivebees.util.BeeHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin({BeeHelper.class})
public class MixinBeeHelper {

    @Inject(method = "populateBeeInfoFromTag", at=@At("TAIL"), remap = false)
    private static void update(CompoundTag tag, List<Component> list, CallbackInfoReturnable<List<Component>> cir){
        list.add(Component.literal("EP: " + tag.getCompound("ForgeCaps").getCompound("tensura:ep").getDouble("currentEP")).withStyle(ChatFormatting.BLUE));
    }
}
