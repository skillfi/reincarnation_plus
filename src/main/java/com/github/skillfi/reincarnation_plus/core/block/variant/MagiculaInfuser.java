package com.github.skillfi.reincarnation_plus.core.block.variant;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.MagiculaInfuserBlock;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;

public enum MagiculaInfuser {

    LIT(1, "magicula_infuser_lit"),
    INFUSE(2, "magicula_infuser_infuse"),
    IDLE(0, "magicula_infuser");


    private static final MagiculaInfuser[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(MagiculaInfuser::getId)).toArray(MagiculaInfuser[]::new);
    @Getter
    private final int id;
    @Getter
    private final String location;

    MagiculaInfuser(int id, String location) {
        this.id = id;
        this.location = location;
    }

    public static ResourceLocation getTextureLocation(MagiculaInfuserBlockEntity entity) {
        if (entity.getBlockState().getValue(MagiculaInfuserBlock.INFUSION)){
            String location = MagiculaInfuser.byId(2).getLocation();
            return new ResourceLocation(ReiMod.MODID, "textures/blocks/" + location + ".png");
        } else if (entity.getBlockState().getValue(MagiculaInfuserBlock.LIT)) {
            String location = MagiculaInfuser.byId(1).getLocation();
            return new ResourceLocation(ReiMod.MODID, "textures/blocks/" + location + ".png");
        }
        String location = MagiculaInfuser.byId(0).getLocation();
        return new ResourceLocation(ReiMod.MODID, "textures/blocks/" + location + ".png");

    }

    public static ResourceLocation getTextureLocation(int id) {
        String location = MagiculaInfuser.byId(id).getLocation();
        return new ResourceLocation(ReiMod.MODID, "textures/blocks/" + location + ".png");

    }

    public static MagiculaInfuser byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
