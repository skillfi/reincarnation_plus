package com.github.skillfi.reincarnation_plus.procedures;

import com.github.skillfi.reincarnation_plus.handler.RPItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EntityDropsProcedure {
	@SubscribeEvent
	public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
		execute(event, event.getCrafting());
	}

	public static void execute(ItemStack itemstack) {
		execute(null, itemstack);
	}

	private static void execute(@Nullable Event event, ItemStack itemstack) {
		if (itemstack.getItem() == RPItems.GEM_STONE.get()) {
			itemstack.getOrCreateTag().putDouble("Test", (Mth.nextDouble(RandomSource.create(), 1, 5)));
		}
	}
}
