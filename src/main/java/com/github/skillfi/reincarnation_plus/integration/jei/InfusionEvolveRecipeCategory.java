package com.github.skillfi.reincarnation_plus.integration.jei;

import com.github.manasmods.tensura.data.pack.GearEPCount;
import com.github.manasmods.tensura.data.pack.TensuraData;
import com.github.manasmods.tensura.registry.items.TensuraMaterialItems;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.data.pack.MagicInfuserMoltenMaterial;
import com.github.skillfi.reincarnation_plus.core.data.pack.ReiData;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.InfuserEvolvingRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.MagicInfusionRecipe;
import com.github.skillfi.reincarnation_plus.core.utils.RenderUtils;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfusionEvolveRecipeCategory implements IRecipeCategory<InfuserEvolvingRecipe> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ReiMod.MODID, "textures/gui/magic_infuser/jei_infusion.png");
    private static final Component TITLE = Component.translatable("reincarnation_plus.jei.evolve.title");
    static final ResourceLocation UID = new ResourceLocation(ReiMod.MODID, "magic_infuser/evolve");
    private final IDrawable background;
    private final IDrawable icon;

    public InfusionEvolveRecipeCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ((Item) TensuraMaterialItems.HIHIIROKANE_INGOT.get()).getDefaultInstance());
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 177, 86);
    }

    public List<Component> getTooltipStrings(InfuserEvolvingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        ArrayList<Component> tooltip = new ArrayList();

        for(MagicInfuserMoltenMaterial moltenMaterial : ReiData.getMagicInfuserMoltenMaterials()) {
            if (this.isHovering(18, 78, 13, 74, mouseX, mouseY) && !moltenMaterial.isRightBar() && !recipe.getPrimaryType().equals(MagicInfusionRecipe.EMPTY) && moltenMaterial.getMoltenType().equals(recipe.getPrimaryType())) {
                tooltip.add(RenderUtils.toolTipFromMoltenMaterial(moltenMaterial, recipe.getPrimaryAmount(), recipe.getPrimaryAmount()));
            }
        }

        return tooltip;
    }

    protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        return pMouseX >= (double)(pX - 1) && pMouseX < (double)(pX + pWidth + 1) && pMouseY >= (double)(pY - 1) && pMouseY < (double)(pY + pHeight + 1);
    }

    public RecipeType<InfuserEvolvingRecipe> getRecipeType() {
        return ReiJeiPlugin.INFUSER_EVOLVING;
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

    private static void initiateItemEP(ItemStack stack) {
        for(GearEPCount gearEPCount : TensuraData.getGearEP()) {
            if (Objects.equals(ForgeRegistries.ITEMS.getKey(stack.getItem()), gearEPCount.getItem())) {
                CompoundTag tag = stack.getOrCreateTag();
                if (tag.getDouble("EP") <= (double)gearEPCount.getMinEP()) {
                    tag.putDouble("EP", (double)gearEPCount.getMinEP());
                }

                if (tag.getDouble("MaxEP") < (double)gearEPCount.getMaxEP()) {
                    tag.putDouble("MaxEP", (double)gearEPCount.getMaxEP());
                }
                break;
            }
        }
    }

    public void setRecipe(IRecipeLayoutBuilder builder, InfuserEvolvingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 65, 36).addIngredients(recipe.getInput());
        ItemStack output = recipe.getInput().getItems()[0];
        initiateItemEP(output);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 98, 36).addItemStack(output);
    }

    public void draw(InfuserEvolvingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        for(MagicInfuserMoltenMaterial moltenMaterial : ReiData.getMagicInfuserMoltenMaterials()) {
            if (!recipe.getPrimaryType().equals(MagicInfusionRecipe.EMPTY) && moltenMaterial.getMoltenType().equals(recipe.getPrimaryType())) {
                RenderUtils.renderMoltenMaterial(stack, moltenMaterial, recipe.getPrimaryAmount(), recipe.getPrimaryAmount());
            }
        }

    }
}
