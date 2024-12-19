package com.github.skillfi.reincarnation_plus.libs.data.recipe.automatic;

import com.github.skillfi.reincarnation_plus.core.block.entity.AutomaticMagiculaInfuserBlockEntity;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AutomaticMagicInfuserRecipe implements Recipe<AutomaticMagiculaInfuserBlockEntity> {
    public AutomaticMagicInfuserRecipe() {
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public ItemStack getResultItem() {
        return ItemStack.EMPTY.copy();
    }

    public abstract FinishedRecipe finishRecipe();
}
