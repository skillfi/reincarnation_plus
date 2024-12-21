package com.github.skillfi.reincarnation_plus.integration.jei;

import com.github.manasmods.tensura.data.pack.KilnMoltenMaterial;
import com.github.manasmods.tensura.data.pack.TensuraData;
import com.github.manasmods.tensura.data.recipe.KilnMixingRecipe;
import com.github.manasmods.tensura.registry.items.TensuraMaterialItems;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.utils.RenderUtils;
import com.github.skillfi.reincarnation_plus.core.data.pack.MagicInfuserMoltenMaterial;
import com.github.skillfi.reincarnation_plus.core.data.pack.ReiData;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.MagicInfusionRecipe;
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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.skillfi.reincarnation_plus.core.data.gen.ReiMoltenMaterialProvider.MOLTEN_MAGICULES;

public class MagicInfusionRecipeCategory implements IRecipeCategory<MagicInfusionRecipe> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ReiMod.MODID, "textures/gui/magic_infuser/jei_infusion.png");
    private static final Component TITLE = Component.translatable("reincarnation_plus.jei.infusion.title");
    static final ResourceLocation UID = new ResourceLocation(ReiMod.MODID, "magic_infuser/infusion");
    private final IDrawable background;
    private final IDrawable icon;

    public MagicInfusionRecipeCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ((Item) TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).getDefaultInstance());
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 177, 86);
    }

    public List<Component> getTooltipStrings(MagicInfusionRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        ArrayList<Component> tooltip = new ArrayList();

        for(MagicInfuserMoltenMaterial moltenMaterial : ReiData.getMagicInfuserMoltenMaterials()) {
            if (this.isHovering(18, 78, 13, 74, mouseX, mouseY) && !moltenMaterial.isRightBar() && !recipe.getPrimaryType().equals(MagicInfusionRecipe.EMPTY) && moltenMaterial.getMoltenType().equals(recipe.getPrimaryType())) {
                tooltip.add(RenderUtils.toolTipFromMoltenMaterial(moltenMaterial, recipe.getPrimaryAmount(), 35000));
            } else if (this.isHovering(59, 64, 62, 4, mouseX, mouseY) && !moltenMaterial.isLeftBar() && !moltenMaterial.isRightBar()) {
                tooltip.add(RenderUtils.toolTipInfusionTime(moltenMaterial, recipe.getInfusionTime()));
            } else if (this.isHovering(145, 78, 13, 74, mouseX, mouseY) && !recipe.getPrimaryType().equals(MagicInfusionRecipe.EMPTY) && !moltenMaterial.isLeftBar() && moltenMaterial.getMoltenType().equals(recipe.getPrimaryType())){
                tooltip.add(RenderUtils.toolTipFromMoltenMaterial(moltenMaterial, recipe.getPrimaryAmount(), 250000));
            }
        }

        return tooltip;
    }

    protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        return pMouseX >= (double)(pX - 1) && pMouseX < (double)(pX + pWidth + 1) && pMouseY >= (double)(pY - 1) && pMouseY < (double)(pY + pHeight + 1);
    }

    public RecipeType<MagicInfusionRecipe> getRecipeType() {
        return ReiJeiPlugin.MAGIC_INFUSION;
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

    public void setRecipe(IRecipeLayoutBuilder builder, MagicInfusionRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 65, 36).addIngredients(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 98, 36).addItemStack(recipe.getResultItem());
    }

    public void draw(MagicInfusionRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        for(MagicInfuserMoltenMaterial moltenMaterial : ReiData.getMagicInfuserMoltenMaterials()) {
            if (!recipe.getPrimaryType().equals(MagicInfusionRecipe.EMPTY) && moltenMaterial.getMoltenType().equals(recipe.getPrimaryType())) {
                RenderUtils.renderMoltenMaterial(stack, moltenMaterial, recipe.getPrimaryAmount(), moltenMaterial.isRightBar() ? 250000: 35000);
            }
        }

    }
}
