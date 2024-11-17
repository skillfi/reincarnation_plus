package com.github.skillfi.reincarnation_plus.world.biomes;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;

public class SpawnBiomeData {
    private List<List<SpawnBiomeEntry>> biomes = new ArrayList();

    public SpawnBiomeData() {
    }

    private SpawnBiomeData(SpawnBiomeEntry[][] biomesRead) {
        this.biomes = new ArrayList();

        for(SpawnBiomeEntry[] innerArray : biomesRead) {
            this.biomes.add(Arrays.asList(innerArray));
        }

    }

    public SpawnBiomeData addBiomeEntry(BiomeEntryType type, boolean negate, String value, int pool) {
        if (this.biomes.isEmpty() || this.biomes.size() < pool + 1) {
            this.biomes.add(new ArrayList());
        }

        ((List)this.biomes.get(pool)).add(new SpawnBiomeEntry(type, negate, value));
        return this;
    }

    public boolean matches(@Nullable Holder<Biome> biomeHolder, ResourceLocation registryName) {
        for(List<SpawnBiomeEntry> all : this.biomes) {
            boolean overall = true;

            for(SpawnBiomeEntry cond : all) {
                if (!cond.matches(biomeHolder, registryName)) {
                    overall = false;
                }
            }

            if (overall) {
                return true;
            }
        }

        return false;
    }

    public static class Deserializer implements JsonDeserializer<SpawnBiomeData>, JsonSerializer<SpawnBiomeData> {
        public Deserializer() {
        }

        public SpawnBiomeData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonobject = json.getAsJsonObject();
            SpawnBiomeEntry[][] biomesRead = (SpawnBiomeEntry[][])GsonHelper.getAsObject(jsonobject, "biomes", new SpawnBiomeEntry[0][0], context, SpawnBiomeEntry[][].class);
            return new SpawnBiomeData(biomesRead);
        }

        public JsonElement serialize(SpawnBiomeData src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("biomes", context.serialize(src.biomes));
            return jsonobject;
        }
    }

    private class SpawnBiomeEntry {
        BiomeEntryType type;
        boolean negate;
        String value;

        public SpawnBiomeEntry(BiomeEntryType type, boolean remove, String value) {
            this.type = type;
            this.negate = remove;
            this.value = value;
        }

        public boolean matches(@Nullable Holder<Biome> biomeHolder, ResourceLocation registryName) {
            if (this.type.isDepreciated()) {
                RPMod.LOGGER.warn("biome config: BIOME_DICT and BIOME_CATEGORY are no longer valid in 1.19+. Please use BIOME_TAG instead.");
                return false;
            } else if (this.type == BiomeEntryType.BIOME_TAG) {
                if (biomeHolder.getTagKeys().anyMatch((biomeTagKey) -> biomeTagKey.location() != null && biomeTagKey.location().toString().equals(this.value))) {
                    return !this.negate;
                } else {
                    return this.negate;
                }
            } else if (registryName.toString().equals(this.value)) {
                return !this.negate;
            } else {
                return this.negate;
            }
        }
    }
}
