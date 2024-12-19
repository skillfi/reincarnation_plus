package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.enums.EnumDragonArmor;
import com.github.manasmods.tensura.block.SimpleBlock;
import com.github.manasmods.tensura.registry.blocks.TensuraBlocks;
import com.github.manasmods.tensura.registry.items.TensuraArmorItems;
import com.github.manasmods.tensura.registry.items.TensuraMaterialItems;
import com.github.manasmods.tensura.registry.items.TensuraMobDropItems;
import com.github.manasmods.tensura.registry.items.TensuraToolItems;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.core.registry.items.ReiItems;
import com.github.skillfi.reincarnation_plus.libs.data.ReiTags;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.automatic.AutomaticMagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.automatic.AutomaticMagicInfusionRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.infuser.MagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.infuser.MagicInfuserRemeltingRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.infuser.MagicInfusionRecipe;
import net.minecraft.data.recipes.FinishedRecipe;
import com.github.manasmods.manascore.api.data.gen.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.function.Consumer;

public class ReiRecipeProvider extends RecipeProvider {

    public ReiRecipeProvider(GatherDataEvent gatherDataEvent) {
        super(gatherDataEvent);
    }

    protected void generate(Consumer<FinishedRecipe> consumer) {
        this.buildRecipes(consumer);
        this.magicInfusion(consumer);
        this.magicMelting(consumer);
        this.magicRemelting(consumer);
    }

    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Приклад рецептів:

