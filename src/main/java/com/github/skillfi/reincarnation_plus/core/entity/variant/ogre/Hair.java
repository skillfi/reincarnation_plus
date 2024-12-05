package com.github.skillfi.reincarnation_plus.core.entity.variant.ogre;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.entity.OgreEntity;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum Hair {
    PURPLE(0, "purple", List.of(Gender.FEMALE, Gender.SMALL_FEMALE)),
    RED(1, "red", List.of(Gender.FEMALE, Gender.MALE, Gender.SMALL_FEMALE)),
    WHITE(2, "white", List.of(Gender.MALE, Gender.SMALL_FEMALE)),
    BLACK(3, "black", List.of(Gender.MALE)),
    BLUE(4, "blue", List.of(Gender.FEMALE, Gender.MALE, Gender.SMALL_FEMALE));

    private static final Hair[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Hair::getId)).toArray(Hair[]::new);
    @Getter
    private final int id;
    @Getter
    private final String location;
    @Getter
    private final List<Gender> genderArrayList;
    private static final List<Integer> MALE_LIST = Arrays.stream(values())
            .filter(face -> face.genderArrayList.contains(Gender.MALE))
            .map(Hair::getId)
            .toList();
    private static final List<Integer> FEMALE_LIST = Arrays.stream(values())
            .filter(face -> face.genderArrayList.contains(Gender.FEMALE))
            .map(Hair::getId)
            .toList();
    private static final List<Integer> SMALL_FEMALE_LIST = Arrays.stream(values())
            .filter(face -> face.genderArrayList.contains(Gender.SMALL_FEMALE))
            .map(Hair::getId)
            .toList();

    Hair(int id, String location, List<Gender> genderArrayList) {
        this.id = id;
        this.location = location;
        this.genderArrayList = genderArrayList;
    }

    public static ResourceLocation getTextureLocation(OgreEntity entity) {
        String skin = entity.getSkin().getLocation();
        return new ResourceLocation(ReiMod.MODID, "textures/entity/" + skin +"/"+entity.getGender().getLocation() + "/hair/" + entity.getHair().getLocation() + ".png");
    }

    public static Hair byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static int getRandom(Gender gender, OgreEntity entity) {
        if (gender.equals(Gender.MALE)){
            return (Integer) MALE_LIST.get(entity.getRandom().nextInt(MALE_LIST.size()));
        } else if (gender.equals(Gender.FEMALE)) {
            return (Integer) FEMALE_LIST.get(entity.getRandom().nextInt(FEMALE_LIST.size()));
        } else if (gender.equals(Gender.SMALL_FEMALE)) {
            return (Integer) SMALL_FEMALE_LIST.get(entity.getRandom().nextInt(SMALL_FEMALE_LIST.size()));
        }
        return 0;
    }
}
