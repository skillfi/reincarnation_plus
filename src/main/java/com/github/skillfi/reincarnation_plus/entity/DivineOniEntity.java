package com.github.skillfi.reincarnation_plus.entity;

import com.github.manasmods.manascore.api.skills.ManasSkill;
import com.github.manasmods.manascore.api.skills.ManasSkillInstance;
import com.github.manasmods.manascore.api.skills.SkillAPI;
import com.github.manasmods.manascore.api.skills.capability.SkillStorage;
import com.github.manasmods.tensura.api.entity.ai.CrossbowAttackGoal;
import com.github.manasmods.tensura.api.entity.ai.UndergroundTargetingEntitiesGoal;
import com.github.manasmods.tensura.api.entity.ai.WanderingFollowOwnerGoal;
import com.github.manasmods.tensura.client.particle.TensuraParticleHelper;
import com.github.manasmods.tensura.race.RaceHelper;
import com.github.manasmods.tensura.registry.attribute.TensuraAttributeRegistry;
import com.github.skillfi.reincarnation_plus.entity.variant.OgreVariant;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import java.util.UUID;

public class DivineOniEntity extends OniEntity implements IAnimatable {
    private static final EntityDataAccessor<Integer> LAUGHING;
    public DivineOniEntity(EntityType<? extends DivineOniEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 2000;
        this.maxUpStep = 1.0F;
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, DIVINE_ONI.getATTACK_DAMAGE())
                .add(Attributes.MAX_HEALTH, DIVINE_ONI.getHP())
                .add(Attributes.MOVEMENT_SPEED, 0.15000000596046448)
                .add(Attributes.FOLLOW_RANGE, 64.0)
                .add(Attributes.ARMOR, DIVINE_ONI.getArmor())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add((Attribute) TensuraAttributeRegistry.MAX_SPIRITUAL_HEALTH.get(), DIVINE_ONI.getSHP())
                .add((Attribute) ForgeMod.SWIM_SPEED.get(), 3.0)
                .add((Attribute)ForgeMod.ATTACK_RANGE.get(), 3.0).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new EatingItemGoal(this, (entity) -> {
            return this.shouldHeal();
        }, 3.0F) {
            public boolean canUse() {
                return DivineOniEntity.this.getLaughing() > 0 ? false : super.canUse();
            }
        });
        this.goalSelector.addGoal(3, new CrossbowAttackGoal(this, 1.2, 10.0F));
        this.goalSelector.addGoal(3, new RangedBowAttackGoal(this, 1.0, 20, 15.0F));
        this.goalSelector.addGoal(3, new SpearTypeAttackGoal(this, 1.0, 20, 15.0F));
        this.goalSelector.addGoal(6, new WanderingFollowOwnerGoal(this, 1.5, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(7, new WanderAroundPosGoal(this) {
            public boolean canUse() {
                return DivineOniEntity.this.getLaughing() > 0 ? false : super.canUse();
            }
        });
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.2, DivineOniEntity.class));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(1, new TensuraOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new TensuraOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new TensuraHurtByTargetGoal(this, new Class[]{DivineOniEntity.class})).setAlertOthers(new Class[0]));
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

    public boolean canEvolve() {
        return this.getEvolving() == 2 && this.getEPCondition();
    }

    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        DivineOniEntity baby = (DivineOniEntity) ((EntityType<?>) RPEntities.DIVINE_ONI.get()).create(pLevel);
        if (baby == null) {
            return null;
        } else {
            UUID uuid = this.getOwnerUUID();
            if (uuid != null) {
                baby.setOwnerUUID(uuid);
                baby.setTame(true);
            }

            baby.randomTexture();
            if (this.isNextOni() && ((DivineOniEntity) pOtherParent).isNextOni()) {
                baby.evolve();
            }

            return baby;
        }
    }

    public void tick() {
        super.tick();
        if (this.canEvolve()){
            this.evolvingTick();
        }
        if (this.prevSwim != this.isInFluidType() && !this.isOnGround()) {
            this.refreshDimensions();
            this.prevSwim = this.isInFluidType() && !this.isOnGround();
        }

        this.miscAnimationHandler();
    }

    /**
     * Get condition for next evolving {@link DivineOniEntity} or {@link WickedOniEntity}
     */
    protected void evolvingTick() {
        this.refreshDimensions();
        this.playSound(SoundEvents.VILLAGER_CELEBRATE, 1.0F, 1.0F);
        TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.SQUID_INK);
        if (this.getEvolving() == 2) {
            this.evolving();
        }
    }

    @Override
    public void evolving() {
        SkillStorage storage = SkillAPI.getSkillsFrom(this);
        for (ManasSkill skill : skills){
            storage.learnSkill(skill);
        }
        for (ManasSkill in : instances){
            ManasSkillInstance instance = new ManasSkillInstance(in);
            storage.learnSkill(instance);
            instance.setToggled(true);
        }
        CompoundTag tag = this.serializeNBT();
        this.discard();
        DivineFighterEntity divineFighter = new DivineFighterEntity((EntityType) RPEntities.DIVINE_FIGHTER.get(), this.level);
        divineFighter.load(tag);
        Level var4 = this.level;
        if (var4 instanceof ServerLevel serverLevel) {
            divineFighter.finalizeSpawn(serverLevel, this.level.getCurrentDifficultyAt(divineFighter.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData)null, (CompoundTag)null);
        }
        RaceHelper.applyBaseAttribute(DivineOniEntity.setAttributes(), divineFighter);
        divineFighter.setHealth(divineFighter.getMaxHealth());
        RaceHelper.updateSpiritualHP(divineFighter);
        RaceHelper.updateEntityEPCount(divineFighter);
        divineFighter.setEvolving(1);
        divineFighter.setInvulnerable(false);
        this.level.addFreshEntity(divineFighter);
        divineFighter.refreshDimensions();
        this.level.playSound((Player)null, divineFighter.blockPosition(), SoundEvents.VILLAGER_CELEBRATE, SoundSource.PLAYERS, 10.0F, 1.0F);
        this.level.addFreshEntity(divineFighter);
        TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.SQUID_INK, 2.0);
    }

    /**
    Evolve Baby
     */
    public void evolve(){
        int current = this.getEvolving();
        if (current == 2){
            this.gainSwimSpeed(this, 1.0);
            this.evolving();
        }
    }

    public int getLaughing() {
        return (Integer)this.entityData.get(LAUGHING);
    }
    public void setLaughing(int tick) {
        this.entityData.set(LAUGHING, tick);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.populateDefaultEquipmentSlots(this.random, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    static {
        LAUGHING = SynchedEntityData.defineId(DivineOniEntity.class, EntityDataSerializers.INT);
    }

}
