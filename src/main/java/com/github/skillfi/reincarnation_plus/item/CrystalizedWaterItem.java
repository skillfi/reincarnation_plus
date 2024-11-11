
package com.github.skillfi.reincarnation_plus.item;

import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusModFluids;
import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusModTabs;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

public class CrystalizedWaterItem extends BucketItem {
	public CrystalizedWaterItem() {
		super(ReincarnationPlusModFluids.CRYSTALIZED_WATER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON).tab(ReincarnationPlusModTabs.TAB_REINCARNATION_PLUS));
	}
}
