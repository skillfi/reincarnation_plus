package com.github.skillfi.reincarnation_plus.core.entity.variant.ogre;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.entity.OgreEntity;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum Face {
    FACE_A(0, "/face/face_a", List.of(Gender.FEMALE, Gender.MALE, Gender.SMALL_FEMALE)),
    FACE_B(1, "/face/face_b", List.of(Gender.FEMALE, Gender.MALE, Gender.SMALL_FEMALE)),
    FACE_C(2, "/face/face_c", List.of(Gender.FEMALE, Gender.MALE, Gender.SMALL_FEMALE)),
    FACE_D(3, "/face/face_d", List.of(Gender.FEMALE, Gender.MALE, Gender.SMALL_FEMALE));

    private static final Face[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Face::getId)).toArray(Face[]::new);
    @Getter
    private final int id;
    @Getter
    private final String location;
    @Getter
    private final List<Gender> genderArrayList;
    private static final List<Integer> MALE_LIST = Arrays.stream(values())
            .filter(face -> face.genderArrayList.contains(Gender.MALE))
            .map(Face::getId)
            .toList();
    private static final List<Integer> FEMALE_LIST = Arrays.stream(values())
            .filter(face -> face.genderArrayList.contains(Gender.FEMALE))
            .map(Face::getId)
            .toList();
    private static final List<Integer> SMALL_FEMALE_LIST = Arrays.stream(values())
            .filter(face -> face.genderArrayList.contains(Gender.SMALL_FEMALE))
            .map(Face::getId)
            .toList();

    Face(int id, String location, List<Gender> genderArrayList) {
        this.id = id;
        this.location = location;
        this.genderArrayList = genderArrayList;
    }

    public ResourceLocation getTextureLocation(OgreEntity entity) {
        String gender = entity.getGender().getLocation();
        String skin = entity.getSkin().getLocation();
        return new ResourceLocation(ReiMod.MODID, "textures/entity/" + skin +"/"+ gender + location + ".png");
    }

    public static Face byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static int getRandom(Gender gender, OgreEntity entity){
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
