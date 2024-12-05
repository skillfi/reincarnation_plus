package com.github.skillfi.reincarnation_plus.libs.capability.aura;

import com.github.skillfi.reincarnation_plus.core.ReiConfig;
import com.github.skillfi.reincarnation_plus.libs.api.aura.AuraModifier;
import com.llamalad7.mixinextras.lib.apache.commons.tuple.Pair;

import java.util.List;

public class AuraCacheEntry {
    private int remains; // Лічильник, що визначає, коли потрібно оновити кеш.
    private final List<AuraModifier> modifiers; // Список модифікаторів аури.
    private Pair<Double, Double> maxValueCache; // Кеш максимального значення аури.
    private double currentAuraPercentage; // Відсоткове значення поточної аури.

    /**
     * Зменшує залишок часу життя кешу.
     */
    public void tick() {
        --this.remains;
    }

    /**
     * Перевіряє, чи кеш застарілий.
     *
     * @return true, якщо час життя кешу вичерпано.
     */
    public boolean isOutdated() {
        return this.remains <= 0;
    }

    /**
     * Обчислює максимальне значення аури з урахуванням модифікаторів.
     *
     * @param maxAura Базове максимальне значення аури.
     * @return Нове максимальне значення аури після застосування модифікаторів.
     */
    public double getMaxAura(double maxAura) {
        if (this.maxValueCache != null && this.maxValueCache.getKey().equals(maxAura)) {
            return this.maxValueCache.getValue();
        } else {
            double newMaxAura = maxAura;

            // Застосовуємо всі модифікатори
            for (AuraModifier modifier : this.modifiers) {
                newMaxAura = modifier.getMaxAura(newMaxAura);
            }

            // Оновлюємо кеш
            this.maxValueCache = Pair.of(maxAura, newMaxAura);
            return newMaxAura;
        }
    }

    /**
     * Встановлює поточне значення аури як відсоток від максимального значення.
     *
     * @param currentAura Значення поточної аури.
     * @param maxAura Максимальне значення аури.
     */
    public void setAura(double currentAura, double maxAura) {
        if (maxAura <= 0) {
            this.currentAuraPercentage = 0;
        } else {
            this.currentAuraPercentage = currentAura / maxAura;
        }
    }

    /**
     * Обчислює поточне значення аури для блоку.
     *
     * @param maxAura        Максимальне значення аури.
     * @param auraPercentage Поточний відсоток заповнення аури.
     * @return Поточне значення аури.
     */
    public double getAura(double maxAura, double auraPercentage) {
        return this.getMaxAura(maxAura) * auraPercentage;
    }

    /**
     * Конструктор для створення нового запису кешу аури.
     *
     * @param modifiers Список модифікаторів аури.
     */
    public AuraCacheEntry(List<AuraModifier> modifiers) {
        this.remains = ReiConfig.INSTANCE.auraConfig.modifierUpdateInterval.get();
        this.modifiers = modifiers;
        this.maxValueCache = null;
        this.currentAuraPercentage = 0;
    }

    /**
     * Статичний метод для створення нового запису кешу.
     *
     * @param modifiers Список модифікаторів аури.
     * @return Новий об'єкт `AuraCacheEntry`.
     */
    public static AuraCacheEntry of(List<AuraModifier> modifiers) {
        return new AuraCacheEntry(modifiers);
    }
}
