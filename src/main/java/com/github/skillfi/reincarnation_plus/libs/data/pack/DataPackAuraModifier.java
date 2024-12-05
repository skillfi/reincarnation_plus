package com.github.skillfi.reincarnation_plus.libs.data.pack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DataPackAuraModifier(Mode mode, double value) {
    public static final Codec<DataPackAuraModifier> CODEC = RecordCodecBuilder.create((instance) -> instance.group(Codec.STRING.fieldOf("mode").xmap(Mode::valueOf, Enum::name).forGetter(DataPackAuraModifier::mode), Codec.DOUBLE.fieldOf("value").forGetter(DataPackAuraModifier::value)).apply(instance, DataPackAuraModifier::new));

    public DataPackAuraModifier(Mode mode, double value) {
        this.mode = mode;
        this.value = value;
    }

    public Mode mode() {
        return this.mode;
    }

    public double value() {
        return this.value;
    }

    public static enum Mode {
        ADD,
        MULTIPLY;

        private Mode() {
        }
    }
}
