package com.github.skillfi.reincarnation_plus.world.biomes;

import static net.minecraft.tags.BiomeTags.*;

public class DefaultBiomes {

    public static final SpawnBiomeData OGRES_HOME = new SpawnBiomeData()
            .addBiomeEntry(BiomeEntryType.BIOME_TAG, false, IS_SAVANNA.location().toString(), 0)
            .addBiomeEntry(BiomeEntryType.BIOME_TAG, false, IS_TAIGA.location().toString(), 0)
            .addBiomeEntry(BiomeEntryType.BIOME_TAG, false, IS_FOREST.location().toString(), 0);

}
