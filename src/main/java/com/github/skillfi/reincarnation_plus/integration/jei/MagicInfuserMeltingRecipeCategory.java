package com.github.skillfi.reincarnation_plus.integration.jei;

import com.github.manasmods.tensura.data.pack.KilnMoltenMaterial;
import com.github.manasmods.tensura.data.pack.TensuraData;
import com.github.manasmods.tensura.data.recipe.KilnMeltingRecipe;
import com.github.manasmods.tensura.data.recipe.KilnMixingRecipe;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.registry.ReiItems;
import com.github.skillfi.reincarnation_plus.core.utils.RenderUtils;
import com.github.skillfi.reincarnation_plus.libs.data.pack.MagicInfuserMoltenMaterial;
import com.github.skillfi.reincarnation_plus.libs.data.pack.ReiData;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.MagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.MagicInfusionRecipe;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class MagicInfuserMeltingRecipeCategory implements IRecipeCategory<MagicInfuserMeltingRecipe> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ReiMod.MODID, "textures/gui/magic_infuser/jei_melting.png");
    private static final Component TITLE = Component.translatable("reincarnation_plus.jei.melting.title");
    static final ResourceLocation UID = new ResourceLocation(ReiMod.MODID, "magic_infuser/melting");
    private final IDrawable background;
    private final IDrawable icon;

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
                RenderUtils.renderMoltenMaterial(stack, moltenMaterial, recipe.getMoltenAmount(), 144);
            }
        }
    }

    public List<Component> getTooltipStrings(MagicInfuserMeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        ArrayList<Component> tooltip = new ArrayList();

        for(MagicInfuserMoltenMaterial moltenMaterial : ReiData.getMagicInfuserMoltenMaterials()) {
            if (!recipe.getMoltenType().equals(MagicInfusionRecipe.EMPTY)) {
                if (moltenMaterial.isLeftBar()) {
                    if (this.isHovering(18, 6, 13, 74, mouseX, mouseY) && !recipe.getMoltenType().equals(MagicInfusionRecipe.EMPTY) && moltenMaterial.isLeftBar()) {
                        if (moltenMaterial.getMoltenType().equals(recipe.getMoltenType())) {
                            tooltip.add(RenderUtils.toolTipFromMoltenMaterial(moltenMaterial, (float)recipe.getMoltenAmount() / 4.0F, 36));
                        }
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
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 32).addIngredients(recipe.getInput());
    }
}
