package com.github.skillfi.reincarnation_plus.core.menu.slot;

import com.github.skillfi.reincarnation_plus.core.menu.MagicInfuserMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ReiCatalystSlot extends SlotItemHandler {
    private final MagicInfuserMenu menu;

    public ReiCatalystSlot(IItemHandler itemHandler, int index, int x, int y, MagicInfuserMenu menu) {
        super(itemHandler, index, x, y);
        this.menu = menu;
    }

    public boolean mayPlace(ItemStack stack) {
        return this.menu.blockEntity.isInfuse(stack);
    }

    public void onTake(Player pPlayer, ItemStack pStack) {
        this.menu.blockEntity.resetInfusionProgress();
    }

}
