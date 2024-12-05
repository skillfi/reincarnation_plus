package com.github.skillfi.reincarnation_plus.core.world.structure;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.data.gen.ReiStructurePieces;
import com.github.skillfi.reincarnation_plus.core.world.ReiStructureTypes;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Optional;

public class EPCaveStructure extends RPStructure {

    public static final Codec<EPCaveStructure> CODEC = RecordCodecBuilder.<EPCaveStructure>mapCodec(instance ->
            instance.group(EPCaveStructure.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
            ).apply(instance, EPCaveStructure::new)).codec();

    public EPCaveStructure(StructureSettings config, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int size, HeightProvider startHeight, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter) {
        super(config, startPool, startJigsawName, size, startHeight, projectStartToHeightmap, maxDistanceFromCenter);
    }

    @Override
    public StructureType<?> type() {
        return ReiStructureTypes.EP_CAVE.get();
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext pContext) {
        ChunkPos chunkPos = pContext.chunkPos();
        BlockPos basePos = chunkPos.getMiddleBlockPosition(0); // Центр чанка на рівні Y=0

        return Optional.empty();
    }

    public static final TagKey<Biome> HAS_EP_CAVE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(ReiMod.MODID, "has_structure/ep_cave"));

    public static EPCaveStructure buildStructureConfig(RegistryOps<JsonElement> registryOps) {
        // Отримання реєстру шаблонів пулу
        var templatePoolRegistry = registryOps.registry(Registry.TEMPLATE_POOL_REGISTRY)
                .orElseThrow(() -> new IllegalStateException("Template pool registry is not available"));

// Отримання холдера для стартового пулу
        Holder<StructureTemplatePool> startPool = registryOps.registry(Registry.TEMPLATE_POOL_REGISTRY).get().getOrCreateHolderOrThrow(ReiStructurePieces.EP_CAVE);

        // Ініціалізація StructureSettings з реєстром Ops
        Structure.StructureSettings settings = createStructureSettings(registryOps);

        // Створення та повернення конфігурації структури
        return new EPCaveStructure(
                settings,
                startPool, // Передача стартового пулу
                Optional.empty(), // Без специфічного Jigsaw блокового імені
                10, // Глибина розгалуження структури
                ConstantHeight.ZERO, // Висотний діапазон
                Optional.of(Heightmap.Types.WORLD_SURFACE), // Тип висотної мапи
                32 // Максимальна відстань від центру
        );
    }


}
