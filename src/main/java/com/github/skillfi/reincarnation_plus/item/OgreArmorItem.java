package com.github.skillfi.reincarnation_plus.item;

import com.github.skillfi.reincarnation_plus.client.model.ModelOgre;
import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusModTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public abstract class OgreArmorItem extends ArmorItem {
    public OgreArmorItem(EquipmentSlot slot, Item.Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForSlot(EquipmentSlot slot) {
                return new int[]{13, 15, 16, 11}[slot.getIndex()] * 25;
            }

            @Override
            public int getDefenseForSlot(EquipmentSlot slot) {
                return new int[]{2, 5, 6, 2}[slot.getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return 9;
            }

            @Override
            public SoundEvent getEquipSound() {
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            @Override
            public String getName() {
                return "ogre_armor";
            }

            @Override
            public float getToughness() {
                return 0f;
            }

            @Override
            public float getKnockbackResistance() {
                return 0f;
            }
        }, slot, properties);
    }

    public static class Chestplate extends OgreArmorItem {
        public Chestplate() {
            super(EquipmentSlot.CHEST, new Item.Properties().tab(ReincarnationPlusModTabs.TAB_REINCARNATION_PLUS));
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                @OnlyIn(Dist.CLIENT)
                public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel armorModel = new ModelOgre(new ModelPart(Collections.emptyList(), Map.of("body", new ModelOgre(Minecraft.getInstance().getEntityModels().bakeLayer(ModelOgre.LAYER_LOCATION)).body, "left_arm",
                            new ModelOgre(Minecraft.getInstance().getEntityModels().bakeLayer(ModelOgre.LAYER_LOCATION)).left_arm, "right_arm", new ModelOgre(Minecraft.getInstance().getEntityModels().bakeLayer(ModelOgre.LAYER_LOCATION)).right_arm,
                            "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                            "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                    armorModel.crouching = living.isShiftKeyDown();
                    armorModel.riding = defaultModel.riding;
                    armorModel.young = living.isBaby();
                    return armorModel;
                }
            });
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "reincarnation_plus:textures/entities/ogre_armor_layer_1.png";
        }
    }

    public static class Leggings extends OgreArmorItem {
        public Leggings() {
            super(EquipmentSlot.LEGS, new Item.Properties().tab(ReincarnationPlusModTabs.TAB_REINCARNATION_PLUS));
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
				@Override
                @OnlyIn(Dist.CLIENT)
                public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel armorModel = new ModelOgre(new ModelPart(Collections.emptyList(),
                            Map.of("left_leg", new ModelOgre(Minecraft.getInstance().getEntityModels().bakeLayer(ModelOgre.LAYER_LOCATION)).left_leg, "right_leg",
                                    new ModelOgre(Minecraft.getInstance().getEntityModels().bakeLayer(ModelOgre.LAYER_LOCATION)).right_leg, "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                    armorModel.crouching = living.isShiftKeyDown();
                    armorModel.riding = defaultModel.riding;
                    armorModel.young = living.isBaby();
                    return armorModel;
                }
            });
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "reincarnation_plus:textures/entities/ogre_armor_layer_2.png";
        }
    }
}
