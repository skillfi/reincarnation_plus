package com.github.skillfi.reincarnation_plus.libs.api.aura;
import com.github.skillfi.reincarnation_plus.libs.capability.aura.IAuraChunkCapability;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AuraEvent extends Event {
    @Getter
    public final LevelChunk chunk;
    public final Level level;
    protected final IAuraChunkCapability capability;

    public double getAura() {
        return this.capability.getAura();
    }

    public double getRegenerationRate() {
        return this.capability.getRegenerationRate();
    }

    public AuraEvent(LevelChunk chunk, IAuraChunkCapability capability) {
        this.chunk = chunk;
        this.capability = capability;
        this.level = chunk.getLevel();
    }

    public double getMaxAura() {
        return this.capability.getMaxAura();
    }

    /**
     * Подія ініціалізації аури.
     */
    public static class Initialization extends AuraEvent {
        @Getter
        @Setter
        private double newMaxAura = this.getMaxAura();
        @Getter
        @Setter
        private double newRegenerationRate = this.getRegenerationRate();

        public Initialization(LevelChunk chunk, IAuraChunkCapability capability) {
            super(chunk, capability);
        }
    }

    /**
     * Подія регенерації аури.
     */
    @Cancelable
    public static class Regeneration extends AuraEvent {
        @Getter
        @Setter
        private double amount = this.getRegenerationRate() / (double)20.0F;;

        public Regeneration(LevelChunk chunk, IAuraChunkCapability capability) {
            super(chunk, capability);
        }

        public double getNewAura() {
            return Math.min(this.getAura() + this.amount, this.getMaxAura());
        }
    }

    /**
     * Подія реєстрації нового модифікатора аури.
     */
    public static class RegisterModifier extends AuraEvent {
        @Getter
        private final BlockPos position;
        private final List<AuraModifier> modifiers;

        public RegisterModifier(LevelChunk chunk, IAuraChunkCapability capability, BlockPos position) {
            super(chunk, capability);
            this.position = position;
            this.modifiers = new ArrayList();
        }

        public synchronized void addModifier(AuraModifier modifier) {
            this.modifiers.add(modifier);
        }

        public List<AuraModifier> getModifiers() {
            // Повернення immutabel списку з уже відсортованої копії
            return ImmutableList.sortedCopyOf(Comparator.reverseOrder(), this.modifiers);
        }
    }

    /**
     * Подія споживання аури.
     */
    @Cancelable
    public static class Consume extends AuraEvent {
        @Getter
        @Setter
        private double amount;
        @Getter
        private final BlockPos position;

        public Consume(LevelChunk chunk, IAuraChunkCapability capability, BlockPos position, double amount) {
            super(chunk, capability);
            this.position = position;
            this.amount = amount;
        }
    }
}
