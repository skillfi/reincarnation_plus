package com.github.skillfi.reincarnation_plus.core.data.recipe.auto;

import com.github.skillfi.reincarnation_plus.core.block.entity.AutoInfuserBlockEntity;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AutoMagicInfuserRecipe implements Recipe<AutoInfuserBlockEntity> {
    public AutoMagicInfuserRecipe() {
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public ItemStack getResultItem() {
        return ItemStack.EMPTY.copy();
    }

    public abstract FinishedRecipe finishRecipe();
}
