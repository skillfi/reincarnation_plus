package com.github.skillfi.reincarnation_plus.core.menu.slot.automatic_slots;

import com.github.skillfi.reincarnation_plus.core.menu.AutoInfuserMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ReiInfuseSlot  extends SlotItemHandler {
    private final AutoInfuserMenu menu;

    public ReiInfuseSlot(IItemHandler itemHandler, int index, int x, int y, AutoInfuserMenu menu) {
        super(itemHandler, index, x, y);
        this.menu = menu;
    }

    public boolean mayPlace(ItemStack stack) {
        return false;
    }
}
