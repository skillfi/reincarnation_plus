package com.github.skillfi.reincarnation_plus;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class RPConfig {
    public static final RPConfig INSTANCE;
    public static final ForgeConfigSpec COMMON_SPEC;
    public static int ogreSpawnChance = 25;
    public static boolean spawnOgres = true;

    static {
        Pair<RPConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(RPConfig::new);
        INSTANCE = pair.getKey();
        COMMON_SPEC = pair.getValue();
    }

    private RPConfig(ForgeConfigSpec.Builder builder) {

    }


}
