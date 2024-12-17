package com.github.skillfi.reincarnation_plus.core.block.entity;

import com.github.skillfi.reincarnation_plus.core.block.MagiculaInfuserBlock;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MagiculaInfuserBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible, IAnimatable {
    public NonNullList<ItemStack> items;
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final int INPUT_FUEL_SLOT_INDEX = 0;
    public static final int INPUT_SLOT_INDEX = 1;
    public static final int INPUT_CATALYST_SLOT_INDEX = 2;
    public static final int OUTPUT_INFUSION_SLOT_INDEX = 3;
    @Getter @Setter private int state;
    @Getter @Setter private Optional<ResourceLocation> leftBarId;
    @Getter private static final double BaseSpeedModifier = 0.0;
    @Setter @Getter private double speedModifier;
    @Getter @Setter private int boostDuration;
    @Getter @Setter private Optional<ResourceLocation> rightBarId;
    @Getter @Setter public int miscAnimationTicks = 0;
    @Getter @Setter private float magicMaterialAmount;
    @Getter @Setter private float maxMagicMaterialAmount;
    @Getter @Setter private float additionalMagicMaterialAmount;
    @Setter @Getter private int meltingProgress;
    @Setter @Getter private int infusionProgress;
    @Setter @Getter private int fuelTime;
    @Setter @Getter private int maxFuelTime;
    @Getter @Setter private int infusionTime;
    @Getter @Setter private int maxInfusionTime;
    private float lastMagiculesAmount;
    private ItemStack lastInputStack;
    private ItemStack lastFuelStack;
    private ItemStack lastCatalystStack;
    public boolean needUpdate;
    @Getter private List possibleInfusionRecipes;
    @Getter private int selectedRecipeIndex;
    private MagicInfuserMeltingRecipe lastMeltingRecipe;
    private MagicInfusionRecipe lastInfusionRecipe;
    private int totalPossibleRecipes;
    private boolean canHopperInfusing;
    private LazyOptional<? extends IItemHandler>[] handlers;

    public MagiculaInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ReiBlockEntities.MAGICAL_INFUSER_ENTITY.get(), pos, state);
        this.items = NonNullList.withSize(4, ItemStack.EMPTY);
        this.leftBarId = Optional.of(MagicInfusionRecipe.MAGICULES);
        this.rightBarId = Optional.of(MagicInfusionRecipe.INFUSION);
        this.magicMaterialAmount = 0;
        this.maxMagicMaterialAmount = 35;
        this.additionalMagicMaterialAmount = 0;
        this.meltingProgress = 0;
        this.boostDuration = 0;
        this.speedModifier = BaseSpeedModifier;
        this.infusionProgress = 0;
        this.state = 0;
        this.fuelTime = 0;
        this.maxFuelTime = 0;
        this.infusionTime = 0;
        this.maxInfusionTime = 0;
        this.lastInputStack = ItemStack.EMPTY;
        this.lastFuelStack = ItemStack.EMPTY;
        this.lastCatalystStack = ItemStack.EMPTY;
        this.needUpdate = false;
        this.possibleInfusionRecipes = new ArrayList();
        this.selectedRecipeIndex = 0;
        this.totalPossibleRecipes = 0;
        this.canHopperInfusing = false;
        this.handlers = SidedInvWrapper.create(this, new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH});
    }

    protected Component getDefaultName() {
        return Component.translatable("reincarnation_plus.magicula_infuser.label");
    }

    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MagicInfuserMenu(id, inventory, this);
    }

    public boolean doubleChanse(){
        if (this.getLevel().random.nextDouble() == 0.05){
            return true;
        }
        return false;
    }

    public void performInfusion() {
        if (this.level instanceof ServerLevel serverLevel) {
            if (!getItem(2).isEmpty()){
                serverLevel.getRecipeManager().
                getRecipeFor(ReiRecipeTypes.MAGIC_INFUSION.get(), this, serverLevel).
                ifPresentOrElse(
                    magicInfusionRecipe -> {
                        rightBarId = Optional.of(MagicInfusionRecipe.INFUSION);
                        if (infusionTime == 0){
                            setInfusionTime(magicInfusionRecipe.getCookingTime());
                            setMaxInfusionTime(magicInfusionRecipe.getCookingTime());
                            this.getBlockState().
                                    setValue(MagiculaInfuserBlock.INFUSION, Boolean.TRUE).
                                    setValue(MagiculaInfuserBlock.LIT, Boolean.FALSE);

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
                Direction backSide = ((Direction)this.getBlockState().getValue(MagiculaInfuserBlock.FACING)).getOpposite();
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
        this.leftBarId.ifPresent((location) -> nbt.putString("magicula_infuser.molten.itemId", location.toString()));
        this.rightBarId.ifPresent((location) -> nbt.putString("magicula_infuser.infusionId", location.toString()));
        nbt.putFloat("magicula_infuser.magicules", this.magicMaterialAmount);
        nbt.putFloat("magicula_infuser.maxMagicules", this.maxMagicMaterialAmount);
        nbt.putFloat("magicula_infuser.addMolten", this.additionalMagicMaterialAmount);
        nbt.putDouble("magicula_infuser.speedModifier", this.speedModifier);
        nbt.putInt("magicula_infuser.meltingProgess", this.meltingProgress);
        nbt.putInt("magicula_infuser.fuel", this.fuelTime);
        nbt.putInt("magicula_infuser.state", this.state);
        nbt.putInt("magicula_infuser.maxFuel", this.maxFuelTime);
        nbt.putInt("magicula_infuser.infusion", this.infusionTime);
        nbt.putInt("magicula_infuser.maxInfusion", this.maxInfusionTime);
        nbt.putInt("magicula_infuser.possibleRecipes", this.possibleInfusionRecipes.size());
        nbt.putInt("magicula_infuser.currentRecipe", this.selectedRecipeIndex);
        nbt.putInt("magicula_infuser.boostduration", this.boostDuration);
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.items);
        this.leftBarId = nbt.contains("magicula_infuser.molten.itemId") ? Optional.ofNullable(ResourceLocation.tryParse(nbt.getString("magicula_infuser.molten.itemId"))) : Optional.of(MagicInfusionRecipe.MAGICULES);
        this.rightBarId = nbt.contains("magicula_infuser.infusionId") ? Optional.ofNullable(ResourceLocation.tryParse(nbt.getString("magicula_infuser.infusionId"))) : Optional.of(MagicInfusionRecipe.INFUSION);
        this.magicMaterialAmount = nbt.getFloat("magicula_infuser.magicules");
        this.maxMagicMaterialAmount = nbt.getFloat("magicula_infuser.maxMagicules");
        this.additionalMagicMaterialAmount = nbt.getFloat("magicula_infuser.addMolten");
        this.speedModifier = nbt.getDouble("magicula_infuser.speedModifier");
        this.meltingProgress = nbt.getInt("magicula_infuser.meltingProgess");
        this.fuelTime = nbt.getInt("magicula_infuser.fuel");
        this.state = nbt.getInt("magicula_infuser.state");
        this.maxFuelTime = nbt.getInt("magicula_infuser.maxFuel");
        this.infusionTime = nbt.getInt("magicula_infuser.infusion");
        this.maxInfusionTime = nbt.getInt("magicula_infuser.maxInfusion");
        this.totalPossibleRecipes = nbt.getInt("magicula_infuser.possibleRecipes");
        this.selectedRecipeIndex = nbt.getInt("magicula_infuser.currentRecipe");
        this.boostDuration = nbt.getInt("magicula_infuser.boostduration");
    }

    public int[] getSlotsForFace(Direction pSide) {
        if (((MagicInfuserPart)this.getBlockState().getValue(MagiculaInfuserBlock.PART)).equals(MagicInfuserPart.TOP)) {
            return new int[0];
        } else {
            Direction backSide = ((Direction)this.getBlockState().getValue(MagiculaInfuserBlock.FACING)).getOpposite();
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

    public boolean isInfuse(ItemStack stack) {
        assert this.level != null;

        // Перебираємо всі рецепти і перевіряємо, чи якийсь з них приймає ItemStack
        return this.level.getRecipeManager()
                .getAllRecipesFor(ReiRecipeTypes.MAGIC_INFUSION.get())
                .stream()
                .anyMatch(magicInfusionRecipe ->
                        magicInfusionRecipe.getInput().test(stack)
                );
    }



    public boolean isMelting(ItemStack stack) {
        assert this.level != null;

        // Перебираємо всі рецепти і перевіряємо, чи якийсь з інгредієнтів підходить під ItemStack
        return this.level.getRecipeManager()
                .getAllRecipesFor(ReiRecipeTypes.MAGIC_INFUSER_MELTING.get())
                .stream()
                .anyMatch(magicMeltingRecipe ->
                        magicMeltingRecipe.getInput().test(stack)
                );
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

    public ItemStack getRenderStack(){
        ItemStack stack;

        if (!getItem(2).isEmpty()){
            stack = getItem(2);
        } else if (!getItem(3).isEmpty()){
            stack = getItem(3);
        } else {
            stack = ItemStack.EMPTY;
        }

        return stack;
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            setItem(i, itemStackHandler.getStackInSlot(i));
        }
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
        if (this.lastMagiculesAmount != this.magicMaterialAmount) {
            this.lastMagiculesAmount = this.magicMaterialAmount;
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
        int reduction = Math.round(infusionTime * (percentage / 100));
        setInfusionTime(Math.max(1, infusionTime - reduction));
    }

    public void reChangeMagicMaterialAmount(){
        if (magicMaterialAmount > (getMaxMagicMaterialAmount() + getAdditionalMagicMaterialAmount())){
            setMagicMaterialAmount(getMaxMagicMaterialAmount() + getAdditionalMagicMaterialAmount());
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagiculaInfuserBlockEntity pEntity) {
        if (!level.isClientSide()) {
            pEntity.miscAnimationHandler();
            pEntity.reChangeMagicMaterialAmount();

            // Обробка boostDuration
            if (pEntity.boostDuration > 0) {
                pEntity.boostDuration--;
                if (pEntity.speedModifier > 0 && pEntity.getInfusionTime() > 1){
                    pEntity.reduceInfusionTimeByPercentage((float) 0.15);
                }
                if (pEntity.boostDuration == 0) {
                    pEntity.resetSpeed();
                }
            }

            // Перевірка блоку під інфузором
            BlockEntity belowBlock = level.getBlockEntity(pos.below());
            pEntity.upgrade(belowBlock instanceof MagicAmplifierBlockEntity amplifierBlock
                    ? amplifierBlock.getMaxMagicMaterialAmount()
                    : 0);

            // Оновлення стану інфузії та плавлення
            boolean infusionNeedsUpdate = pEntity.checkInfusingCache();
            boolean meltingNeedsUpdate = pEntity.checkMeltingCache();

            if (infusionNeedsUpdate || pEntity.infusionProgress > 0) {
                pEntity.checkInfusionRecipe();
            }

            if (meltingNeedsUpdate || pEntity.meltingProgress > 0) {
                pEntity.checkMeltingRecipe();
            }

            // Оновлення лівого індикатора
            if (pEntity.magicMaterialAmount > 0) {
                pEntity.leftBarId = Optional.of(MagicInfusionRecipe.MAGICULES);
            }

            // Перевірка предметів для інфузії
            if (pEntity.getItem(2).isEmpty() || pEntity.getItem(3).isEmpty()) {
                state = state.setValue(MagiculaInfuserBlock.INFUSION, false);
                pEntity.needUpdate = true;
            }
            pEntity.updateInfusionTime();
            pEntity.updateFuelTime();

            // Оновлення блоку, якщо потрібне
            if (pEntity.needUpdate) {
                pEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 2);
                pEntity.needUpdate = false;
            }

        } else if (state.getValue(MagiculaInfuserBlock.LIT) || state.getValue(MagiculaInfuserBlock.INFUSION)) {
            RandomSource random = level.random;
            if (random.nextFloat() < 0.11F) {
                for (int i = 0; i < random.nextInt(2) + 2; ++i) {
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
                    if (!this.getBlockState().getValue(MagiculaInfuserBlock.INFUSION)){
                        this.getBlockState().setValue(MagiculaInfuserBlock.LIT, Boolean.TRUE);
                    }
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
            if (!(Boolean)this.getBlockState().getValue(MagiculaInfuserBlock.LIT)) {
                BlockState newState = (BlockState)this.getBlockState().setValue(MagiculaInfuserBlock.LIT, true);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        } else {
            if ((Boolean)this.getBlockState().getValue(MagiculaInfuserBlock.LIT)) {
                BlockState newState = (BlockState)this.getBlockState().setValue(MagiculaInfuserBlock.LIT, false);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                setState(0);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        }
    }

    private void updateInfusionTime() {
        if (this.infusionTime > 0) {
            --this.infusionTime;
            this.needUpdate = true;
            if (!(Boolean)this.getBlockState().getValue(MagiculaInfuserBlock.INFUSION)) {
                BlockState newState = (BlockState)this.getBlockState().setValue(MagiculaInfuserBlock.INFUSION, true);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                setState(2);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        } else {
            if ((Boolean)this.getBlockState().getValue(MagiculaInfuserBlock.INFUSION)) {
                BlockState newState = (BlockState)this.getBlockState().setValue(MagiculaInfuserBlock.INFUSION, false);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                setState(0);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        }
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
            this.getBlockState().setValue(MagiculaInfuserBlock.LIT, Boolean.FALSE);
            this.setBoostDuration(0);
            this.needUpdate = true;
        }
    }

    public void resetInfusionProgress(){
        if (this.infusionProgress > 0) {
            this.infusionProgress = 0;
            this.maxInfusionTime = 0;
            this.infusionTime = 0;
            this.getBlockState().setValue(MagiculaInfuserBlock.INFUSION, Boolean.FALSE);
            this.lastCatalystStack = ItemStack.EMPTY;
            this.setBoostDuration(0);
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

    // region Boolen
    public boolean hasPrevInfusionRecipe() {
        return this.totalPossibleRecipes > this.selectedRecipeIndex - 1 && this.selectedRecipeIndex - 1 >= 0;
    }

    public boolean hasNextInfusionRecipe() {
        return this.totalPossibleRecipes > this.selectedRecipeIndex + 1 && this.selectedRecipeIndex + 1 >= 0;
    }

    public boolean hasInfuse(){
        return this.infusionTime > 0;
    }
    // endregion

    // region Geckolib
    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!((BlockEntity)event.getAnimatable()).getBlockState().getValue(MagiculaInfuserBlock.LIT) && !((BlockEntity)event.getAnimatable()).getBlockState().getValue(MagiculaInfuserBlock.INFUSION)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));
        }
        if (((BlockEntity)event.getAnimatable()).getBlockState().getValue(MagiculaInfuserBlock.LIT)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("lit", ILoopType.EDefaultLoopTypes.LOOP));
        }
        if (((BlockEntity)event.getAnimatable()).getBlockState().getValue(MagiculaInfuserBlock.INFUSION)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("lit", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    protected void miscAnimationHandler() {
        if (this.getState() != 0) {
            ++this.miscAnimationTicks;
            if (this.miscAnimationTicks >= this.getAnimationTick(this.getState())) {
                this.setState(0);
                this.miscAnimationTicks = 0;
            }
        }
    }

    private int getAnimationTick(int miscAnimation) {
        return miscAnimation == 1 ? 25 : 7;
    }
    // endregion
}
