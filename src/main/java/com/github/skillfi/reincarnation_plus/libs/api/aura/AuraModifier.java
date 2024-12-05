package com.github.skillfi.reincarnation_plus.libs.api.aura;

import org.jetbrains.annotations.NotNull;

/**
 * Інтерфейс для модифікаторів аури.
 * Використовується для зміни базових властивостей аури, таких як максимальне значення або регенерація.
 */
public interface AuraModifier extends Comparable<AuraModifier>{
    int getPriority();
    /**
     * Модифікує максимальне значення аури.
     *
     * @param baseMaxAura Базове максимальне значення аури.
     * @return Нове максимальне значення аури після застосування модифікатора.
     */
    double getMaxAura(double baseMaxAura);

    /**
     * Модифікує швидкість регенерації аури.
     *
     * @param baseRegenerationRate Базова швидкість регенерації аури.
     * @return Нова швидкість регенерації аури після застосування модифікатора.
     */
    double getRegenerationRate(double baseRegenerationRate);
    default int compareTo(@NotNull AuraModifier o) {
        return Integer.compare(this.getPriority(), o.getPriority());
    }
}
