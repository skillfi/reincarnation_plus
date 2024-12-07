package com.github.skillfi.reincarnation_plus.core.block.entity;

import com.github.skillfi.reincarnation_plus.core.block.MagicInfuserBlock;
import com.github.skillfi.reincarnation_plus.core.menu.MagicInfuserMenu;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
import com.github.skillfi.reincarnation_plus.libs.block.state.properties.MagicInfuserPart;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.MagicInfuserMeltingRecipe;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.MagicInfusionRecipe;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MagicInfuserBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible {
    public NonNullList<ItemStack> items;
    public static final int INPUT_FUEL_SLOT_INDEX = 0;
    public static final int INPUT_SLOT_INDEX = 1;
    public static final int INPUT_CATALYST_SLOT_INDEX = 2;
    public static final int OUTPUT_INFUSION_SLOT_INDEX = 3;
    @Getter
    @Setter
    private Optional<ResourceLocation> leftBarId;
    @Getter
    private static final double BaseSpeedModifier = 1.0;
    @Setter
    @Getter
    private double speedModifier;
    @Getter
    @Setter
    private int boostDuration = 0;
    @Getter
    @Setter
    private Optional<ResourceLocation> rightBarId;
    @Getter
    @Setter
    private float magicMaterialAmount;
    @Getter
    @Setter
    private float maxMagicMaterialAmount;
    @Getter
    @Setter
    private float additionalMagicMaterialAmount;
    @Getter
    @Setter
    private int itemMaterialAmount;
    @Setter
    @Getter
    private int meltingProgress;
    @Setter
    @Getter
    private int infusionProgress;
    @Setter
    @Getter
    private int fuelTime;
    @Setter
    @Getter
    private int maxFuelTime;
    @Getter
    @Setter
    private int infusionTime;
    @Getter
    @Setter
    private int maxInfusionTime;
    private float lastMoltenAmount;
    private ItemStack lastInfusionStack;
    private ItemStack lastInputStack;
    private ItemStack lastFuelStack;
    private ItemStack lastCatalystStack;
    public boolean needUpdate;
    @Getter
    private List possibleInfusionRecipes;
    @Getter
    private int selectedRecipeIndex;
    private int lastSelectedRecipeIndex;
    private MagicInfuserMeltingRecipe lastMeltingRecipe;
    private MagicInfusionRecipe lastInfusionRecipe;
    private int totalPossibleRecipes;
    private boolean canHopperInfusing;
    private LazyOptional<? extends IItemHandler>[] handlers;

    public MagicInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ReiBlockEntities.MAGICAL_INFUSER_ENTITY.get(), pos, state);
        this.items = NonNullList.withSize(4, ItemStack.EMPTY);
        this.leftBarId = Optional.of(MagicInfusionRecipe.MAGICULES);
        this.rightBarId = Optional.of(MagicInfusionRecipe.INFUSION);
        this.magicMaterialAmount = 0;
        this.maxMagicMaterialAmount = 35;
        this.additionalMagicMaterialAmount = 0;
        this.itemMaterialAmount = 0;
        this.meltingProgress = 0;
        this.speedModifier = BaseSpeedModifier;
        this.infusionProgress = 0;
        this.fuelTime = 0;
        this.maxFuelTime = 0;
        this.infusionTime = 0;
        this.maxInfusionTime = 0;
        this.lastInfusionStack = ItemStack.EMPTY;
        this.lastInputStack = ItemStack.EMPTY;
        this.lastFuelStack = ItemStack.EMPTY;
        this.lastCatalystStack = ItemStack.EMPTY;
        this.needUpdate = false;
        this.possibleInfusionRecipes = new ArrayList();
        this.selectedRecipeIndex = 0;
        this.lastSelectedRecipeIndex = -1;
        this.totalPossibleRecipes = 0;
        this.canHopperInfusing = false;
        this.handlers = SidedInvWrapper.create(this, new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH});
    }

    protected Component getDefaultName() {
        return Component.translatable("reincarnation_plus.magic_infuser.label");
    }

    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MagicInfuserMenu(id, inventory, this);
    }

    public void performInfusion() {
        if (this.level instanceof ServerLevel serverLevel) {
            if (!getItem(2).isEmpty()){
                serverLevel.getRecipeManager().
                getRecipeFor(ReiRecipeTypes.MAGIC_INFUSION.get(), this, serverLevel).
                ifPresentOrElse(
                    magicInfusionRecipe -> {
                        if (infusionTime == 0){
                            setInfusionTime(magicInfusionRecipe.getCookingTime());
                            setMaxInfusionTime(magicInfusionRecipe.getCookingTime());
                        }

                        if (!checkInfusion()) {
                            resetInfusionProgress();
                            return;
                        }

                        if (infusionProgress >= 99) {
                            magicInfusionRecipe.assemble(this);
                            resetInfusionProgress();
                        } else {

                            infusionProgress = 100 * (this.maxInfusionTime - this.infusionTime) / this.maxInfusionTime;
                        }


                        needUpdate = true;
                    },
                    this::resetInfusionProgress
                );
            }

        }
    }

    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return this.createMenu(pContainerId, pInventory, pInventory.player);
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
            if (facing.equals(Direction.DOWN)) {
                return this.handlers[0].cast();
            } else {
                Direction backSide = ((Direction)this.getBlockState().getValue(MagicInfuserBlock.FACING)).getOpposite();
                return backSide.equals(facing) ? this.handlers[2].cast() : this.handlers[1].cast();
            }
        } else {
            return super.getCapability(capability, facing);
        }
    }

    public void invalidateCaps() {
        super.invalidateCaps();

        for(LazyOptional<? extends IItemHandler> handler : this.handlers) {
            handler.invalidate();
        }

    }

    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = SidedInvWrapper.create(this, new Direction[]{Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST});
    }

    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, this.items);
        this.leftBarId.ifPresent((location) -> nbt.putString("magic_infuser.molten.itemId", location.toString()));
        this.rightBarId.ifPresent((location) -> nbt.putString("magic_infuser.infusionId", location.toString()));
        nbt.putFloat("magic_infuser.magicules", this.magicMaterialAmount);
        nbt.putFloat("magic_infuser.maxMagicules", this.maxMagicMaterialAmount);
        nbt.putFloat("magic_infuser.addMolten", this.additionalMagicMaterialAmount);
        nbt.putDouble("magic_infuser.speedModifier", this.speedModifier);
        nbt.putInt("magic_infuser.meltingProgess", this.meltingProgress);
        nbt.putInt("magic_infuser.fuel", this.fuelTime);
        nbt.putInt("magic_infuser.maxFuel", this.maxFuelTime);
        nbt.putInt("magic_infuser.infusion", this.infusionTime);
        nbt.putInt("magic_infuser.maxInfusion", this.maxInfusionTime);
        nbt.putInt("magic_infuser.possibleRecipes", this.possibleInfusionRecipes.size());
        nbt.putInt("magic_infuser.currentRecipe", this.selectedRecipeIndex);
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.items);
        this.leftBarId = nbt.contains("magic_infuser.molten.itemId") ? Optional.ofNullable(ResourceLocation.tryParse(nbt.getString("magic_infuser.molten.itemId"))) : Optional.of(MagicInfusionRecipe.MAGICULES);
        this.rightBarId = nbt.contains("magic_infuser.infusionId") ? Optional.ofNullable(ResourceLocation.tryParse(nbt.getString("magic_infuser.infusionId"))) : Optional.of(MagicInfusionRecipe.INFUSION);
        this.magicMaterialAmount = nbt.getFloat("magic_infuser.magicules");
        this.maxMagicMaterialAmount = nbt.getFloat("magic_infuser.maxMagicules");
        this.additionalMagicMaterialAmount = nbt.getFloat("magic_infuser.addMolten");
        this.speedModifier = nbt.getDouble("magic_infuser.speedModifier");
        this.meltingProgress = nbt.getInt("magic_infuser.meltingProgess");
        this.fuelTime = nbt.getInt("magic_infuser.fuel");
        this.maxFuelTime = nbt.getInt("magic_infuser.maxFuel");
        this.infusionTime = nbt.getInt("magic_infuser.infusion");
        this.maxInfusionTime = nbt.getInt("magic_infuser.maxInfusion");
        this.totalPossibleRecipes = nbt.getInt("magic_infuser.possibleRecipes");
        this.selectedRecipeIndex = nbt.getInt("magic_infuser.currentRecipe");
    }

    public int[] getSlotsForFace(Direction pSide) {
        if (((MagicInfuserPart)this.getBlockState().getValue(MagicInfuserBlock.PART)).equals(MagicInfuserPart.TOP)) {
            return new int[0];
        } else {
            Direction backSide = ((Direction)this.getBlockState().getValue(MagicInfuserBlock.FACING)).getOpposite();
            if (pSide.equals(backSide)) {
                return new int[]{0};
            } else {
                return pSide.equals(Direction.DOWN) ? new int[]{2} : new int[]{1};
            }
        }
    }

    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        boolean var10000;
        switch (pIndex) {
            case 0 -> var10000 = AbstractFurnaceBlockEntity.isFuel(pItemStack);
            case 1, 2 -> var10000 = true;
            default -> var10000 = false;
        }

        return var10000;
    }

    public void boost(double baseSpeedModifier){
        if (speedModifier == 0){
            speedModifier += baseSpeedModifier;
            needUpdate = true;
        }
    }

    public void resetSpeed(){
        speedModifier = 0;
        needUpdate = true;
    }

    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        if (pDirection == Direction.DOWN && pIndex == 0) {
            return pStack.is(Items.WATER_BUCKET) || pStack.is(Items.BUCKET);
        } else {
            if (pIndex == 2 && this.canHopperInfusing) {
                this.performInfusion();
                this.canHopperInfusing = false;
            }

            return pIndex != 2;
        }
    }

    public int getContainerSize() {
        return this.items.size();
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getItem(int pIndex) {
        return this.items.get(pIndex);
    }

    public ItemStack removeItem(int pIndex, int pCount) {
        this.needUpdate = true;
        return ContainerHelper.removeItem(this.items, pIndex, pCount);
    }

    public ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(this.items, pIndex);
    }

    public void setItem(int pIndex, ItemStack pStack) {
        this.items.set(pIndex, pStack);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }

        this.needUpdate = true;
    }

    public boolean stillValid(Player pPlayer) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return pPlayer.distanceToSqr((double)this.worldPosition.getX() + (double)0.5F, (double)this.worldPosition.getY() + (double)0.5F, (double)this.worldPosition.getZ() + (double)0.5F) <= (double)64.0F;
        }
    }

    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        boolean var10000;
        switch (pIndex) {
            case 0 -> var10000 = AbstractFurnaceBlockEntity.isFuel(pStack);
            case 1, 2 -> var10000 = true;
            default -> var10000 = false;
        }

        return var10000;
    }

    public void clearContent() {
        this.items.clear();
        this.needUpdate = true;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(this.items.size());

        for(int i = 0; i < this.items.size(); ++i) {
            if (i != 2) {
                inventory.setItem(i, (ItemStack)this.items.get(i));
            }
        }

        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    private boolean checkInfusingCache() {
        if (this.lastMoltenAmount != this.magicMaterialAmount) {
            this.lastMoltenAmount = this.magicMaterialAmount;
            return true;
        }  else if (!this.lastCatalystStack.equals(this.items.get(INPUT_CATALYST_SLOT_INDEX), true)) {
            this.lastCatalystStack = this.items.get(INPUT_CATALYST_SLOT_INDEX).copy();
            return true;
        } else return this.getInfusionTime() > 0;
    }

    private boolean checkMeltingCache() {
        if (!this.lastInputStack.equals(this.items.get(INPUT_SLOT_INDEX), true)) {
            this.lastInputStack = this.items.get(INPUT_SLOT_INDEX).copy();
            return true;
        } else if (!this.lastFuelStack.equals(this.items.get(INPUT_FUEL_SLOT_INDEX), true)) {
            this.lastFuelStack = this.items.get(INPUT_FUEL_SLOT_INDEX).copy();
            return true;
        } else if (magicMaterialAmount < (maxMagicMaterialAmount + additionalMagicMaterialAmount)) {
            return true;
        } else {
            return false;
        }
    }

    public void reduceInfusionTimeByPercentage(float percentage){
        int reduction = Math.round(maxInfusionTime * (percentage / 100));
        setInfusionTime(Math.max(1, infusionTime - reduction));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagicInfuserBlockEntity pEntity) {
        if (!level.isClientSide()) {
            if (pEntity.boostDuration > 0) {
                pEntity.boostDuration--;
                if (pEntity.speedModifier > 0 && pEntity.getInfusionProgress() < 95){
                    pEntity.reduceInfusionTimeByPercentage((float) pEntity.speedModifier);
                }

                // Логіка пришвидшеної роботи під час boostDuration
                if (pEntity.boostDuration == 0) {
                    pEntity.resetSpeed(); // Скидання швидкості до стандартної
                }
            }

            BlockEntity belowBlock = level.getBlockEntity(pos.below());
            if (belowBlock instanceof MagicAmplifierBlockEntity amplifierBlock) {
                pEntity.upgrade(amplifierBlock.getMaxMagicMaterialAmount());
            } else {
                pEntity.upgrade(0);
            }
            if (pEntity.getAdditionalMagicMaterialAmount() == 0){
                pEntity.upgrade(0);
            }
            boolean infusionNeedsUpdate = pEntity.checkInfusingCache();
            boolean meltingNeedsUpdate = pEntity.checkMeltingCache();

            if (infusionNeedsUpdate || pEntity.infusionProgress > 0) {
                pEntity.checkInfusionRecipe();
                pEntity.updateInfusionTime();
                pEntity.rightBarId = Optional.of(MagicInfusionRecipe.INFUSION);
            }

            if (meltingNeedsUpdate || pEntity.meltingProgress > 0) {
                pEntity.checkMeltingRecipe();
            }

            if (pEntity.magicMaterialAmount > 0) {
                pEntity.leftBarId = Optional.of(MagicInfusionRecipe.MAGICULES);
            }

            pEntity.updateFuelTime();


            if (pEntity.needUpdate) {
                pEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 2);
                pEntity.needUpdate = false;
            }
        } else if (state.getValue(MagicInfuserBlock.LIT) || state.getValue(MagicInfuserBlock.INFUSION)) {
            RandomSource random = level.random;
            if (random.nextFloat() < 0.11F) {
                for(int i = 0; i < random.nextInt(2) + 2; ++i) {
                    CampfireBlock.makeParticles(level, pos.above(2), false, false);
                }
            }
        }

    }

    private void checkInfusionRecipe() {
        assert this.level != null;
        performInfusion();
    }

    public void performMelting(){
        this.level.getRecipeManager()
            .getRecipeFor(ReiRecipeTypes.MAGIC_INFUSER_MELTING.get(), this, this.level)
            .ifPresentOrElse(
            magicMeltingRecipe -> {
                if (!checkFuel()) {
                    resetMeltingProgress();
                    return;
                }

                if (lastMeltingRecipe == null || !lastMeltingRecipe.getId().equals(magicMeltingRecipe.getId())) {
                    lastMeltingRecipe = (MagicInfuserMeltingRecipe) magicMeltingRecipe;
                    resetMeltingProgress();
                }

                if (meltingProgress >= 100) {
                    magicMeltingRecipe.assemble(this);
                    resetMeltingProgress();
                } else {
                    meltingProgress++;
                }

                needUpdate = true;
            },
            this::resetMeltingProgress
        );
    }

    private void checkMeltingRecipe() {
        if (this.items.get(INPUT_SLOT_INDEX).isEmpty()) {
            resetMeltingProgress();
            return;
        }

        assert this.level != null;
        performMelting();
    }

    private void updateFuelTime() {
        if (this.fuelTime > 0) {
            --this.fuelTime;
            this.needUpdate = true;
            if (!(Boolean)this.getBlockState().getValue(MagicInfuserBlock.LIT)) {
                BlockState newState = (BlockState)this.getBlockState().setValue(MagicInfuserBlock.LIT, true);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        } else {
            if ((Boolean)this.getBlockState().getValue(MagicInfuserBlock.LIT)) {
                BlockState newState = (BlockState)this.getBlockState().setValue(MagicInfuserBlock.LIT, false);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        }
    }

    private void updateInfusionTime() {
        if (this.infusionTime > 0) {
            --this.infusionTime;
            this.needUpdate = true;
            if (!(Boolean)this.getBlockState().getValue(MagicInfuserBlock.INFUSION)) {
                BlockState newState = (BlockState)this.getBlockState().setValue(MagicInfuserBlock.INFUSION, true);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        } else {
            if ((Boolean)this.getBlockState().getValue(MagicInfuserBlock.INFUSION)) {
                BlockState newState = (BlockState)this.getBlockState().setValue(MagicInfuserBlock.INFUSION, false);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        }
    }

    private void updatePossibleInfussionRecipes() {
        Optional<MagicInfusionRecipe> selectedRecipe = this.possibleInfusionRecipes.size() > this.selectedRecipeIndex && this.selectedRecipeIndex >= 0 ? Optional.of((MagicInfusionRecipe)this.possibleInfusionRecipes.get(this.selectedRecipeIndex)) : Optional.empty();
        assert this.level != null;
        this.possibleInfusionRecipes = this.level.getRecipeManager().getRecipesFor(
                        ReiRecipeTypes.MAGIC_INFUSION.get(), this, this.level)
                .parallelStream()
                .sorted(/* додайте компаратор, якщо необхідно */)
                .toList();
        this.totalPossibleRecipes = this.possibleInfusionRecipes.size();
        selectedRecipe.ifPresentOrElse((magicInfusionRecipe) -> {
            if (this.possibleInfusionRecipes.contains(magicInfusionRecipe)) {
                this.selectedRecipeIndex = this.possibleInfusionRecipes.indexOf(magicInfusionRecipe);
            } else {
                this.selectedRecipeIndex = 0;
            }

        }, () -> this.selectedRecipeIndex = 0);
        if (this.possibleInfusionRecipes.size() > 0 && this.getInfusionProgress() == 0) {
            this.items.set(OUTPUT_INFUSION_SLOT_INDEX, ((MagicInfusionRecipe)this.possibleInfusionRecipes.get(this.selectedRecipeIndex)).getResultItem());
        }

        this.needUpdate = true;
    }

    public void infusuinNextRecipe() {
        if (this.selectedRecipeIndex + 1 >= this.possibleInfusionRecipes.size()) {
            this.selectedRecipeIndex = 0;
        } else {
            ++this.selectedRecipeIndex;
        }

        this.needUpdate = true;
    }

    public void infusionPrevRecipe() {
        if (this.selectedRecipeIndex - 1 < 0) {
            this.selectedRecipeIndex = this.possibleInfusionRecipes.size() - 1;
        } else {
            --this.selectedRecipeIndex;
        }

        this.needUpdate = true;
    }

    private void resetMeltingProgress() {
        if (this.meltingProgress > 0) {
            this.meltingProgress = 0;
            this.lastInputStack = ItemStack.EMPTY;
            this.needUpdate = true;
        }
    }

    public void resetInfusionProgress(){
        if (this.infusionProgress > 0) {
            this.infusionProgress = 0;
            this.maxInfusionTime = 0;
            this.infusionTime = 0;
            this.lastCatalystStack = ItemStack.EMPTY;
            this.needUpdate = true;
        }
    }

    public boolean checkInfusion(){
        if (this.infusionTime > 0){
            return true;
        } else {
            ItemStack infuseSlotStack = this.items.get(INPUT_CATALYST_SLOT_INDEX).copy();
            if (infuseSlotStack.isEmpty()) {
                return false;
            } else {
                this.needUpdate = true;
                return true;
            }
        }
    }

    private boolean checkFuel() {
        if (this.fuelTime > 0) {
            return true;
        } else {
            ItemStack fuelSlotStack = ((ItemStack)this.items.get(0)).copy();
            if (fuelSlotStack.isEmpty()) {
                return false;
            } else {
                int fuelTime = ForgeHooks.getBurnTime(fuelSlotStack, RecipeType.SMELTING);
                if (fuelTime < 0) {
                    return false;
                } else {
                    if (fuelSlotStack.getItem() instanceof BucketItem) {
                        fuelSlotStack = Items.BUCKET.getDefaultInstance();
                    } else {
                        fuelSlotStack.shrink(1);
                    }

                    this.items.set(0, fuelSlotStack);
                    this.fuelTime = fuelTime;
                    this.maxFuelTime = fuelTime;
                    this.needUpdate = true;
                    return true;
                }
            }
        }
    }

    public void fillStackedContents(StackedContents pHelper) {
    }

    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }

    public void addMoltenMaterialAmount(float moltenAmount) {
        this.magicMaterialAmount += moltenAmount;
        this.needUpdate = true;
    }
    public void upgrade(float additionalMagicMaterialAmount) {
        this.additionalMagicMaterialAmount = additionalMagicMaterialAmount;
        this.needUpdate = true;
    }

    public void removeMoltenMaterialAmount(float moltenAmount) {
        this.magicMaterialAmount -= moltenAmount;
        this.needUpdate = true;
    }

    public boolean hasPrevInfusionRecipe() {
        return this.totalPossibleRecipes > this.selectedRecipeIndex - 1 && this.selectedRecipeIndex - 1 >= 0;
    }

    public boolean hasNextInfusionRecipe() {
        return this.totalPossibleRecipes > this.selectedRecipeIndex + 1 && this.selectedRecipeIndex + 1 >= 0;
    }

    public boolean hasInfuse(){
        return this.infusionTime > 0;
    }

}
