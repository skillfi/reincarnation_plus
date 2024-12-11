package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.Map;

public class ReiProcessorLists {
    public static final ResourceKey<StructureProcessorList> EP_CAVE_PROCESSORS = createKey("ep_cave_processors");

    private static ResourceKey<StructureProcessorList> createKey(String name) {
        return ResourceKey.create(Registry.PROCESSOR_LIST_REGISTRY, new ResourceLocation(ReiMod.MODID, name));
    }

}
