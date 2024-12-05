package com.github.skillfi.reincarnation_plus.libs.api.gem;

import net.minecraft.nbt.CompoundTag;

public class GemStat implements IGemStat {
    public String id;
    public int maxLevel;

    public GemStat(String id, int maxLevel){
        this.id = id;
        this.maxLevel = maxLevel;
    }

    public GemStat(String id, double maxLevel){
        this.id = id;
        this.maxLevel = (int) maxLevel;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public String getTranslatedName() {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String statId = id.substring(i+1);
        return  "gem_stat." + modId + "." + statId;
    }

    public CompoundTag to(){
      CompoundTag nbt = new CompoundTag();
      nbt.putString("id", this.getId());
      nbt.putInt("maxLevel", this.getMaxLevel());
      return nbt;
    }

    public static GemStat of(CompoundTag tag){
        GemStat gemStat = new GemStat("",1);
        gemStat.id = tag.getString("id");
        gemStat.maxLevel = tag.getInt("maxLevel");
        return gemStat;
    }
}
