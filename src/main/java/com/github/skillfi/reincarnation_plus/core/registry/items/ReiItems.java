
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.core.registry.items;

import com.github.manasmods.tensura.item.TensuraArmourMaterials;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.*;
import com.github.skillfi.reincarnation_plus.core.entity.ReiEntities;
import com.github.skillfi.reincarnation_plus.core.item.armor.JacketWomanArmorItem;
import com.github.skillfi.reincarnation_plus.core.item.armor.KimonoWomanArmorItem;
import com.github.skillfi.reincarnation_plus.core.item.armor.LeatherArmorItem;
import com.github.skillfi.reincarnation_plus.core.registry.ReiCreativeTab;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReiItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ReiMod.MODID);
	public static final RegistryObject<Item> GEM_STONE = ITEMS.register("gem_stone", () -> new GemStoneItem(
			com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STONE_BLOCK.get(),
			new Item.Properties().stacksTo(1).tab(ReiCreativeTab.ITEMS)
	));
	public static final RegistryObject<Item> GEMS_BLOCK = block(com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEMS_BLOCK, ReiCreativeTab.BLOCKS);
	public static final RegistryObject<Item> GEM_STAGE_0 = block(com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STAGE_0, null);
	public static final RegistryObject<Item> GEM_STAGE_1 = block(com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STAGE_1, null);
	public static final RegistryObject<Item> GEM_STAGE_2 = block(com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STAGE_2, null);
	public static final RegistryObject<Item> GEM_STAGE_3 = block(com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STAGE_3, null);
	public static final RegistryObject<Item> GEM_STAGE_4 = block(com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks.GEM_STAGE_4, null);
	public static final RegistryObject<Item> GEM_DUST = ITEMS.register("gem_dust", GemDustItem::new);
	public static final RegistryObject<Item> SUPER_CHARGER = ITEMS.register("super_charger", SuperCharger::new);
	public static final RegistryObject<Item> SIPHON = ITEMS.register("siphon", Siphon::new);
	public static final RegistryObject<Item> CRYSTALIZED_WATER_BUCKET = ITEMS.register("crystalized_water_bucket", CrystalizedWaterItem::new);
	public static final RegistryObject<Item> HAKAMA_CHESTPLATE_D = ITEMS.register("leather_chestplate",
			() -> new LeatherArmorItem(TensuraArmourMaterials.MONSTER_LEATHER_D, EquipmentSlot.CHEST,
					new Item.Properties().tab(ReiCreativeTab.ITEMS)));
	public static final RegistryObject<Item> HAKAMA_LEGGINGS_D = ITEMS.register("leather_leggings",
			() -> new LeatherArmorItem(TensuraArmourMaterials.MONSTER_LEATHER_D, EquipmentSlot.LEGS,
					new Item.Properties().tab(ReiCreativeTab.ITEMS)));
	public static final RegistryObject<Item> MONSTER_LEATHER_WOMAN_LEGGINGS_D = ITEMS.register("shoes",
			() -> new JacketWomanArmorItem(TensuraArmourMaterials.MONSTER_LEATHER_D, EquipmentSlot.LEGS,
					new Item.Properties().tab(ReiCreativeTab.ITEMS)));
	public static final RegistryObject<Item> MONSTER_LEATHER_JACKET_ARMOR_D = ITEMS.register("woman_jacket",
			() -> new JacketWomanArmorItem(TensuraArmourMaterials.MONSTER_LEATHER_D, EquipmentSlot.CHEST,
					new Item.Properties().tab(ReiCreativeTab.ITEMS)));
	public static final RegistryObject<Item> MONSTER_LEATHER_SANDALS_D = ITEMS.register("sandals",
			() -> new JacketWomanArmorItem(TensuraArmourMaterials.MONSTER_LEATHER_D, EquipmentSlot.FEET,
					new Item.Properties().tab(ReiCreativeTab.ITEMS)));
	public static final RegistryObject<Item> KIMONO_D = ITEMS.register("woman_kimono",
			() -> new KimonoWomanArmorItem(TensuraArmourMaterials.MONSTER_LEATHER_D, EquipmentSlot.CHEST,
					new Item.Properties().tab(ReiCreativeTab.ITEMS)));
	public static final RegistryObject<Item> YUKATA_D = ITEMS.register("woman_skirt",
			() -> new KimonoWomanArmorItem(TensuraArmourMaterials.MONSTER_LEATHER_D, EquipmentSlot.LEGS,
					new Item.Properties().tab(ReiCreativeTab.ITEMS)));
	public static final RegistryObject<Item> OGRE_SPAWN_EGG = ITEMS.register("ogre_spawn_egg", () -> new ForgeSpawnEggItem(ReiEntities.OGRE, -52378, -1, new Item.Properties().tab(ReiCreativeTab.ITEMS)));
	public static final RegistryObject<Item> MAGIC_INFUSER = ITEMS.register(ReiBlockEntities.ReiBlocks.MAGICAL_INFUSER.getId().getPath(), () -> new MagiculaInfuserItem(ReiBlockEntities.ReiBlocks.MAGICAL_INFUSER.get(), new Item.Properties().tab(ReiCreativeTab.BLOCKS)));
	public static final RegistryObject<Item> AUTOMATIC_MAGIC_INFUSER = ITEMS.register(ReiBlockEntities.ReiBlocks.AUTOMATIC_MAGICAL_INFUSER.getId().getPath(), () -> new MagiculaInfuserItem(ReiBlockEntities.ReiBlocks.AUTOMATIC_MAGICAL_INFUSER.get(), new Item.Properties().tab(ReiCreativeTab.BLOCKS)));
	public static final RegistryObject<Item> MAGIC_AMPLIFIER = ITEMS.register(ReiBlockEntities.ReiBlocks.MAGIC_AMPLIFIER.getId().getPath(), () -> new BlockItem(ReiBlockEntities.ReiBlocks.MAGIC_AMPLIFIER.get(), new Item.Properties().tab(ReiCreativeTab.BLOCKS)));
	public static final RegistryObject<Item> INFUSION_BELLOWS = ITEMS.register(ReiBlockEntities.ReiBlocks.INFUSION_BELLOWS_BLOCK_REGISTRY_OBJECT.getId().getPath(), () -> new InfusionBellowsItem(ReiBlockEntities.ReiBlocks.INFUSION_BELLOWS_BLOCK_REGISTRY_OBJECT.get(), new Item.Properties().tab(ReiCreativeTab.BLOCKS)));

	public static final RegistryObject<Item> IRON_MAGIC_ORE = ITEMS.register(ReiBlockEntities.ReiBlocks.IRON_MAGIC_ORE.getId().getPath(), () -> new IronMagicOreItem(ReiBlockEntities.ReiBlocks.IRON_MAGIC_ORE.get(), new Item.Properties().tab(ReiCreativeTab.BLOCKS)));
	public static final RegistryObject<Item> COOPER_MAGIC_ORE = ITEMS.register(ReiBlockEntities.ReiBlocks.COOPER_MAGIC_ORE.getId().getPath(), () -> new CooperMagicOreItem(ReiBlockEntities.ReiBlocks.COOPER_MAGIC_ORE.get(), new Item.Properties().tab(ReiCreativeTab.BLOCKS)));
	public static final RegistryObject<Item> GOLD_MAGIC_ORE = ITEMS.register(ReiBlockEntities.ReiBlocks.GOLD_MAGIC_ORE.getId().getPath(), () -> new GoldMagicOreItem(ReiBlockEntities.ReiBlocks.GOLD_MAGIC_ORE.get(), new Item.Properties().tab(ReiCreativeTab.BLOCKS)));

	private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
		return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
}
