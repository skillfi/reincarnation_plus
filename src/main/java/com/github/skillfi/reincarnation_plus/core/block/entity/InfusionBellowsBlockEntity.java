package com.github.skillfi.reincarnation_plus.core.block.entity;

import com.github.skillfi.reincarnation_plus.core.block.InfusionBellowsBlock;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.stream.IntStream;


public class InfusionBellowsBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, IAnimatable {
    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(0, ItemStack.EMPTY);
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    @Getter @Setter public boolean boost;
    @Getter @Setter private int state;
    @Getter public double speedModifier;
    @Getter @Setter private int boostduration;
    public boolean needUpdate;
    @Getter @Setter public int miscAnimationTicks = 0;


    public InfusionBellowsBlockEntity(BlockPos position, BlockState state) {
        super(ReiBlockEntities.INFUSION_BELLOWS.get(), position, state);
        this.state = 0;
        this.boostduration = 0;
        this.speedModifier = 0.5;
        this.needUpdate = false;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (!this.tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.stacks);
        this.state = compound.getInt("infusion_bellows.state");
        this.boostduration = compound.getInt("infusion_bellows.boostduration");
        this.speedModifier = compound.getDouble("infusion_bellows.speedModifier");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks);
        }
        compound.putInt("infusion_bellows.state", this.state);
        compound.putInt("infusion_bellows.boostduration", this.boostduration);
        compound.putDouble("infusion_bellows.speedModifier", getSpeedModifier());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithFullMetadata();
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks)
            if (!itemstack.isEmpty())
                return false;
        return true;
    }

    @Override
    public Component getDefaultName() {
        return Component.literal("infusion_bellows");
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Infusion Bellows");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER)
            return handlers[facing.ordinal()].cast();
        return super.getCapability(capability, facing);
    }

    private void updateBoost(int boostduration){
        setBoostduration(boostduration);
        this.getBlockState().setValue(InfusionBellowsBlock.USE, true);
        this.needUpdate = true;
    }

    private void resetBoost(){
        setBoostduration(0);
        this.getBlockState().setValue(InfusionBellowsBlock.USE, false);
        this.needUpdate = true;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : handlers)
            handler.invalidate();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 2);
            needUpdate = false;
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, InfusionBellowsBlockEntity pEntity) {
        if (!level.isClientSide()) {

            // Отримання блоку MagicInfuser поруч
            Direction[] directions = Direction.values();
            for (Direction direction : directions) {
                BlockPos neighborPos = pos.relative(direction);
                BlockEntity neighborEntity = level.getBlockEntity(neighborPos);

                if (neighborEntity instanceof MagiculaInfuserBlockEntity infuser) {
                    if (infuser.getBoostDuration() == 0){
                        pEntity.resetBoost();
                    }
                    pEntity.updateBoost(infuser.getBoostDuration());
                }
            }

            if (pEntity.needUpdate) {
                pEntity.setChanged();
            }
        }

        pEntity.miscAnimationHandler();
    }

    // region Geckolib
    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!((BlockEntity)event.getAnimatable()).getBlockState().getValue(InfusionBellowsBlock.USE)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }


    private <E extends BlockEntity & IAnimatable> PlayState MiscPredicate(AnimationEvent<E> event) {
        InfusionBellowsBlockEntity entity = (InfusionBellowsBlockEntity) event.getAnimatable();

        // Перевірка стану анімації
        AnimationController<?> controller = event.getController();
        if (controller.getAnimationState() == AnimationState.Stopped) {
            // Маркуємо контролер для перезавантаження
            controller.markNeedsReload();

            // Перевіряємо стан блоку
            if (getBoostduration() > 0) {
                // Встановлюємо анімацію
                controller.setAnimation(new AnimationBuilder().addAnimation("use", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            } else {
                // Якщо `USE` == false, зупиняємо анімацію
                controller.clearAnimationCache();
            }
        }
        return PlayState.CONTINUE;
    }


    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
        animationData.addAnimationController(new AnimationController<>(this, "misccontroller", 0, this::MiscPredicate));
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