        ShapedRecipeBuilder.shaped(ReiItems.HAKAMA_LEGGINGS_D.get())
                .define('D', TensuraMobDropItems.MONSTER_LEATHER_D.get())
                .define('S', TensuraMobDropItems.LOW_QUALITY_MAGIC_CRYSTAL.get())
                .pattern("DDD")
                .pattern("D D")
                .pattern("S S")
                .unlockedBy("has_monster_leather", has(TensuraMobDropItems.MONSTER_LEATHER_D.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ReiItems.SUPER_CHARGER.get())
                .define('A', TensuraMaterialItems.ORICHALCUM_INGOT.get())
                .define('M', TensuraMaterialItems.HIGH_MAGISTEEL_INGOT.get())
                .define('I', ReiItems.SIPHON.get())
                .pattern("AMA")
                .pattern("AIA")
                .pattern("AMA")
                .unlockedBy("has_magic_stone", has(ReiItems.SIPHON.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ReiItems.AUTOMATIC_MAGIC_INFUSER.get())
                .define('S', ReiItems.SUPER_CHARGER.get())
                .define('B', ReiItems.INFUSION_BELLOWS.get())
                .define('I', ReiItems.MAGIC_INFUSER.get())
                .define('A', ReiBlockEntities.ReiBlocks.MAGIC_AMPLIFIER.get())
                .pattern("  S")
                .pattern("AIB")
                .unlockedBy("has_magic_infuser", has(ReiItems.MAGIC_INFUSER.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ReiItems.SIPHON.get())
                .define('O', TensuraMaterialItems.ORICHALCUM_INGOT.get())
                .define('I', TensuraMaterialItems.MAGIC_STONE.get())
                .define('A', TensuraMaterialItems.ADAMANTITE_INGOT.get())
                .define('S', TensuraMaterialItems.SILVER_INGOT.get())
                .pattern(" A ")
                .pattern("OIO")
                .pattern(" S ")
                .unlockedBy("has_magic_stone", has(TensuraMaterialItems.MAGIC_STONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ReiItems.HAKAMA_CHESTPLATE_D.get())
                .define('D', TensuraMobDropItems.MONSTER_LEATHER_D.get())
                .define('S', TensuraMobDropItems.LOW_QUALITY_MAGIC_CRYSTAL.get())
                .pattern("D D")
                .pattern("DSD")
                .pattern("SSS")
                .unlockedBy("has_monster_leather", has(TensuraMobDropItems.MONSTER_LEATHER_D.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ReiItems.MONSTER_LEATHER_JACKET_ARMOR_D.get())
                .define('D', TensuraMobDropItems.MONSTER_LEATHER_D.get())
                .define('S', TensuraMobDropItems.LOW_QUALITY_MAGIC_CRYSTAL.get())
                .pattern("D D")
                .pattern("DDD")
                .pattern("DSD")
                .unlockedBy("has_monster_leather", has(TensuraMobDropItems.MONSTER_LEATHER_D.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ReiItems.MONSTER_LEATHER_SANDALS_D.get())
                .define('D', TensuraMobDropItems.MONSTER_LEATHER_D.get())
                .pattern("D D")
                .pattern("D D")
                .unlockedBy("has_monster_leather", has(TensuraMobDropItems.MONSTER_LEATHER_D.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ReiItems.MONSTER_LEATHER_WOMAN_LEGGINGS_D.get())
                .define('D', TensuraMobDropItems.MONSTER_LEATHER_D.get())
                .pattern("DDD")
                .pattern("D D")
                .pattern("D D")
                .unlockedBy("has_monster_leather", has(TensuraMobDropItems.MONSTER_LEATHER_D.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ReiItems.YUKATA_D.get())
                .define('D', TensuraMobDropItems.MONSTER_LEATHER_D.get())
                .pattern("DDD")
                .pattern("DDD")
                .pattern("D D")
                .unlockedBy("has_monster_leather", has(TensuraMobDropItems.MONSTER_LEATHER_D.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ReiItems.KIMONO_D.get())
                .define('D', TensuraMobDropItems.MONSTER_LEATHER_D.get())
                .pattern("DDD")
                .pattern("DDD")
                .pattern("D D")
                .unlockedBy("has_monster_leather", has(TensuraMobDropItems.MONSTER_LEATHER_D.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ReiItems.MAGIC_INFUSER.get())
                .define('K', TensuraBlocks.Items.KILN.get())
                .define('C', Items.CALCITE)
                .define('B', Items.COMPARATOR)
                .define('D', ReiTags.Items.DEEPSLATE)
                .define('M', TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get())
                .pattern("CBC")
                .pattern("MKM")
                .pattern("CDC")
                .unlockedBy("has_kiln", has(TensuraBlocks.Items.KILN.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ReiItems.MAGIC_AMPLIFIER.get())
                .define('S', Items.GLASS)
                .define('M', TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get())
                .pattern("SSS")
                .pattern("MSM")
                .pattern("MSM")
                .unlockedBy("has_magisteel", has(TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ReiItems.INFUSION_BELLOWS.get())
                .define('S', TensuraMobDropItems.MONSTER_LEATHER_D.get())
                .define('M', TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get())
                .define('I', TensuraMaterialItems.MAGIC_STONE.get())
                .define('L', TensuraBlocks.LOW_QUALITY_MAGIC_CRYSTAL_BLOCK.get())
                .pattern(" SS")
                .pattern("MIL")
                .pattern(" SS")
                .unlockedBy("has_magisteel", has(TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get()))
                .save(consumer);
    }

    protected void magicInfusion(Consumer<FinishedRecipe> consumer){
        //      region Ores
        magicInfusionLeft(consumer, TensuraBlocks.MAGIC_ORE.get().asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 15000, 4800, ItemTags.IRON_ORES, "iron");
        magicInfusionLeft(consumer, TensuraBlocks.MAGIC_ORE.get().asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 25000, 4800, ItemTags.COPPER_ORES, "cooper");
        magicInfusionLeft(consumer, TensuraBlocks.MAGIC_ORE.get().asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 10000, 4800, ItemTags.GOLD_ORES, "gold");

        magicInfusionRawOres(consumer, TensuraMaterialItems.MAGIC_ORE.get().asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 75000,
                2400,
                Items.RAW_IRON.getDefaultInstance(), Items.RAW_COPPER.getDefaultInstance(), Items.RAW_GOLD.getDefaultInstance(),
                12500, 5000);
        magicInfusionOres(consumer, Blocks.RAW_IRON_BLOCK.asItem().getDefaultInstance(), TensuraBlocks.MAGIC_ORE_BLOCK.get().asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 15000*9, 4800, "raw_iron_block");
        magicInfusionOres(consumer, Blocks.RAW_COPPER_BLOCK.asItem().getDefaultInstance(), TensuraBlocks.MAGIC_ORE_BLOCK.get().asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 25000*9, 4800, "raw_copper_block");
        magicInfusionOres(consumer, Blocks.RAW_GOLD_BLOCK.asItem().getDefaultInstance(), TensuraBlocks.MAGIC_ORE_BLOCK.get().asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 10000*9, 4800, "raw_gold_block");
        // endregion

        // region OtherBlocks
        magicInfusionLeft(consumer, TensuraMaterialItems.HIPOKUTE_SEEDS.get().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 2500, 600, Items.WHEAT_SEEDS.getDefaultInstance());
        magicInfusionLeft(consumer, Blocks.SLIME_BLOCK.asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1000, 3600, Blocks.GREEN_WOOL.asItem().getDefaultInstance());
        magicInfusionLeft(consumer, Blocks.AMETHYST_BLOCK.asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 2000, 3600, Blocks.QUARTZ_BLOCK.asItem().getDefaultInstance());
        magicInfusionLeft(consumer, Items.AMETHYST_SHARD.getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 500, 2400, Items.QUARTZ.getDefaultInstance());
        magicInfusionLeft(consumer, Blocks.CALCITE.asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 500, 7400, Blocks.DIORITE.asItem().getDefaultInstance());
        magicInfusionLeft(consumer, Items.GUNPOWDER.getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1000, 6000, Items.FLINT.getDefaultInstance());
        magicInfusionLeft(consumer, Blocks.ANCIENT_DEBRIS.asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 40000, 120000, Blocks.IRON_BLOCK.asItem().getDefaultInstance());
        magicInfusionLeft(consumer, Blocks.CRYING_OBSIDIAN.asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1000, 3600, Blocks.OBSIDIAN.asItem().getDefaultInstance());
        magicInfusionOres(consumer, TensuraMaterialItems.MITHRIL_INGOT.get().getDefaultInstance(), TensuraMaterialItems.ADAMANTITE_INGOT.get().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 225000, 5000, "adamantite");
        magicInfusionOres(consumer, TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get().getDefaultInstance(), TensuraMaterialItems.ADAMANTITE_INGOT.get().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 225000, 5000, "pure_adamantite");
        // endregion
    }

    private void magicRemelting(Consumer<FinishedRecipe> consumer){
        magicRemeltingGears(consumer, ReiMoltenMaterialProvider.EP,  "mithril", (ItemLike) TensuraArmorItems.MITHRIL_HELMET.get(), (ItemLike)TensuraArmorItems.MITHRIL_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.MITHRIL_LEGGINGS.get(), (ItemLike)TensuraArmorItems.MITHRIL_BOOTS.get(), (ItemLike)TensuraToolItems.MITHRIL_PICKAXE.get(), (ItemLike)TensuraToolItems.MITHRIL_AXE.get(), (ItemLike)TensuraToolItems.MITHRIL_SHOVEL.get(), (ItemLike)TensuraToolItems.MITHRIL_HOE.get(), (ItemLike)TensuraToolItems.MITHRIL_SICKLE.get(), (ItemLike)TensuraToolItems.MITHRIL_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_LONG_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_KATANA.get(), (ItemLike)TensuraToolItems.MITHRIL_KODACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_TACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_ODACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_SPEAR.get(), 378, 378);
        magicRemeltingGears(consumer, ReiMoltenMaterialProvider.EP,  "orichalcum", (ItemLike)TensuraArmorItems.ORICHALCUM_HELMET.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_LEGGINGS.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_BOOTS.get(), (ItemLike)TensuraToolItems.ORICHALCUM_PICKAXE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_AXE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SHOVEL.get(), (ItemLike)TensuraToolItems.ORICHALCUM_HOE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SICKLE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_LONG_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_KATANA.get(), (ItemLike)TensuraToolItems.ORICHALCUM_KODACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_TACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_ODACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SPEAR.get(), 378, 378);
        magicRemeltingGears(consumer, ReiMoltenMaterialProvider.EP,  "adamantite", (ItemLike)TensuraArmorItems.ADAMANTITE_HELMET.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_LEGGINGS.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_BOOTS.get(), (ItemLike)TensuraToolItems.ADAMANTITE_PICKAXE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_AXE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SHOVEL.get(), (ItemLike)TensuraToolItems.ADAMANTITE_HOE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SICKLE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_LONG_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_KATANA.get(), (ItemLike)TensuraToolItems.ADAMANTITE_KODACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_TACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_ODACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SPEAR.get(), 378, 378);
        magicRemeltingGears(consumer, ReiMoltenMaterialProvider.EP,  "hihirokane", (ItemLike)TensuraArmorItems.HIHIIROKANE_HELMET.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_LEGGINGS.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_BOOTS.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_PICKAXE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_AXE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SHOVEL.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_HOE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SICKLE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_LONG_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_KATANA.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_KODACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_TACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_ODACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SPEAR.get(), 378, 378);
        magicRemeltingGears(consumer, ReiMoltenMaterialProvider.EP,  "ep_gold", Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_PICKAXE, Items.GOLDEN_AXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_HOE, (ItemLike)TensuraToolItems.GOLDEN_SICKLE.get(), Items.GOLDEN_SWORD, (ItemLike)TensuraToolItems.GOLDEN_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.GOLDEN_LONG_SWORD.get(), (ItemLike)TensuraToolItems.GOLDEN_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.GOLDEN_KATANA.get(), (ItemLike)TensuraToolItems.GOLDEN_KODACHI.get(), (ItemLike)TensuraToolItems.GOLDEN_TACHI.get(), (ItemLike)TensuraToolItems.GOLDEN_ODACHI.get(), (ItemLike)TensuraToolItems.GOLDEN_SPEAR.get(), 378, 378);
        magicRemeltingGears(consumer, ReiMoltenMaterialProvider.EP,  "ep_iron", Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_SHOVEL, Items.IRON_HOE, (ItemLike)TensuraToolItems.IRON_SICKLE.get(), Items.IRON_SWORD, (ItemLike)TensuraToolItems.IRON_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.IRON_LONG_SWORD.get(), (ItemLike)TensuraToolItems.IRON_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.IRON_KATANA.get(), (ItemLike)TensuraToolItems.IRON_KODACHI.get(), (ItemLike)TensuraToolItems.IRON_TACHI.get(), (ItemLike)TensuraToolItems.IRON_ODACHI.get(), (ItemLike)TensuraToolItems.IRON_SPEAR.get(), 378, 378);
        magicRemeltingGears(consumer, ReiMoltenMaterialProvider.EP,  "ep_silver", (ItemLike)TensuraArmorItems.SILVER_HELMET.get(), (ItemLike)TensuraArmorItems.SILVER_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.SILVER_LEGGINGS.get(), (ItemLike)TensuraArmorItems.SILVER_BOOTS.get(), (ItemLike)TensuraToolItems.SILVER_PICKAXE.get(), (ItemLike)TensuraToolItems.SILVER_AXE.get(), (ItemLike)TensuraToolItems.SILVER_SHOVEL.get(), (ItemLike)TensuraToolItems.SILVER_HOE.get(), (ItemLike)TensuraToolItems.SILVER_SICKLE.get(), (ItemLike)TensuraToolItems.SILVER_SWORD.get(), (ItemLike)TensuraToolItems.SILVER_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.SILVER_LONG_SWORD.get(), (ItemLike)TensuraToolItems.SILVER_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.SILVER_KATANA.get(), (ItemLike)TensuraToolItems.SILVER_KODACHI.get(), (ItemLike)TensuraToolItems.SILVER_TACHI.get(), (ItemLike)TensuraToolItems.SILVER_ODACHI.get(), (ItemLike)TensuraToolItems.SILVER_SPEAR.get(), 378, 378);
        magicRemeltingGears(consumer, ReiMoltenMaterialProvider.EP,  "ep_netherite", Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS, Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_HOE, (ItemLike)TensuraToolItems.NETHERITE_SICKLE.get(), Items.NETHERITE_SWORD, (ItemLike)TensuraToolItems.NETHERITE_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.NETHERITE_LONG_SWORD.get(), (ItemLike)TensuraToolItems.NETHERITE_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.NETHERITE_KATANA.get(), (ItemLike)TensuraToolItems.NETHERITE_KODACHI.get(), (ItemLike)TensuraToolItems.NETHERITE_TACHI.get(), (ItemLike)TensuraToolItems.NETHERITE_ODACHI.get(), (ItemLike)TensuraToolItems.NETHERITE_SPEAR.get(), 378, 378);
        magicRemeltingGears(consumer, ReiMoltenMaterialProvider.EP,  "ep_low_magisteel", (ItemLike)TensuraArmorItems.LOW_MAGISTEEL_HELMET.get(), (ItemLike)TensuraArmorItems.LOW_MAGISTEEL_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.LOW_MAGISTEEL_LEGGINGS.get(), (ItemLike)TensuraArmorItems.LOW_MAGISTEEL_BOOTS.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_PICKAXE.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_AXE.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_SHOVEL.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_HOE.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_SICKLE.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_SWORD.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_LONG_SWORD.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_KATANA.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_KODACHI.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_TACHI.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_ODACHI.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_SPEAR.get(), 378, 378);
        magicRemeltingGears(consumer, ReiMoltenMaterialProvider.EP,  "ep_high_magisteel", (ItemLike)TensuraArmorItems.HIGH_MAGISTEEL_HELMET.get(), (ItemLike)TensuraArmorItems.HIGH_MAGISTEEL_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.HIGH_MAGISTEEL_LEGGINGS.get(), (ItemLike)TensuraArmorItems.HIGH_MAGISTEEL_BOOTS.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_PICKAXE.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_AXE.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_SHOVEL.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_HOE.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_SICKLE.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_SWORD.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_LONG_SWORD.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_KATANA.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_KODACHI.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_TACHI.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_ODACHI.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_SPEAR.get(), 378, 378);

        for (EnumDragonArmor dragonArmor:EnumDragonArmor.values()){
            magicRemelting(consumer, ReiMoltenMaterialProvider.EP, 378, dragonArmor.helmet.get());
            magicRemelting(consumer, ReiMoltenMaterialProvider.EP, 378, dragonArmor.chestplate.get());
            magicRemelting(consumer, ReiMoltenMaterialProvider.EP, 378, dragonArmor.leggings.get());
            magicRemelting(consumer, ReiMoltenMaterialProvider.EP, 378, dragonArmor.boots.get());
        }
    }

    protected void  magicRemelting(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, TagKey<Item> input, String path){
        MagicInfuserRemeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, path);
    }

    private void magicMelting(Consumer<FinishedRecipe> consumer){
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 250, ((SimpleBlock)TensuraBlocks.MAGIC_ORE.get()).asItem());
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 250, ((RotatedPillarBlock)TensuraBlocks.DEEPSLATE_MAGIC_ORE.get()).asItem());
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 250, (Item)TensuraMaterialItems.MAGIC_ORE.get());
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 2250, ((SimpleBlock)TensuraBlocks.MAGIC_ORE_BLOCK.get()).asItem());
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 250, (Item)TensuraMaterialItems.HIGH_MAGISTEEL_NUGGET.get());
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 500, com.github.manasmods.tensura.data.TensuraTags.Items.NUGGETS_MITHRIL, "mithril_nugget");
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 500, com.github.manasmods.tensura.data.TensuraTags.Items.NUGGETS_ORICHALCUM, "orichalcum_nugget");
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1000, (Item)TensuraMaterialItems.PURE_MAGISTEEL_NUGGET.get());
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 9000, (Item)TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get());
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 250, (Item) TensuraToolItems.KANABO.get());
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 250, (Item)TensuraToolItems.PURE_MAGISTEEL_KUNAI.get());
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1000, (Item)TensuraToolItems.DRAGON_KNUCKLE.get());
        magicMeltingGearsx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, "mithril", (ItemLike) TensuraArmorItems.MITHRIL_HELMET.get(), (ItemLike)TensuraArmorItems.MITHRIL_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.MITHRIL_LEGGINGS.get(), (ItemLike)TensuraArmorItems.MITHRIL_BOOTS.get(), (ItemLike)TensuraToolItems.MITHRIL_PICKAXE.get(), (ItemLike)TensuraToolItems.MITHRIL_AXE.get(), (ItemLike)TensuraToolItems.MITHRIL_SHOVEL.get(), (ItemLike)TensuraToolItems.MITHRIL_HOE.get(), (ItemLike)TensuraToolItems.MITHRIL_SICKLE.get(), (ItemLike)TensuraToolItems.MITHRIL_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_LONG_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_KATANA.get(), (ItemLike)TensuraToolItems.MITHRIL_KODACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_TACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_ODACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_SPEAR.get(), 1000, 500);
        magicMeltingGearsx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, "orichalcum", (ItemLike)TensuraArmorItems.ORICHALCUM_HELMET.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_LEGGINGS.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_BOOTS.get(), (ItemLike)TensuraToolItems.ORICHALCUM_PICKAXE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_AXE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SHOVEL.get(), (ItemLike)TensuraToolItems.ORICHALCUM_HOE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SICKLE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_LONG_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_KATANA.get(), (ItemLike)TensuraToolItems.ORICHALCUM_KODACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_TACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_ODACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SPEAR.get(), 1000, 500);
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 2000, (Item)TensuraMaterialItems.ADAMANTITE_NUGGET.get());
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 9000, (Item)TensuraMaterialItems.ADAMANTITE_INGOT.get());
        magicMeltingGearsx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, "adamantite", (ItemLike)TensuraArmorItems.ADAMANTITE_HELMET.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_LEGGINGS.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_BOOTS.get(), (ItemLike)TensuraToolItems.ADAMANTITE_PICKAXE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_AXE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SHOVEL.get(), (ItemLike)TensuraToolItems.ADAMANTITE_HOE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SICKLE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_LONG_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_KATANA.get(), (ItemLike)TensuraToolItems.ADAMANTITE_KODACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_TACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_ODACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SPEAR.get(), 4000, 2000);
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 3000, (Item)TensuraMaterialItems.HIHIIROKANE_NUGGET.get());
        this.magicMeltingx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 13500, (Item)TensuraMaterialItems.HIHIIROKANE_INGOT.get());
        magicMeltingGearsx20(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, "hihiirokane", (ItemLike)TensuraArmorItems.HIHIIROKANE_HELMET.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_LEGGINGS.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_BOOTS.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_PICKAXE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_AXE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SHOVEL.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_HOE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SICKLE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_LONG_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_KATANA.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_KODACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_TACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_ODACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SPEAR.get(), 6000, 3000);
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1000, (Item)TensuraMobDropItems.LOW_QUALITY_MAGIC_CRYSTAL.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 5000, (Item)TensuraMobDropItems.MEDIUM_QUALITY_MAGIC_CRYSTAL.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 10000, (Item)TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 9000, (Item)TensuraBlocks.LOW_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 5000*9, (Item)TensuraBlocks.MEDIUM_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 90000, (Item)TensuraBlocks.HIGH_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 3000, IafItemTags.DRAGON_BLOODS, "dragon_bloods");
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 7000, IafItemTags.DRAGON_HEARTS, "dragon_hearts");
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 25000, TensuraMobDropItems.DRAGON_ESSENCE.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 250, TensuraMobDropItems.MONSTER_LEATHER_D.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 5000, TensuraMobDropItems.MONSTER_LEATHER_C.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1250, TensuraMobDropItems.MONSTER_LEATHER_B.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 2500, TensuraMobDropItems.MONSTER_LEATHER_A.get());
    }

    protected void magicMelting(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, TagKey<Item> input, String path){
        MagicInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, path);
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, path);
    }
    protected void magicMeltingx20(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, TagKey<Item> input, String path){
        MagicInfuserMeltingRecipe.Builder.of(moltenType, amount*20).requires(Ingredient.of(input)).build(consumer, path);
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, amount*20.0F).requires(Ingredient.of(input)).build(consumer, path);
    }

    protected static void magicRemeltingGears(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, String filePrefix, ItemLike helmet, ItemLike chestplate, ItemLike leggings, ItemLike boots, ItemLike pickaxe, ItemLike axe, ItemLike shovel, ItemLike hoe, ItemLike sickle, ItemLike sword, ItemLike shortSword, ItemLike longSword, ItemLike greatSword, ItemLike katana, ItemLike kodachi, ItemLike tachi, ItemLike odachi, ItemLike spear, int moltenArmor, int moltenTools) {
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor).requires(Ingredient.of(new ItemLike[]{helmet})).outputRequires(helmet.asItem().getDefaultInstance()).build(consumer, filePrefix + "_helmet");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor).requires(Ingredient.of(new ItemLike[]{chestplate})).outputRequires(chestplate.asItem().getDefaultInstance()).build(consumer, filePrefix + "_chestplate");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor).requires(Ingredient.of(new ItemLike[]{leggings})).outputRequires(leggings.asItem().getDefaultInstance()).build(consumer, filePrefix + "_leggings");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor).requires(Ingredient.of(new ItemLike[]{boots})).outputRequires(boots.asItem().getDefaultInstance()).build(consumer, filePrefix + "_boots");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{pickaxe})).outputRequires(pickaxe.asItem().getDefaultInstance()).build(consumer, filePrefix + "_pickaxe");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{axe})).outputRequires(axe.asItem().getDefaultInstance()).build(consumer, filePrefix + "_axe");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{shovel})).outputRequires(shovel.asItem().getDefaultInstance()).build(consumer, filePrefix + "_shovel");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{hoe})).outputRequires(hoe.asItem().getDefaultInstance()).build(consumer, filePrefix + "_hoe");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{sickle})).outputRequires(sickle.asItem().getDefaultInstance()).build(consumer, filePrefix + "_sickle");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{sword})).outputRequires(sword.asItem().getDefaultInstance()).build(consumer, filePrefix + "_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{shortSword})).outputRequires(shortSword.asItem().getDefaultInstance()).build(consumer, filePrefix + "_short_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{longSword})).outputRequires(longSword.asItem().getDefaultInstance()).build(consumer, filePrefix + "_long_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{greatSword})).outputRequires(greatSword.asItem().getDefaultInstance()).build(consumer, filePrefix + "_great_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{katana})).outputRequires(katana.asItem().getDefaultInstance()).build(consumer, filePrefix + "_katana");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{kodachi})).outputRequires(kodachi.asItem().getDefaultInstance()).build(consumer, filePrefix + "_kodachi");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{tachi})).outputRequires(tachi.asItem().getDefaultInstance()).build(consumer, filePrefix + "_tachi");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{odachi})).outputRequires(odachi.asItem().getDefaultInstance()).build(consumer, filePrefix + "_odachi");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{spear})).outputRequires(spear.asItem().getDefaultInstance()).build(consumer, filePrefix + "_spear");
    }

    protected static void magicMeltingGearsx20(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, String filePrefix, ItemLike helmet, ItemLike chestplate, ItemLike leggings, ItemLike boots, ItemLike pickaxe, ItemLike axe, ItemLike shovel, ItemLike hoe, ItemLike sickle, ItemLike sword, ItemLike shortSword, ItemLike longSword, ItemLike greatSword, ItemLike katana, ItemLike kodachi, ItemLike tachi, ItemLike odachi, ItemLike spear, int moltenArmor, int moltenTools) {
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor*20).requires(Ingredient.of(new ItemLike[]{helmet})).build(consumer, filePrefix + "_helmet");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor*20).requires(Ingredient.of(new ItemLike[]{chestplate})).build(consumer, filePrefix + "_chestplate");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor*20).requires(Ingredient.of(new ItemLike[]{leggings})).build(consumer, filePrefix + "_leggings");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor*20).requires(Ingredient.of(new ItemLike[]{boots})).build(consumer, filePrefix + "_boots");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{pickaxe})).build(consumer, filePrefix + "_pickaxe");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{axe})).build(consumer, filePrefix + "_axe");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{shovel})).build(consumer, filePrefix + "_shovel");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{hoe})).build(consumer, filePrefix + "_hoe");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{sickle})).build(consumer, filePrefix + "_sickle");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{sword})).build(consumer, filePrefix + "_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{shortSword})).build(consumer, filePrefix + "_short_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{longSword})).build(consumer, filePrefix + "_long_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{greatSword})).build(consumer, filePrefix + "_great_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{katana})).build(consumer, filePrefix + "_katana");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{kodachi})).build(consumer, filePrefix + "_kodachi");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{tachi})).build(consumer, filePrefix + "_tachi");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{odachi})).build(consumer, filePrefix + "_odachi");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20).requires(Ingredient.of(new ItemLike[]{spear})).build(consumer, filePrefix + "_spear");

        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor*20.0F).requires(Ingredient.of(new ItemLike[]{helmet})).build(consumer, filePrefix + "_helmet");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor*20.0F).requires(Ingredient.of(new ItemLike[]{chestplate})).build(consumer, filePrefix + "_chestplate");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor*20.0F).requires(Ingredient.of(new ItemLike[]{leggings})).build(consumer, filePrefix + "_leggings");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor*20.0F).requires(Ingredient.of(new ItemLike[]{boots})).build(consumer, filePrefix + "_boots");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{pickaxe})).build(consumer, filePrefix + "_pickaxe");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{axe})).build(consumer, filePrefix + "_axe");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{shovel})).build(consumer, filePrefix + "_shovel");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{hoe})).build(consumer, filePrefix + "_hoe");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{sickle})).build(consumer, filePrefix + "_sickle");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{sword})).build(consumer, filePrefix + "_sword");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{shortSword})).build(consumer, filePrefix + "_short_sword");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{longSword})).build(consumer, filePrefix + "_long_sword");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{greatSword})).build(consumer, filePrefix + "_great_sword");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{katana})).build(consumer, filePrefix + "_katana");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{kodachi})).build(consumer, filePrefix + "_kodachi");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{tachi})).build(consumer, filePrefix + "_tachi");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{odachi})).build(consumer, filePrefix + "_odachi");
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools*20.0F).requires(Ingredient.of(new ItemLike[]{spear})).build(consumer, filePrefix + "_spear");
    }
    
    protected void magicMelting(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, Item input){
        MagicInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, this.rl(input).getPath());
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, this.rl(input).getPath());
    }

    protected void magicRemelting(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, Item input){
        MagicInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).outputRequires(input.getDefaultInstance()).build(consumer, this.rl(input).getPath());
