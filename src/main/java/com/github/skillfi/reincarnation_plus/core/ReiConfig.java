package com.github.skillfi.reincarnation_plus.core;

import com.github.skillfi.reincarnation_plus.core.config.AuraConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ReiConfig {
    public static final ReiConfig INSTANCE;
    public static final ForgeConfigSpec COMMON_SPEC;
    public static int ogreSpawnChance = 25;
    public static boolean spawnOgres = true;
    public final AuraConfig auraConfig;

    static {
        Pair<ReiConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(ReiConfig::new);
        INSTANCE = pair.getKey();
        COMMON_SPEC = pair.getValue();
    }

    private ReiConfig(ForgeConfigSpec.Builder builder) {
        builder.push("aura");
        this.auraConfig = new AuraConfig(builder);
        builder.pop();
    }


}
