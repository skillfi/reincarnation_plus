
package com.github.skillfi.reincarnation_plus.core.item;

import com.github.skillfi.reincarnation_plus.core.registry.fluids.ReiFluids;
import com.github.skillfi.reincarnation_plus.core.registry.ReiCreativeTab;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

public class CrystalizedWaterItem extends BucketItem {
	public CrystalizedWaterItem() {
		super(ReiFluids.CRYSTALIZED_WATER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON).tab(ReiCreativeTab.ITEMS));
	}
}
