package com.github.skillfi.reincarnation_plus.config;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class SpawnRateConfig {
    public static final SpawnRateConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;
    public final ForgeConfigSpec.IntValue ogreSpawnrate;

    public SpawnRateConfig(ForgeConfigSpec.Builder builder) {
        builder.push("ogre");
        this.ogreSpawnrate = this.spawnrate(builder, 50);
        builder.pop();
    }

    private ForgeConfigSpec.IntValue spawnrate(ForgeConfigSpec.Builder builder, int defaultValue) {
        return builder.comment("How many times will the entity attempt to spawn before failing\n0 = disabled, 1 = guaranteed, higher = lower chance\ndefault = " + defaultValue).defineInRange("spawnAttempts", defaultValue, 0, 50);
    }

    private ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String valueName, String comment, int defaultValue) {
        return builder.comment(comment).defineInRange(valueName, defaultValue, 0, 1024);
    }

    public static boolean rollSpawn(int rolls, RandomSource randomSource, MobSpawnType reason) {
        if (reason == MobSpawnType.SPAWNER) {
            return true;
        } else if (rolls > 0) {
            return randomSource.nextInt(rolls) == 1;
        } else {
            return false;
        }
    }

    static {
        Pair<SpawnRateConfig, ForgeConfigSpec> pair = (new ForgeConfigSpec.Builder()).configure(SpawnRateConfig::new);
        INSTANCE = (SpawnRateConfig)pair.getKey();
        SPEC = (ForgeConfigSpec)pair.getValue();
    }
}
