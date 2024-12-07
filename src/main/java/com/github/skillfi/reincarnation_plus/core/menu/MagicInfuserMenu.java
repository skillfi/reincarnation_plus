package com.github.skillfi.reincarnation_plus.core.menu;

import com.github.manasmods.tensura.menu.TensuraMenuHelper;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagicInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.menu.slot.ReiCatalystSlot;
import com.github.skillfi.reincarnation_plus.core.menu.slot.ReiFuelSlot;
import com.github.skillfi.reincarnation_plus.core.menu.slot.ReiInfuseSlot;
import com.github.skillfi.reincarnation_plus.core.registry.ReiMenus;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MagicInfuserMenu extends AbstractContainerMenu {
    private static final Logger log = LogManager.getLogger(MagicInfuserMenu.class);
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = 27;
    private static final int VANILLA_SLOT_COUNT = 36;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = 36;
    private static final int TE_INVENTORY_SLOT_COUNT = 3;
    public final MagicInfuserBlockEntity blockEntity;
    private final Level level;
    private int fuelSlotIndex;
    private int meltingSlotIndex;
    private int infuseSlotIndex;
    private int infusingSlotIndex;

    public MagicInfuserMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, (MagicInfuserBlockEntity)inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public MagicInfuserMenu(int id, Inventory inv, MagicInfuserBlockEntity entity) {
        super((MenuType) ReiMenus.MAGIC_INFUSER_MENU.get(), id);
        checkContainerSize(inv, 4);
        this.blockEntity = entity;
        this.level = inv.player.level;
        this.addPlayerInventory(inv);
        this.addPlayerHotbar(inv);
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((handler) -> {
            this.fuelSlotIndex = this.addSlot(new ReiFuelSlot(handler, 0, 35, 97)).index;
            this.meltingSlotIndex = this.addSlot(new SlotItemHandler(handler, 1, 35, 51)).index;
            this.infuseSlotIndex = this.addSlot(new ReiCatalystSlot(handler, 2, 143, 36, this)).index;
            this.infusingSlotIndex = this.addSlot(new ReiInfuseSlot(handler, 3, 174, 36, this)).index;
        });
    }

    public boolean isSmelting() {
        return this.blockEntity.getMeltingProgress() > 0;
    }

    public boolean hasFuel() {
        return this.blockEntity.getFuelTime() > 0;
    }

    public boolean hasInfuse() {
        return this.blockEntity.getInfusionTime() > 0;
    }

    public int getMagiculesProgress() {
        int progress = (int)this.blockEntity.getMagicMaterialAmount();
        int maxmagicules = this.blockEntity.getMaxMagicMaterialAmount();
        int addtionMagicules = this.blockEntity.getAdditionalMagicMaterialAmount();
        int progressArrowSize = 74;
        return progress != 0 ? progress * progressArrowSize / (maxmagicules+addtionMagicules) : 0;
    }

    public int getScaledProgress() {
        int progress = this.blockEntity.getMeltingProgress();
        int maxProgress = 100;
        int progressArrowSize = 25;
        return progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getInfuseProgress() {
        int infuseProgress = this.blockEntity.getInfusionTime();
        int maxInfusionProgress = this.blockEntity.getMaxInfusionTime();
        int progressArrowSize = 74;
        return maxInfusionProgress != 0 ? (int)((float)infuseProgress / (float)maxInfusionProgress * (float)progressArrowSize) : 0;
    }

    public int getScaledFuelProgress() {
        int fuelProgress = this.blockEntity.getFuelTime();
        int maxFuelProgress = this.blockEntity.getMaxFuelTime();
        int fuelProgressSize = 13;
        return maxFuelProgress != 0 ? (int)((float)fuelProgress / (float)maxFuelProgress * (float)fuelProgressSize) : 0;
    }

    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = (Slot)this.slots.get(index);
        if (sourceSlot == null) {
            return ItemStack.EMPTY;
        } else if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        } else {
            ItemStack sourceStack = sourceSlot.getItem();
            ItemStack copyOfSourceStack = sourceStack.copy();
            if (index < 36) {
                Slot fuelSlot = (Slot)this.slots.get(this.fuelSlotIndex);
                Slot infuseSlot = (Slot)this.slots.get(this.infuseSlotIndex);
                if (fuelSlot.mayPlace(sourceStack) && !this.moveItemStackTo(sourceStack, this.fuelSlotIndex, this.fuelSlotIndex + 1, false)) {
                    return ItemStack.EMPTY;
                } else {
                    Slot meltingSlot = (Slot)this.slots.get(this.meltingSlotIndex);
                    return meltingSlot.mayPlace(sourceStack) && !this.moveItemStackTo(sourceStack, this.meltingSlotIndex, this.meltingSlotIndex + 1, false) ? ItemStack.EMPTY : TensuraMenuHelper.quickMoveStack(playerIn, sourceStack, sourceSlot, copyOfSourceStack);
                }
            } else if (index < 39) {
                return !this.moveItemStackTo(sourceStack, 0, 36, false) ? ItemStack.EMPTY : TensuraMenuHelper.quickMoveStack(playerIn, sourceStack, sourceSlot, copyOfSourceStack);
            } else {
                log.error("Invalid slotIndex {} for QuickCraft in BlockEntity at {}", index, this.blockEntity.getBlockPos());
                return ItemStack.EMPTY;
            }
        }
    }

    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(this.level, this.blockEntity.getBlockPos()), player, (Block) ReiBlockEntities.Blocks.MAGICAL_INFUSER.get());
    }

    /**
     * Додає слоти інвентарю гравця до користувацького інтерфейсу.
     *
     * <p>Цей метод створює та додає до користувацького інтерфейсу слоти, які відповідають рядкам інвентарю гравця,
     * за виключенням гарячої панелі. Метод розміщує слоти у вигляді сітки 3x9,
     * починаючи з визначеної початкової позиції.</p>
     *
     * @param playerInventory Інвентар гравця, який буде додано до кастомного користувацького інтерфейсу.
     */
    private void addPlayerInventory(Inventory playerInventory) {
        // Додавання слотів інвентарю гравця (3x9)
        for(int i = 0; i < 3; ++i) {
            for(int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 86 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        // Додавання слотів гарячої панелі (1x9)
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 86 + i * 18, 144));
        }

    }

    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.index == this.infusingSlotIndex ? false : super.canTakeItemForPickAll(pStack, pSlot);
    }
}