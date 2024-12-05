package com.github.skillfi.reincarnation_plus.core.world.gen.processor;

import com.github.skillfi.reincarnation_plus.core.world.ReiProcessors;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EPCaveProcessor extends StructureProcessor {

    public static final EPCaveProcessor INSTANCE = new EPCaveProcessor();
    public static final Codec<EPCaveProcessor> CODEC = Codec.unit(() -> INSTANCE);

    public EPCaveProcessor() {}

    @Override
    public StructureTemplate.StructureBlockInfo process(@NotNull LevelReader worldReader, @NotNull BlockPos pos, @NotNull BlockPos pos2, StructureTemplate.@NotNull StructureBlockInfo infoIn1, StructureTemplate.StructureBlockInfo infoIn2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {

        // Workaround for https://bugs.mojang.com/browse/MC-130584
        // Due to a hardcoded field in Templates, any waterloggable blocks in structures replacing water in the world will become waterlogged.
        // Idea of workaround is detect if we are placing a waterloggable block and if so, remove the water in the world instead.
        if (infoIn2.state.getBlock() instanceof SimpleWaterloggedBlock) {
            if (worldReader.getFluidState(infoIn2.pos).is(FluidTags.WATER)) {
                ChunkPos currentChunk = new ChunkPos(infoIn2.pos);
                worldReader.getChunk(currentChunk.x, currentChunk.z).setBlockState(infoIn2.pos, Blocks.AIR.defaultBlockState(), false);
            }
        }

        return infoIn2;
    }

    @Override
    protected @NotNull StructureProcessorType getType() {
        return ReiProcessors.EPCAVEPROCESSOR.get();
    }
}
