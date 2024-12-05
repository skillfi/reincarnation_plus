package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.Map;

public class ReiStructurePieces {
    public static final ResourceKey<StructureTemplatePool> EP_CAVE = createKey("ep_cave/start_pool");

    private static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registry.TEMPLATE_POOL_REGISTRY, new ResourceLocation(ReiMod.MODID, name));
    }

    public static Map<ResourceLocation, StructureTemplatePool> gather(RegistryOps<JsonElement> registryOps) {
        Holder<StructureTemplatePool> fallback = registryOps.registry(Registry.TEMPLATE_POOL_REGISTRY).get().getOrCreateHolderOrThrow(Pools.EMPTY);
        Holder<StructureProcessorList> epCaveProcessors = registryOps.registry(Registry.PROCESSOR_LIST_REGISTRY).get().getOrCreateHolderOrThrow(ReiProcessorLists.EP_CAVE_PROCESSORS);
        StructureTemplatePool epCaveTemplatePool = new StructureTemplatePool(EP_CAVE.location(), fallback.value().getName(), List.of(Pair.of(StructurePoolElement.single(ReiMod.MODID + ":" + "ep_cave/building", epCaveProcessors), 1)), StructureTemplatePool.Projection.RIGID);
        return Map.of(
                EP_CAVE.location(), epCaveTemplatePool
        );
    }
}
