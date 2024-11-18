package com.github.skillfi.reincarnation_plus.entity.variant;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.entity.OgreEntity;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;

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

        public static ResourceLocation getModelLocation(OgreEntity entity) {
            String gender = entity.getGender().getLocation();
            String evolution = entity.getEvolutionState().getLocation();
            String modified_evolution = evolution.substring(0, evolution.length() -1);

            return new ResourceLocation(RPMod.MODID, "geo/entity/" + modified_evolution + "_" + gender + ".geo.json");
        }
    }

    public enum Style{

        DEFAULT(0, "default"),
        WHITE(1, "white"),
        BLUE(2, "blue");

        private static final Style[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Style::getId)).toArray((x$0) -> new Style[x$0]);

        private final int id;
        private final String location;
        Style(int id, String name) {
            this.id = id;
            this.location = name;
        }

        public int getId() {
            return this.id;
        }

        public String getLocation() {
            return this.location;
        }

        public static Style byId(int id) {
            return BY_ID[id % BY_ID.length];
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
            String style = entity.getStyle().getLocation();
            if (gender.equals("small_female")){
                return new ResourceLocation(RPMod.MODID, "textures/entity/" + modified_evolution + "_" + gender + ".png");
            }
            return new ResourceLocation(RPMod.MODID, "textures/entity/" + modified_evolution + "_" + gender + "_" +style + ".png");
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
