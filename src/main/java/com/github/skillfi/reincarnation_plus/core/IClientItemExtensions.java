package com.github.skillfi.reincarnation_plus.core;

import com.github.skillfi.reincarnation_plus.client.model.ModelOgre;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import com.github.skillfi.reincarnation_plus.client.ForgeHooksClient;
import org.jetbrains.annotations.NotNull;


public interface IClientItemExtensions {
    IClientItemExtensions DEFAULT = new IClientItemExtensions() {
    };


    default @NotNull ModelOgre<?> getOgreArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, ModelOgre<?> original) {
        return original;
    }

    default @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, ModelOgre<?> original) {
        ModelOgre<?> replacement = this.getOgreArmorModel(livingEntity, itemStack, equipmentSlot, original);
        if (replacement != original) {
            ForgeHooksClient.copyModelProperties(original, replacement);
            return replacement;
        } else {
            return original;
        }
    }

    static IClientItemExtensions of(Item item) {
        Object var2 = item.getRenderPropertiesInternal();
        IClientItemExtensions var10000;
        if (var2 instanceof IClientItemExtensions e) {
            var10000 = e;
        } else {
            var10000 = DEFAULT;
        }

        return var10000;
    }
}
