package com.github.skillfi.reincarnation_plus.core.item;

import com.github.skillfi.reincarnation_plus.core.registry.ReiCreativeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class SuperCharger extends Item {
    public SuperCharger() {
        super(new Item.Properties().tab(ReiCreativeTab.ITEMS).stacksTo(1).rarity(Rarity.RARE));
    }
}
