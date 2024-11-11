
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ReincarnationPlusModTabs {
	public static CreativeModeTab TAB_REINCARNATION_PLUS;

	public static void load() {
		TAB_REINCARNATION_PLUS = new CreativeModeTab("tabisekai_quartet") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(ReincarnationPlusModItems.GEM_STONE.get());
			}

			@Override
			public boolean hasSearchBar() {
				return true;
			}
		}.setBackgroundSuffix("item_search.png");
	}
}
