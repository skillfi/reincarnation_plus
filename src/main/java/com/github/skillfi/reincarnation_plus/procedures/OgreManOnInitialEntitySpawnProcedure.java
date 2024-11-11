package com.github.skillfi.reincarnation_plus.procedures;

import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class OgreManOnInitialEntitySpawnProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			Entity _entity = entity;
			if (_entity instanceof Player _player) {
				_player.getInventory().armor.set(1, new ItemStack(ReincarnationPlusModItems.OGRE_ARMOR_CHESTPLATE.get()));
				_player.getInventory().setChanged();
			} else if (_entity instanceof LivingEntity _living) {
				_living.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ReincarnationPlusModItems.OGRE_ARMOR_CHESTPLATE.get()));
			}
		}
		{
			Entity _entity = entity;
			if (_entity instanceof Player _player) {
				_player.getInventory().armor.set(2, new ItemStack(ReincarnationPlusModItems.OGRE_ARMOR_LEGGINGS.get()));
				_player.getInventory().setChanged();
			} else if (_entity instanceof LivingEntity _living) {
				_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ReincarnationPlusModItems.OGRE_ARMOR_LEGGINGS.get()));
			}
		}
	}
}
