package com.github.skillfi.reincarnation_plus.world.biomes;

public enum BiomeEntryType {
    REGISTRY_NAME(false),
    BIOME_TAG(false),
    BIOME_DICT(true),
    BIOME_CATEGORY(true);

    private boolean depreciated;

    private BiomeEntryType(boolean depreciated) {
        this.depreciated = depreciated;
    }

    public boolean isDepreciated() {
        return this.depreciated;
    }
}
