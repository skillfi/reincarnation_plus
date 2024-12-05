package com.github.skillfi.reincarnation_plus.libs.data.pack;

import com.github.skillfi.reincarnation_plus.libs.api.aura.AuraModifier;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public record LevelAuraModifier(ResourceLocation worldId, int priority, List<DataPackAuraModifier> modifiers, List<DataPackAuraModifier> regenModifiers) implements AuraModifier {
    public static final Codec<LevelAuraModifier> CODEC = RecordCodecBuilder.create((instance) -> instance.group(ResourceLocation.CODEC.fieldOf("world").forGetter(LevelAuraModifier::worldId), Codec.intRange(0, 255).optionalFieldOf("priority", 0).forGetter(LevelAuraModifier::priority), DataPackAuraModifier.CODEC.listOf().optionalFieldOf("modifiers", List.of()).forGetter(LevelAuraModifier::modifiers), DataPackAuraModifier.CODEC.listOf().optionalFieldOf("regen_modifiers", List.of()).forGetter(LevelAuraModifier::regenModifiers)).apply(instance, LevelAuraModifier::new));

    public LevelAuraModifier(ResourceLocation worldId, int priority, List<DataPackAuraModifier> modifiers, List<DataPackAuraModifier> regenModifiers) {
        this.worldId = worldId;
        this.priority = priority;
        this.modifiers = modifiers;
        this.regenModifiers = regenModifiers;
    }

    public double getMaxAura(double oldMaxAura) {
        for(DataPackAuraModifier modifier : this.modifiers) {
            switch (modifier.mode()) {
                case ADD:
                    oldMaxAura += modifier.value();
                    break;
                case MULTIPLY:
                    oldMaxAura *= modifier.value();
            }
        }

        return oldMaxAura;
    }

    public double getRegenerationRate(double oldRegenerationRate) {
        for(DataPackAuraModifier modifier : this.regenModifiers) {
            switch (modifier.mode()) {
                case ADD:
                    oldRegenerationRate += modifier.value();
                    break;
                case MULTIPLY:
                    oldRegenerationRate *= modifier.value();
            }
        }

        return oldRegenerationRate;
    }

    public int getPriority() {
        return this.priority;
    }

    public void buildJson(BiConsumer<ResourceLocation, Supplier<JsonElement>> consumer) {
        consumer.accept(this.worldId, (Supplier)() -> (JsonElement)CODEC.encodeStart(JsonOps.INSTANCE, this).result().orElseThrow(() -> new IllegalStateException("Could not serialize " + this)));
    }

    public ResourceLocation worldId() {
        return this.worldId;
    }

    public int priority() {
        return this.priority;
    }

    public List<DataPackAuraModifier> modifiers() {
        return this.modifiers;
    }

    public List<DataPackAuraModifier> regenModifiers() {
        return this.regenModifiers;
    }
}
