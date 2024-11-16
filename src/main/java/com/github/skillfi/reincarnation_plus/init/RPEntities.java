package com.github.skillfi.reincarnation_plus.init;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RPEntities {
    private static final DeferredRegister<EntityType<?>> registry;

    public static final RegistryObject<EntityType<OgreEntity>> OGRE;



    static {
        registry = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RPMod.MODID);
        OGRE = registry.register("ogre", () -> EntityType.Builder.of(OgreEntity::new, MobCategory.MONSTER).canSpawnFarFromPlayer().sized(0.45f, 1.35f).build((new ResourceLocation(RPMod.MODID, "ogre")).toString()));
    }

    public static void init(IEventBus modEventBus) {
        registry.register(modEventBus);
    }
}
