package com.github.skillfi.reincarnation_plus.core.registry;

import com.github.alexthe666.citadel.config.biome.SpawnBiomeData;
import com.github.alexthe666.iceandfire.config.BiomeConfig;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.world.features.ReiPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class ReiWorldRegistry {

    public static final DeferredRegister<Feature<?>> FEATURES;
    public static HashMap<String, Boolean> LOADED_FEATURES;
    private static List<String> ADDED_FEATURES;


    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> register(String name, Supplier<? extends F> supplier) {
        return FEATURES.register(name, supplier);
    }

    private static void addFeatureToBiome(ResourceKey<PlacedFeature> featureResource, HashMap<String, Holder<PlacedFeature>> features, ModifiableBiomeInfo.BiomeInfo.Builder builder, GenerationStep.Decoration step) {
        String identifier = featureResource.location().toString();
        Holder<PlacedFeature> feature = (Holder)features.get(identifier);
        if (feature != null) {
            builder.getGenerationSettings().getFeatures(step).add(feature);
            LOADED_FEATURES.put(identifier, true);
            ADDED_FEATURES.add(identifier);
        } else {
            ReiMod.LOGGER.warn("Feature [{}] could not be found", identifier);
        }

    }

    public static void addFeatures(Holder<Biome> biome, HashMap<String, Holder<PlacedFeature>> features, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        ADDED_FEATURES = new ArrayList();

        if (safelyTestBiome(BiomeConfig.oreGenBiomes, biome)) {
            addFeatureToBiome(ReiPlacedFeatures.ORE_IRON, features, builder, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeatureToBiome(ReiPlacedFeatures.ORE_IRON_SMALL, features, builder, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeatureToBiome(ReiPlacedFeatures.ORE_COPPER_LARGE, features, builder, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeatureToBiome(ReiPlacedFeatures.ORE_GOLD, features, builder, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeatureToBiome(ReiPlacedFeatures.ORE_GOLD_BURIED, features, builder, GenerationStep.Decoration.UNDERGROUND_ORES);
            addFeatureToBiome(ReiPlacedFeatures.ORE_COPPER_SMALL, features, builder, GenerationStep.Decoration.UNDERGROUND_ORES);
        }


        if (!ADDED_FEATURES.isEmpty()) {
            StringBuilder featureList = new StringBuilder();

            for(String feature : ADDED_FEATURES) {
                featureList.append("\n").append("\t- ").append(feature);
            }

            ReiMod.LOGGER.debug("Added the following features to the biome [{}]: {}", ((ResourceKey)biome.unwrapKey().get()).location(), featureList);
        }

        ADDED_FEATURES = null;
    }

    private static boolean safelyTestBiome(Pair<String, SpawnBiomeData> entry, Holder<Biome> biomeHolder) {
        try {
            return BiomeConfig.test(entry, biomeHolder);
        } catch (Exception var3) {
            return false;
        }
    }

    static {
        FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ReiMod.MODID);
        LOADED_FEATURES = new HashMap();
        LOADED_FEATURES.put(ReiMod.MODID + ":ore_iron", false);
        LOADED_FEATURES.put(ReiMod.MODID + ":ore_iron_small", false);
        LOADED_FEATURES.put(ReiMod.MODID + ":ore_gold", false);
        LOADED_FEATURES.put(ReiMod.MODID + ":ore_gold_buried", false);
        LOADED_FEATURES.put(ReiMod.MODID + ":ore_copper_small", false);
        LOADED_FEATURES.put(ReiMod.MODID + ":ore_copper_large", false);
    }
}
