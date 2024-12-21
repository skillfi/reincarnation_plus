package com.github.skillfi.reincarnation_plus.core.registry.recipe;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.data.recipe.automatic.AutomaticMagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.automatic.AutomaticMagicInfusionRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.InfuserEvolvingRecipe;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.MagicInfuserMeltingRecipe;

import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.MagicInfusionRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReiRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> registry;
    public static final RegistryObject<RecipeType<MagicInfusionRecipe>> MAGIC_INFUSION;
    public static final RegistryObject<RecipeType<InfuserEvolvingRecipe>> INFUSER_EVOLVING;
    public static final RegistryObject<RecipeType<AutomaticMagicInfusionRecipe>> AUTOMATIC_MAGIC_INFUSION;
    public static final RegistryObject<RecipeType<MagicInfuserMeltingRecipe>> MAGIC_INFUSER_MELTING;
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
        INFUSER_EVOLVING = registry.register("infuser_evolving", InfuserEvolvingRecipe.Type::new);
        AUTOMATIC_MAGIC_INFUSION = registry.register("automatic_magic_infusion", AutomaticMagicInfusionRecipe.Type::new);
        MAGIC_INFUSER_MELTING = registry.register("infuser_melting", MagicInfuserMeltingRecipe.Type::new);
        AUTOMATIC_MAGIC_INFUSER_MELTING = registry.register("automatic_infuser_melting", AutomaticMagicInfuserMeltingRecipe.Type::new);
    }

    public static class Serializer {
        private static final DeferredRegister<RecipeSerializer<?>> registry;
        public static final RegistryObject<RecipeSerializer<MagicInfuserMeltingRecipe>> MAGIC_INFUSER_MELTING;
        public static final RegistryObject<RecipeSerializer<AutomaticMagicInfuserMeltingRecipe>> AUTOMATIC_MAGIC_INFUSER_MELTING;
        public static final RegistryObject<RecipeSerializer<MagicInfusionRecipe>> MAGIC_INFUSION;
        public static final RegistryObject<RecipeSerializer<InfuserEvolvingRecipe>> INFUSER_EVOLVING;
        public static final RegistryObject<RecipeSerializer<AutomaticMagicInfusionRecipe>> AUTOMATIC_MAGIC_INFUSION;

        public Serializer() {
        }

        static {
            registry = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ReiMod.MODID);
            MAGIC_INFUSER_MELTING = registry.register("infuser_melting", MagicInfuserMeltingRecipe.Serializer::new);
            AUTOMATIC_MAGIC_INFUSER_MELTING = registry.register("automatic_infuser_melting", AutomaticMagicInfuserMeltingRecipe.Serializer::new);
            MAGIC_INFUSION = registry.register("magic_infusion", MagicInfusionRecipe.Serializer::new);
            INFUSER_EVOLVING = registry.register("infuser_evolving", InfuserEvolvingRecipe.Serializer::new);
            AUTOMATIC_MAGIC_INFUSION = registry.register("automatic_magic_infusion", AutomaticMagicInfusionRecipe.Serializer::new);
        }
    }
}
