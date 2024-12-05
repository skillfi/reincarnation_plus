package com.github.skillfi.reincarnation_plus.core.registry;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.api.gem.GemHandler;
import com.github.skillfi.reincarnation_plus.libs.api.gem.GemStat;
import com.github.skillfi.reincarnation_plus.libs.api.gem.GemType;
import com.github.skillfi.reincarnation_plus.libs.api.gem.PolishingType;
import com.github.skillfi.reincarnation_plus.libs.gem.GemPolishingType;
import com.github.skillfi.reincarnation_plus.libs.gem.StaticalGemType;

import java.awt.*;

public class ReiGems {
    public static Color GemColor = new Color(138, 201, 123);

    public static GemStat STATIC_DAMAGE = new GemStat(ReiMod.MODID+":damage", 12);
    public static GemStat STATIC_SPEED = new GemStat(ReiMod.MODID+":speed", 12);
    public static GemStat STATIC_STAMINA = new GemStat(ReiMod.MODID+":stamina", 12);
    public static GemStat STATIC_MANA = new GemStat(ReiMod.MODID+":mana", 12);


    public static GemStat DYNAMIC_DAMAGE = new GemStat(ReiMod.MODID+":dynamic_damage", 0.12);
    public static GemStat DYNAMIC_SPEED = new GemStat(ReiMod.MODID+":dynamic_speed", 0.12);
    public static GemStat DYNAMIC_STAMINA = new GemStat(ReiMod.MODID+":dynamic_stamina", 0.12);
    public static GemStat DYNAMIC_MANA = new GemStat(ReiMod.MODID+":dynamic_mana", 0.12);

    public static final PolishingType GEM = new GemPolishingType(ReiMod.MODID+":gem");

    public static final GemType STATICAL = new StaticalGemType(ReiMod.MODID+":statical");
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
