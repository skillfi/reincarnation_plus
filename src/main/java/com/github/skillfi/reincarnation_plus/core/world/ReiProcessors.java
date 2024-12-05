package com.github.skillfi.reincarnation_plus.core.world;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.world.gen.processor.EPCaveProcessor;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ReiProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS = DeferredRegister.create(Registry.STRUCTURE_PROCESSOR_REGISTRY, ReiMod.MODID);

    public static final RegistryObject<StructureProcessorType<EPCaveProcessor>> EPCAVEPROCESSOR = registerProcessor("ep_cave_processor", () -> () -> EPCaveProcessor.CODEC);

    private static <P extends StructureProcessor> RegistryObject<StructureProcessorType<P>> registerProcessor(String name, Supplier<StructureProcessorType<P>> processor) {
        return PROCESSORS.register(name, processor);
    }
}
