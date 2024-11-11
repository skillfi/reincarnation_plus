
package com.github.skillfi.reincarnation_plus.item;

import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusModTabs;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;

import com.github.skillfi.reincarnation_plus.procedures.GemDustItemIsDroppedByPlayerProcedure;

public class GemDustItem extends Item {
	public GemDustItem() {
		super(new Item.Properties().tab(ReincarnationPlusModTabs.TAB_REINCARNATION_PLUS).stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack itemstack, Player entity) {
		GemDustItemIsDroppedByPlayerProcedure.execute(entity.level, entity.getX(), entity.getY(), entity.getZ(), entity);
		return true;
	}
}
