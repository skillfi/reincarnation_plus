package com.github.skillfi.reincarnation_plus.entity;

import com.github.manasmods.manascore.api.skills.SkillAPI;
import com.github.manasmods.manascore.api.skills.capability.SkillStorage;
import com.github.manasmods.tensura.api.entity.ai.CrossbowAttackGoal;
import com.github.manasmods.tensura.api.entity.ai.TamableFollowParentGoal;
import com.github.manasmods.tensura.api.entity.ai.WanderingFollowOwnerGoal;
import com.github.manasmods.tensura.api.entity.subclass.IRanking;
import com.github.manasmods.tensura.client.particle.TensuraParticleHelper;
import com.github.manasmods.tensura.config.SpawnRateConfig;
import com.github.manasmods.tensura.entity.OrcEntity;
import com.github.manasmods.tensura.entity.OrcLordEntity;
import com.github.manasmods.tensura.entity.template.HumanoidNPCEntity;
import com.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import com.github.manasmods.tensura.race.RaceHelper;
import com.github.manasmods.tensura.registry.items.TensuraMobDropItems;
import com.github.manasmods.tensura.registry.items.TensuraToolItems;
import com.github.manasmods.tensura.registry.particle.TensuraParticles;
import com.github.manasmods.tensura.registry.skill.ExtraSkills;
import com.github.manasmods.tensura.registry.skill.UniqueSkills;
import com.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.entity.variant.OgreVariant;
import com.github.skillfi.reincarnation_plus.entity.variant.OgreVariant.Gender;
import com.github.skillfi.reincarnation_plus.handler.RPItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.UUID;

public class OgreEntity extends HumanoidNPCEntity implements IRanking, IAnimatable {

