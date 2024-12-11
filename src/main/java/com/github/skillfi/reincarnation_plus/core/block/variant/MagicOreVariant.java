package com.github.skillfi.reincarnation_plus.core.block.variant;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.CooperOreBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.GoldOreBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.ores.IronOreBlockEntity;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;

public class MagicOreVariant {

    public enum OreAge {

        STAGE_0(0, "age_0"),
        STAGE_1(1, "age_1"),
        STAGE_2(2, "age_2"),
        STAGE_3(3, "age_3");

        private static final OreAge[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(OreAge::getId)).toArray(OreAge[]::new);
        @Getter
        private final int id;
        @Getter
        private final String location;

        OreAge(int age, String location){
            this.id = age;
            this.location = location;
        }

        public static ResourceLocation getTextureLocation(IronOreBlockEntity entity) {
            int id = entity.getId();
            String location = entity.getAge().getLocation();
            String ore = Ore.byId(id).getLocation();
            return new ResourceLocation(ReiMod.MODID, "textures/blocks/" + ore +  location + ".png");
        }

        public static ResourceLocation getTextureLocation(GoldOreBlockEntity entity) {
            int id = entity.getId();
            String location = entity.getAge().getLocation();
            String ore = Ore.byId(id).getLocation();
            return new ResourceLocation(ReiMod.MODID, "textures/blocks/" + ore +  location + ".png");
        }

        public static ResourceLocation getTextureLocation(CooperOreBlockEntity entity) {
            int id = entity.getId();
            String location = entity.getAge().getLocation();
            String ore = Ore.byId(id).getLocation();
            return new ResourceLocation(ReiMod.MODID, "textures/blocks/" + ore +  location + ".png");
        }

        public static OreAge byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }

    public enum Ore{

        IRON(0, "iron_ore/"),
        GOLD(1, "gold_ore/"),
        COOPER(2, "ore_copper/");

        private static final Ore[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Ore::getId)).toArray(Ore[]::new);
        @Getter
        private final int id;
        @Getter
        private final String location;

        Ore(int id,  String location){
            this.id = id;
            this.location = location;
        }

        public static Ore byId(int id) {
            return BY_ID[id % BY_ID.length];
        }
    }
}
