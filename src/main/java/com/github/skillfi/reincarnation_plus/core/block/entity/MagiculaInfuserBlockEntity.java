package com.github.skillfi.reincarnation_plus.core.block.entity;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.MagiculaInfuserBlock;
import com.github.skillfi.reincarnation_plus.core.block.state.properties.MagicInfuserPart;
import com.github.skillfi.reincarnation_plus.core.capability.block.IMagiculaInfuserCapability;
import com.github.skillfi.reincarnation_plus.core.capability.block.MagiculaInfuserCapability;
import com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.MagicInfusionRecipe;
import com.github.skillfi.reincarnation_plus.core.menu.MagicInfuserMenu;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
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
import net.minecraft.world.level.block.entity.ChestBlockEntity;
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

import static com.github.skillfi.reincarnation_plus.core.data.recipe.infuser.MagicInfusionRecipe.INFUSION;

@Slf4j
public class MagiculaInfuserBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible, IAnimatable {
    public static final int INPUT_FUEL_SLOT_INDEX = 0;
    public static final int INPUT_SLOT_INDEX = 1;
    public static final int INPUT_CATALYST_SLOT_INDEX = 2;
    public static final int OUTPUT_SLOT_INDEX = 3;
    @Getter
    private static final double BaseSpeedModifier = 0.0;
    public static String NBT_KEY = "magicula_infuser";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final MagiculaInfuserCapability capability = new MagiculaInfuserCapability();
    private final LazyOptional<IMagiculaInfuserCapability> capabilityLazyOptional = LazyOptional.of(() -> capability);
    @Getter
    private final List possibleInfusionRecipes;
    public NonNullList<ItemStack> items;
    @Getter
    @Setter
    public int miscAnimationTicks = 0;
    public boolean needUpdate;
    @Getter
    private int selectedRecipeIndex;
    private int totalPossibleRecipes;
    private LazyOptional<? extends IItemHandler>[] handlers;

