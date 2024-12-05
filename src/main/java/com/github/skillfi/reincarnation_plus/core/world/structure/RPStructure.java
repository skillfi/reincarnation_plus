package com.github.skillfi.reincarnation_plus.core.world.structure;

import com.google.gson.JsonElement;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Map;
import java.util.Optional;

import static com.github.skillfi.reincarnation_plus.core.world.structure.EPCaveStructure.HAS_EP_CAVE;

public class RPStructure extends Structure {
    protected final Holder<StructureTemplatePool> startPool;
    protected final Optional<ResourceLocation> startJigsawName;
    protected final int size;
    protected final HeightProvider startHeight;
    protected final Optional<Heightmap.Types> projectStartToHeightmap;
    protected final int maxDistanceFromCenter;

    protected RPStructure(Structure.StructureSettings config,
                          Holder<StructureTemplatePool> startPool,
                          Optional<ResourceLocation> startJigsawName,
                          int size,
                          HeightProvider startHeight,
                          Optional<Heightmap.Types> projectStartToHeightmap,
                          int maxDistanceFromCenter) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext generationContext) {
        return Optional.empty();
    }

    @Override
    public StructureType<?> type() {
        return null;
    }

    public static Structure.StructureSettings createStructureSettings(RegistryOps<JsonElement> registryOps) {
        // Отримання реєстру біомів
        var biomeRegistry = registryOps.registry(Registry.BIOME_REGISTRY)
                .orElseThrow(() -> new IllegalStateException("Biome registry is not available"));

        // Ініціалізація HolderSet для біомів з тегами
        HolderSet<Biome> biomeHolderSet = biomeRegistry.getOrCreateTag(HAS_EP_CAVE);

        // Створення та повернення конфігурації StructureSettings
        return new Structure.StructureSettings(
                biomeHolderSet, // Використання HolderSet<Biome> замість TagKey
                Map.of(), // Мапа умов спауну, наприклад, монстрів або інших параметрів
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES, // Генерація під землею
                TerrainAdjustment.NONE // Відсутність коригування рельєфу
        );
    }
}
