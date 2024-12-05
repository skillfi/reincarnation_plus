package com.github.skillfi.reincarnation_plus.core.entity.variant.ogre;

import com.github.skillfi.reincarnation_plus.core.entity.RPEntitiesStats;

import java.util.Arrays;
import java.util.Comparator;

public enum Evolving {
    OGRE(5, RPEntitiesStats.Ogre),
    KIJIN(4, RPEntitiesStats.Kijin),
    ONI(3, RPEntitiesStats.MysticOni),
    DIVINE_ONI(2, RPEntitiesStats.DivineOni),
    DIVINE_FIGHTER(0, RPEntitiesStats.DivineFighter);

    private static final Evolving[] BY_ID = Arrays.stream(values())
            .sorted(Comparator.comparingInt(Evolving::getEvolution))
            .toArray(Evolving[]::new);

    private final int evolution;
    private final RPEntitiesStats stats;

    Evolving(int evolution, RPEntitiesStats stats) {
        this.evolution = evolution;
        this.stats = stats;
    }

    public int getEvolution() {
        return this.evolution;
    }

    public static Evolving byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public RPEntitiesStats getStats() {
        return this.stats;
    }
}
