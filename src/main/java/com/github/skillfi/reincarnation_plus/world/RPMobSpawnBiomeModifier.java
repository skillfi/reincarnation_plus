package com.github.skillfi.reincarnation_plus.world;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.entity.RPEntities;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RPMobSpawnBiomeModifier implements BiomeModifier {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, RPMod.MODID);

    public static final RegistryObject<Codec<RPMobSpawnBiomeModifier>> MOB_SPAWN_MODIFIER =
            BIOME_MODIFIER_SERIALIZERS.register("rp_mob_spawns", RPMobSpawnBiomeModifier::makeCodec);

    public RPMobSpawnBiomeModifier() {

    }

    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        RPMod.LOGGER.info("BiomeModifier invoked for biome: " + biome.unwrapKey().map(Object::toString).orElse("unknown") + " in phase: " + phase);
        if (phase == Phase.ADD) {
            RPEntities.addSpawners(biome, builder);
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return MOB_SPAWN_MODIFIER.get();
    }


    public static Codec<RPMobSpawnBiomeModifier> makeCodec() {
        return Codec.unit(RPMobSpawnBiomeModifier::new);
    }
}