    public static final RPEntitiesStats OGRE = RPEntitiesStats.Ogre;
    public static final RPEntitiesStats KIJIN = RPEntitiesStats.Kijin;
    private static final EntityDataAccessor<Integer> MISC_ANIMATION;
    private static final EntityDataAccessor<Integer> OGRE_VARIANT = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> EVOLVING;
    private static final EntityDataAccessor<Integer> GENDER;
    private static final EntityDataAccessor<Integer> STYLE;
    private static final EntityDataAccessor<Integer> EVOLVE_STATE = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);

    static {
        EVOLVING = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        GENDER = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        STYLE = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        MISC_ANIMATION = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
    }

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public int miscAnimationTicks = 0;
    public boolean prevSwim = false;

    public SimpleContainer ogreInventory = new SimpleContainer(6);

    public OgreEntity(EntityType<? extends OgreEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 5;
        this.lookControl = new EvolvingLookControl();
        this.moveControl = new EvolvingMoveControl();
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).
                add(Attributes.MAX_HEALTH, (OGRE.getHP())).
                add(Attributes.ARMOR, OGRE.getArmor()).
                add(Attributes.ATTACK_DAMAGE, 3).
                add(Attributes.FOLLOW_RANGE, 16).
        add((Attribute) ForgeMod.ATTACK_RANGE.get(), 1.0).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new HumanoidNPCEntity.EatingItemGoal(this, (entity) -> {
            return this.shouldHeal();
        }, 3.0F));
        this.goalSelector.addGoal(3, new CrossbowAttackGoal(this, 1.2, 20.0F));
        this.goalSelector.addGoal(3, new RangedBowAttackGoal(this, 1.0, 20, 20.0F));
        this.goalSelector.addGoal(3, new HumanoidNPCEntity.SpearTypeAttackGoal(this, 1.0, 20, 20.0F) {
            public boolean canUse() {
                OgreEntity ogre = OgreEntity.this;
                LivingEntity target = ogre.getTarget();
                if (target == null) {
                    return false;
                } else {
                    return (ogre.isTame() || !(target.getY() - ogre.getY() < 5.0)) && super.canUse();
                }
            }
        });
        this.goalSelector.addGoal(3, new HumanoidNPCEntity.NPCMeleeAttackGoal(this, 2.0, true));
        this.goalSelector.addGoal(4, new WanderingFollowOwnerGoal(this, 1.5, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.2, OrcEntity.class));
        this.goalSelector.addGoal(6, new TamableFollowParentGoal(this, 1.5));
        this.goalSelector.addGoal(7, new TensuraTamableEntity.WanderAroundPosGoal(this));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(1, new TensuraTamableEntity.TensuraOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new TensuraTamableEntity.TensuraOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new TensuraTamableEntity.TensuraHurtByTargetGoal(this, OgreEntity.class)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,  (entity) -> entity instanceof LivingEntity && this.isAngryAt((LivingEntity) entity)));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MISC_ANIMATION, 0);
        this.entityData.define(EVOLVING, 0);
        this.entityData.define(EVOLVE_STATE, 0);
        this.entityData.define(OGRE_VARIANT, 0);
        this.entityData.define(GENDER, 0);
        this.entityData.define(STYLE, 0);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("MiscAnimation", this.getMiscAnimation());
        compoundTag.put("Inventory", this.ogreInventory.createTag());
        compoundTag.putInt("EvoState", this.getCurrentEvolutionState());
        compoundTag.putInt("Gender", this.entityData.get(GENDER));
        compoundTag.putInt("Style", this.entityData.get(STYLE));
        compoundTag.putInt("Evolution", this.getEvolutionState().getId());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setEvolving(compound.getInt("Evolving"));
        this.entityData.set(MISC_ANIMATION, compound.getInt("MiscAnimation"));
        this.entityData.set(GENDER, compound.getInt("Gender"));
        this.entityData.set(STYLE, compound.getInt("Style"));
        this.entityData.set(EVOLVE_STATE, compound.getInt("Evolution"));
    }

    public int getEvolving() {
        return this.entityData.get(EVOLVING);
    }

    public void setEvolving(int tick) {
        this.entityData.set(EVOLVING, tick);
    }

    public OgreVariant.Skin getEvolutionState() {
        return OgreVariant.Skin.byId(this.entityData.get(EVOLVE_STATE));
    }

    public int getMaxEvolutionState() {
        return 1;
    }

    public void gainMaxHealth(LivingEntity entity, double amount) {
        AttributeInstance health = entity.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(amount);
        }

        entity.heal(entity.getMaxHealth());
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FLAGS_ID.equals(pKey)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(pKey);
    }

    public void evolve() {
        int current = this.getCurrentEvolutionState();
        if (current < this.getMaxEvolutionState()) {
            this.setCurrentEvolutionState(current + 1);
            SkillStorage storage = SkillAPI.getSkillsFrom(this);
            switch (current) {
                case 1: {
                    storage.learnSkill(ExtraSkills.BLACK_FLAME.get());
                    if (this.getGender().getId() > 1) {
                        storage.learnSkill(UniqueSkills.CHEF.get());
                    }
                    storage.learnSkill(ExtraSkills.STRENGTHEN_BODY.get());
                    storage.learnSkill(UniqueSkills.MARTIAL_MASTER.get());
                    this.gainMovementSpeed(this, KIJIN.getMovementSpeed());
                    this.gainSwimSpeed(this, 3.0F);
                    this.gainMaxHealth(this, KIJIN.getHP());
                    this.gainAttackDamage(this, KIJIN.getATTACK_DAMAGE());
                }
            }


        }

    }

    public void setChargingCrossbow(boolean pIsCharging) {
        super.setChargingCrossbow(pIsCharging);
        if (pIsCharging) {
            this.setMiscAnimation(3);
        }

    }

    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        OgreEntity baby = (OgreEntity) ((EntityType<?>) RPEntities.OGRE.get()).create(pLevel);
        if (baby == null) {
            return null;
        } else {
            UUID uuid = this.getOwnerUUID();
            if (uuid != null) {
                baby.setOwnerUUID(uuid);
                baby.setTame(true);
            }

            baby.randomTexture();
            if (this.isKijin() && ((OgreEntity) pOtherParent).isKijin()) {
                baby.evolve();
            }

            return baby;
        }
    }

    public boolean isFood(ItemStack pStack) {
        return !pStack.is(TensuraMobDropItems.ROYAL_BLOOD.get()) && pStack.getItem().isEdible();
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.CACTUS || super.isInvulnerableTo(source);
    }

    public EntityDimensions getDimensions(Pose pPose) {
        EntityDimensions entitydimensions = super.getDimensions(pPose);
        if (this.isSleeping()) {
            return entitydimensions.scale(1.0F, 0.5F);
        } else if (this.shouldSwim()) {
            return entitydimensions.scale(1.0F, 0.25F);
        } else if (this.getClass() == OgreEntity.class && (this.isOrderedToSit() || this.isInSittingPose())) {
            return entitydimensions.scale(1.0F, 0.75F);
        } else if (this.getClass() == OgreEntity.class) {
//            replace to Kijin
            return entitydimensions;
        } else {
            int tick = 40 - this.getEvolving();
            if (this.getEvolving() > 0 && tick > 0) {
                float scale = 1.0F + 0.5F * ((float) tick / 40.0F);
                return entitydimensions.scale(scale);
            } else {
                return entitydimensions;
            }
        }
    }

    public boolean canSleep() {
        return true;
    }

    public void push(Entity pEntity) {
        if (!(pEntity instanceof OrcLordEntity)) {
            super.push(pEntity);
        }
    }

    public void tick() {
        super.tick();
        this.evolvingTick();
        if (this.prevSwim != this.isInFluidType() && !this.isOnGround()) {
            this.refreshDimensions();
            this.prevSwim = this.isInFluidType() && !this.isOnGround();
        }

        this.miscAnimationHandler();
    }

    protected void evolvingTick() {
        if (this.getEvolving() > 0) {
            this.setEvolving(this.getEvolving() - 1);
            this.refreshDimensions();
            this.playSound(SoundEvents.VILLAGER_CELEBRATE, 1.0F, 1.0F);
            if (this.getEvolving() == 0) {
                this.evolveToKijin();
            }
        }
    }

    protected void miscAnimationHandler() {
        if (this.getMiscAnimation() != 0) {
            ++this.miscAnimationTicks;
            if (this.miscAnimationTicks >= this.getAnimationTick(this.getMiscAnimation())) {
                this.setMiscAnimation(0);
                this.miscAnimationTicks = 0;
            }
        }
    }

    private int getAnimationTick(int miscAnimation) {
        return miscAnimation == 3 ? 25 : 7;
    }

    public boolean doHurtTarget(Entity pEntity) {
        boolean flag = super.doHurtTarget(pEntity);
        if (flag && this.getMiscAnimation() == 0) {
            this.setMiscAnimation(1);
        }

        return flag;
    }

    protected void hurtShield(ItemStack stack, float pAmount) {
        super.hurtShield(stack, pAmount);
        this.setMiscAnimation(2);
    }

    protected boolean spearThrowAttack(LivingEntity pTarget, ItemStack weapon) {
        boolean success = super.spearThrowAttack(pTarget, weapon);
        if (success) {
            this.setMiscAnimation(4);
        }

        return success;
    }

    protected float getEquipmentDropChance(EquipmentSlot pSlot) {
        if (this.isTame()) {
            return 0.0F;
        } else if (pSlot.getType().equals(EquipmentSlot.Type.ARMOR)) {
            return super.getEquipmentDropChance(pSlot) >= 2.0F ? 2.0F : 0.0F;
        } else {
            return super.getEquipmentDropChance(pSlot);
        }
    }

    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
    }

    public InteractionResult handleEating(Player player, InteractionHand hand, ItemStack stack) {
        if (this.isFood(stack)) {
            if (this.getHealth() < this.getMaxHealth()) {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }

                this.ate();
                this.getLevel().playSound(null, this, TensuraSoundEvents.EATING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }

            if (this.isBaby()) {
                this.usePlayerItem(player, hand, stack);
                this.ageUp(getSpeedUpSecondsWhenFeeding(-this.getAge()), true);
                this.getLevel().playSound(player, this, TensuraSoundEvents.EATING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }

            if (!this.isBaby() && this.canFallInLove()) {
                this.usePlayerItem(player, hand, stack);
                this.setInLove(player);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }

        return InteractionResult.PASS;
    }

    public void ate() {
        super.ate();
        this.heal(3.0F);
    }

    private void evolveToKijin() {
        Level level = this.getLevel();
        CompoundTag tag = this.serializeNBT();
        this.discard();
        KijinEntity kijin = new KijinEntity((EntityType) RPEntities.KIJIN.get(), level);
        kijin.load(tag);
        if (level instanceof ServerLevel serverLevel) {
            kijin.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(kijin.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData)null, (CompoundTag)null);
        }

//        kijin.setVariant(OrcVariant.NORMAL);
        RaceHelper.applyBaseAttribute(KijinEntity.setAttributes(), kijin, true);
        RaceHelper.updateSpiritualHP(kijin);
        RaceHelper.updateEntityEPCount(kijin);
        kijin.setHealth(kijin.getMaxHealth());
        level.addFreshEntity(kijin);
        level.playSound((Player)null, kijin.blockPosition(), SoundEvents.PIGLIN_BRUTE_CONVERTED_TO_ZOMBIFIED, SoundSource.PLAYERS, 1.0F, 1.0F);
//        TensuraParticleHelper.addServerParticlesAroundSelf(this, (ParticleOptions)TensuraParticles.CHAOS_EATER_EFFECT.get());
//        TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.SQUID_INK, 2.0);
//        TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.FLASH);
//        TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.EXPLOSION_EMITTER);
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        if (this.isTame()) {
            return false;
        }
        return false;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.populateDefaultEquipmentSlots(this.random, pDifficulty);
        this.randomTexture();
        RPMod.LOGGER.info("Ogre spawned at " + this.blockPosition().getX() + ", " + this.blockPosition().getY() + ", " + this.blockPosition().getZ());
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
        return SpawnRateConfig.rollSpawn((Integer)SpawnRateConfig.INSTANCE.orcSpawnRate.get(), this.getRandom(), pSpawnReason) && super.checkSpawnRules(pLevel, pSpawnReason);
    }

    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        if (!(pRandom.nextFloat() >= 0.2F)) {
            int i = pRandom.nextInt(3);
            ItemStack stack = new ItemStack(Items.IRON_AXE);
            if (i == 0) {
                stack = new ItemStack(TensuraToolItems.IRON_SPEAR.get());
            }

            this.setItemSlot(EquipmentSlot.MAINHAND, stack);
            this.inventory.setItem(4, stack);
            this.inventory.setChanged();
        }
    }

    @Nullable
    public Item getEquipmentForArmorSlot(EquipmentSlot pSlot, int pChance) {
        Item item;
        switch (this.getGender().getId()) {
            case 0: {
                switch (pSlot) {
                    case HEAD -> item = Items.LEATHER_HELMET;
                    case CHEST -> item = RPItems.LEATHER_CHESTPLATE.get();
                    case LEGS -> item = RPItems.LEATHER_LEGGINGS.get();
                    case FEET ->
                            item = pChance == 2 ? Items.LEATHER_BOOTS : (pChance == 4 ? Items.CHAINMAIL_BOOTS : null);
                    default -> item = null;
                }
            }
            case 1: {
                switch (pSlot) {
                    case HEAD -> item = Items.LEATHER_HELMET;
                    case CHEST -> item = RPItems.JACKET_ARMOR.get();
                    case LEGS -> item = RPItems.LEATHER_WOMAN_LEGGINGS.get();
                    case FEET -> item = RPItems.SANDALS.get();
                    default -> item = null;
                }
            }
            case 2: {
                switch (pSlot) {
                    case HEAD -> item = Items.LEATHER_HELMET;
                    case CHEST -> item = RPItems.KIMONO.get();
                    case LEGS -> item = RPItems.YUKATA.get();
                    case FEET -> item = RPItems.SANDALS.get();
                    default -> item = null;
                }
            }
            default:
                item = null;
        }

        return item;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return SoundEvents.VILLAGER_NO;
        } else {
            return this.isBaby() ? SoundEvents.PIGLIN_AMBIENT : SoundEvents.PIGLIN_BRUTE_AMBIENT;
        }
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.VILLAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    public SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isSleeping()) {
            event.getController().setAnimation((new AnimationBuilder()).addAnimation("animation.ogre.sleep", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else if (this.shouldSwim()) {
            event.getController().setAnimation((new AnimationBuilder()).addAnimation("animation.ogre.swim", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isInSittingPose()) {
            event.getController().setAnimation((new AnimationBuilder()).addAnimation("animation.ogre.sitting", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else {
            if (event.isMoving()) {
                if (this.isOnGround() && this.isAngry()) {
                    event.getController().setAnimation((new AnimationBuilder()).addAnimation("animation.ogre.run", EDefaultLoopTypes.LOOP));
                } else if (this.isOnGround() || !this.isInFluidType()) {
                    event.getController().setAnimation((new AnimationBuilder()).addAnimation("animation.ogre.walk", EDefaultLoopTypes.LOOP));
                }
            } else {
                event.getController().setAnimation((new AnimationBuilder()).addAnimation("animation.ogre.idle", EDefaultLoopTypes.LOOP));
            }

            return PlayState.CONTINUE;
        }
    }

    private <E extends IAnimatable> PlayState miscPredicate(AnimationEvent<E> event) {
        if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            if (this.getMiscAnimation() == 1) {
                event.getController().setAnimation((new AnimationBuilder()).addAnimation("animation.ogre.attack", EDefaultLoopTypes.PLAY_ONCE));
            } else if (this.getMiscAnimation() == 2) {
                event.getController().setAnimation((new AnimationBuilder()).addAnimation("animation.ogre.shield", EDefaultLoopTypes.PLAY_ONCE));
            } else if (this.getMiscAnimation() == 3) {
                event.getController().setAnimation((new AnimationBuilder()).addAnimation("animation.ogre.crossbow", EDefaultLoopTypes.PLAY_ONCE));
            } else if (this.getMiscAnimation() == 4 || this.isUsingItem() && this.isSpearType(this.getUseItem())) {
                event.getController().setAnimation((new AnimationBuilder()).addAnimation("animation.ogre.spear", EDefaultLoopTypes.PLAY_ONCE));
            }
        }

        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0.0F, this::predicate));
        animationData.addAnimationController(new AnimationController<>(this, "miscController", 0.0F, this::miscPredicate));

    }

    public AnimationFactory getFactory() {
        return this.factory;
    }

    public void setCurrentEvolutionState(int state) {
        this.entityData.set(EVOLVE_STATE, 1);
    }

    public Gender getGender() {
        return Gender.byId(this.entityData.get(GENDER));
    }

    public void setGender(int gender) {
        this.entityData.set(GENDER, gender);
    }

    public OgreVariant.Style getStyle() {
        return OgreVariant.Style.byId(this.entityData.get(STYLE));
    }

    public void setStyle(int style) {
        this.entityData.set(STYLE, style);
    }

    protected void hurtCurrentlyUsedShield(float damage) {
        if (this.useItem.canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK)) {
            if (damage >= 3.0F) {
                int i = 1 + Mth.floor(damage);
                InteractionHand hand = this.getUsedItemHand();
                this.useItem.hurtAndBreak(i, this, (entity) -> entity.broadcastBreakEvent(hand));
                if (this.useItem.isEmpty()) {
                    if (hand == InteractionHand.MAIN_HAND) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                    this.useItem = ItemStack.EMPTY;
                    this.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + this.level.random.nextFloat() * 0.4F);
                }
            }
        }
    }

    private void randomTexture() {
        this.setGender(this.random.nextInt(3));
        this.setStyle(this.random.nextInt(3));
    }

    public float getScale() {
        float multiplier = this.isKijin() ? 1.3333334F : 1.0F;
        return multiplier * (this.isBaby() ? 0.5F : 1.0F);
    }

    public boolean isKijin() {
        return this.getCurrentEvolutionState() >= 1;
    }

    public int getMiscAnimation() {
        return this.entityData.get(MISC_ANIMATION);
    }

    public class EvolvingLookControl extends TensuraTamableEntity.SleepLookControl {
        public EvolvingLookControl() {
            super();
        }

        public void tick() {
            if (OgreEntity.this.getEvolving() <= 0) {
                super.tick();
            }

        }
    }

    public class EvolvingMoveControl extends TensuraTamableEntity.SleepMoveControl {
        public EvolvingMoveControl() {
            super();
        }

        public void tick() {
            if (OgreEntity.this.getEvolving() <= 0) {
                super.tick();
            }

        }
    }

}
