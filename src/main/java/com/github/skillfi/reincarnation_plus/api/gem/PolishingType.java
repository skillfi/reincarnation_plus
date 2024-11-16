package com.github.skillfi.reincarnation_plus.api.gem;

import java.awt.*;

public class PolishingType implements IPolishingType{

    public String id;
    public int level;

    public PolishingType(String id, int level) {
        this.id = id;
        this.level = level;
    }

    @Override
    public boolean hasParticle() {
        return false;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public int getPolishingLevel() {
        return 0;
    }
}
