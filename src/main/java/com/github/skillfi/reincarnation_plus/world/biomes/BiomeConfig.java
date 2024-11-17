package com.github.skillfi.reincarnation_plus.world.biomes;

import com.github.skillfi.reincarnation_plus.RPMod;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BiomeConfig {

    public static final Pair<String, SpawnBiomeData> OGRE_HOME_BIOMES =
            Pair.of(RPMod.MODID + ":ogre_home_biomes", DefaultBiomes.OGRES_HOME);

    private static boolean initialized = false;
    private static final Map<String, SpawnBiomeData> biomeConfigValues = new HashMap<>();

    public static void init() {
        if (initialized) return; // Уникаємо повторної ініціалізації

        try {
            for (Field field : BiomeConfig.class.getFields()) {
                Object obj = field.get(null);
                if (obj instanceof Pair<?, ?> pair) {
                    String id = (String) pair.getLeft();
                    SpawnBiomeData data = (SpawnBiomeData) pair.getRight();
                    biomeConfigValues.put(id, SpawnBiomeConfig.create(new ResourceLocation(id), data));
                }
            }
        } catch (Exception e) {
            RPMod.LOGGER.warn("Error building biome config JSON files for Reincarnation Plus.", e);
        }

        initialized = true;
    }

    private static ResourceLocation getBiomeName(Holder<Biome> biome) {
        return biome.unwrap()
                .map(resourceKey -> resourceKey.location(), noKey -> null);
    }

    public static boolean test(Pair<String, SpawnBiomeData> entry, Holder<Biome> biome, ResourceLocation name) {
        if (!initialized) {
            init();
        }

        SpawnBiomeData data = biomeConfigValues.get(entry.getKey());
        return data != null && data.matches(biome, name);
    }

    public static boolean test(Pair<String, SpawnBiomeData> entry, Holder<Biome> biome) {
        return test(entry, biome, getBiomeName(biome));
    }

    public static boolean test(Pair<String, SpawnBiomeData> entry, Holder.Reference<Biome> biome) {
        return test(entry, biome, biome.key().location());
    }
}
