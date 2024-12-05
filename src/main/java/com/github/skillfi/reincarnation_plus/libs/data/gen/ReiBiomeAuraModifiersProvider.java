package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.manasmods.manascore.api.data.gen.CustomDataProvider;
import com.github.manasmods.tensura.registry.biome.TensuraBiomes;
import com.github.skillfi.reincarnation_plus.libs.data.pack.BiomeAuraModifier;
import com.github.skillfi.reincarnation_plus.libs.data.pack.DataPackAuraModifier;
import com.github.skillfi.reincarnation_plus.core.registry.ReiBiomeAuraModifier;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ReiBiomeAuraModifiersProvider extends CustomDataProvider {
    public ReiBiomeAuraModifiersProvider(GatherDataEvent gatherDataEvent) {
        super(String.format("%s/%s", ReiBiomeAuraModifier.REGISTRY_KEY.location().getNamespace(), ReiBiomeAuraModifier.REGISTRY_KEY.location().getPath()), gatherDataEvent.getGenerator());
    }

    public String getName() {
        return "Reincarnation Plus biome modifiers";
    }

    protected void run(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        List<ResourceLocation> biomeList = new ArrayList();
        biomeList.add(this.auraModifier(biConsumer, Biomes.DARK_FOREST.location(), (double)1500.0F, (double)10.0F));
        biomeList.add(this.auraModifier(biConsumer, Biomes.DEEP_DARK.location(), (double)2000.0F, (double)20.0F));
        biomeList.add(this.auraModifier(biConsumer, Biomes.BASALT_DELTAS.location(), (double)2500.0F, (double)30.0F));
        biomeList.add(this.auraModifier(biConsumer, Biomes.CRIMSON_FOREST.location(), (double)4500.0F, (double)50.0F));
        biomeList.add(this.auraModifier(biConsumer, Biomes.NETHER_WASTES.location(), (double)1500.0F, (double)20.0F));
        biomeList.add(this.auraModifier(biConsumer, Biomes.WARPED_FOREST.location(), (double)3500.0F, (double)40.0F));
        biomeList.add(this.auraModifier(biConsumer, Biomes.END_BARRENS.location(), (double)4500.0F, (double)25.0F));
        biomeList.add(this.auraModifier(biConsumer, Biomes.END_MIDLANDS.location(), (double)4500.0F, (double)25.0F));
        biomeList.add(this.auraModifier(biConsumer, Biomes.END_HIGHLANDS.location(), (double)4500.0F, (double)25.0F));
        biomeList.add(this.auraModifier(biConsumer, TensuraBiomes.UNDERWORLD_BARRENS.getId(), (double)3500.0F, (double)50.0F));
        biomeList.add(this.auraModifier(biConsumer, TensuraBiomes.UNDERWORLD_RED_SANDS.getId(), (double)1500.0F, (double)50.0F));
        biomeList.add(this.auraModifier(biConsumer, TensuraBiomes.UNDERWORLD_SANDS.getId(), (double)500.0F, (double)50.0F));
        biomeList.add(this.auraModifier(biConsumer, TensuraBiomes.UNDERWORLD_SPIKES.getId(), (double)2500.0F, (double)50.0F));

        for(ResourceLocation biomeId : ForgeRegistries.BIOMES.getKeys()) {
            if (!biomeList.contains(biomeId)) {
                (new BiomeAuraModifier(biomeId, -1, List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.ADD, (double)0.0F)), List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.ADD, (double)0.0F)))).buildJson(biConsumer);
            }
        }

    }

    private ResourceLocation auraModifier(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer, ResourceLocation biomeId, double base, double regen) {
        BiomeAuraModifier biomeAuraModifier = new BiomeAuraModifier(biomeId, -1, List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.ADD, base)), List.of(new DataPackAuraModifier(DataPackAuraModifier.Mode.ADD, regen)));
        biomeAuraModifier.buildJson(biConsumer);
        return biomeId;
    }

    private ResourceLocation auraModifier(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer, String biome, double base, double regen) {
        ResourceLocation biomeId = new ResourceLocation(biome);
        return this.auraModifier(biConsumer, biomeId, base, regen);
    }
}
