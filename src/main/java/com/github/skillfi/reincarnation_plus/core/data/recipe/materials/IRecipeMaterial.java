package com.github.skillfi.reincarnation_plus.core.data.recipe.materials;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public interface IRecipeMaterial {
    int getPrimaryAmount();
    int getSecondaryAmount();
    ResourceLocation getPrimaryType();
    ResourceLocation getSecondaryType();
    Item getInput();
    boolean isKiln();
    String getTagName();
}
