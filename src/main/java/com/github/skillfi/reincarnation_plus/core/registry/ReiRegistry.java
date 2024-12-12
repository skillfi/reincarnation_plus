package com.github.skillfi.reincarnation_plus.core.registry;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.entity.ReiEntities;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks;
import com.github.skillfi.reincarnation_plus.core.registry.fluids.ReiFluidTypes;
import com.github.skillfi.reincarnation_plus.core.registry.fluids.ReiFluids;
import com.github.skillfi.reincarnation_plus.core.registry.items.ReiItems;
import com.github.skillfi.reincarnation_plus.core.registry.menu.ReiMenuTypes;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
import com.github.skillfi.reincarnation_plus.core.world.features.ReiFeatureBiomeModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ReiRegistry {

    public static void register(IEventBus modEventBus){
        ReiBiomeAuraModifier.init(modEventBus);
        ReiLevelAuraModifier.init(modEventBus);
        ReiItems.ITEMS.register(modEventBus);
        ReiBlockEntities.init(modEventBus);
        ReiBlocks.BLOCKS.register(modEventBus);
        ReiFluids.REGISTRY.register(modEventBus);
        ReiFluidTypes.REGISTRY.register(modEventBus);
        // Ініціалізація сутностей
        ReiEntities.init(modEventBus);
        // Реєстрація вкладок
        ReiRecipeTypes.init(modEventBus);
        ReiMenuTypes.init(modEventBus);

        DeferredRegister<Codec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ReiMod.MODID);
        biomeModifiers.register(modEventBus);
        biomeModifiers.register("rei_features", ReiFeatureBiomeModifier::makeCodec);

    }
}
