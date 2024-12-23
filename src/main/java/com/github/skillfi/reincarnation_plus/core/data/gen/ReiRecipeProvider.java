package com.github.skillfi.reincarnation_plus.core.data.gen;

import com.github.alexthe666.iceandfire.enums.EnumDragonArmor;
import com.github.manasmods.tensura.registry.blocks.TensuraBlocks;
import com.github.manasmods.tensura.registry.items.TensuraArmorItems;
import com.github.manasmods.tensura.registry.items.TensuraMaterialItems;
import com.github.manasmods.tensura.registry.items.TensuraMobDropItems;
import com.github.manasmods.tensura.registry.items.TensuraToolItems;
import com.github.skillfi.reincarnation_plus.core.data.recipe.auto.AutoInfuserEvolvingRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.auto.AutoInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.auto.AutoMagicInfusionRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.InfuserEvolvingRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.materials.RecipeMaterials;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.core.registry.items.ReiItems;
import com.github.skillfi.reincarnation_plus.core.data.ReiTags;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.MagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.MagicInfusionRecipe;
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
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.units.qual.C;

import java.util.function.Consumer;

import static com.github.skillfi.reincarnation_plus.core.data.GearSet.*;
import static com.github.skillfi.reincarnation_plus.core.data.recipe.materials.RecipeMaterials.meltingMaterials;

public class ReiRecipeProvider extends RecipeProvider {

    public ReiRecipeProvider(GatherDataEvent gatherDataEvent) {
        super(gatherDataEvent);
    }

    protected void generate(Consumer<FinishedRecipe> consumer) {
        this.buildRecipes(consumer);
        this.magicInfusion(consumer);
        this.infuserMelting(consumer);
        this.infuserEvolving(consumer);
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

    private void infuserMelting(Consumer<FinishedRecipe> consumer){
        for (RecipeMaterials material : meltingMaterials) {
            if (!material.isTag()) {
                this.infuserMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, material.getPrimaryAmount(), material.getInput());
            } else {
                this.infuserMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, material.getPrimaryAmount(), material.getItemTagKey(), material.getTagName(), true);
            }
        }

        infuserMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, ReiMoltenMaterialProvider.EP, "mithril", (ItemLike) TensuraArmorItems.MITHRIL_HELMET.get(), (ItemLike)TensuraArmorItems.MITHRIL_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.MITHRIL_LEGGINGS.get(), (ItemLike)TensuraArmorItems.MITHRIL_BOOTS.get(), (ItemLike)TensuraToolItems.MITHRIL_PICKAXE.get(), (ItemLike)TensuraToolItems.MITHRIL_AXE.get(), (ItemLike)TensuraToolItems.MITHRIL_SHOVEL.get(), (ItemLike)TensuraToolItems.MITHRIL_HOE.get(), (ItemLike)TensuraToolItems.MITHRIL_SICKLE.get(), (ItemLike)TensuraToolItems.MITHRIL_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_LONG_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_KATANA.get(), (ItemLike)TensuraToolItems.MITHRIL_KODACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_TACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_ODACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_SPEAR.get(), 4000, 2000, true);
        infuserMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, ReiMoltenMaterialProvider.EP, "orichalcum", (ItemLike)TensuraArmorItems.ORICHALCUM_HELMET.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_LEGGINGS.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_BOOTS.get(), (ItemLike)TensuraToolItems.ORICHALCUM_PICKAXE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_AXE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SHOVEL.get(), (ItemLike)TensuraToolItems.ORICHALCUM_HOE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SICKLE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_LONG_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_KATANA.get(), (ItemLike)TensuraToolItems.ORICHALCUM_KODACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_TACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_ODACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SPEAR.get(), 4000, 2000, true);
        infuserMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, ReiMoltenMaterialProvider.EP, "adamantite", (ItemLike)TensuraArmorItems.ADAMANTITE_HELMET.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_LEGGINGS.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_BOOTS.get(), (ItemLike)TensuraToolItems.ADAMANTITE_PICKAXE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_AXE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SHOVEL.get(), (ItemLike)TensuraToolItems.ADAMANTITE_HOE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SICKLE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_LONG_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_KATANA.get(), (ItemLike)TensuraToolItems.ADAMANTITE_KODACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_TACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_ODACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SPEAR.get(), 16000, 8000, true);
        infuserMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, ReiMoltenMaterialProvider.EP, "hihiirokane", (ItemLike)TensuraArmorItems.HIHIIROKANE_HELMET.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_LEGGINGS.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_BOOTS.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_PICKAXE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_AXE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SHOVEL.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_HOE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SICKLE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_LONG_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_KATANA.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_KODACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_TACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_ODACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SPEAR.get(), 24000, 12000, true);
        infuserMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, ReiMoltenMaterialProvider.EP, "low_magisteel", (ItemLike)TensuraArmorItems.LOW_MAGISTEEL_HELMET.get(), (ItemLike)TensuraArmorItems.LOW_MAGISTEEL_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.LOW_MAGISTEEL_LEGGINGS.get(), (ItemLike)TensuraArmorItems.LOW_MAGISTEEL_BOOTS.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_PICKAXE.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_AXE.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_SHOVEL.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_HOE.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_SICKLE.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_SWORD.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_LONG_SWORD.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_KATANA.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_KODACHI.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_TACHI.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_ODACHI.get(), (ItemLike)TensuraToolItems.LOW_MAGISTEEL_SPEAR.get(), 1000, 0, true);
        infuserMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, ReiMoltenMaterialProvider.EP, "high_magisteel", (ItemLike)TensuraArmorItems.HIGH_MAGISTEEL_HELMET.get(), (ItemLike)TensuraArmorItems.HIGH_MAGISTEEL_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.HIGH_MAGISTEEL_LEGGINGS.get(), (ItemLike)TensuraArmorItems.HIGH_MAGISTEEL_BOOTS.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_PICKAXE.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_AXE.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_SHOVEL.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_HOE.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_SICKLE.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_SWORD.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_LONG_SWORD.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_KATANA.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_KODACHI.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_TACHI.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_ODACHI.get(), (ItemLike)TensuraToolItems.HIGH_MAGISTEEL_SPEAR.get(), 4000, 2000, true);
        infuserMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, ReiMoltenMaterialProvider.EP, "pure_magisteel", (ItemLike)TensuraArmorItems.PURE_MAGISTEEL_HELMET.get(), (ItemLike)TensuraArmorItems.PURE_MAGISTEEL_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.PURE_MAGISTEEL_LEGGINGS.get(), (ItemLike)TensuraArmorItems.PURE_MAGISTEEL_BOOTS.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_PICKAXE.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_AXE.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_SHOVEL.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_HOE.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_SICKLE.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_SWORD.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_LONG_SWORD.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_KATANA.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_KODACHI.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_TACHI.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_ODACHI.get(), (ItemLike)TensuraToolItems.PURE_MAGISTEEL_SPEAR.get(), 8000, 4000, true);
        infuserMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES,  ReiMoltenMaterialProvider.EP,"gold", Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_PICKAXE, Items.GOLDEN_AXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_HOE, (ItemLike)TensuraToolItems.GOLDEN_SICKLE.get(), Items.GOLDEN_SWORD, (ItemLike)TensuraToolItems.GOLDEN_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.GOLDEN_LONG_SWORD.get(), (ItemLike)TensuraToolItems.GOLDEN_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.GOLDEN_KATANA.get(), (ItemLike)TensuraToolItems.GOLDEN_KODACHI.get(), (ItemLike)TensuraToolItems.GOLDEN_TACHI.get(), (ItemLike)TensuraToolItems.GOLDEN_ODACHI.get(), (ItemLike)TensuraToolItems.GOLDEN_SPEAR.get(), 0, 0, false);
        infuserMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES,  ReiMoltenMaterialProvider.EP,"iron", Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_SHOVEL, Items.IRON_HOE, (ItemLike)TensuraToolItems.IRON_SICKLE.get(), Items.IRON_SWORD, (ItemLike)TensuraToolItems.IRON_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.IRON_LONG_SWORD.get(), (ItemLike)TensuraToolItems.IRON_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.IRON_KATANA.get(), (ItemLike)TensuraToolItems.IRON_KODACHI.get(), (ItemLike)TensuraToolItems.IRON_TACHI.get(), (ItemLike)TensuraToolItems.IRON_ODACHI.get(), (ItemLike)TensuraToolItems.IRON_SPEAR.get(), 0, 0, false);
        infuserMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES,  ReiMoltenMaterialProvider.EP,"silver", (ItemLike)TensuraArmorItems.SILVER_HELMET.get(), (ItemLike)TensuraArmorItems.SILVER_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.SILVER_LEGGINGS.get(), (ItemLike)TensuraArmorItems.SILVER_BOOTS.get(), (ItemLike)TensuraToolItems.SILVER_PICKAXE.get(), (ItemLike)TensuraToolItems.SILVER_AXE.get(), (ItemLike)TensuraToolItems.SILVER_SHOVEL.get(), (ItemLike)TensuraToolItems.SILVER_HOE.get(), (ItemLike)TensuraToolItems.SILVER_SICKLE.get(), (ItemLike)TensuraToolItems.SILVER_SWORD.get(), (ItemLike)TensuraToolItems.SILVER_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.SILVER_LONG_SWORD.get(), (ItemLike)TensuraToolItems.SILVER_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.SILVER_KATANA.get(), (ItemLike)TensuraToolItems.SILVER_KODACHI.get(), (ItemLike)TensuraToolItems.SILVER_TACHI.get(), (ItemLike)TensuraToolItems.SILVER_ODACHI.get(), (ItemLike)TensuraToolItems.SILVER_SPEAR.get(), 0, 0, false);
        infuserMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES,  ReiMoltenMaterialProvider.EP,"netherite", Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS, Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_HOE, (ItemLike)TensuraToolItems.NETHERITE_SICKLE.get(), Items.NETHERITE_SWORD, (ItemLike)TensuraToolItems.NETHERITE_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.NETHERITE_LONG_SWORD.get(), (ItemLike)TensuraToolItems.NETHERITE_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.NETHERITE_KATANA.get(), (ItemLike)TensuraToolItems.NETHERITE_KODACHI.get(), (ItemLike)TensuraToolItems.NETHERITE_TACHI.get(), (ItemLike)TensuraToolItems.NETHERITE_ODACHI.get(), (ItemLike)TensuraToolItems.NETHERITE_SPEAR.get(), 0, 0, false);

        for (EnumDragonArmor dragonArmor:EnumDragonArmor.values()){
            magicRemelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 0, ReiMoltenMaterialProvider.EP, 378, dragonArmor.helmet.get());
            magicRemelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 0, ReiMoltenMaterialProvider.EP, 378,dragonArmor.chestplate.get());
            magicRemelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 0, ReiMoltenMaterialProvider.EP, 378,dragonArmor.leggings.get());
            magicRemelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 0, ReiMoltenMaterialProvider.EP, 378,dragonArmor.boots.get());
        }

        magicRemelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 0, ReiMoltenMaterialProvider.EP, 378, Items.ENCHANTED_BOOK);
    }

    private void infuserEvolving(Consumer<FinishedRecipe> consumer){
        for (Item item: pureMagisteel){
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 225000);
        }
        for (Item item: HIGH_MAGISTEEL)
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 52000);
        for (Item item: lowMagisteel){
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 18000);
        }
        for (Item item: ironGear){
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 50000);
        }
        for (Item item: silverGear){
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 25000);
        }
        for (Item item: MITHRIL){
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 225000);
        }
        for (Item item: CHARYBDIS){
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 1000000);
        }
        for (Item item: ADAMANTITE)
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 750000);
        for (Item item: ORICHALCUM)
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 750000);
        for (Item item: HIHIIROKANE)
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 1000000);
        for (Item item: netheriteGear)
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 50000);
        for (Item item: MONSTER_LEATHER_D)
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 2500);
        for (Item item: MONSTER_LEATHER_C)
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 5000);
        for (Item item: MONSTER_LEATHER_B)
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 8000);
        for (Item item: MONSTER_LEATHER_A)
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 80000);
        for (Item item: MONSTER_LEATHER_SPECIAL_A)
            infuserEvolve(consumer, item, ReiMoltenMaterialProvider.EP, 80000);
    }

    protected void infuserMelting(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, TagKey<Item> input, String path, boolean kiln){
        if (!kiln){
            MagicInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, path);
            AutoInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, path);
        } else {
            MagicInfuserMeltingRecipe.Builder.of(moltenType, amount*20).requires(Ingredient.of(input)).build(consumer, path);
            AutoInfuserMeltingRecipe.Builder.of(moltenType, amount*20).requires(Ingredient.of(input)).build(consumer, path);
        }


    }

    protected void infuserEvolve(Consumer<FinishedRecipe> consumer, Item input, ResourceLocation moltenType, int amount){
        InfuserEvolvingRecipe.Builder.of(moltenType, amount)
                .requires(Ingredient.of(input))
                .build(consumer, this.rl(input).getPath());
        AutoInfuserEvolvingRecipe.Builder.of(moltenType, amount)
                .requires(Ingredient.of(input))
                .build(consumer, this.rl(input).getPath());
    }

    protected static void infuserMeltingGears(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, ResourceLocation secondaryType, String filePrefix,
                                              ItemLike helmet, ItemLike chestplate, ItemLike leggings, ItemLike boots,
                                              ItemLike pickaxe, ItemLike axe, ItemLike shovel, ItemLike hoe, ItemLike sickle,
                                              ItemLike sword, ItemLike shortSword, ItemLike longSword, ItemLike greatSword,
                                              ItemLike katana, ItemLike kodachi, ItemLike tachi, ItemLike odachi, ItemLike spear,
                                              int moltenArmor, int moltenTools, boolean kiln) {
        // Масиви для предметів та їх назв
        ItemLike[] armorItems = {helmet, chestplate, leggings, boots};
        String[] armorNames = {"helmet", "chestplate", "leggings", "boots"};
        int[] armorAmounts = {moltenArmor, moltenArmor, moltenArmor, moltenArmor};

        ItemLike[] toolItems = {pickaxe, axe, shovel, hoe, sickle, sword, shortSword, longSword, greatSword, katana, kodachi, tachi, odachi, spear};
        String[] toolNames = {"pickaxe", "axe", "shovel", "hoe", "sickle", "sword", "short_sword", "long_sword", "great_sword", "katana", "kodachi", "tachi", "odachi", "spear"};
        int[] toolAmounts = {moltenTools, moltenTools, moltenTools, moltenTools, moltenTools, moltenTools, moltenTools, moltenTools, moltenTools, moltenTools, moltenTools, moltenTools, moltenTools, moltenTools};

        // Додавання рецептів для броні та інструментів
        if (!kiln) {
            for (int i = 0; i < armorItems.length; i++) {
                MagicInfuserMeltingRecipe.Builder.of(moltenType, armorAmounts[i])
                        .requires(Ingredient.of(armorItems[i]))
                        .secondary(secondaryType, 378)
                        .build(consumer, filePrefix + "_" + armorNames[i]);
                AutoInfuserMeltingRecipe.Builder.of(moltenType, armorAmounts[i])
                        .requires(Ingredient.of(armorItems[i]))
                        .secondary(secondaryType, 378)
                        .build(consumer, filePrefix + "_" + armorNames[i]);
            }
            for (int i = 0; i < toolItems.length; i++) {
                MagicInfuserMeltingRecipe.Builder.of(moltenType, toolAmounts[i])
                        .requires(Ingredient.of(toolItems[i]))
                        .secondary(secondaryType, 378)
                        .build(consumer, filePrefix + "_" + toolNames[i]);
                AutoInfuserMeltingRecipe.Builder.of(moltenType, toolAmounts[i])
                        .requires(Ingredient.of(toolItems[i]))
                        .secondary(secondaryType, 378)
                        .build(consumer, filePrefix + "_" + toolNames[i]);
            }
        } else {
            // Додавання рецептів із множником
            int armorAmountMultiplied = moltenArmor * 20;
            int toolAmountMultiplied = moltenTools * 20;

            for (int i = 0; i < armorItems.length; i++) {
                MagicInfuserMeltingRecipe.Builder.of(moltenType, armorAmountMultiplied)
                        .requires(Ingredient.of(armorItems[i]))
                        .secondary(secondaryType, 378)
                        .build(consumer, filePrefix + "_" + armorNames[i]);
                AutoInfuserMeltingRecipe.Builder.of(moltenType, armorAmountMultiplied)
                        .requires(Ingredient.of(armorItems[i]))
                        .secondary(secondaryType, 378)
                        .build(consumer, filePrefix + "_" + armorNames[i]);
            }
            for (int i = 0; i < toolItems.length; i++) {
                MagicInfuserMeltingRecipe.Builder.of(moltenType, toolAmountMultiplied)
                        .requires(Ingredient.of(toolItems[i]))
                        .secondary(secondaryType, 378)
                        .build(consumer, filePrefix + "_" + toolNames[i]);
                AutoInfuserMeltingRecipe.Builder.of(moltenType, toolAmountMultiplied)
                        .requires(Ingredient.of(toolItems[i]))
                        .secondary(secondaryType, 378)
                        .build(consumer, filePrefix + "_" + toolNames[i]);
            }
        }
    }
    
    protected void infuserMelting(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, Item input){
        MagicInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, this.rl(input).getPath());
        AutoInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, this.rl(input).getPath());
    }

    protected void magicRemelting(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, ResourceLocation secondaryType, int secondaryAmount, Item input){
        MagicInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).secondary(secondaryType, secondaryAmount).build(consumer, this.rl(input).getPath());
        AutoInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).secondary(secondaryType, secondaryAmount).build(consumer, this.rl(input).getPath());
    }

    protected void magicMeltingx20(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, Item input){
        MagicInfuserMeltingRecipe.Builder.of(moltenType, amount*20).requires(Ingredient.of(input)).build(consumer, this.rl(input).getPath());
        AutoInfuserMeltingRecipe.Builder.of(moltenType, amount*20).requires(Ingredient.of(input)).build(consumer, this.rl(input).getPath());
    }

    protected static void magicInfusionLeft(Consumer<FinishedRecipe> consumer, ItemStack output, ResourceLocation magiculesId, int magicules, int infusionTime, ItemStack input) {
        MagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, magicules).
                requires(Ingredient.of(input)).
                time(infusionTime).
                build(consumer);

        AutoMagicInfusionRecipe.Builder.of(output).
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

        AutoMagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, magicules).
                requires(Ingredient.of(iron)).
                time(infusionTime).
                build(consumer, "raw_iron");
        AutoMagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, cooper_amount).
                requires(Ingredient.of(cooper)).
                time(infusionTime).
                build(consumer, "raw_cooper");
        AutoMagicInfusionRecipe.Builder.of(output).
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

        AutoMagicInfusionRecipe.Builder.of(output).
                magicules(magiculesId, magicules).
                requires(Ingredient.of(input)).
                time(infusionTime).
                build(consumer, path);
    }

    protected static void magicInfusionOres(Consumer<FinishedRecipe> consumer, ItemStack input, ItemStack output, ResourceLocation magicules, int amount, int time, String path){
        MagicInfusionRecipe.Builder.of(output).requires(Ingredient.of(input)).magicules(magicules, amount).time(time).build(consumer, path);
        AutoMagicInfusionRecipe.Builder.of(output).requires(Ingredient.of(input)).magicules(magicules, amount).time(time).build(consumer, path);
    }


}
