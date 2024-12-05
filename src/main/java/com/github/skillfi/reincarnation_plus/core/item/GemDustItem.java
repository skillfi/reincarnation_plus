
package com.github.skillfi.reincarnation_plus.core.item;

import com.github.skillfi.reincarnation_plus.core.registry.ReiCreativeTab;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;

import com.github.skillfi.reincarnation_plus.core.procedures.GemDustItemIsDroppedByPlayerProcedure;

public class GemDustItem extends Item {
	public GemDustItem() {
		super(new Item.Properties().tab(ReiCreativeTab.ITEMS).stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack itemstack, Player entity) {
		GemDustItemIsDroppedByPlayerProcedure.execute(entity.level, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
		return true;
	}
}
