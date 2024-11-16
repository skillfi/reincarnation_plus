package com.github.skillfi.reincarnation_plus.procedures;

/* imports omitted */

import com.github.manasmods.tensura.registry.biome.TensuraBiomes;
import com.github.skillfi.reincarnation_plus.entity.OgreEntity;
import com.github.skillfi.reincarnation_plus.init.RPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class OgreEntitySpawningConditionProcedure {
    @SubscribeEvent
    public static void onEntitySpawned(EntityJoinLevelEvent event) {
        execute(event, event.getLevel(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
    }

    public static void execute(LevelAccessor world, double x, double y, double z) {
        execute(null, world, x, y, z);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
        if (world.getBiome(new BlockPos(x, y, z)).is(new ResourceLocation("tensura", "sakura_forest"))) {
            for (int index0 = 0; index0 < 3; index0++) {
                if (world instanceof ServerLevel _level) {
                    OgreEntity entityToSpawn = new OgreEntity(RPEntities.OGRE.get(), _level);
                    entityToSpawn.moveTo(x, y, z, world.getRandom().nextFloat() * 360F, 0);
                    entityToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                    world.addFreshEntity(entityToSpawn);
                }
            }
        }
    }
}