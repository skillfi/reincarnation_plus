package com.github.skillfi.reincarnation_plus.entity;

import com.github.skillfi.reincarnation_plus.RPMod;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RPEntities {

    private static final DeferredRegister<EntityType<?>> ENTITY_REGISTRY =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RPMod.MODID);

    public static final RegistryObject<EntityType<OgreEntity>> OGRE = ENTITY_REGISTRY.register("ogre",
            () -> EntityType.Builder.of(OgreEntity::new, MobCategory.MONSTER)
                    .canSpawnFarFromPlayer()
                    .sized(0.45f, 1.35f)
                    .build(new ResourceLocation(RPMod.MODID, "ogre").toString())
    );

    public static final Map<String, Boolean> LOADED_ENTITIES = new ConcurrentHashMap<>();

    static {
        LOADED_ENTITIES.put("OGRE", false);
    }

    public static void init(IEventBus modEventBus) {
        ENTITY_REGISTRY.register(modEventBus);
    }

    public static void addSpawners(Holder<Biome> biome, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        builder.getMobSpawnSettings()
                .getSpawner(MobCategory.CREATURE)
                .add(new MobSpawnSettings.SpawnerData(OGRE.get(), 80, 5, 5));

        LOADED_ENTITIES.put("OGRE", true);
        RPMod.LOGGER.info("Added spawner for OGRE in biome: " + biome.unwrapKey().map(Object::toString).orElse("unknown"));
    }

    public static Map<String, Boolean> getLoadedEntities() {
        return LOADED_ENTITIES;
    }
}
