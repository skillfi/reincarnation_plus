package com.github.skillfi.reincarnation_plus.core.registry;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.data.pack.BiomeAuraModifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ReiBiomeAuraModifier {
    public static final ResourceKey<Registry<BiomeAuraModifier>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ReiMod.MODID, "aura/modifier/biome"));
    private static final DeferredRegister<BiomeAuraModifier> REGISTRY;
    private static final Supplier<IForgeRegistry<BiomeAuraModifier>> REGISTRY_SUPPLIER;

    public ReiBiomeAuraModifier() {
    }

    public static void init(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }

    static {
        REGISTRY = DeferredRegister.create(REGISTRY_KEY, ReiMod.MODID);
        REGISTRY_SUPPLIER = REGISTRY.makeRegistry(() -> (new RegistryBuilder()).dataPackRegistry(BiomeAuraModifier.CODEC, BiomeAuraModifier.CODEC));
    }
}
