package com.github.skillfi.reincarnation_plus.core.block.variant;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum EPRuneType implements StringRepresentable {
    BLANK,
    CAPACITY;

    @Override
    public String getSerializedName() {
        return this.toString();
    }

    @Override
    public String toString()
    {
        return name().toLowerCase(Locale.ROOT);
    }
}
