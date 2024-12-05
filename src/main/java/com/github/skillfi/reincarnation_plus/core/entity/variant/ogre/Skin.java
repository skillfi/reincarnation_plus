package com.github.skillfi.reincarnation_plus.core.entity.variant.ogre;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.entity.OgreEntity;
import lombok.Getter;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;

public enum Skin {
    OGRE(0, "ogre", false),
    ONI(1, "oni", true);

    private static final Skin[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Skin::getId)).toArray(Skin[]::new);
    @Getter
    private final int id;
    @Getter
    private final String location;
    @Getter
    private final boolean evolve;

    Skin(int id, String location, boolean evolve) {
        this.id = id;
        this.location = location;
        this.evolve = evolve;
    }

    public static ResourceLocation getTextureLocation(OgreEntity entity) {
        String gender = entity.getGender().getLocation();
        String skin = entity.getSkin().getLocation();
        return new ResourceLocation(ReiMod.MODID, "textures/entity/" + skin + "/" + gender + "/skin/"+ gender + ".png");
    }

    public static Skin byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static Skin evolved(boolean evolve){
        return evolve ? ONI : OGRE;
    }

    public static int getRandom(OgreEntity entity) {
        return ((Skin) Util.getRandom(values(), entity.getRandom())).getId();
    }
}
