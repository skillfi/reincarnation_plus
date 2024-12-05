package com.github.skillfi.reincarnation_plus.core.registry;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.data.pack.LevelAuraModifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ReiLevelAuraModifier {
    public static final ResourceKey<Registry<LevelAuraModifier>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ReiMod.MODID, "aura/modifier/level"));
    private static final DeferredRegister<LevelAuraModifier> REGISTRY;
    private static final Supplier<IForgeRegistry<LevelAuraModifier>> REGISTRY_SUPPLIER;

    public ReiLevelAuraModifier() {
    }

    public static void init(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }

    static {
        REGISTRY = DeferredRegister.create(REGISTRY_KEY, ReiMod.MODID);
        REGISTRY_SUPPLIER = REGISTRY.makeRegistry(() -> (new RegistryBuilder()).dataPackRegistry(LevelAuraModifier.CODEC, LevelAuraModifier.CODEC));
    }
}
