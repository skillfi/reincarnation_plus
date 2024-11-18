package com.github.skillfi.reincarnation_plus.entity;

import com.github.manasmods.tensura.api.entity.ai.CrossbowAttackGoal;
import com.github.manasmods.tensura.api.entity.ai.UndergroundTargetingEntitiesGoal;
import com.github.manasmods.tensura.api.entity.ai.WanderingFollowOwnerGoal;
import com.github.manasmods.tensura.client.particle.TensuraParticleHelper;
import com.github.manasmods.tensura.entity.template.HumanoidNPCEntity;
import com.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.ForgeMod;
import software.bernie.geckolib3.core.IAnimatable;

import javax.annotation.Nullable;

public class KijinEntity extends OgreEntity implements IAnimatable {
    private static final EntityDataAccessor<Integer> LAUGHING;
    public KijinEntity(EntityType<? extends KijinEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 2000;
        this.maxUpStep = 1.0F;
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, KIJIN.getATTACK_DAMAGE())
                .add(Attributes.MAX_HEALTH, KIJIN.getHP())
                .add(Attributes.MOVEMENT_SPEED, 0.15000000596046448)
                .add(Attributes.FOLLOW_RANGE, 64.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add((Attribute) ForgeMod.SWIM_SPEED.get(), 3.0)
                .add((Attribute)ForgeMod.ATTACK_RANGE.get(), 3.0).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new HumanoidNPCEntity.EatingItemGoal(this, (entity) -> {
            return this.shouldHeal();
        }, 3.0F) {
            public boolean canUse() {
                return KijinEntity.this.getLaughing() > 0 ? false : super.canUse();
            }
        });
        this.goalSelector.addGoal(3, new CrossbowAttackGoal(this, 1.2, 10.0F));
        this.goalSelector.addGoal(3, new RangedBowAttackGoal(this, 1.0, 20, 15.0F));
        this.goalSelector.addGoal(3, new HumanoidNPCEntity.SpearTypeAttackGoal(this, 1.0, 20, 15.0F));
//        this.goalSelector.addGoal(3, new OrcLordEntity.OrcLordAttackGoal(this));
//        this.goalSelector.addGoal(4, new OrcLordEntity.SummonOrcsGoal(this, 15, 400));
//        this.goalSelector.addGoal(5, new OrcLordEntity.RecoveryMagicGoal(this, 15, 100, 20.0F));
        this.goalSelector.addGoal(6, new WanderingFollowOwnerGoal(this, 1.5, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(7, new TensuraTamableEntity.WanderAroundPosGoal(this) {
            public boolean canUse() {
                return KijinEntity.this.getLaughing() > 0 ? false : super.canUse();
            }
        });
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(1, new TensuraTamableEntity.TensuraOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new TensuraTamableEntity.TensuraOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new TensuraTamableEntity.TensuraHurtByTargetGoal(this, new Class[]{KijinEntity.class})).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(4, new UndergroundTargetingEntitiesGoal(
                this,
                LivingEntity.class,
                false,
                8.0F,
                (entity) -> entity instanceof LivingEntity && this.isAngryAt((LivingEntity) entity)
        ));

        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal(this, true));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LAUGHING, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Laughing", this.getLaughing());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setLaughing(compound.getInt("Laughing"));
    }

    public int getLaughing() {
        return (Integer)this.entityData.get(LAUGHING);
    }
    public void setLaughing(int tick) {
        this.entityData.set(LAUGHING, tick);
    }

    private boolean shouldAttack(LivingEntity entity) {
        if (entity != this && !this.isAlliedTo(entity)) {
            if (this.getOwner() == null) {
                if (entity instanceof Player) {
                    Player player = (Player)entity;
                    if (!player.isCreative() && !player.isSpectator()) {
                        return true;
                    }
                }

                if (!(entity instanceof AbstractVillager) && !(entity instanceof AbstractIllager)) {
                    if (entity instanceof Animal) {
                        return !(entity instanceof KijinEntity);
                    } else {
                        return entity instanceof IronGolem;
                    }
                } else {
                    return true;
                }
            } else if (entity.isAlliedTo(this.getOwner())) {
                return false;
            } else if (entity instanceof Mob) {
                Mob mob = (Mob)entity;
                return mob.getTarget() == this.getOwner();
            } else {
                return this.getOwner().getLastHurtMob() == entity || this.getOwner().getLastHurtByMob() == entity;
            }
        } else {
            return false;
        }
    }

    protected void evolvingTick() {
        if (this.getEvolving() > 0) {
            this.setEvolving(this.getEvolving() - 1);
            this.refreshDimensions();
            this.playSound(SoundEvents.VILLAGER_CELEBRATE, 1.0F, 1.0F);
            TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.SQUID_INK);
            if (this.getEvolving() == 0) {
                this.evolveToOni();
            }
        }

    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.populateDefaultEquipmentSlots(this.random, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    static {
        LAUGHING = SynchedEntityData.defineId(KijinEntity.class, EntityDataSerializers.INT);
    }

    protected void evolveToOni() {
        CompoundTag tag = this.serializeNBT();
        this.discard();
//        OrcDisasterEntity orc = new OrcDisasterEntity((EntityType) TensuraEntityTypes.ORC_DISASTER.get(), this.level);
//        orc.load(tag);
//        Level var4 = this.level;
//        if (var4 instanceof ServerLevel serverLevel) {
//            orc.finalizeSpawn(serverLevel, this.level.getCurrentDifficultyAt(orc.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData)null, (CompoundTag)null);
//        }
//
//        RaceHelper.applyBaseAttribute(OrcDisasterEntity.setAttributes(), orc);
//        orc.setHealth(orc.getMaxHealth());
//        RaceHelper.updateSpiritualHP(orc);
//        RaceHelper.updateEntityEPCount(orc);
//        orc.setEvolving(0);
//        orc.setInvulnerable(false);
//        this.level.addFreshEntity(orc);
//        orc.refreshDimensions();
//        this.level.playSound((Player)null, orc.blockPosition(), SoundEvents.PIGLIN_BRUTE_CONVERTED_TO_ZOMBIFIED, SoundSource.PLAYERS, 10.0F, 1.0F);
//        this.level.addFreshEntity(orc);
//        TensuraParticleHelper.addServerParticlesAroundSelf(this, (ParticleOptions) TensuraParticles.CHAOS_EATER_EFFECT.get());
//        TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.SQUID_INK, 2.0);
//        TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.FLASH);
//        TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.EXPLOSION_EMITTER);
    }

}
