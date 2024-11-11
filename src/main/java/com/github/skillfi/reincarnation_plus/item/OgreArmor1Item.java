
package com.github.skillfi.reincarnation_plus.item;

import net.minecraft.sounds.SoundEvent;

import java.util.function.Consumer;
import java.util.Map;
import java.util.Collections;

import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusModTabs;
import com.github.skillfi.reincarnation_plus.client.model.ModelOgre;

public abstract class OgreArmor1Item extends ArmorItem {
	public OgreArmor1Item(EquipmentSlot slot, Item.Properties properties) {
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
				return "ogre_armor_1";
			}

			@Override
			public float getToughness() {
				return 4f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0f;
			}
		}, slot, properties);
	}

	public static class Chestplate extends OgreArmor1Item {
		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties().tab(ReincarnationPlusModTabs.TAB_ISEKAI_QUARTET));
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of("body", new ModelOgre(Minecraft.getInstance().getEntityModels().bakeLayer(ModelOgre.LAYER_LOCATION)).Armor, "left_arm",
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
			return "reincarnation_plus:textures/models/armor/ogre_armor__layer_1.png";
		}
	}

	public static class Leggings extends OgreArmor1Item {
		public Leggings() {
			super(EquipmentSlot.LEGS, new Item.Properties().tab(ReincarnationPlusModTabs.TAB_ISEKAI_QUARTET));
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
							Map.of("left_leg", new ModelOgre(Minecraft.getInstance().getEntityModels().bakeLayer(ModelOgre.LAYER_LOCATION)).waist, "right_leg",
									new ModelOgre(Minecraft.getInstance().getEntityModels().bakeLayer(ModelOgre.LAYER_LOCATION)).waist, "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat",
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
			return "reincarnation_plus:textures/models/armor/ogre_armor__layer_2.png";
		}
	}
}
