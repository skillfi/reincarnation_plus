
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.handler;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class RPTabs {
	public static CreativeModeTab TAB_REINCARNATION_PLUS;
	public static CreativeModeTab TAB_REINCARNATION_PLUS_BLOCKS;

	public static void load() {
		TAB_REINCARNATION_PLUS = new CreativeModeTab("tab_reincarnation_plus") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(RPItems.GEM_STONE.get());
			}

			@Override
			public boolean hasSearchBar() {
				return true;
			}
		}.setBackgroundSuffix("item_search.png");
		TAB_REINCARNATION_PLUS_BLOCKS = new CreativeModeTab("tab_reincarnation_plus_blocks") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(RPItems.GEMS_BLOCK.get());
			}

			@Override
			public boolean hasSearchBar() {
				return true;
			}
		}.setBackgroundSuffix("item_search.png");
	}
}
