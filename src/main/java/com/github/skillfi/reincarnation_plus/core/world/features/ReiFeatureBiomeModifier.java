package com.github.skillfi.reincarnation_plus.core.world.features;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

public class ReiFeatureBiomeModifier implements BiomeModifier {
    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER;
    private final HolderSet<PlacedFeature> features;
    public final HashMap<String, Holder<PlacedFeature>> featureMap = new HashMap();

    public ReiFeatureBiomeModifier(HolderSet<PlacedFeature> features) {
        this.features = features;
        this.features.forEach((feature) -> this.featureMap.put(((ResourceKey)feature.unwrapKey().get()).location().toString(), feature));
    }

    public void modify(Holder<Biome> biome, BiomeModifier.Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {

        }

    }

    public Codec<? extends BiomeModifier> codec() {
        return (Codec)SERIALIZER.get();
    }

    public static Codec<ReiFeatureBiomeModifier> makeCodec() {
        return RecordCodecBuilder.create((config) -> config.group(PlacedFeature.LIST_CODEC.fieldOf("features").forGetter((otherConfig) -> otherConfig.features)).apply(config, ReiFeatureBiomeModifier::new));
    }

    static {
        SERIALIZER = RegistryObject.create(new ResourceLocation(ReiMod.MODID, "rei_features"), ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ReiMod.MODID);
    }
}