//        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, this.rl(input).getPath());
    }
    protected void magicMeltingx20(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, Item input){
        MagicInfuserMeltingRecipe.Builder.of(moltenType, amount*20).requires(Ingredient.of(input)).build(consumer, this.rl(input).getPath());
        AutomaticMagicInfuserMeltingRecipe.Builder.of(moltenType, amount*20.0F).requires(Ingredient.of(input)).build(consumer, this.rl(input).getPath());
    }

    protected static void magicInfusionLeft(Consumer<FinishedRecipe> consumer, ItemStack output, ResourceLocation magiculesId, int magicules, int infusionTime, ItemStack input) {
        MagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, magicules).
                requires(Ingredient.of(input)).
                time(infusionTime).
                build(consumer);
        AutomaticMagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, magicules).
                requires(Ingredient.of(input)).
                time(infusionTime).
                build(consumer);
    }
    protected static void magicInfusionRawOres(Consumer<FinishedRecipe> consumer, ItemStack output, ResourceLocation magiculesId, int magicules, int infusionTime, ItemStack iron, ItemStack cooper, ItemStack gold, int cooper_amount, int gold_amount) {
        MagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, magicules).
                requires(Ingredient.of(iron)).
                time(infusionTime).
                build(consumer, "raw_iron");
        MagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, cooper_amount).
                requires(Ingredient.of(cooper)).
                time(infusionTime).
                build(consumer, "raw_cooper");
        MagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, gold_amount).
                requires(Ingredient.of(gold)).
                time(infusionTime).
                build(consumer, "raw_gold");

        AutomaticMagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, magicules).
                requires(Ingredient.of(iron)).
                time(infusionTime).
                build(consumer, "raw_iron");
        AutomaticMagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, cooper_amount).
                requires(Ingredient.of(cooper)).
                time(infusionTime).
                build(consumer, "raw_cooper");
        AutomaticMagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, gold_amount).
                requires(Ingredient.of(gold)).
                time(infusionTime).
                build(consumer, "raw_gold");
    }

    protected static void magicInfusionLeft(Consumer<FinishedRecipe> consumer, ItemStack output, ResourceLocation magiculesId, int magicules, int infusionTime, TagKey<Item> input, String path) {
        MagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, magicules).
                requires(Ingredient.of(input)).
                time(infusionTime).
                build(consumer, path);

        AutomaticMagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, magicules).
                requires(Ingredient.of(input)).
                time(infusionTime).
                build(consumer, path);
    }

    protected static void magicInfusionOres(Consumer<FinishedRecipe> consumer, ItemStack input, ItemStack output, ResourceLocation magicules, int amount, int time, String path){
        MagicInfusionRecipe.Builder.of(output).requires(Ingredient.of(input)).magicules(magicules, amount).time(time).build(consumer, path);
    }


}
