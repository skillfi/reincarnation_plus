
package com.github.skillfi.reincarnation_plus.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusTabs;
import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusFluids;

public class CrystalizedWaterItem extends BucketItem {
	public CrystalizedWaterItem() {
		super(ReincarnationPlusFluids.CRYSTALIZED_WATER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON).tab(ReincarnationPlusTabs.TAB_REINCARNATION_PLUS));
	}
}
