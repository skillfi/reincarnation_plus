package com.github.skillfi.reincarnation_plus.api.gem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;

public interface IGemType {

    public Color getColor();

    public ItemStack getFracturedGem();

    public ItemStack getGem();

    public void addStat(GemStat stat);

    public ArrayList<GemStat> getStats();

    public String getId();

    public String getTranslatedName();
}
