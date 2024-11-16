package com.github.skillfi.reincarnation_plus.entity.variant;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.entity.OgreEntity;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class OgreVariant {


    public OgreVariant() {
    }

    public enum Gender {

        MALE(0, "male"),
        FEMALE(1, "female"),
        SMALL_FEMALE(2, "small_female");

        private static final Gender[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Gender::getId)).toArray((x$0) -> new Gender[x$0]);
        private final int id;
        private final String location;
        public static final Map<Gender, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(Gender.class), (variant) -> {
            variant.put(MALE, new ResourceLocation(RPMod.MODID, "geo/ogre_male.geo.json"));
            variant.put(FEMALE, new ResourceLocation(RPMod.MODID, "geo/ogre_female.geo.json"));
            variant.put(SMALL_FEMALE, new ResourceLocation(RPMod.MODID, "geo/ogre_small_female.geo.json"));
        });

        Gender(int id, String location) {
            this.id = id;
            this.location = location;
        }

        public static Gender byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public int getId() {
            return this.id;
        }

        public String getLocation() {
            return this.location;
        }
    }

    public enum Skin {

        OGRE(0, "ogres"),
        ONI(1, "onis");

        private static final Skin[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Skin::getId)).toArray((x$0) -> new Skin[x$0]);

        private final int id;
        private final String location;

        Skin(int id, String name) {
            this.id = id;
            this.location = name;
        }

        public static ResourceLocation getTextureLocation(OgreEntity entity) {
            String gender = entity.getGender().getLocation();
            String evolution = entity.getEvolutionState().getLocation();
            String modified_evolution = evolution.substring(0, evolution.length() -1);
            return new ResourceLocation(RPMod.MODID, "textures/entity/" + evolution + "/" + gender + "/" + modified_evolution + "_" + gender + ".png");
        }

        public static Skin byId(int id) {
            return BY_ID[id % BY_ID.length];
        }

        public int getId() {
            return this.id;
        }

        public String getLocation() {
            return this.location;
        }
    }
}
