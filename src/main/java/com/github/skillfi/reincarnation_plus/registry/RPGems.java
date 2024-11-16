package com.github.skillfi.reincarnation_plus.registry;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.api.gem.GemHandler;
import com.github.skillfi.reincarnation_plus.api.gem.GemStat;
import com.github.skillfi.reincarnation_plus.api.gem.GemType;
import com.github.skillfi.reincarnation_plus.api.gem.PolishingType;
import com.github.skillfi.reincarnation_plus.gem.GemPolishingType;
import com.github.skillfi.reincarnation_plus.gem.StaticalGemType;

import java.awt.*;

public class RPGems {
    public static Color GemColor = new Color(138, 201, 123);

    public static GemStat STATIC_DAMAGE = new GemStat(RPMod.MODID+":damage", 12);
    public static GemStat STATIC_SPEED = new GemStat(RPMod.MODID+":speed", 12);
    public static GemStat STATIC_STAMINA = new GemStat(RPMod.MODID+":stamina", 12);
    public static GemStat STATIC_MANA = new GemStat(RPMod.MODID+":mana", 12);


    public static GemStat DYNAMIC_DAMAGE = new GemStat(RPMod.MODID+":dynamic_damage", 0.12);
    public static GemStat DYNAMIC_SPEED = new GemStat(RPMod.MODID+":dynamic_speed", 0.12);
    public static GemStat DYNAMIC_STAMINA = new GemStat(RPMod.MODID+":dynamic_stamina", 0.12);
    public static GemStat DYNAMIC_MANA = new GemStat(RPMod.MODID+":dynamic_mana", 0.12);

    public static final PolishingType GEM = new GemPolishingType(RPMod.MODID+":gem");

    public static final GemType STATICAL = new StaticalGemType(RPMod.MODID+":statical");
//    public static final GemType DINAMICAL = new WaterCrystalType(ReincarnationPlusMod.MODID+":water");
//    public static final GemType ENCHANCMENT = new AirCrystalType(ReincarnationPlusMod.MODID+":air");
//    public static final GemType FIRE = new FireCrystalType(ReincarnationPlusMod.MODID+":fire");
//    public static final GemType VOID = new VoidCrystalType(ReincarnationPlusMod.MODID+":void");

    public static void register() {
        GemHandler.registerPolishing(GEM);
//        GemHandler.registerPolishing(FACETED);
//        GemHandler.registerPolishing(ADVANCED);
//        GemHandler.registerPolishing(MASTERFUL);
//        GemHandler.registerPolishing(PURE);

        GemHandler.registerType(STATICAL);
//        GemHandler.registerType(WATER);
//        GemHandler.registerType(AIR);
//        GemHandler.registerType(FIRE);
//        GemHandler.registerType(VOID);
    }
}
