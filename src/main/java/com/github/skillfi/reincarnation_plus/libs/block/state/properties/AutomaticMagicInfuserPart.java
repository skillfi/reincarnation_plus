package com.github.skillfi.reincarnation_plus.libs.block.state.properties;

import lombok.Getter;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

@Getter
public enum AutomaticMagicInfuserPart implements StringRepresentable {

    BASE("base"),
    TOP("top"),
    LEFT("left"),
    RIGHT("right");

    private final String name;

    public @NotNull String getSerializedName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    private AutomaticMagicInfuserPart(String name) {
        this.name = name;
    }

}
