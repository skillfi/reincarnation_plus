package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.manasmods.tensura.block.SimpleBlock;
import com.github.manasmods.tensura.data.TensuraTags;
import com.github.manasmods.tensura.data.gen.TensuraMoltenMaterialProvider;
import com.github.manasmods.tensura.data.recipe.KilnMeltingRecipe;
import com.github.manasmods.tensura.registry.blocks.TensuraBlocks;
import com.github.manasmods.tensura.registry.items.TensuraArmorItems;
import com.github.manasmods.tensura.registry.items.TensuraMaterialItems;
import com.github.manasmods.tensura.registry.items.TensuraMobDropItems;
import com.github.manasmods.tensura.registry.items.TensuraToolItems;
import com.github.skillfi.reincarnation_plus.core.registry.ReiItems;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.libs.data.ReiTags;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.MagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.MagicInfusionRecipe;
import net.minecraft.data.recipes.FinishedRecipe;
import com.github.manasmods.manascore.api.data.gen.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
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
        ShapedRecipeBuilder.shaped(ReiItems.MAGIC_MECH.get())
                .define('S', Items.IRON_INGOT)
                .define('M', TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get())
                .define('W', ItemTags.WOOL)
                .pattern("SSS")
                .pattern("MWW")
                .pattern("SSS")
                .unlockedBy("has_magisteel", has(TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get()))
                .save(consumer);
    }

    protected void magicInfusion(Consumer<FinishedRecipe> consumer){
        magicInfusionLeft(consumer, TensuraBlocks.MAGIC_ORE.get().asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 15, 5000, ItemTags.IRON_ORES, "iron");
        magicInfusionLeft(consumer, TensuraBlocks.MAGIC_ORE.get().asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 25, 5000, ItemTags.COPPER_ORES, "cooper");
        magicInfusionLeft(consumer, TensuraBlocks.MAGIC_ORE.get().asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 10, 5000, ItemTags.GOLD_ORES, "gold");
        magicInfusionLeft(consumer, TensuraMaterialItems.HIPOKUTE_SEEDS.get().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 3, 2400, Items.WHEAT_SEEDS.getDefaultInstance());
        magicInfusionRawOres(consumer, TensuraMaterialItems.MAGIC_ORE.get().asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 15, 4800, Items.RAW_IRON.getDefaultInstance(), Items.RAW_COPPER.getDefaultInstance(), Items.RAW_GOLD.getDefaultInstance(), 25, 10);
        magicInfusionLeft(consumer, Blocks.SLIME_BLOCK.asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, 3600, Blocks.GREEN_WOOL.asItem().getDefaultInstance());
        magicInfusionLeft(consumer, Blocks.AMETHYST_BLOCK.asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, 2400, Blocks.QUARTZ_BLOCK.asItem().getDefaultInstance());
        magicInfusionLeft(consumer, Blocks.CALCITE.asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, 7400, Blocks.DIORITE.asItem().getDefaultInstance());
        magicInfusionLeft(consumer, Items.GUNPOWDER.getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, 6000, Items.FLINT.getDefaultInstance());
        magicInfusionLeft(consumer, Blocks.ANCIENT_DEBRIS.asItem().getDefaultInstance(), ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 40, 120000, Blocks.IRON_BLOCK.asItem().getDefaultInstance());

    }

    private void kilnMelting(Consumer<FinishedRecipe> consumer){
        this.kilnMelting(consumer, TensuraMoltenMaterialProvider.MOLTEN_MAGISTEEL, 1, (Item)TensuraMobDropItems.LOW_QUALITY_MAGIC_CRYSTAL.get());
        this.kilnMelting(consumer, TensuraMoltenMaterialProvider.MOLTEN_MAGISTEEL, 5, (Item)TensuraMobDropItems.MEDIUM_QUALITY_MAGIC_CRYSTAL.get());
        this.kilnMelting(consumer, TensuraMoltenMaterialProvider.MOLTEN_MAGISTEEL, 10, (Item)TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get());
        this.kilnMelting(consumer, TensuraMoltenMaterialProvider.MOLTEN_MAGISTEEL, 1*8, (Item)TensuraBlocks.LOW_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem());
        this.kilnMelting(consumer, TensuraMoltenMaterialProvider.MOLTEN_MAGISTEEL, 5*8, (Item)TensuraBlocks.MEDIUM_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem());
        this.kilnMelting(consumer, TensuraMoltenMaterialProvider.MOLTEN_MAGISTEEL, 10*8, (Item)TensuraBlocks.HIGH_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem());
    }

    private void magicMelting(Consumer<FinishedRecipe> consumer){
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, ((SimpleBlock)TensuraBlocks.MAGIC_ORE.get()).asItem());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, ((RotatedPillarBlock)TensuraBlocks.DEEPSLATE_MAGIC_ORE.get()).asItem());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, (Item)TensuraMaterialItems.MAGIC_ORE.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 9, ((SimpleBlock)TensuraBlocks.MAGIC_ORE_BLOCK.get()).asItem());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, (Item)TensuraMaterialItems.HIGH_MAGISTEEL_NUGGET.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 2, com.github.manasmods.tensura.data.TensuraTags.Items.NUGGETS_MITHRIL, "mithril_nugget");
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 2, com.github.manasmods.tensura.data.TensuraTags.Items.NUGGETS_ORICHALCUM, "orichalcum_nugget");
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 4, (Item)TensuraMaterialItems.PURE_MAGISTEEL_NUGGET.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 36, (Item)TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, (Item) TensuraToolItems.KANABO.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, (Item)TensuraToolItems.PURE_MAGISTEEL_KUNAI.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 4, (Item)TensuraToolItems.DRAGON_KNUCKLE.get());
        magicMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, "mithril", (ItemLike) TensuraArmorItems.MITHRIL_HELMET.get(), (ItemLike)TensuraArmorItems.MITHRIL_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.MITHRIL_LEGGINGS.get(), (ItemLike)TensuraArmorItems.MITHRIL_BOOTS.get(), (ItemLike)TensuraToolItems.MITHRIL_PICKAXE.get(), (ItemLike)TensuraToolItems.MITHRIL_AXE.get(), (ItemLike)TensuraToolItems.MITHRIL_SHOVEL.get(), (ItemLike)TensuraToolItems.MITHRIL_HOE.get(), (ItemLike)TensuraToolItems.MITHRIL_SICKLE.get(), (ItemLike)TensuraToolItems.MITHRIL_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_LONG_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.MITHRIL_KATANA.get(), (ItemLike)TensuraToolItems.MITHRIL_KODACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_TACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_ODACHI.get(), (ItemLike)TensuraToolItems.MITHRIL_SPEAR.get(), 4, 2);
        magicMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, "orichalcum", (ItemLike)TensuraArmorItems.ORICHALCUM_HELMET.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_LEGGINGS.get(), (ItemLike)TensuraArmorItems.ORICHALCUM_BOOTS.get(), (ItemLike)TensuraToolItems.ORICHALCUM_PICKAXE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_AXE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SHOVEL.get(), (ItemLike)TensuraToolItems.ORICHALCUM_HOE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SICKLE.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_LONG_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.ORICHALCUM_KATANA.get(), (ItemLike)TensuraToolItems.ORICHALCUM_KODACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_TACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_ODACHI.get(), (ItemLike)TensuraToolItems.ORICHALCUM_SPEAR.get(), 4, 2);
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 8, (Item)TensuraMaterialItems.ADAMANTITE_NUGGET.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 36, (Item)TensuraMaterialItems.ADAMANTITE_INGOT.get());
        magicMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, "adamantite", (ItemLike)TensuraArmorItems.ADAMANTITE_HELMET.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_LEGGINGS.get(), (ItemLike)TensuraArmorItems.ADAMANTITE_BOOTS.get(), (ItemLike)TensuraToolItems.ADAMANTITE_PICKAXE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_AXE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SHOVEL.get(), (ItemLike)TensuraToolItems.ADAMANTITE_HOE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SICKLE.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_LONG_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.ADAMANTITE_KATANA.get(), (ItemLike)TensuraToolItems.ADAMANTITE_KODACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_TACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_ODACHI.get(), (ItemLike)TensuraToolItems.ADAMANTITE_SPEAR.get(), 16, 8);
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 12, (Item)TensuraMaterialItems.HIHIIROKANE_NUGGET.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 54, (Item)TensuraMaterialItems.HIHIIROKANE_INGOT.get());
        magicMeltingGears(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, "hihiirokane", (ItemLike)TensuraArmorItems.HIHIIROKANE_HELMET.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_CHESTPLATE.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_LEGGINGS.get(), (ItemLike)TensuraArmorItems.HIHIIROKANE_BOOTS.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_PICKAXE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_AXE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SHOVEL.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_HOE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SICKLE.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SHORT_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_LONG_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_GREAT_SWORD.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_KATANA.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_KODACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_TACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_ODACHI.get(), (ItemLike)TensuraToolItems.HIHIIROKANE_SPEAR.get(), 24, 12);
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, (Item)TensuraMobDropItems.LOW_QUALITY_MAGIC_CRYSTAL.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 5, (Item)TensuraMobDropItems.MEDIUM_QUALITY_MAGIC_CRYSTAL.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 10, (Item)TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1*8, (Item)TensuraBlocks.LOW_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 5*8, (Item)TensuraBlocks.MEDIUM_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 10*8, (Item)TensuraBlocks.HIGH_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 3, IafItemTags.DRAGON_BLOODS, "dragon_bloods");
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 7, IafItemTags.DRAGON_HEARTS, "dragon_hearts");
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 25, TensuraMobDropItems.DRAGON_ESSENCE.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 1, TensuraMobDropItems.MONSTER_LEATHER_D.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 2, TensuraMobDropItems.MONSTER_LEATHER_C.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 5, TensuraMobDropItems.MONSTER_LEATHER_B.get());
        this.magicMelting(consumer, ReiMoltenMaterialProvider.MOLTEN_MAGICULES, 10, TensuraMobDropItems.MONSTER_LEATHER_A.get());
    }

    protected void magicMelting(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, TagKey<Item> input, String path){
        MagicInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, path);
    }

    protected void kilnMelting(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, Item input) {
        KilnMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(new ItemLike[]{input})).build(consumer, this.rl(input).getPath());
    }

    protected void kilnMelting(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, TagKey<Item> input, String path) {
        KilnMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(input)).build(consumer, path);
    }

    protected static void magicMeltingGears(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, String filePrefix, ItemLike helmet, ItemLike chestplate, ItemLike leggings, ItemLike boots, ItemLike pickaxe, ItemLike axe, ItemLike shovel, ItemLike hoe, ItemLike sickle, ItemLike sword, ItemLike shortSword, ItemLike longSword, ItemLike greatSword, ItemLike katana, ItemLike kodachi, ItemLike tachi, ItemLike odachi, ItemLike spear, int moltenArmor, int moltenTools) {
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor).requires(Ingredient.of(new ItemLike[]{helmet})).build(consumer, filePrefix + "_helmet");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor).requires(Ingredient.of(new ItemLike[]{chestplate})).build(consumer, filePrefix + "_chestplate");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor).requires(Ingredient.of(new ItemLike[]{leggings})).build(consumer, filePrefix + "_leggings");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenArmor).requires(Ingredient.of(new ItemLike[]{boots})).build(consumer, filePrefix + "_boots");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{pickaxe})).build(consumer, filePrefix + "_pickaxe");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{axe})).build(consumer, filePrefix + "_axe");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{shovel})).build(consumer, filePrefix + "_shovel");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{hoe})).build(consumer, filePrefix + "_hoe");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{sickle})).build(consumer, filePrefix + "_sickle");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{sword})).build(consumer, filePrefix + "_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{shortSword})).build(consumer, filePrefix + "_short_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{longSword})).build(consumer, filePrefix + "_long_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{greatSword})).build(consumer, filePrefix + "_great_sword");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{katana})).build(consumer, filePrefix + "_katana");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{kodachi})).build(consumer, filePrefix + "_kodachi");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{tachi})).build(consumer, filePrefix + "_tachi");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{odachi})).build(consumer, filePrefix + "_odachi");
        MagicInfuserMeltingRecipe.Builder.of(moltenType, moltenTools).requires(Ingredient.of(new ItemLike[]{spear})).build(consumer, filePrefix + "_spear");
    }
    
    protected void magicMelting(Consumer<FinishedRecipe> consumer, ResourceLocation moltenType, int amount, Item input){
        MagicInfuserMeltingRecipe.Builder.of(moltenType, amount).requires(Ingredient.of(new ItemLike[]{input})).build(consumer, this.rl(input).getPath());
    }

    protected static void magicInfusionLeft(Consumer<FinishedRecipe> consumer, ItemStack output, ResourceLocation moltenType, int amount, int infusionTime, ItemStack input) {
        MagicInfusionRecipe.Builder.of(output).
                magicules(moltenType, amount).
                requires(Ingredient.of(input)).
                time(infusionTime).
                build(consumer);
    }
    protected static void magicInfusionRawOres(Consumer<FinishedRecipe> consumer, ItemStack output, ResourceLocation moltenType, int amount, int infusionTime, ItemStack iron, ItemStack cooper, ItemStack gold, int cooper_amount, int gold_amount) {
        MagicInfusionRecipe.Builder.of(output).
                magicules(moltenType, amount).
                requires(Ingredient.of(iron)).
                time(infusionTime).
                build(consumer, "raw_iron");
        MagicInfusionRecipe.Builder.of(output).
                magicules(moltenType, cooper_amount).
                requires(Ingredient.of(cooper)).
                time(infusionTime).
                build(consumer, "raw_cooper");
        MagicInfusionRecipe.Builder.of(output).
                magicules(moltenType, gold_amount).
                requires(Ingredient.of(gold)).
                time(infusionTime).
                build(consumer, "raw_gold");
    }
    protected static void magicInfusionLeft(Consumer<FinishedRecipe> consumer, ItemStack output, ResourceLocation moltenType, int amount, int infusionTime,TagKey<Item> input, String path) {
        MagicInfusionRecipe.Builder.of(output).
                magicules(moltenType, amount).
                requires(Ingredient.of(input)).
                time(infusionTime).
                build(consumer, path);
    }


}
