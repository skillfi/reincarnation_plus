package com.github.skillfi.reincarnation_plus.api.gem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GemUtil {
    public static void createCrystalItemStats(ItemStack stack, GemType type, Level level, int count) {
        int maxCount = 0;
        Map<GemStat, Integer> intStats = new HashMap<>();
        ArrayList<GemStat> stats = new ArrayList<>();

        for (GemStat stat : type.getStats()) {
            maxCount = maxCount + stat.getMaxLevel();
            intStats.put(stat, 0);
            stats.add(stat);
        }
        if (count > maxCount) {
            count = maxCount;
        }

        for (int i = 0; i < count; i++) {
            int id = Mth.nextInt(level.random, 0, stats.size() - 1);
            GemStat stat = stats.get(id);
            intStats.put(stat, intStats.get(stat) + 1);
            if (intStats.get(stat) >= stat.getMaxLevel()) {
                stats.remove(stat);
            }
        }

        CompoundTag nbt = stack.getOrCreateTag();
        for (GemStat stat : type.getStats()) {
            nbt.putInt(stat.getId(), intStats.get(stat));
        }
        stack.setTag(nbt);
    }

    public static int getStatLevel(CompoundTag nbt, GemStat stat) {
        int statlevel = 0;
        if (nbt.contains(stat.getId())) {
            statlevel = nbt.getInt(stat.getId());
        }
        return statlevel;
    }
}
