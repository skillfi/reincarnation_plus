package com.github.skillfi.reincarnation_plus.item.custom;

import com.google.common.collect.Iterables;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class LeatherArmorItem extends GeoArmorItem implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public LeatherArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
        super(materialIn, slot, builder);
    }


    public void registerControllers(AnimationData animationData) {}

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private boolean hasFullSuitArmorOn(LivingEntity livingEntity){
        ItemStack[] armorItems = Iterables.toArray(livingEntity.getArmorSlots(), ItemStack.class);
        ItemStack boots = (ItemStack) armorItems[0];
        ItemStack leggings = (ItemStack) armorItems[1];
        ItemStack breastplate = (ItemStack) armorItems[2];
        ItemStack helmet = (ItemStack) armorItems[3];

        return !boots.isEmpty() && !leggings.isEmpty() && !breastplate.isEmpty() && !helmet.isEmpty();
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, LivingEntity livingEntity){
        for (ItemStack armorStack: livingEntity.getArmorSlots()){
            if(!(armorStack.getItem() instanceof ArmorItem)){
                return false;
            }
        }
        ItemStack[] armorItems = Iterables.toArray(livingEntity.getArmorSlots(), ItemStack.class);
        ArmorItem boots = (ArmorItem) armorItems[0].getItem();
        ArmorItem leggings = (ArmorItem) armorItems[1].getItem();
        ArmorItem breastplate = (ArmorItem) armorItems[2].getItem();
        ArmorItem helmet = (ArmorItem) armorItems[3].getItem();

        return helmet.getMaterial() == material && breastplate.getMaterial() == material && leggings.getMaterial() == material && boots.getMaterial() == material;
    }
}
