package com.github.skillfi.reincarnation_plus.integration.jei;

import com.github.manasmods.tensura.registry.items.TensuraMaterialItems;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.utils.RenderUtils;
import com.github.skillfi.reincarnation_plus.libs.data.pack.MagicInfuserMoltenMaterial;
import com.github.skillfi.reincarnation_plus.libs.data.pack.ReiData;
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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.skillfi.reincarnation_plus.libs.data.gen.ReiMoltenMaterialProvider.INFUSION;
import static com.github.skillfi.reincarnation_plus.libs.data.gen.ReiMoltenMaterialProvider.MOLTEN_MAGICULES;

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
            if (this.isHovering(18, 6, 13, 74, mouseX, mouseY) && !recipe.getMagiculesId().equals(MagicInfusionRecipe.EMPTY) && moltenMaterial.isLeftBar() && moltenMaterial.getMoltenType().equals(recipe.getMagiculesId())) {
                tooltip.add(RenderUtils.toolTipFromMoltenMaterial(moltenMaterial, recipe.getMagicules(), 250.0F));
            } else if (this.isHovering(145, 6, 13, 74, mouseX, mouseY) && !recipe.getMagiculesId().equals(MagicInfusionRecipe.EMPTY) && !moltenMaterial.isLeftBar()) {
                tooltip.add(RenderUtils.toolTipInfusionTime(moltenMaterial, recipe.getCookingTime(), recipe.getCookingTime()));
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
        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 36).addItemStack(recipe.getResultItem());
    }

    public void draw(MagicInfusionRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        RenderUtils.renderMoltenMaterial(stack, MagicInfuserMoltenMaterial.of(MOLTEN_MAGICULES, true, new Color(0, 233, 255, 255)), recipe.getMagicules(), 250.0F);
        RenderUtils.renderInfusion(stack, MagicInfuserMoltenMaterial.of(INFUSION, false, new Color(186, 85, 211)), recipe.getCookingTime(), recipe.getCookingTime());
    }
}
