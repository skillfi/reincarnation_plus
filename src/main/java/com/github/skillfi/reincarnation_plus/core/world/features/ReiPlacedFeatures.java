package com.github.skillfi.reincarnation_plus.core.world.features;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.google.gson.JsonElement;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReiPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ORE_COPPER_SMALL = registerKey("ore_copper_small");
    public static final ResourceKey<PlacedFeature> ORE_COPPER_LARGE = registerKey("ore_copper_large");
    public static final ResourceKey<PlacedFeature> ORE_GOLD_BURIED = registerKey("ore_gold_buried");
    public static final ResourceKey<PlacedFeature> ORE_GOLD = registerKey("ore_gold");
    public static final ResourceKey<PlacedFeature> ORE_IRON = registerKey("ore_iron");
    public static final ResourceKey<PlacedFeature> ORE_IRON_SMALL = registerKey("ore_iron_small");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(ReiMod.MODID, name));
    }


    public ReiPlacedFeatures(){}

    private static List<PlacementModifier> orePlacement(int countPlacement, HeightRangePlacement heightRangePlacement) {
        return List.of(CountPlacement.of(countPlacement), InSquarePlacement.spread(), heightRangePlacement, BiomeFilter.biome());
    }

    public static Map<ResourceLocation, PlacedFeature> gather(RegistryOps<JsonElement> registryOps) {
        Map<ResourceLocation, PlacedFeature> entries = new HashMap();
        entries.put(ORE_GOLD.location(), new PlacedFeature(((Registry)registryOps.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get()).getOrCreateHolderOrThrow(ReiOreFeatures.ORE_GOLD), orePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32)))));
        entries.put(ORE_GOLD_BURIED.location(), new PlacedFeature(((Registry)registryOps.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get()).getOrCreateHolderOrThrow(ReiOreFeatures.ORE_GOLD_BURIED), orePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32)))));
        entries.put(ORE_COPPER_SMALL.location(), new PlacedFeature(((Registry)registryOps.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get()).getOrCreateHolderOrThrow(ReiOreFeatures.ORE_COPPER_SMALL), orePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112)))));
        entries.put(ORE_COPPER_LARGE.location(), new PlacedFeature(((Registry)registryOps.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get()).getOrCreateHolderOrThrow(ReiOreFeatures.ORE_COPPER_LARGE), orePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(256)))));
        entries.put(ORE_IRON.location(), new PlacedFeature(((Registry)registryOps.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get()).getOrCreateHolderOrThrow(ReiOreFeatures.ORE_IRON), orePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(256)))));
        entries.put(ORE_IRON_SMALL.location(), new PlacedFeature(((Registry)registryOps.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get()).getOrCreateHolderOrThrow(ReiOreFeatures.ORE_IRON_SMALL), orePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(256)))));
        return entries;
    }
}
