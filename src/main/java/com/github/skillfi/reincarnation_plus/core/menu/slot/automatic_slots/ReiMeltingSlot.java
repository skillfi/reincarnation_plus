package com.github.skillfi.reincarnation_plus.core.menu.slot.automatic_slots;

import com.github.skillfi.reincarnation_plus.core.menu.AutoInfuserMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ReiMeltingSlot extends SlotItemHandler {
    private final AutoInfuserMenu menu;

    public ReiMeltingSlot(IItemHandler itemHandler, int index, int x, int y, AutoInfuserMenu menu) {
        super(itemHandler, index, x, y);
        this.menu = menu;
    }

    public boolean mayPlace(ItemStack stack) {
        return true;
    }

    public void set(ItemStack pStack) {
        this.menu.blockEntity.setItem(getSlotIndex(), pStack);
        this.setChanged();
        if (!pStack.isEmpty()){
            this.menu.blockEntity.performMelting();
        }

    }

}
