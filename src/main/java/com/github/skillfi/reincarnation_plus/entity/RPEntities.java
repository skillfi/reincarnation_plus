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
                    .sized(1.2F, 2.5F)
                    .build(new ResourceLocation(RPMod.MODID, "ogre").toString())
    );
    public static final RegistryObject<EntityType<KijinEntity>> KIJIN = ENTITY_REGISTRY.register("kijin",
            () -> EntityType.Builder.of(KijinEntity::new, MobCategory.MONSTER)
                    .canSpawnFarFromPlayer()
                    .sized(1.2F, 2.5F)
                    .build(new ResourceLocation(RPMod.MODID, "kijin").toString())
    );

    public static final Map<String, Boolean> LOADED_ENTITIES = new ConcurrentHashMap<>();

    static {
        LOADED_ENTITIES.put("OGRE", false);
    }

    public static void init(IEventBus modEventBus) {
        ENTITY_REGISTRY.register(modEventBus);
    }


    public static Map<String, Boolean> getLoadedEntities() {
        return LOADED_ENTITIES;
    }
}
