package com.github.skillfi.reincarnation_plus.core.world.features;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.google.common.base.Suppliers;
import com.google.gson.JsonElement;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ReiOreFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COPPER_SMALL = registerKey("ore_copper_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COPPER_LARGE = registerKey("ore_copper_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GOLD_BURIED = registerKey("ore_gold_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GOLD = registerKey("ore_gold");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IRON = registerKey("ore_iron");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IRON_SMALL = registerKey("ore_iron_small");
    public static final RuleTest ORE_REPLACEABLES;
    public static final RuleTest DEEPSLATE_ORE_REPLACEABLES;
    public static final Supplier<List<OreConfiguration.TargetBlockState>> COOPER_MAGIC_ORE_TARGET_LIST;
    public static final Supplier<List<OreConfiguration.TargetBlockState>> GOLD_MAGIC_ORE_TARGET_LIST;
    private static final Supplier<List<OreConfiguration.TargetBlockState>> IRON_MAGIC_ORE_TARGET_LIST;

    public ReiOreFeatures(){}

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(ReiMod.MODID, name));
    }

    public static Map<ResourceLocation, ConfiguredFeature<?, ?>> gather(RegistryOps<JsonElement> registryOps) {
        Map<ResourceLocation, ConfiguredFeature<?, ?>> entries = new HashMap();
        entries.put(ORE_IRON.location(), new ConfiguredFeature(Feature.ORE, new OreConfiguration(IRON_MAGIC_ORE_TARGET_LIST.get(), 9)));
        entries.put(ORE_IRON_SMALL.location(), new ConfiguredFeature(Feature.ORE, new OreConfiguration(IRON_MAGIC_ORE_TARGET_LIST.get(), 4)));
        entries.put(ORE_GOLD.location(), new ConfiguredFeature(Feature.ORE, new OreConfiguration(GOLD_MAGIC_ORE_TARGET_LIST.get(), 9)));
        entries.put(ORE_GOLD_BURIED.location(), new ConfiguredFeature(Feature.ORE, new OreConfiguration(GOLD_MAGIC_ORE_TARGET_LIST.get(), 9, 0.5F)));
        entries.put(ORE_COPPER_SMALL.location(), new ConfiguredFeature(Feature.ORE, new OreConfiguration(COOPER_MAGIC_ORE_TARGET_LIST.get(), 10)));
        entries.put(ORE_COPPER_LARGE.location(), new ConfiguredFeature(Feature.ORE, new OreConfiguration(COOPER_MAGIC_ORE_TARGET_LIST.get(), 20)));
        return entries;
    }

    static {
        ORE_REPLACEABLES = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        IRON_MAGIC_ORE_TARGET_LIST = Suppliers.memoize(() -> List.of(OreConfiguration.target(ORE_REPLACEABLES, (ReiBlockEntities.ReiBlocks.IRON_MAGIC_ORE.get()).defaultBlockState()),
                OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, (ReiBlockEntities.ReiBlocks.IRON_MAGIC_ORE.get()).defaultBlockState())));
        COOPER_MAGIC_ORE_TARGET_LIST = Suppliers.memoize(() -> List.of(OreConfiguration.target(ORE_REPLACEABLES, (ReiBlockEntities.ReiBlocks.COOPER_MAGIC_ORE.get()).defaultBlockState()),
                OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, (ReiBlockEntities.ReiBlocks.COOPER_MAGIC_ORE.get()).defaultBlockState())));
        GOLD_MAGIC_ORE_TARGET_LIST = Suppliers.memoize(() -> List.of(OreConfiguration.target(ORE_REPLACEABLES, (ReiBlockEntities.ReiBlocks.GOLD_MAGIC_ORE.get()).defaultBlockState()),
                OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, (ReiBlockEntities.ReiBlocks.GOLD_MAGIC_ORE.get()).defaultBlockState())));
    }
}
