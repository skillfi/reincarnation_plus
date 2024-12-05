package com.github.skillfi.reincarnation_plus.core.entity.variant.ogre;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.entity.OgreEntity;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;

public enum Gender {
    MALE(0, "male"),
    FEMALE(1, "female"),
    SMALL_FEMALE(2, "small_female");

    private static final Gender[] BY_ID = Arrays.stream(values())
            .sorted(Comparator.comparingInt(Gender::getId))
            .toArray(Gender[]::new);

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
        String skin = entity.getSkin().getLocation();

        return new ResourceLocation(ReiMod.MODID, "geo/entity/" + skin + "/" + gender + ".geo.json");
    }
}
