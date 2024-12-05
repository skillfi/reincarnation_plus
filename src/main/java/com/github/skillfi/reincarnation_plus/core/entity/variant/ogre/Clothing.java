package com.github.skillfi.reincarnation_plus.core.entity.variant.ogre;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.entity.OgreEntity;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum Clothing {
    BLACK(0, "_black", List.of(Gender.FEMALE, Gender.MALE)),
    DARK_PURPLE(1, "_dark_purple", List.of(Gender.FEMALE, Gender.MALE)),
    WHITE(2, "_white", List.of(Gender.FEMALE, Gender.MALE, Gender.SMALL_FEMALE));

    private static final Clothing[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Clothing::getId)).toArray(Clothing[]::new);
    private final int id;
    private final String location;
    private final List<Gender> genderArrayList;
    private static final List<Integer> MALE_LIST = Arrays.stream(values())
            .filter(clothing -> clothing.genderArrayList.contains(Gender.MALE))
            .map(Clothing::getId)
            .toList();
    private static final List<Integer> FEMALE_LIST = Arrays.stream(values())
            .filter(clothing -> clothing.genderArrayList.contains(Gender.FEMALE))
            .map(Clothing::getId)
            .toList();
    private static final List<Integer> SMALL_FEMALE_LIST = Arrays.stream(values())
            .filter(clothing -> clothing.genderArrayList.contains(Gender.SMALL_FEMALE))
            .map(Clothing::getId)
            .toList();


    Clothing(int id, String location, List<Gender> genderArrayList) {
        this.id = id;
        this.location = location;
        this.genderArrayList = genderArrayList;
    }

    public static ResourceLocation getTextureLocation(OgreEntity entity) {
        String skin = entity.getSkin().getLocation();
        return new ResourceLocation(ReiMod.MODID, "textures/entity/" + skin +"/"+ entity.getGender().getLocation() + "/clothing/loin_" + entity.getGender().getLocation() + entity.getClothing().getLocation() + ".png");
    }

    public static Clothing byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static int getRandom(Gender gender, OgreEntity entity) {
        if (gender.equals(Gender.FEMALE)){
            return (Integer) FEMALE_LIST.get(entity.getRandom().nextInt(FEMALE_LIST.size()));
        } else if (gender.equals(Gender.MALE)) {
            return (Integer) MALE_LIST.get(entity.getRandom().nextInt(MALE_LIST.size()));
        } else if (gender.equals(Gender.SMALL_FEMALE)) {
            return (Integer) SMALL_FEMALE_LIST.get(entity.getRandom().nextInt(SMALL_FEMALE_LIST.size()));
        }
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public List<Gender> getGenderArrayList() {
        return genderArrayList;
    }
}
