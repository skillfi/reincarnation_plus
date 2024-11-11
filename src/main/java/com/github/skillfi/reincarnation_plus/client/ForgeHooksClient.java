package com.github.skillfi.reincarnation_plus.client;

import com.github.skillfi.reincarnation_plus.client.model.ModelOgre;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.github.skillfi.reincarnation_plus.core.IClientItemExtensions;

public class ForgeHooksClient {

    public static Model getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot slot, ModelOgre<?> _default) {
        return IClientItemExtensions.of(itemStack.getItem()).getGenericArmorModel(entityLiving, itemStack, slot, _default);
    }

    public static <T extends LivingEntity> void copyModelProperties(ModelOgre<T> original, ModelOgre<?> replacement) {
        original.copyPropertiesTo((EntityModel<T>) replacement);
        replacement.Head.visible = original.Head.visible;
        replacement.Body.visible = original.Body.visible;
        replacement.ArmRight.visible = original.ArmRight.visible;
        replacement.ArmLeft.visible = original.ArmLeft.visible;
        replacement.LegRight.visible = original.LegRight.visible;
        replacement.LegLeft.visible = original.LegLeft.visible;
        replacement.HornRight.visible = original.HornRight.visible;
        replacement.HornLeft.visible = original.HornLeft.visible;
    }

    public static String getArmorTexture(Entity entity, ItemStack armor, String _default, EquipmentSlot slot, String type) {
        String result = armor.getItem().getArmorTexture(armor, entity, slot, type);
        return result != null ? result : _default;
    }
}
