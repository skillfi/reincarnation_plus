package com.github.skillfi.reincarnation_plus.core.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ReiCreativeTab {
	public static final CreativeModeTab ITEMS = new CreativeModeTab("reincarnation_plus_items") {
		public ItemStack makeIcon() {return new ItemStack((ItemLike) ReiItems.GEM_STONE.get());
		}
	};
	public static final CreativeModeTab BLOCKS = new CreativeModeTab("reincarnation_plus_blocks") {
		public ItemStack makeIcon() {return new ItemStack((ItemLike) ReiItems.MAGIC_INFUSER.get());}
	};

	public ReiCreativeTab(){}
}
