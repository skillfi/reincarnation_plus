package com.github.skillfi.reincarnation_plus.core.menu.slot.automatic_slots;

import com.github.skillfi.reincarnation_plus.core.menu.AutomaticMagicInfuserMenu;
import com.github.skillfi.reincarnation_plus.core.menu.MagicInfuserMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ReiCatalystSlot extends SlotItemHandler {
    private final AutomaticMagicInfuserMenu menu;

    public ReiCatalystSlot(IItemHandler itemHandler, int index, int x, int y, AutomaticMagicInfuserMenu menu) {
        super(itemHandler, index, x, y);
        this.menu = menu;
    }

    public boolean mayPlace(ItemStack stack) {
        return true;
    }

    public void onTake(Player pPlayer, ItemStack pStack) {
        this.menu.blockEntity.resetInfusionProgress();
    }

    public void set(ItemStack pStack) {
        this.menu.blockEntity.setItem(getSlotIndex(), pStack);
        this.setChanged();
        if (!pStack.isEmpty()){
            this.menu.blockEntity.performInfusion();
        }

    }

}
