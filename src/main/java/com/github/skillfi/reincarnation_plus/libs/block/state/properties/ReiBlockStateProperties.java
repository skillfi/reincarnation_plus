package com.github.skillfi.reincarnation_plus.libs.block.state.properties;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ReiBlockStateProperties {

    public static final EnumProperty<MagicInfuserPart> MAGIC_INFUSER_PART = EnumProperty.create("part", MagicInfuserPart.class);
    public static final EnumProperty<AutomaticMagicInfuserPart> AUTOMATIC_MAGIC_INFUSER_PART = EnumProperty.create("automatic_part", AutomaticMagicInfuserPart.class);
    public static final IntegerProperty MAGICULES = IntegerProperty.create("magicules", 0, 50000);
    public ReiBlockStateProperties() {
    }
}
