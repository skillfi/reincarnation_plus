package com.github.skillfi.reincarnation_plus.procedures;

import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusModItems;

public class OgreManOnInitialEntitySpawnProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			Entity _entity = entity;
			if (_entity instanceof Player _player) {
				_player.getInventory().armor.set(1, new ItemStack(ReincarnationPlusModItems.OGRE_ARMOR_1_CHESTPLATE.get()));
				_player.getInventory().setChanged();
			} else if (_entity instanceof LivingEntity _living) {
				_living.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ReincarnationPlusModItems.OGRE_ARMOR_1_CHESTPLATE.get()));
			}
		}
		{
			Entity _entity = entity;
			if (_entity instanceof Player _player) {
				_player.getInventory().armor.set(2, new ItemStack(ReincarnationPlusModItems.OGRE_ARMOR_1_LEGGINGS.get()));
				_player.getInventory().setChanged();
			} else if (_entity instanceof LivingEntity _living) {
				_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ReincarnationPlusModItems.OGRE_ARMOR_1_LEGGINGS.get()));
			}
		}
	}
}
