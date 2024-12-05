package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.world.structure.EPCaveStructure;
import com.google.gson.JsonElement;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Map;

public class ReiStructures {
    // Заміна ResourceKey на сам Structure
    public static final ResourceKey<Structure> EP_CAVE = registerKey("ep_cave");

    public static ResourceKey<Structure> registerKey(String name) {
        return ResourceKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(ReiMod.MODID, name));
    }

    public static Map<ResourceLocation, Structure> gather(RegistryOps<JsonElement> registryOps) {
        return Map.of(
                EP_CAVE.location(), EPCaveStructure.buildStructureConfig(registryOps)
        );
    }

    public static Structure getStructureByResourceKey(RegistryOps<?> registryOps, ResourceKey<Structure> structureKey) {
        // Отримання реєстру структур
        var structureRegistry = registryOps.registry(Registry.STRUCTURE_REGISTRY)
                .orElseThrow(() -> new IllegalStateException("Structure registry is not available"));

        // Отримання структури з ResourceKey
        return structureRegistry.get(structureKey);
    }
}
