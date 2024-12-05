package com.github.skillfi.reincarnation_plus.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.UUID;

public class AuraConfig {
    public final ForgeConfigSpec.DoubleValue baseAura;
    public final ForgeConfigSpec.DoubleValue baseAuraRegeneration;
    public final ForgeConfigSpec.IntValue AuraSyncInterval;
    public final ForgeConfigSpec.IntValue modifierUpdateInterval;
    public final ForgeConfigSpec.LongValue auraSeed;

    public AuraConfig(ForgeConfigSpec.Builder builder) {
        this.baseAura = builder.comment("Base aura for a chunk").defineInRange("baseAura", (double)500.0F, (double)0.0F, (double)1.0E8F);
        this.baseAuraRegeneration = builder.comment("Base aura regeneration per second for a chunk").defineInRange("baseAuraRegeneration", (double)10.0F, (double)0.0F, (double)1.0E8F);
        this.AuraSyncInterval = builder.comment("How often aura is synced to clients when marked as dirty in ticks").defineInRange("auraSyncInterval", 15, 1, 12000);
        this.modifierUpdateInterval = builder.comment("How often maxAuraModifier are updated in ticks").defineInRange("modifierUpdateInterval", 100, 1, 12000);
        this.auraSeed = builder.comment(new String[]{"Seed for aura generation", "Changing this will change the aura values for all chunks"}).defineInRange("auraSeed", UUID.randomUUID().getLeastSignificantBits(), Long.MIN_VALUE, Long.MAX_VALUE);
    }
}
