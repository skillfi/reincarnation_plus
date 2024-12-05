package com.github.skillfi.reincarnation_plus.core.item;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class RPArmorMaterials implements ArmorMaterial {

    public static final RPArmorMaterials OGRE1 = new RPArmorMaterials("ogre", 28, new int[]{
            2, 5, 8, 3
    }, 25, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F, () -> Ingredient.of(Items.LEATHER));

    public static final RPArmorMaterials KIJIN = new RPArmorMaterials("kijin", 40, new int[]{
            OGRE1.slotProtections[0]*2, OGRE1.slotProtections[1]*2, OGRE1.slotProtections[2]*2, OGRE1.slotProtections[3]*2
    }, OGRE1.enchantmentValue*2, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F, () -> Ingredient.of(Items.LEATHER));

    public static final RPArmorMaterials ONI = new RPArmorMaterials("oni", 50, new int[]{
            KIJIN.slotProtections[0]*2, KIJIN.slotProtections[1]*2, KIJIN.slotProtections[2]*2, KIJIN.slotProtections[3]*2
    }, KIJIN.enchantmentValue*2, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F, () -> Ingredient.of(Items.LEATHER));

    public static final RPArmorMaterials DIVINE_ONI = new RPArmorMaterials("divine_oni", 65, new int[]{
            ONI.slotProtections[0]*2, ONI.slotProtections[1]*2, ONI.slotProtections[2]*2, ONI.slotProtections[3]*2
    }, KIJIN.enchantmentValue, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F, () -> Ingredient.of(Items.LEATHER));

    public static final RPArmorMaterials WICKED_ONI = new RPArmorMaterials("wicked_oni", 65, new int[]{
            DIVINE_ONI.slotProtections[0]*2, DIVINE_ONI.slotProtections[1]*2, DIVINE_ONI.slotProtections[2]*2, DIVINE_ONI.slotProtections[3]*2
    }, KIJIN.enchantmentValue, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F, () -> Ingredient.of(Items.LEATHER));

    public static final RPArmorMaterials DIVINE_FIGHTER = new RPArmorMaterials("divine_fighter", 65, new int[]{
            WICKED_ONI.slotProtections[0]*2, WICKED_ONI.slotProtections[1]*2, WICKED_ONI.slotProtections[2]*2, WICKED_ONI.slotProtections[3]*2
    }, KIJIN.enchantmentValue, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F, () -> Ingredient.of(Items.LEATHER));

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    RPArmorMaterials(String name, int dMultiplier, int[] sProtection, int enchantmentLevel,
                     SoundEvent soundEvent, float toughnessValue, float knockbackResistanceValue,
                     Supplier<Ingredient> repairMaterialSupplier) {
        this.name = name;
        this.durabilityMultiplier = dMultiplier;
        this.slotProtections = sProtection;
        this.enchantmentValue = enchantmentLevel;
        this.sound = soundEvent;
        this.toughness = toughnessValue;
        this.knockbackResistance = knockbackResistanceValue;
        this.repairIngredient = new LazyLoadedValue<>(repairMaterialSupplier);
    }

    public int getDurabilityForSlot(EquipmentSlot pSlot) {
        return HEALTH_PER_SLOT[pSlot.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlot pSlot) {
        return this.slotProtections[pSlot.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return ReiMod.MODID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
