package com.github.skillfi.reincarnation_plus.integration.jei;

import com.github.manasmods.tensura.data.recipe.KilnMixingRecipe;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.registry.items.ReiItems;
import com.github.skillfi.reincarnation_plus.core.utils.RenderUtils;
import com.github.skillfi.reincarnation_plus.libs.data.pack.MagicInfuserMoltenMaterial;
import com.github.skillfi.reincarnation_plus.libs.data.pack.ReiData;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.infuser.MagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.infuser.MagicInfusionRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.ArrayList;
import java.util.List;

public class MagicInfuserMeltingRecipeCategory implements IRecipeCategory<MagicInfuserMeltingRecipe> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ReiMod.MODID, "textures/gui/magic_infuser/jei_melting.png");
    private static final Component TITLE = Component.translatable("reincarnation_plus.jei.melting.title");
    static final ResourceLocation UID = new ResourceLocation(ReiMod.MODID, "magic_infuser/melting");
    private final IDrawable background;
    private final IDrawable icon;
    public ItemStack inputStack = ItemStack.EMPTY;

    public MagicInfuserMeltingRecipeCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ((Item) ReiItems.MAGIC_INFUSER.get()).getDefaultInstance());
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 177, 121);
    }

    public RecipeType<MagicInfuserMeltingRecipe> getRecipeType() {
        return ReiJeiPlugin.MAGIC_INFUSER_MELTING;
    }

    public void draw(MagicInfuserMeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        for(MagicInfuserMoltenMaterial moltenMaterial : ReiData.getMagicInfuserMoltenMaterials()) {
            if (!recipe.getMoltenType().equals(MagicInfusionRecipe.EMPTY) && moltenMaterial.getMoltenType().equals(recipe.getMoltenType())) {
                RenderUtils.renderMoltenMaterial(stack, moltenMaterial, recipe.getMoltenAmount(), 35000);
            }

            if (!recipe.getSecondaryType().equals(MagicInfusionRecipe.EMPTY) && moltenMaterial.getMoltenType().equals(recipe.getSecondaryType())) {
                RenderUtils.renderMoltenMaterial(stack, moltenMaterial, recipe.getSecondaryAmount(), 35000);
            }
        }
    }

    public int calculateEpFromEnchantments(ItemStack itemStack, int amount) {
        // Перевірка на наявність чарів
        if (!itemStack.hasTag() || !itemStack.getTag().contains("Enchantments", 9)) {
            return 0; // Якщо немає чарів, досвід дорівнює 0
        }

        // Отримуємо список чарів
        ListTag enchantments = itemStack.getTag().getList("Enchantments", 10);

        int totalXp = amount;
        int totalLVl = 0;

        // Розрахунок досвіду для кожного зачарування
        for (int i = 0; i < enchantments.size(); i++) {
            CompoundTag enchantment = enchantments.getCompound(i);
            int level = enchantment.getInt("lvl"); // Рівень зачарування
            totalLVl += level;
        }

        return totalXp * totalLVl;
    }

    public List<Component> getTooltipStrings(MagicInfuserMeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        ArrayList<Component> tooltip = new ArrayList();

        for(MagicInfuserMoltenMaterial moltenMaterial : ReiData.getMagicInfuserMoltenMaterials()) {
            if (!recipe.getMoltenType().equals(MagicInfusionRecipe.EMPTY)) {
                if (moltenMaterial.isRightBar()) {
                    if (this.isHovering(145, 6, 13, 74, mouseX, mouseY) && !recipe.getMoltenType().equals(KilnMixingRecipe.EMPTY) && moltenMaterial.isRightBar()) {
                        if (moltenMaterial.getMoltenType().equals(recipe.getMoltenType())) {
                            tooltip.add(RenderUtils.toolTipFromMoltenMaterial(moltenMaterial, recipe.getMoltenAmount(), 35000));
                        }

                        if (moltenMaterial.getMoltenType().equals(recipe.getSecondaryType())) {
                            tooltip.add(RenderUtils.toolTipFromMoltenMaterial(moltenMaterial, calculateEpFromEnchantments(inputStack, recipe.getSecondaryAmount()), 35000));
                        }
                    }
                } else if (this.isHovering(18, 6, 13, 74, mouseX, mouseY)) {
                    if (moltenMaterial.getMoltenType().equals(recipe.getMoltenType())) {
                        tooltip.add(RenderUtils.toolTipFromMoltenMaterial(moltenMaterial, recipe.getMoltenAmount(), 35000));
                    }

                    if (moltenMaterial.getMoltenType().equals(recipe.getSecondaryType())) {
                        tooltip.add(RenderUtils.toolTipFromMoltenMaterial(moltenMaterial, recipe.getSecondaryAmount(), 35000));
                    }
                }
            }
        }

        return tooltip;
    }

    protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        return pMouseX >= (double)(pX - 1) && pMouseX < (double)(pX + pWidth + 1) && pMouseY >= (double)(pY - 1) && pMouseY < (double)(pY + pHeight + 1);
    }

    public Component getTitle() {
        return TITLE;
    }

    public IDrawable getBackground() {
        return this.background;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    public void setRecipe(IRecipeLayoutBuilder builder, MagicInfuserMeltingRecipe recipe, IFocusGroup focuses) {
        if (recipe.getOutput().isEmpty())
            builder.addSlot(RecipeIngredientRole.INPUT, 80, 32).addIngredients(recipe.getInput());
        else {
            // Створюємо копію вхідного предмета
            ItemStack inputStack = recipe.getInput().getItems()[0].copy();
            RandomSource random = RandomSource.create();
            // Додаємо випадкові зачарування до копії предмета
            EnchantmentHelper.enchantItem(random, inputStack, 30, false);
            this.inputStack = inputStack;
            builder.addSlot(RecipeIngredientRole.INPUT, 80, 78).addItemStack(inputStack);
            builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 32).addItemStack(recipe.getResultItem());
        }
    }
}
