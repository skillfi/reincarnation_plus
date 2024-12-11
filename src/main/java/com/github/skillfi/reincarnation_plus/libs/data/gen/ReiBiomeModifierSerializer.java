package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.world.features.ReiFeatureBiomeModifier;
import com.github.skillfi.reincarnation_plus.core.world.features.ReiPlacedFeatures;
import com.google.gson.JsonElement;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReiBiomeModifierSerializer {
    public static ResourceKey<BiomeModifier> FEATURES = createKey("rei_features");

    public ReiBiomeModifierSerializer() {
    }

    public static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(ReiMod.MODID, name));
    }

    public static Map<ResourceLocation, BiomeModifier> gather(RegistryOps<JsonElement> registryOps) {
        List<ResourceKey<PlacedFeature>> features = List.of(ReiPlacedFeatures.ORE_COPPER_LARGE, ReiPlacedFeatures.ORE_COPPER_SMALL, ReiPlacedFeatures.ORE_GOLD, ReiPlacedFeatures.ORE_GOLD_BURIED, ReiPlacedFeatures.ORE_IRON_SMALL, ReiPlacedFeatures.ORE_IRON);
        List<Holder<PlacedFeature>> holders = new ArrayList();
        features.forEach((feature) -> holders.add(((Registry)registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).get()).getOrCreateHolderOrThrow(feature)));
        return Map.of(FEATURES.location(), new ReiFeatureBiomeModifier(HolderSet.direct(holders)));
    }
}
