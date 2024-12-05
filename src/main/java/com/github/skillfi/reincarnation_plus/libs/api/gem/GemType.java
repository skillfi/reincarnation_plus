package com.github.skillfi.reincarnation_plus.libs.api.gem;

import com.github.skillfi.reincarnation_plus.core.registry.ReiGems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;

public class GemType implements IGemType {
    public ArrayList<GemStat> stats = new ArrayList<>();
    public String id;

    public GemType(String id){
        this.id = id;
        addStat(ReiGems.STATIC_DAMAGE);
        addStat(ReiGems.STATIC_STAMINA);
        addStat(ReiGems.STATIC_SPEED);
        addStat(ReiGems.STATIC_MANA);
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public ItemStack getFracturedGem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getGem() {
        return ItemStack.EMPTY;
    }

    @Override
    public void addStat(GemStat stat) {
        stats.add(stat);
    }

    @Override
    public ArrayList<GemStat> getStats() {
        return stats;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String gemTypeId = id.substring(i + 1);
        return "gem_type."  + modId + "." + gemTypeId;
    }

    public CompoundTag to() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("id", this.getId());

        // Створюємо окремий CompoundTag для stats
        CompoundTag statsTag = new CompoundTag();
        int index = 0;

        for (GemStat stat : this.stats) {
            statsTag.put(stat.getId() + "_" + index, stat.to());
            index++;
        }

        nbt.put("stats", statsTag); // Додаємо stats як CompoundTag
        nbt.putInt("statsCount", this.stats.size()); // Зберігаємо кількість stats

        return nbt;
    }

    public static GemType of(CompoundTag tag) {
        GemType gemType = new GemType(tag.getString("id"));
        ArrayList<GemStat> stats = new ArrayList<>();

        // Витягуємо stats
        CompoundTag statsTag = tag.getCompound("stats");
        int statsCount = tag.getInt("statsCount");

        for (int i = 0; i < statsCount; i++) {
            String statKey = "stat_" + i;
            if (statsTag.contains(statKey)) {
                stats.add(GemStat.of(statsTag.getCompound(statKey)));
            }
        }

        return gemType;
    }

}
