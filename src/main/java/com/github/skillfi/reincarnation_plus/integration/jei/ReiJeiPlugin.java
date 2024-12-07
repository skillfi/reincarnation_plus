package com.github.skillfi.reincarnation_plus.integration.jei;

import com.github.manasmods.tensura.registry.blocks.TensuraBlocks;
import com.github.manasmods.tensura.registry.items.TensuraConsumableItems;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.client.screen.MagicInfuserScreen;
import com.github.skillfi.reincarnation_plus.core.registry.ReiItems;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.MagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.MagicInfusionRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Objects;

@JeiPlugin
public class ReiJeiPlugin implements IModPlugin {
    static final RecipeType<MagicInfuserMeltingRecipe> MAGIC_INFUSER_MELTING;
    static final RecipeType<MagicInfusionRecipe> MAGIC_INFUSION;

    public ReiJeiPlugin() {}

    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ReiMod.MODID, "jei_plugin");
    }

    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MagicInfuserMeltingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MagicInfusionRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ReiItems.MAGIC_INFUSER.get().getDefaultInstance(), new RecipeType[]{MAGIC_INFUSER_MELTING});
        registration.addRecipeCatalyst(ReiItems.MAGIC_INFUSER.get().getDefaultInstance(), new RecipeType[]{MAGIC_INFUSION});
    }

    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(MAGIC_INFUSER_MELTING, rm.getAllRecipesFor((net.minecraft.world.item.crafting.RecipeType) ReiRecipeTypes.MAGIC_INFUSER_MELTING.get()));
        registration.addRecipes(MAGIC_INFUSION, rm.getAllRecipesFor((net.minecraft.world.item.crafting.RecipeType)ReiRecipeTypes.MAGIC_INFUSION.get()));
    }

    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MagicInfuserScreen.class, 37, 89, 13, 13, new RecipeType[]{MAGIC_INFUSER_MELTING});
        registration.addRecipeClickArea(MagicInfuserScreen.class, 95, 5, 15, 76, new RecipeType[]{MAGIC_INFUSION});
    }

    static {
        MAGIC_INFUSER_MELTING = new RecipeType(MagicInfuserMeltingRecipeCategory.UID, MagicInfuserMeltingRecipe.class);
        MAGIC_INFUSION = new RecipeType(MagicInfusionRecipeCategory.UID, MagicInfusionRecipe.class);
    }
}
