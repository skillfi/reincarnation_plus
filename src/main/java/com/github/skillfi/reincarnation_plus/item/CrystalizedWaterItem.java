
package com.github.skillfi.reincarnation_plus.item;

import com.github.skillfi.reincarnation_plus.handler.RPFluids;
import com.github.skillfi.reincarnation_plus.handler.RPTabs;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

public class CrystalizedWaterItem extends BucketItem {
	public CrystalizedWaterItem() {
		super(RPFluids.CRYSTALIZED_WATER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON).tab(RPTabs.TAB_REINCARNATION_PLUS));
	}
}
