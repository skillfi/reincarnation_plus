
package com.github.skillfi.reincarnation_plus.item;

import com.github.skillfi.reincarnation_plus.api.gem.*;
import com.github.skillfi.reincarnation_plus.block.GemStoneBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class GemStoneItem extends BlockItem implements ICustomBlockEntityDataItem {

	public GemStoneItem(Block block, Properties properties){
		super(block, properties);
	}

	public GemType getType() {
		if (getBlock() instanceof GemStoneBlock block) {
			return block.type;
		}
		return new GemType("");
	}

	public static int getStatLevel(ItemStack stack, GemStat stat) {
		CompoundTag nbt = stack.getOrCreateTag();
		int statLevel = 0;
		if (nbt.contains(stat.getId())) {
			statLevel = nbt.getInt(stat.getId());
		}
		return statLevel;
	}

	public static ItemStack creativeTabRandomStats(Item item) {
		ItemStack stack = item.getDefaultInstance();
		CompoundTag nbt = stack.getOrCreateTag();
		nbt.putBoolean("randomStats", true);
		stack.setTag(nbt);
		return stack;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
		GemType type = getType();
		Color color = type.getColor();
		for (GemStat stat : type.getStats()) {
			int statLevel = getStatLevel(stack, stat);
			list.add(Component.translatable(stat.getTranslatedName()).append(": " + statLevel).withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)));
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
		if (!level.isClientSide()) {
			CompoundTag nbt = stack.getOrCreateTag();
			if (nbt.contains("randomStats")) {
				nbt.remove("randomStats");
				GemUtil.createCrystalItemStats(stack, getType(), level, 12);
				stack.setTag(nbt);
			}
		}
	}

	@Override
	public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag nbt) {
		if (!nbt.contains("Items")) {
			NonNullList<ItemStack> ret = NonNullList.withSize(1, ItemStack.EMPTY);
			ret.set(0, stack);
			ContainerHelper.saveAllItems(nbt, ret);
		}

		return nbt;
	}
}
