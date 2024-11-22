package com.github.skillfi.reincarnation_plus.entity;


import com.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import com.github.skillfi.reincarnation_plus.RPMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = RPMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class RPEntityHandler {

    public RPEntityHandler() {}

    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put((EntityType) RPEntities.OGRE.get(), OgreEntity.setAttributes());
        event.put((EntityType) RPEntities.KIJIN.get(), KijinEntity.setAttributes());
        event.put((EntityType) RPEntities.ONI.get(), OniEntity.setAttributes());
        event.put((EntityType) RPEntities.DIVINE_ONI.get(), DivineOniEntity.setAttributes());
        event.put((EntityType) RPEntities.DIVINE_FIGHTER.get(), DivineFighterEntity.setAttributes());
    }

    public static void registerEntityPlacements(SpawnPlacementRegisterEvent e) {
        e.register((EntityType) RPEntities.OGRE.get(), Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TensuraTamableEntity::checkTensuraMobSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }
}
