package com.github.skillfi.reincarnation_plus.libs.api.aura;

import com.github.skillfi.reincarnation_plus.libs.capability.aura.AuraChunkCapabilityImpl;
import com.github.skillfi.reincarnation_plus.libs.capability.aura.IAuraChunkCapability;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

/**
 * API для роботи з системою аури.
 */
public final class AuraAPI {

    // Отримання можливостей аури з чанку
    public static IAuraChunkCapability getCapability(LevelChunk chunk) {
        return chunk.getCapability(AuraChunkCapabilityImpl.CAPABILITY).orElseThrow(() ->
                new IllegalStateException("Capability must be present at this point!")
        );
    }
    /**
     * Отримує поточний рівень аури у зазначеній позиції.
     *
     * @param level Рівень світу.
     * @param pos   Позиція у світі.
     * @return Поточний рівень аури.
     */
    public static double getAura(Level level, BlockPos pos) {
        IAuraChunkCapability cap = getCapability(level.getChunkAt(pos));
        return cap.getAura(pos);
    }

    /**
     * Отримує поточний рівень аури біля сутності.
     *
     * @param entity Сутність.
     * @return Поточний рівень аури.
     */
    public static double getAura(LivingEntity entity) {
        return getAura(entity.level, entity.blockPosition());
    }

    /**
     * Отримує максимальне значення аури у зазначеній позиції.
     *
     * @param level Рівень світу.
     * @param pos   Позиція у світі.
     * @return Максимальне значення аури.
     */
    public static double getMaxAura(Level level, BlockPos pos) {
        IAuraChunkCapability cap = getCapability(level.getChunkAt(pos));
        return cap.getMaxAura(pos);
    }

    /**
     * Отримує максимальне значення аури біля сутності.
     *
     * @param entity Сутність.
     * @return Максимальне значення аури.
     */
    public static double getMaxAura(LivingEntity entity) {
        return getMaxAura(entity.level, entity.blockPosition());
    }

    /**
     * Отримує пару значень поточної та максимальної аури у зазначеній позиції.
     *
     * @param level Рівень світу.
     * @param pos   Позиція у світі.
     * @return Пара (поточна аура, максимальна аура).
     */
    public static Pair<Double, Double> getAuraPair(Level level, BlockPos pos) {
        IAuraChunkCapability cap = getCapability(level.getChunkAt(pos));
        return Pair.of(cap.getAura(pos), cap.getMaxAura(pos));
    }

    /**
     * Отримує пару значень поточної та максимальної аури біля сутності.
     *
     * @param entity Сутність.
     * @return Пара (поточна аура, максимальна аура).
     */
    public static Pair<Double, Double> getAuraPair(LivingEntity entity) {
        return getAuraPair(entity.level, entity.blockPosition());
    }

    /**
     * Споживає зазначену кількість аури у позиції.
     *
     * @param level  Рівень світу.
     * @param pos    Позиція у світі.
     * @param amount Кількість аури для споживання.
     * @return true, якщо споживання успішне, інакше false.
     */
    public static boolean consumeAura(Level level, BlockPos pos, double amount) {
        IAuraChunkCapability cap = getCapability(level.getChunkAt(pos));
        return cap.consumeAura(pos, amount);
    }

    /**
     * Споживає зазначену кількість аури біля сутності.
     *
     * @param entity Сутність.
     * @param amount Кількість аури для споживання.
     * @return true, якщо споживання успішне, інакше false.
     */
    public static boolean consumeAura(LivingEntity entity, double amount) {
        return consumeAura(entity.level, entity.blockPosition(), amount);
    }

    /**
     * Отримує швидкість регенерації аури у зазначеній позиції.
     *
     * @param level Рівень світу.
     * @param pos   Позиція у світі.
     * @return Швидкість регенерації аури.
     */
    public static double getAuraRegenerationRate(Level level, BlockPos pos) {
        IAuraChunkCapability cap = getCapability(level.getChunkAt(pos));
        return cap.getRegenerationRate();
    }

    /**
     * Отримує швидкість регенерації аури біля сутності.
     *
     * @param entity Сутність.
     * @return Швидкість регенерації аури.
     */
    public static double getAuraRegenerationRate(LivingEntity entity) {
        return getAuraRegenerationRate(entity.level, entity.blockPosition());
    }

    /**
     * Приватний конструктор, щоб запобігти створенню екземпляра класу.
     */
    private AuraAPI() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
