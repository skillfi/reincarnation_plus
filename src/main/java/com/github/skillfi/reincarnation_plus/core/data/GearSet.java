package com.github.skillfi.reincarnation_plus.core.data;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class GearSet {
    @Getter private final ResourceLocation[] firstGear;
    @Getter private final ResourceLocation[] evolveGear;

    public GearSet(ResourceLocation[] firstGear, ResourceLocation[] evolveGear) {
        this.firstGear = firstGear;
        this.evolveGear = evolveGear;
    }

    // Метод для перетворення у мапу
    public Map<ResourceLocation, ResourceLocation> toMap() {
        Map<ResourceLocation, ResourceLocation> gearMap = new HashMap<>();

        // Об'єднання firstGear як ключів і evolveGear як значень
        for (int i = 0; i < Math.min(firstGear.length, evolveGear.length); i++) {
            gearMap.put(firstGear[i], evolveGear[i]);
        }

        return gearMap;
    }
}

