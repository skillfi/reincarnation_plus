package com.github.skillfi.reincarnation_plus.mixins;
import com.github.manasmods.tensura.api.entity.subclass.IRanking;
import com.github.manasmods.tensura.capability.ep.TensuraEPCapability;
import com.github.manasmods.tensura.race.RaceHelper;
import cy.jdkdigital.productivebees.common.entity.bee.ConfigurableBee;

import cy.jdkdigital.productivebees.common.entity.bee.ProductiveBee;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.github.skillfi.reincarnation_plus.core.entity.BeeEvolve;

import static cy.jdkdigital.productivebees.init.ModEntities.CONFIGURABLE_BEE;

@Mixin(value = ConfigurableBee.class)
public class MixinConfigurableBee extends ProductiveBee implements IRanking {

    // Поле для збереження еволюційного стану
    private static final EntityDataAccessor<Integer> EVOLVING = SynchedEntityData.defineId(MixinConfigurableBee.class, EntityDataSerializers.INT);

    public MixinConfigurableBee(EntityType<? extends Bee> entityType, Level world) {
        super(entityType, world);
    }

    // Дефініція нових синхронізованих даних
    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void define(CallbackInfo ci) {
        this.entityData.define(EVOLVING, 0);
    }

    // Збереження даних до NBT
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        compound.putInt("EvoState", this.getCurrentEvolutionState());
    }

    // Завантаження даних з NBT
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.setCurrentEvolutionState(compound.getInt("EvoState"));
    }

    // Унікальний метод для встановлення еволюційного стану
    @Unique
    public void setCurrentEvolutionState(int state) {
        this.entityData.set(EVOLVING, state);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void ticking(CallbackInfo ci){
        CompoundTag tag = this.serializeNBT();
        ResourceLocation type = new ResourceLocation(tag.getString("type"));
        BeeEvolve evolve = BeeEvolve.byBeeName(type);
        if (evolve != null){
            TensuraEPCapability.getFrom(this).ifPresent((cap)->{
                if (evolve.getMaxEP() <= cap.getCurrentEP()){
                    this.evolve();
                }
            });
        }
    }

    @Unique
    public void evolve(){
        int current = this.getCurrentEvolutionState();
        if (current < this.getMaxEvolutionState()) {
            this.setCurrentEvolutionState(current + 1);
        }
        Level level = this.getLevel();
        CompoundTag tag = this.serializeNBT();
        ResourceLocation type = new ResourceLocation(tag.getString("type"));
        BeeEvolve evolve = BeeEvolve.byBeeName(type);
        tag.putString("type", evolve.getEvolveBeeName().toString());
        this.discard();
        ConfigurableBee bee = new ConfigurableBee(CONFIGURABLE_BEE.get(), level);
        bee.load(tag);
        if (level instanceof ServerLevel serverLevel) {
            bee.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(bee.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData)null, (CompoundTag)null);
        }
        RaceHelper.updateSpiritualHP(bee);
        RaceHelper.updateEntityEPCount(bee);
        bee.setHealth(bee.getMaxHealth());
        level.addFreshEntity(bee);
    }

    // Унікальний метод для отримання еволюційного стану
    @Unique
    public int getCurrentEvolutionState() {
        return this.entityData.get(EVOLVING);
    }
}

