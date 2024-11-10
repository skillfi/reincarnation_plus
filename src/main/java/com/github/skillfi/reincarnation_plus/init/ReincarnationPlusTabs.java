
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;

public class ReincarnationPlusTabs {
	public static CreativeModeTab TAB_REINCARNATION_PLUS;

	public static void load() {
		TAB_REINCARNATION_PLUS = new CreativeModeTab("tab_reincarnation_plus") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(ReincarnationPlusItems.GEM_STONE.get());
			}

			@Override
			public boolean hasSearchBar() {
				return true;
			}
		}.setBackgroundSuffix("item_search.png");
	}
}
