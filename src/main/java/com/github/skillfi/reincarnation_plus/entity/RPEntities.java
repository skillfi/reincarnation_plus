package com.github.skillfi.reincarnation_plus.entity;

import com.github.skillfi.reincarnation_plus.RPMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
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
    public static final RegistryObject<EntityType<OniEntity>> ONI = ENTITY_REGISTRY.register("oni",
            () -> EntityType.Builder.of(OniEntity::new, MobCategory.MONSTER)
                    .canSpawnFarFromPlayer()
                    .sized(1.2F, 2.5F)
                    .build(new ResourceLocation(RPMod.MODID, "oni").toString())
    );
    public static final RegistryObject<EntityType<DivineOniEntity>> DIVINE_ONI = ENTITY_REGISTRY.register("divine_oni",
            () -> EntityType.Builder.of(DivineOniEntity::new, MobCategory.MONSTER)
                    .canSpawnFarFromPlayer()
                    .sized(1.2F, 2.5F)
                    .build(new ResourceLocation(RPMod.MODID, "divine_oni").toString())
    );
    public static final RegistryObject<EntityType<DivineFighterEntity>> DIVINE_FIGHTER = ENTITY_REGISTRY.register("divine_fighter",
            () -> EntityType.Builder.of(DivineFighterEntity::new, MobCategory.MONSTER)
                    .canSpawnFarFromPlayer()
                    .sized(1.2F, 2.5F)
                    .build(new ResourceLocation(RPMod.MODID, "divine_fighter").toString())
    );

    public static final Map<String, Boolean> LOADED_ENTITIES = new ConcurrentHashMap<>();

    static {
        LOADED_ENTITIES.put("OGRE", false);
    }

    public static void init(IEventBus modEventBus) {
        ENTITY_REGISTRY.register(modEventBus);
    }

}
