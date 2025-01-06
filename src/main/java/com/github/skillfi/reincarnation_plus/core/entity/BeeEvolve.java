package com.github.skillfi.reincarnation_plus.core.entity;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public enum BeeEvolve {

    MAGIC(0, 1000.0, 8000.0, new ResourceLocation("productivebees", "magic"), new ResourceLocation("productivebees", "magic")),
    LOW_MAGIC_STEEL(1, 8000.0, 15000.0, new ResourceLocation("productivebees", "low_magic_steel"), new ResourceLocation("productivebees", "high_magic_steel")),
    HIGH_MAGIC_STEEL(2, 15000.0, 50000.0, new ResourceLocation("productivebees", "high_magic_steel"), new ResourceLocation("productivebees", "pure_magic_steel")),
    PURE_MAGIC_STEEL(3, 50000.0, 225000.0, HIGH_MAGIC_STEEL.getEvolveBeeName(), new ResourceLocation("productivebees", "adamantite")),
    MITHRIL(4, 50000.0, 225000.0, new ResourceLocation("productivebees", "mithril"), new ResourceLocation("productivebees", "adamantite")),
    ADAMANTITE(5, 225000.0, 750000.0, PURE_MAGIC_STEEL.getEvolveBeeName(), new ResourceLocation("productivebees", "hihiirokane")),
    ORICHALCUM(6, 225000.0, 750000.0, new ResourceLocation("productivebees", "orichalcum"),new ResourceLocation("productivebees", "hihiirokane"));


    private static final BeeEvolve[] BY_ID = Arrays.stream(values())
            .sorted(Comparator.comparingInt(BeeEvolve::getId))
            .toArray(BeeEvolve[]::new);

    private static final Map<ResourceLocation, BeeEvolve> BY_BEE_NAME = Arrays.stream(values())
            .collect(Collectors.toMap(BeeEvolve::getBeeName, beeEvolve -> beeEvolve));

    @Getter private final double minEP;
    @Getter private final double maxEP;
    @Getter private final int id;
    @Getter private final ResourceLocation beeName;
    @Getter private final ResourceLocation evolveBeeName;

    BeeEvolve(int id, double minEP, double maxEP, ResourceLocation beeName, ResourceLocation evolveBeeName){
        this.id = id;
        this.minEP = minEP;
        this.maxEP = maxEP;
        this.beeName = beeName;
        this.evolveBeeName = evolveBeeName;
    }

    public static BeeEvolve byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static BeeEvolve byBeeName(String beeName) {
        ResourceLocation name = new ResourceLocation("productivebees", beeName);
        return BY_BEE_NAME.get(name);
    }

    public static BeeEvolve byBeeName(ResourceLocation beeName) {
        return BY_BEE_NAME.get(beeName);
    }


}
