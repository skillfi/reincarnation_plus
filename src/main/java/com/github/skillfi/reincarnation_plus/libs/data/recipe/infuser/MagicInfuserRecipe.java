package com.github.skillfi.reincarnation_plus.libs.data.recipe.infuser;

import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class MagicInfuserRecipe implements Recipe<MagiculaInfuserBlockEntity> {
    public MagicInfuserRecipe() {
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public ItemStack getResultItem() {
        return ItemStack.EMPTY.copy();
    }

    public abstract FinishedRecipe finishRecipe();
}