    public MagiculaInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ReiBlockEntities.MAGICAL_INFUSER_ENTITY.get(), pos, state);
        this.items = NonNullList.withSize(4, ItemStack.EMPTY);
        this.capability.setLeftBarId(Optional.of(MagicInfusionRecipe.EMPTY));
        this.capability.setRightBarId(Optional.of(MagicInfusionRecipe.EMPTY));
        this.capability.setInfusionBarId(Optional.of(MagicInfusionRecipe.EMPTY));
        this.capability.setMagicMaterialAmount(0);
        this.capability.setMaxMagicMaterialAmount(35000);
        this.capability.setMoltenAmount(0);
        this.capability.setMaxMoltenAmount(35000);
        this.capability.setAdditionalMagicMaterialAmount(0);
        this.capability.setMeltingProgress(0);
        this.capability.setBoostDuration(0);
        this.capability.setSpeedModifier(BaseSpeedModifier);
        this.capability.setInfusionProgress(0);
        this.capability.setState(0);
        this.capability.setFuelTime(0);
        this.capability.setMaxFuelTime(0);
        this.capability.setInfusionTime(0);
        this.capability.setMaxInfusionTime(0);
        this.capability.setLastInputStack(ItemStack.EMPTY);
        this.capability.setLastFuelStack(ItemStack.EMPTY);
        this.capability.setLastCatalystStack(ItemStack.EMPTY);
        this.needUpdate = false;
        this.possibleInfusionRecipes = new ArrayList();
        this.selectedRecipeIndex = 0;
        this.totalPossibleRecipes = 0;
        this.capability.setCanHopperInfusing(false);
        this.capability.setAuto(false);
        this.handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagiculaInfuserBlockEntity pEntity) {
        if (!level.isClientSide()) {
            pEntity.miscAnimationHandler();
            MagiculaInfuserCapability cap = pEntity.capability;
            // Обробка boostDuration
            if (cap.getBoostDuration() > 0) {
                pEntity.decreesBoostDuration();
                if (cap.getSpeedModifier() > 0 && cap.getInfusionTime() > 1) {
                    pEntity.reduceInfusionTimeByPercentage((float) 0.15);
                }
                if (cap.getBoostDuration() == 0) {
                    pEntity.resetSpeed();
                }
            }

            // Оновлення стану інфузії та плавлення
            boolean infusionNeedsUpdate = pEntity.checkInfusingCache();
            boolean meltingNeedsUpdate = pEntity.checkMeltingCache();

            if (pEntity.isEvolve(pEntity.getItem(2)) && cap.getMoltenAmount() > 1) {
                pEntity.checkEvolvingRecipe();
            }

            if (infusionNeedsUpdate || cap.getInfusionProgress() > 0) {
                pEntity.checkInfusionRecipe();
            }

            if (meltingNeedsUpdate || cap.getMeltingProgress() > 0) {
                pEntity.checkMeltingRecipe();
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

    protected Component getDefaultName() {
        return Component.translatable("reincarnation_plus.magicula_infuser.label");
    }

    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MagicInfuserMenu(id, inventory, this);
    }

    public boolean doubleChanse() {
        return this.getLevel().random.nextDouble() == 0.05;
    }

    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return this.createMenu(pContainerId, pInventory, pInventory.player);
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
            if (facing.equals(Direction.DOWN)) {
                return this.handlers[0].cast();
            } else {
                Direction backSide = this.getBlockState().getValue(MagiculaInfuserBlock.FACING).getOpposite();
                return backSide.equals(facing) ? this.handlers[2].cast() : this.handlers[1].cast();
            }
        } else if (capability == ReiMod.MAGICULA_INFUSER_CAPABILITY) {
            return capabilityLazyOptional.cast();
        } else {
            return super.getCapability(capability, facing);
        }
    }

    public void invalidateCaps() {
        super.invalidateCaps();
        capabilityLazyOptional.invalidate();

        for (LazyOptional<? extends IItemHandler> handler : this.handlers) {
            handler.invalidate();
        }

    }

    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);
        capabilityLazyOptional.invalidate();
    }

    // region NBT
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, this.items);
        this.capability.getLeftBarId().ifPresent((location) -> nbt.putString(NBT_KEY + ".molten.leftBarId", location.toString()));
        this.capability.getRightBarId().ifPresent((location) -> nbt.putString(NBT_KEY + ".rightBarId", location.toString()));
        this.capability.getInfusionBarId().ifPresent((location) -> nbt.putString(NBT_KEY + ".InfusionBarId", location.toString()));
        nbt.putInt(NBT_KEY + ".magicules", capability.getMagicMaterialAmount());
        nbt.putInt(NBT_KEY + ".maxMagicules", capability.getMaxMagicMaterialAmount());
        nbt.putInt(NBT_KEY + ".addMolten", capability.getAdditionalMagicMaterialAmount());
        nbt.putDouble(NBT_KEY + ".speedModifier", capability.getSpeedModifier());
        nbt.putInt(NBT_KEY + ".meltingProgress", capability.getMeltingProgress());
        nbt.putInt(NBT_KEY + ".fuel", capability.getFuelTime());
        nbt.putInt(NBT_KEY + ".state", capability.getState());
        nbt.putInt(NBT_KEY + ".maxFuel", capability.getMaxFuelTime());
        nbt.putInt(NBT_KEY + ".infusion", capability.getInfusionTime());
        nbt.putInt(NBT_KEY + ".maxInfusion", capability.getMaxInfusionTime());
        nbt.putInt(NBT_KEY + ".possibleRecipes", this.possibleInfusionRecipes.size());
        nbt.putInt(NBT_KEY + ".currentRecipe", this.selectedRecipeIndex);
        nbt.putInt(NBT_KEY + ".boostduration", capability.getBoostDuration());
        nbt.putInt(NBT_KEY + ".existencePointsAmount", capability.getMoltenAmount());
        nbt.putInt(NBT_KEY + ".maxExistencePointsAmount", capability.getMaxMoltenAmount());
    }


    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.items);
        capability.setLeftBarId(nbt.contains(NBT_KEY + ".molten.leftBarId") ? Optional.ofNullable(ResourceLocation.tryParse(nbt.getString(NBT_KEY + ".molten.leftBarId"))) : Optional.of(MagicInfusionRecipe.EMPTY));
        capability.setRightBarId(nbt.contains(NBT_KEY + ".rightBarId") ? Optional.ofNullable(ResourceLocation.tryParse(nbt.getString(NBT_KEY + ".rightBarId"))) : Optional.of(MagicInfusionRecipe.EMPTY));
        capability.setInfusionBarId(nbt.contains(NBT_KEY + ".InfusionBarId") ? Optional.ofNullable(ResourceLocation.tryParse(nbt.getString(NBT_KEY + ".InfusionBarId"))) : Optional.of(MagicInfusionRecipe.EMPTY));
        capability.setMagicMaterialAmount(nbt.getInt(NBT_KEY + ".magicules"));
        capability.setMaxMagicMaterialAmount(nbt.getInt(NBT_KEY + ".maxMagicules"));
        capability.setAdditionalMagicMaterialAmount(nbt.getInt(NBT_KEY + ".addMolten"));
        capability.setSpeedModifier(nbt.getDouble(NBT_KEY + ".speedModifier"));
        capability.setMeltingProgress(nbt.getInt(NBT_KEY + ".meltingProgress"));
        capability.setFuelTime(nbt.getInt(NBT_KEY + ".fuel"));
        capability.setState(nbt.getInt(NBT_KEY + ".state"));
        capability.setMaxFuelTime(nbt.getInt(NBT_KEY + ".maxFuel"));
        capability.setInfusionTime(nbt.getInt(NBT_KEY + ".infusion"));
        capability.setMaxInfusionTime(nbt.getInt(NBT_KEY + ".maxInfusion"));
        this.totalPossibleRecipes = nbt.getInt(NBT_KEY + ".possibleRecipes");
        this.selectedRecipeIndex = nbt.getInt(NBT_KEY + ".currentRecipe");
        capability.setBoostDuration(nbt.getInt(NBT_KEY + ".boostduration"));
        capability.setMoltenAmount(nbt.getInt(NBT_KEY + ".existencePointsAmount"));
        if (nbt.getInt(NBT_KEY + ".maxExistencePointsAmount") == 0)
            capability.setMaxMoltenAmount(35000);
        else
            capability.setMaxMoltenAmount(nbt.getInt(NBT_KEY + ".maxExistencePointsAmount"));
    }
    // endregion

    public int[] getSlotsForFace(Direction pSide) {
        if (this.getBlockState().getValue(MagiculaInfuserBlock.PART).equals(MagicInfuserPart.TOP)) {
            return new int[0]; // Верхня частина недоступна
        } else {
            Direction backSide = this.getBlockState().getValue(MagiculaInfuserBlock.FACING).getOpposite(); // Задня сторона
            Direction rightSide = backSide.getClockWise(); // Права сторона відносно задньої
            Direction leftSide = backSide.getCounterClockWise(); // Ліва сторона відносно задньої

            if (pSide.equals(backSide)) {
                return new int[]{3}; // Задня сторона -> слот 3
            } else if (pSide.equals(rightSide)) {
                return new int[]{2}; // Права сторона -> слоти 0 і 1
            } else if (pSide.equals(leftSide)) {
                return new int[]{0, 1}; // Ліва сторона -> слот 2
            } else {
                return new int[0]; // Інші сторони недоступні
            }
        }
    }

    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        boolean var10000;
        switch (pIndex) {
            case 0 -> var10000 = AbstractFurnaceBlockEntity.isFuel(pItemStack);
            case 1 -> var10000 = isMelting(pItemStack);
            case 2 -> var10000 = isInfuse(pItemStack) || isEvolve(pItemStack);
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

    public boolean isEvolve(ItemStack stack) {
        assert this.level != null;
        // Перебираємо всі рецепти і перевіряємо, чи якийсь з них приймає ItemStack
        return this.level.getRecipeManager()
                .getAllRecipesFor(ReiRecipeTypes.INFUSER_EVOLVING.get())
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

    public void boost(double baseSpeedModifier) {
        if (capability.getSpeedModifier() == 0) {
            capability.setSpeedModifier(capability.getSpeedModifier() + baseSpeedModifier);
            needUpdate = true;
        }
    }

    public void resetSpeed() {
        capability.setSpeedModifier(0);
        needUpdate = true;
    }

    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        Direction backSide = this.getBlockState().getValue(MagiculaInfuserBlock.FACING).getOpposite(); // Задня сторона

        if (pDirection == Direction.DOWN && pIndex == 0) {
            // Дозволити брати воду або відро з водою через низ
            return pStack.is(Items.WATER_BUCKET) || pStack.is(Items.BUCKET);
        } else if (pDirection == backSide && pIndex == 3) {
            // Дозволити брати предмети з слоту 3 через задню сторону
            BlockEntity blockEntity = this.level.getBlockEntity(this.worldPosition.relative(backSide));

            if (blockEntity instanceof ChestBlockEntity chest) {
                // Спробувати додати предмет у скриню
                ItemStack stackInSlot = this.getItem(pIndex);
                ItemStack remaining = addItemToChest(chest, stackInSlot);

                // Якщо всі предмети переміщені, очистити слот
                if (remaining.isEmpty()) {
                    this.setItem(pIndex, ItemStack.EMPTY);
                } else {
                    this.setItem(pIndex, remaining);
                }
                return false; // Предмет не можна взяти, він вже переміщений
            }

            return true; // Якщо позаду немає скрині, дозволити забрати предмет
        } else {
            // Якщо слот 2 і дозволене хопером створення, виконай його
            if (pIndex == 2 && capability.isCanHopperInfusing()) {
                this.performInfusion();
                capability.setCanHopperInfusing(false);
            }

            // Заборонити брати предмети зі слоту 2
            return pIndex != 2;
        }
    }

    private ItemStack addItemToChest(ChestBlockEntity chest, ItemStack stack) {
        for (int i = 0; i < chest.getContainerSize(); i++) {
            ItemStack chestStack = chest.getItem(i);

            // Якщо слот пустий, перемістити весь стек
            if (chestStack.isEmpty()) {
                chest.setItem(i, stack);
                chest.setChanged();
                return ItemStack.EMPTY;
            }

            // Якщо предмети однакові, додати до існуючого стека
            if (ItemStack.isSameItemSameTags(stack, chestStack)) {
                int space = chestStack.getMaxStackSize() - chestStack.getCount();
                int toMove = Math.min(space, stack.getCount());

                if (toMove > 0) {
                    chestStack.grow(toMove);
                    stack.shrink(toMove);
                    chest.setChanged();

                    if (stack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        // Повернути залишок, якщо не вдалося перемістити весь стек
        return stack;
    }

    public int getContainerSize() {
        return this.items.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Функція для відображення анімації інфузії
     *
     * @return Предмет конкретного слоту
     */
    public ItemStack getRenderStack() {
        ItemStack stack;

        if (!getItem(2).isEmpty()) {
            stack = getItem(2);
        } else if (!getItem(3).isEmpty()) {
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
            return pPlayer.distanceToSqr((double) this.worldPosition.getX() + (double) 0.5F, (double) this.worldPosition.getY() + (double) 0.5F, (double) this.worldPosition.getZ() + (double) 0.5F) <= (double) 64.0F;
        }
    }

    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        boolean var10000;
        switch (pIndex) {
            case 0 -> var10000 = AbstractFurnaceBlockEntity.isFuel(pStack);
            case 1 -> var10000 = isMelting(pStack);
            case 2 -> var10000 = isInfuse(pStack) || isEvolve(pStack);
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

        for (int i = 0; i < this.items.size(); ++i) {
            if (i != 2) {
                inventory.setItem(i, this.items.get(i));
            }
        }

        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    private boolean checkInfusingCache() {
        if (capability.getLastMagiculesAmount() != capability.getMagicMaterialAmount()) {
            capability.setLastMagiculesAmount(capability.getMagicMaterialAmount());
            return true;
        } else if (!capability.getLastCatalystStack().equals(this.items.get(INPUT_CATALYST_SLOT_INDEX), true)) {
            capability.setLastCatalystStack(this.items.get(INPUT_CATALYST_SLOT_INDEX).copy());
            return true;
        } else return capability.getInfusionTime() > 0;
    }

    private boolean checkMeltingCache() {
        if (!capability.getLastInputStack().equals(this.getItem(INPUT_SLOT_INDEX), true) && !this.getItem(INPUT_SLOT_INDEX).isEnchanted()) {
            capability.setLastInputStack(this.getItem(INPUT_SLOT_INDEX).copy());
            return true;
        } else if (!capability.getLastCatalystStack().equals(this.getItem(INPUT_FUEL_SLOT_INDEX), true)) {
            capability.setLastFuelStack(this.getItem(INPUT_FUEL_SLOT_INDEX).copy());
            return true;
        } else if (capability.getMagicMaterialAmount() < (capability.getMaxMagicMaterialAmount() + capability.getAdditionalMagicMaterialAmount())) {
            return true;
        } else return capability.getMoltenAmount() < capability.getMaxMoltenAmount();
    }

    public void reduceInfusionTimeByPercentage(float percentage) {
        int reduction = Math.round(capability.getInfusionTime() * (percentage / 100));
        capability.setInfusionTime(Math.max(1, capability.getInfusionTime() - reduction));
    }

    public void decreesBoostDuration() {
        capability.setBoostDuration(capability.getBoostDuration() - 1);
    }

    // region Check Recipes
    private void checkInfusionRecipe() {
        assert this.level != null;
        performInfusion();
    }

    private void checkEvolvingRecipe() {
        if (isEvolve(this.getItem(INPUT_CATALYST_SLOT_INDEX))) {
            pervormEvolving();
        }
    }

    private void checkMeltingRecipe() {
        if (this.items.get(INPUT_SLOT_INDEX).isEmpty()) {
            resetMeltingProgress();
            return;
        }

        assert this.level != null;
        performMelting();
    }
    // endregion

    // region perform
    public void pervormEvolving() {
        this.level.getRecipeManager().
                getRecipeFor(ReiRecipeTypes.INFUSER_EVOLVING.get(), this, this.level).
                ifPresentOrElse(
                        magicInfusionRecipe -> {
                            if (capability.getMoltenAmount() >= 1) {
                                magicInfusionRecipe.assemble(this);
                            }

                            needUpdate = true;
                        },
                        this::resetEvolve
                );
    }

    public void performInfusion() {
        if (this.level instanceof ServerLevel serverLevel) {
            if (!getItem(2).isEmpty()) {
                serverLevel.getRecipeManager().
                        getRecipeFor(ReiRecipeTypes.MAGIC_INFUSION.get(), this, serverLevel).
                        ifPresentOrElse(
                                magicInfusionRecipe -> {
                                    if (capability.getInfusionTime() == 0) {
                                        capability.setInfusionBarId(Optional.of(INFUSION));
                                        capability.setInfusionTime(magicInfusionRecipe.getInfusionTime());
                                        capability.setMaxInfusionTime(magicInfusionRecipe.getInfusionTime());
                                        this.getBlockState().
                                                setValue(MagiculaInfuserBlock.INFUSION, Boolean.TRUE).
                                                setValue(MagiculaInfuserBlock.LIT, Boolean.FALSE);

                                    }

                                    if (!checkInfusion()) {
                                        resetInfusionProgress();
                                        return;
                                    }

                                    if (capability.getLastInfusionRecipe() == null || !capability.getLastInfusionRecipe().getId().equals(magicInfusionRecipe.getId())) {
                                        capability.setLastInfusionRecipe(magicInfusionRecipe);
                                        resetInfusionProgress();
                                    }

                                    if (capability.getInfusionProgress() >= 99) {
                                        magicInfusionRecipe.assemble(this);
                                        resetInfusionProgress();
                                    } else {
                                        capability.setInfusionProgress(100 * (capability.getMaxInfusionTime() - capability.getInfusionTime()) / capability.getMaxInfusionTime());
                                    }


                                    needUpdate = true;
                                },
                                this::resetInfusionProgress
                        );
            }

        }
    }

    public void performMelting() {
        this.level.getRecipeManager()
                .getRecipeFor(ReiRecipeTypes.MAGIC_INFUSER_MELTING.get(), this, this.level)
                .ifPresentOrElse(
                        magicMeltingRecipe -> {
                            if (!checkFuel()) {
                                resetMeltingProgress();
                                return;
                            }

                            if (capability.getLastMeltingRecipe() == null || !capability.getLastMeltingRecipe().getId().equals(magicMeltingRecipe.getId())) {
                                capability.setLastMeltingRecipe(magicMeltingRecipe);
                                resetMeltingProgress();
                            }

                            if (capability.getMeltingProgress() >= 100) {
                                magicMeltingRecipe.assemble(this);
                                resetMeltingProgress();

                            } else {
                                if (!this.getBlockState().getValue(MagiculaInfuserBlock.INFUSION)) {
                                    this.getBlockState().setValue(MagiculaInfuserBlock.LIT, Boolean.TRUE);
                                }
                                capability.setMeltingProgress(capability.getMeltingProgress() + 1);
                            }

                            needUpdate = true;
                        },
                        this::resetMeltingProgress
                );
    }
    // endregion

    // region Time
    private void updateFuelTime() {
        if (capability.getFuelTime() > 0) {
            capability.setFuelTime(capability.getFuelTime() - 1);
            this.needUpdate = true;
            if (!(Boolean) this.getBlockState().getValue(MagiculaInfuserBlock.LIT)) {
                BlockState newState = this.getBlockState().setValue(MagiculaInfuserBlock.LIT, true);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        } else {
            if (this.getBlockState().getValue(MagiculaInfuserBlock.LIT)) {
                BlockState newState = this.getBlockState().setValue(MagiculaInfuserBlock.LIT, false);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                capability.setState(0);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        }
    }

    private void updateInfusionTime() {
        if (capability.getInfusionTime() > 0) {
            capability.setInfusionTime(capability.getInfusionTime() - 1);
            this.needUpdate = true;
            if (!(Boolean) this.getBlockState().getValue(MagiculaInfuserBlock.INFUSION)) {
                BlockState newState = this.getBlockState().setValue(MagiculaInfuserBlock.INFUSION, true);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                capability.setState(2);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        } else {
            if (this.getBlockState().getValue(MagiculaInfuserBlock.INFUSION)) {
                BlockState newState = this.getBlockState().setValue(MagiculaInfuserBlock.INFUSION, false);
                this.level.setBlock(this.getBlockPos(), newState, 3);
                capability.setState(0);
                setChanged(this.level, this.getBlockPos(), newState);
            }

        }
    }
    // endregion

    // region Buttons
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
    // endregion

    // region Progress
    private void resetMeltingProgress() {
        if (capability.getMeltingProgress() > 0) {
            capability.setMeltingProgress(0);
            capability.setLastInputStack(ItemStack.EMPTY);
            this.getBlockState().setValue(MagiculaInfuserBlock.LIT, Boolean.FALSE);
            capability.setBoostDuration(0);
            this.needUpdate = true;
        }
    }

    public void resetInfusionProgress() {
        if (capability.getInfusionProgress() > 0) {
            capability.setInfusionProgress(0);
            capability.setMaxInfusionTime(0);
            capability.setInfusionTime(0);
            capability.setInfusionBarId(Optional.of(MagicInfusionRecipe.EMPTY));
            this.getBlockState().setValue(MagiculaInfuserBlock.INFUSION, Boolean.FALSE);
            capability.setLastCatalystStack(ItemStack.EMPTY);
            capability.setBoostDuration(0);
            this.needUpdate = true;
        }
    }

    public void resetEvolve() {
        if (this.capability.getMoltenAmount() > 0) {
            this.needUpdate = true;
        }
    }

    public boolean checkInfusion() {
        if (capability.getInfusionTime() > 0) {
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
        if (capability.getFuelTime() > 0) {
            return true;
        } else {
            ItemStack fuelSlotStack = this.items.get(0).copy();
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
                    capability.setFuelTime(fuelTime);
                    capability.setMaxFuelTime(fuelTime);
                    this.needUpdate = true;
                    return true;
                }
            }
        }
    }
    // endregion

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

    // region Molten Amount
    public void addMagicMaterialAmount(int moltenAmount) {
        BlockEntity belowBlock = level.getBlockEntity(getBlockPos().below());
        if (belowBlock instanceof MagicAmplifierBlockEntity container && (capability.getMagicMaterialAmount() + moltenAmount) >= capability.getMaxMagicMaterialAmount()) {
            container.addMagicMaterialAmount(moltenAmount);
            needUpdate = true;
        } else {
            this.capability.increseMagicAmount(moltenAmount);
            this.needUpdate = true;
        }

    }

    public void setRightBarId(Optional<ResourceLocation> barId) {
        capability.setRightBarId(barId);
        needUpdate = true;
    }

    public void setLeftBarId(Optional<ResourceLocation> barId) {
        capability.setLeftBarId(barId);
        needUpdate = true;
    }

    public void setInfusionBarId(Optional<ResourceLocation> barId) {
        capability.setInfusionBarId(barId);
        needUpdate = true;
    }

    public int getMoltenAmount() {
        BlockEntity belowBlock = level.getBlockEntity(getBlockPos().below());
        if (belowBlock instanceof MagicAmplifierBlockEntity container && capability.getMagicMaterialAmount() == capability.getMaxMagicMaterialAmount()) {
            return capability.getMoltenAmount() + container.getMoltenAmount();
        } else {
            return capability.getMoltenAmount();
        }
    }

    public int getMaxMoltenAmount() {
        BlockEntity belowBlock = level.getBlockEntity(getBlockPos().below());
        if (belowBlock instanceof MagicAmplifierBlockEntity container && capability.getMagicMaterialAmount() == capability.getMaxMagicMaterialAmount()) {
            return capability.getMaxMoltenAmount() + container.getMaxMoltenAmount();
        } else {
            return capability.getMaxMoltenAmount();
        }

    }

    public int getMagicMaterialAmount() {
        BlockEntity belowBlock = level.getBlockEntity(getBlockPos().below());
        if (belowBlock instanceof MagicAmplifierBlockEntity container && capability.getMagicMaterialAmount() == capability.getMaxMagicMaterialAmount()) {
            return capability.getMagicMaterialAmount() + container.getMagicMaterialAmount();
        } else {
            return capability.getMagicMaterialAmount();
        }

    }

    public int getMaxMagicMaterialAmount() {
        BlockEntity belowBlock = level.getBlockEntity(getBlockPos().below());
        if (belowBlock instanceof MagicAmplifierBlockEntity container && capability.getMagicMaterialAmount() == capability.getMaxMagicMaterialAmount()) {
            return capability.getMaxMagicMaterialAmount() + container.getMaxMagicMaterialAmount();
        } else {
            return capability.getMaxMagicMaterialAmount();
        }

    }

    public void addMoltenMaterialAmount(int points) {
        BlockEntity belowBlock = level.getBlockEntity(getBlockPos().below());
        if (belowBlock instanceof MagicAmplifierBlockEntity container && (capability.getMoltenAmount() + points) >= capability.getMaxMoltenAmount()) {
            container.addMoltenMaterialAmount(points);
            needUpdate = true;
        } else {
            this.capability.increseMoltenAmount(points);
            this.needUpdate = true;
        }

    }

    public void removeMagicMaterialAmount(int moltenAmount) {
        BlockEntity belowBlock = level.getBlockEntity(getBlockPos().below());
        if (belowBlock instanceof MagicAmplifierBlockEntity container && capability.getMagicMaterialAmount() == capability.getMaxMagicMaterialAmount()) {
            if (moltenAmount > container.getMagicMaterialAmount()) {
                int amount = moltenAmount - container.getMagicMaterialAmount();
                container.removeMagicMaterialAmount(amount);
                capability.decreseMagicAmount(container.getMagicMaterialAmount() - amount);
            } else {
                container.removeMagicMaterialAmount(moltenAmount);
                needUpdate = true;
            }
        } else {
            this.capability.decreseMagicAmount(moltenAmount);
            this.needUpdate = true;
        }
    }

    public void removeMoltenMaterialAmount(int moltenAmount) {
        BlockEntity belowBlock = level.getBlockEntity(getBlockPos().below());
        if (belowBlock instanceof MagicAmplifierBlockEntity container && capability.getMoltenAmount() == capability.getMaxMoltenAmount()) {
            if (moltenAmount > container.getMoltenAmount()) {
                int amount = moltenAmount - container.getMoltenAmount();
                container.removeMoltenMaterialAmount(amount);
                capability.decreseMoltenAmount(container.getMoltenAmount() - amount);
                needUpdate = true;
            } else {
                container.removeMoltenMaterialAmount(moltenAmount);
                needUpdate = true;
            }
        } else {
            this.capability.decreseMoltenAmount(moltenAmount);
            this.needUpdate = true;
        }

    }
    // endregion

    // region Boolen
    public boolean hasPrevInfusionRecipe() {
        return this.totalPossibleRecipes > this.selectedRecipeIndex - 1 && this.selectedRecipeIndex - 1 >= 0;
    }

    public boolean hasNextInfusionRecipe() {
        return this.totalPossibleRecipes > this.selectedRecipeIndex + 1 && this.selectedRecipeIndex + 1 >= 0;
    }
    // endregion

    // region Geckolib
    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!event.getAnimatable().getBlockState().getValue(MagiculaInfuserBlock.LIT) && !event.getAnimatable().getBlockState().getValue(MagiculaInfuserBlock.INFUSION)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));
        }
        if (event.getAnimatable().getBlockState().getValue(MagiculaInfuserBlock.LIT)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("lit", ILoopType.EDefaultLoopTypes.LOOP));
        }
        if (event.getAnimatable().getBlockState().getValue(MagiculaInfuserBlock.INFUSION)) {
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
        if (capability.getState() != 0) {
            ++this.miscAnimationTicks;
            if (this.miscAnimationTicks >= this.getAnimationTick(capability.getState())) {
                capability.setState(0);
                this.miscAnimationTicks = 0;
            }
        }
    }

    private int getAnimationTick(int miscAnimation) {
        return miscAnimation == 1 ? 25 : 7;
    }
    // endregion
}
