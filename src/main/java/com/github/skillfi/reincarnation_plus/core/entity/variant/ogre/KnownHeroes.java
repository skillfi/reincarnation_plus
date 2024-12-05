package com.github.skillfi.reincarnation_plus.core.entity.variant.ogre;

import java.util.Arrays;
import java.util.Comparator;

public enum KnownHeroes {
    BENIMARU(0, "benimaru"),
    SOUEI(1, "souei"),
    SHUNA(2, "shuna"),
    SHION(3, "shion");

    private static final KnownHeroes[] BY_ID = Arrays.stream(values())
            .sorted(Comparator.comparingInt(KnownHeroes::getId))
            .toArray(KnownHeroes[]::new);

    private final int id;
    private final String location;

    KnownHeroes(int id, String name) {
        this.id = id;
        this.location = name;
    }

    public int getId() {
        return this.id;
    }

    public String getLocation() {
        return this.location;
    }

    public static KnownHeroes byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
