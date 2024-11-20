package com.github.skillfi.reincarnation_plus.config;

import com.github.manasmods.tensura.config.SpawnRateConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class EntityConfig {
    public static final EntityConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.IntValue ogreHp;
    public static ForgeConfigSpec.IntValue ogreMaxHp;
    public static ForgeConfigSpec.IntValue ogreSHp;
    public static ForgeConfigSpec.IntValue ogreMinEP;
    public static ForgeConfigSpec.IntValue ogreMaxEP;
    public static ForgeConfigSpec.IntValue ogreArmor;
    public static ForgeConfigSpec.IntValue ogreAttackDamage;

    public EntityConfig(ForgeConfigSpec.Builder builder) {
        builder.push("ogre");
        this.ogreHp = this.buildInt(builder, "Ogre Health", "Default Ogre Health", 26);
        this.ogreMaxHp = this.buildInt(builder, "Ogre Max Health", "Default Max Ogre Health", 26);
        this.ogreSHp = this.buildInt(builder, "Ogre Spiritual Health", "Default Ogre Spiritual Health", 72);
        this.ogreMinEP = this.buildInt(builder, "Ogre Min Magicules", "Default Ogre Magicules", 6000);
        this.ogreMaxEP = this.buildInt(builder, "Ogre Max Magicules", "Default Max Ogre Magicules", 10000);
        this.ogreArmor = this.buildInt(builder, "Ogre Armor", "Default Ogre Armor", 10);
        this.ogreAttackDamage = this.buildInt(builder, "Ogre Attack Damage", "Default Ogre Attack Damage", 3);
        builder.pop();
   }

    private ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String valueName, String comment, int defaultValue) {
        return builder.comment(comment).defineInRange(valueName, defaultValue, 0, 1024);
    }

    static {
        Pair<EntityConfig, ForgeConfigSpec> pair = (new ForgeConfigSpec.Builder()).configure(EntityConfig::new);
        INSTANCE = (EntityConfig)pair.getKey();
        SPEC = (ForgeConfigSpec)pair.getValue();
    }
}
