package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.manasmods.manascore.api.data.gen.CustomDataProvider;
import com.github.manasmods.tensura.registry.dimensions.TensuraDimensions;
import com.github.skillfi.reincarnation_plus.libs.data.pack.DataPackAuraModifier;
import com.github.skillfi.reincarnation_plus.libs.data.pack.LevelAuraModifier;
import com.github.skillfi.reincarnation_plus.core.registry.ReiLevelAuraModifier;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ReiLevelAuraModifiersProvider extends CustomDataProvider {
    public ReiLevelAuraModifiersProvider(GatherDataEvent gatherDataEvent) {
        super(String.format("%s/%s", ReiLevelAuraModifier.REGISTRY_KEY.location().getNamespace(), ReiLevelAuraModifier.REGISTRY_KEY.location().getPath()), gatherDataEvent.getGenerator());
    }

    public String getName() {
        return "Tensura level modifiers";
    }

    protected void run(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        for(LevelAuraModifier modifier : List.of(new LevelAuraModifier(Level.OVERWORLD.location(), 0, List.of(), List.of()), new LevelAuraModifier(Level.NETHER.location(), 0, List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.MULTIPLY, 1.2)), List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.MULTIPLY, (double)1.25F))), new LevelAuraModifier(Level.END.location(), 0, List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.MULTIPLY, (double)1.5F)), List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.MULTIPLY, (double)1.75F))), new LevelAuraModifier(TensuraDimensions.LABYRINTH.location(), 0, List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.ADD, (double)29500.0F)), List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.MULTIPLY, (double)2.0F))), new LevelAuraModifier(TensuraDimensions.HELL.location(), 0, List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.ADD, (double)100000.0F)), List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.MULTIPLY, (double)2.0F))))) {
            modifier.buildJson(biConsumer);
        }

    }
}
