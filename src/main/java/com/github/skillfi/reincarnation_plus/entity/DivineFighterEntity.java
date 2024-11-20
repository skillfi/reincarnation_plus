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

public class DivineFighterEntity extends DivineOniEntity implements IAnimatable {
    private static final EntityDataAccessor<Integer> LAUGHING;
    public DivineFighterEntity(EntityType<? extends DivineFighterEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 2000;
        this.maxUpStep = 1.0F;
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, DIVINE_FIGHTER.getATTACK_DAMAGE())
                .add(Attributes.MAX_HEALTH, DIVINE_FIGHTER.getHP())
                .add(Attributes.MOVEMENT_SPEED, 0.15000000596046448)
                .add(Attributes.FOLLOW_RANGE, 64.0)
                .add(Attributes.ARMOR, DIVINE_FIGHTER.getArmor())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add((Attribute) ForgeMod.SWIM_SPEED.get(), 3.0)
                .add((Attribute) TensuraAttributeRegistry.MAX_SPIRITUAL_HEALTH.get(), DIVINE_FIGHTER.getSHP())
                .add((Attribute)ForgeMod.ATTACK_RANGE.get(), 3.0).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new EatingItemGoal(this, (entity) -> {
            return this.shouldHeal();
        }, 3.0F) {
            public boolean canUse() {
                return DivineFighterEntity.this.getLaughing() > 0 ? false : super.canUse();
            }
        });
        this.goalSelector.addGoal(3, new CrossbowAttackGoal(this, 1.2, 10.0F));
        this.goalSelector.addGoal(3, new RangedBowAttackGoal(this, 1.0, 20, 15.0F));
        this.goalSelector.addGoal(3, new SpearTypeAttackGoal(this, 1.0, 20, 15.0F));
        this.goalSelector.addGoal(6, new WanderingFollowOwnerGoal(this, 1.5, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(7, new WanderAroundPosGoal(this) {
            public boolean canUse() {
                return DivineFighterEntity.this.getLaughing() > 0 ? false : super.canUse();
            }
        });
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.2, DivineFighterEntity.class));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(1, new TensuraOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new TensuraOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new TensuraHurtByTargetGoal(this, new Class[]{DivineFighterEntity.class})).setAlertOthers(new Class[0]));
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

    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        WickedOniEntity baby = (WickedOniEntity) ((EntityType<?>) RPEntities.WICKED_ONI.get()).create(pLevel);
        if (baby == null) {
            return null;
        } else {
            UUID uuid = this.getOwnerUUID();
            if (uuid != null) {
                baby.setOwnerUUID(uuid);
                baby.setTame(true);
            }

            baby.randomTexture();
            if (this.isDivineFighter() && ((WickedOniEntity) pOtherParent).isDivineFighter()) {
                baby.evolve();
            }

            return baby;
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.populateDefaultEquipmentSlots(this.random, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    static {
        LAUGHING = SynchedEntityData.defineId(DivineFighterEntity.class, EntityDataSerializers.INT);
    }


}
