package com.github.skillfi.reincarnation_plus.core.registry.recipe;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.automatic.AutomaticMagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.automatic.AutomaticMagicInfusionRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.infuser.MagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.infuser.MagicInfuserRemeltingRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.infuser.MagicInfusionRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReiRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> registry;
    public static final RegistryObject<RecipeType<MagicInfusionRecipe>> MAGIC_INFUSION;
    public static final RegistryObject<RecipeType<AutomaticMagicInfusionRecipe>> AUTOMATIC_MAGIC_INFUSION;
    public static final RegistryObject<RecipeType<MagicInfuserMeltingRecipe>> MAGIC_INFUSER_MELTING;
    public static final RegistryObject<RecipeType<MagicInfuserRemeltingRecipe>> MAGIC_INFUSER_REMELTING;
    public static final RegistryObject<RecipeType<AutomaticMagicInfuserMeltingRecipe>> AUTOMATIC_MAGIC_INFUSER_MELTING;

    public ReiRecipeTypes() {
    }

    public static void init(IEventBus modEventBus) {
        registry.register(modEventBus);
        Serializer.registry.register(modEventBus);
    }

    static {
        registry = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ReiMod.MODID);
        MAGIC_INFUSION = registry.register("magic_infusion", MagicInfusionRecipe.Type::new);
        AUTOMATIC_MAGIC_INFUSION = registry.register("automatic_magic_infusion", AutomaticMagicInfusionRecipe.Type::new);
        MAGIC_INFUSER_MELTING = registry.register("magic_melting", MagicInfuserMeltingRecipe.Type::new);
        MAGIC_INFUSER_REMELTING = registry.register("magic_remelting", MagicInfuserRemeltingRecipe.Type::new);
        AUTOMATIC_MAGIC_INFUSER_MELTING = registry.register("automatic_magic_melting", AutomaticMagicInfuserMeltingRecipe.Type::new);
    }

    public static class Serializer {
        private static final DeferredRegister<RecipeSerializer<?>> registry;
        public static final RegistryObject<RecipeSerializer<MagicInfuserMeltingRecipe>> MAGIC_INFUSER_MELTING;
        public static final RegistryObject<RecipeSerializer<MagicInfuserRemeltingRecipe>> MAGIC_INFUSER_REMELTING;
        public static final RegistryObject<RecipeSerializer<AutomaticMagicInfuserMeltingRecipe>> AUTOMATIC_MAGIC_INFUSER_MELTING;
        public static final RegistryObject<RecipeSerializer<MagicInfusionRecipe>> MAGIC_INFUSION;
        public static final RegistryObject<RecipeSerializer<AutomaticMagicInfusionRecipe>> AUTOMATIC_MAGIC_INFUSION;

        public Serializer() {
        }

        static {
            registry = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ReiMod.MODID);
            MAGIC_INFUSER_MELTING = registry.register("magic_melting", MagicInfuserMeltingRecipe.Serializer::new);
            MAGIC_INFUSER_REMELTING = registry.register("magic_remelting", MagicInfuserRemeltingRecipe.Serializer::new);
            AUTOMATIC_MAGIC_INFUSER_MELTING = registry.register("automatic_magic_melting", AutomaticMagicInfuserMeltingRecipe.Serializer::new);
            MAGIC_INFUSION = registry.register("magic_infusion", MagicInfusionRecipe.Serializer::new);
            AUTOMATIC_MAGIC_INFUSION = registry.register("automatic_magic_infusion", AutomaticMagicInfusionRecipe.Serializer::new);
        }
    }
}
