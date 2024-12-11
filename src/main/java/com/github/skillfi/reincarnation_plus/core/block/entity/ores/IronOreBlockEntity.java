package com.github.skillfi.reincarnation_plus.core.block.entity.ores;

import com.github.manasmods.tensura.registry.blocks.TensuraBlocks;
import com.github.skillfi.reincarnation_plus.core.block.ores.IronMagicOreBlock;
import com.github.skillfi.reincarnation_plus.core.block.variant.MagicOreVariant;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.libs.api.aura.AuraAPI;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class IronOreBlockEntity extends BlockEntity implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    @Getter
    @Setter
    private int tickCounter;
    @Getter
    @Setter
    private double magicules;
    @Getter @Setter private int id;
    public boolean needUpdate;
    @Getter
    public double maxMagicules;

    private static final int TICKS_PER_ATTEMPT = 100; // Кількість тіків між спробами
    private static final double GROWTH_CHANCE = 0.05; // Шанс перетворення (5%)

    public IronOreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ReiBlockEntities.IRON_MAGIC_ORE.get(), pPos, pBlockState);
        this.tickCounter = 0;
        this.magicules = 0.0;
        this.id = 0;
        this.needUpdate = false;
        this.maxMagicules = 50000;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        setTickCounter(tag.getInt("TickCounter"));
        setMagicules(tag.getDouble("Magicules"));
        setId(tag.getInt("Id"));
    }

    private void addMagicules(double magicules){
        this.magicules += magicules;
        this.needUpdate = true;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("TickCounter", getTickCounter());
        tag.putDouble("Magicules", getMagicules());
        tag.putInt("Id", getId());
    }

    public MagicOreVariant.OreAge getAge(){
        return MagicOreVariant.OreAge.byId(getBlockState().getValue(IronMagicOreBlock.AGE));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, IronOreBlockEntity pEntity) {
        if (!level.isClientSide()){
            pEntity.tickCounter++;
            if (pEntity.tickCounter >= TICKS_PER_ATTEMPT) {
                pEntity.tickCounter = 0;// Скидання лічильника
                if (AuraAPI.getAura(level, pos) > 0.0){
                    if (AuraAPI.consumeAura(level, pos, 500.0)){
                        if (pEntity.getMagicules() < pEntity.getMaxMagicules())
                            pEntity.addMagicules(500);
                        if (level.random.nextDouble() < GROWTH_CHANCE && pEntity.getMagicules() > 10000) {
                            pEntity.getBlockState().setValue(IronMagicOreBlock.AGE, 1);
                            pEntity.needUpdate = true;
                        }
                        if (level.random.nextDouble() < GROWTH_CHANCE && pEntity.getMagicules() > 20000) {
                            pEntity.getBlockState().setValue(IronMagicOreBlock.AGE, 2);
                            pEntity.needUpdate = true;
                        }
                        if (level.random.nextDouble() < GROWTH_CHANCE && pEntity.getMagicules() > 30000) {
                            pEntity.getBlockState().setValue(IronMagicOreBlock.AGE, 3);
                            pEntity.needUpdate = true;
                        }
                    }
                }
            }

            if (pEntity.needUpdate) {
                pEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 2);
                pEntity.needUpdate = false;
            }
        }
    }

    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));
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
}
