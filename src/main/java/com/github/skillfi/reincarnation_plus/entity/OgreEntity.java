package com.github.skillfi.reincarnation_plus.entity;

import com.github.manasmods.manascore.api.skills.ManasSkill;
import com.github.manasmods.manascore.api.skills.SkillAPI;
import com.github.manasmods.manascore.api.skills.capability.SkillStorage;
import com.github.manasmods.tensura.api.entity.ai.CrossbowAttackGoal;
import com.github.manasmods.tensura.api.entity.ai.TamableFollowParentGoal;
import com.github.manasmods.tensura.api.entity.ai.WanderingFollowOwnerGoal;
import com.github.manasmods.tensura.api.entity.controller.FlightMoveController;
import com.github.manasmods.tensura.api.entity.navigator.StraightFlightNavigator;
import com.github.manasmods.tensura.api.entity.subclass.IRanking;
import com.github.manasmods.tensura.entity.template.HumanoidNPCEntity;
import com.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import com.github.manasmods.tensura.registry.skill.CommonSkills;
import com.github.manasmods.tensura.registry.skill.ExtraSkills;
import com.github.manasmods.tensura.registry.skill.UniqueSkills;
import com.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.entity.variant.OgreVariant;
import com.github.skillfi.reincarnation_plus.entity.variant.OgreVariant.Gender;
import com.github.skillfi.reincarnation_plus.init.RPEntities;
import com.github.skillfi.reincarnation_plus.init.RPItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;
import org.stringtemplate.v4.ST;
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
    public static final RPEntitiesStats ONI = RPEntitiesStats.Oni;
    public static final RPEntitiesStats DIVINE_ONI = RPEntitiesStats.DivineOni;
    public static final RPEntitiesStats WICKED_ONI = RPEntitiesStats.WickedOni;
    public static final RPEntitiesStats divineFighter = RPEntitiesStats.DivineFighter;
    private static final EntityDataAccessor<Integer> MISC_ANIMATION;
    private static final EntityDataAccessor<Integer> OGRE_VARIANT = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> EVOLUTION_STATE;
    private static final EntityDataAccessor<Integer> GENDER;
    private static final EntityDataAccessor<Integer> STYLE;
    private static final EntityDataAccessor<Integer> EVOLVE_STATE = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);

    static {
        EVOLUTION_STATE = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        GENDER = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        STYLE = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        MISC_ANIMATION = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
    }

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public boolean prevSwim = false;

    public SimpleContainer ogreInventory = new SimpleContainer(6);

    public OgreEntity(EntityType<? extends OgreEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 5;
        this.maxUpStep = 1.0F;
        this.switchNavigator(false);
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).
                add(Attributes.MAX_HEALTH, (OGRE.getHP())).
                add(Attributes.ARMOR, OGRE.getArmor()).
                add(Attributes.ATTACK_DAMAGE, 3).
                add(Attributes.FOLLOW_RANGE, 16).build();
    }

    protected void switchNavigator(boolean onLand) {
        if (this.isKijin() || this.isOni() || this.isDivineOni() || this.isWickedOni() || this.isDivineFighter()) {
            if (!onLand && !this.isSleeping()) {
                this.moveControl = new FlightMoveController(this, 0.7F, true);
                this.navigation = new StraightFlightNavigator(this, this.level);
            } else {
//                this.moveControl = new TensuraTamableEntity.SleepMoveControl(this);
                this.navigation = new GroundPathNavigation(this, this.level);
            }

        }
    }

    public void tick() {
        super.tick();
        if (this.prevSwim != this.isInFluidType() && !this.isOnGround()) {
            this.refreshDimensions();
            this.prevSwim = this.isInFluidType() && !this.isOnGround();
        }

//        this.miscAnimationHandler();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new HumanoidNPCEntity.EatingItemGoal(this, (entity) -> this.shouldHeal(), 3.0F));
        this.goalSelector.addGoal(3, new CrossbowAttackGoal(this, 1.2, 20.0F));
        this.goalSelector.addGoal(3, new RangedBowAttackGoal(this, 1.0F, 20, 20.0F));
        this.goalSelector.addGoal(3, new HumanoidNPCEntity.SpearTypeAttackGoal(this, 1.0F, 20, 20.0F) {
            public boolean canUse() {
                OgreEntity ogre = OgreEntity.this;
                LivingEntity target = ogre.getTarget();
                if (target == null) {
                    return false;
                } else {
                    return (ogre.isTame() || !(target.getY() - ogre.getY() < (double) 5.0F)) && super.canUse();
                }
            }
        });
//        this.goalSelector.addGoal(3, new HumanoidNPCEntity.NPCMeleeAttackGoal(this, this, (double)2.0F, true));
        this.goalSelector.addGoal(4, new WanderingFollowOwnerGoal(this, 1.5F, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.2, OgreEntity.class));
        this.goalSelector.addGoal(6, new TamableFollowParentGoal(this, 1.5F));
        this.goalSelector.addGoal(7, new TensuraTamableEntity.WanderAroundPosGoal(this));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(1, new TensuraTamableEntity.TensuraOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new TensuraTamableEntity.TensuraOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new TensuraTamableEntity.TensuraHurtByTargetGoal(this, OgreEntity.class)).setAlertOthers());
//        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal(this, true));
    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MISC_ANIMATION, 0);
        this.entityData.define(EVOLUTION_STATE, 0);
        this.entityData.define(EVOLVE_STATE, 0);
        this.entityData.define(OGRE_VARIANT, 0);
        this.entityData.define(GENDER, 0);
        this.entityData.define(STYLE, 0);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setCurrentEvolutionState(compound.getInt("EvoState"));
        this.entityData.set(MISC_ANIMATION, compound.getInt("MiscAnimation"));
        this.entityData.set(GENDER, compound.getInt("Gender"));
        this.entityData.set(STYLE, compound.getInt("Style"));
        this.entityData.set(EVOLVE_STATE, compound.getInt("Evolution"));
    }


    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("MiscAnimation", this.getMiscAnimation());
        compoundTag.put("Inventory", this.ogreInventory.createTag());
        compoundTag.putInt("EvoState", this.getCurrentEvolutionState());
        compoundTag.putInt("Gender", this.entityData.get(GENDER));
        compoundTag.putInt("Style", this.entityData.get(STYLE));
        compoundTag.putInt("Evolution", this.getEvolutionState().getId());
    }

    public int getCurrentEvolutionState() {
        return this.entityData.get(EVOLUTION_STATE);
    }

    public void setCurrentEvolutionState(int state) {
        this.entityData.set(EVOLUTION_STATE, state);
        this.entityData.set(EVOLVE_STATE, 1);
    }

    public OgreVariant.Skin getEvolutionState() {
        return OgreVariant.Skin.byId(this.entityData.get(EVOLVE_STATE));
    }

    public int getMaxEvolutionState() {
        return 5;
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

    public void setChargingCrossbow(boolean pIsCharging) {
        super.setChargingCrossbow(pIsCharging);
        if (pIsCharging) {
            this.setMiscAnimation(3);
        }

    }

    public boolean canMate(Animal pOtherAnimal) {
        if (pOtherAnimal == this) {
            return false;
        } else if (pOtherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && pOtherAnimal.isInLove() && ((OgreEntity) pOtherAnimal).getGender() != this.getGender();
        }
    }

    public boolean shouldFollow() {
        return !this.isOrderedToSit() && !this.isWandering() && (this.getTarget() == null || !this.getTarget().isAlive());
    }

    public boolean canSleep() {
        return true;
    }

    public void followEntity(TamableAnimal animal, LivingEntity owner, double followSpeed) {
        if (this.distanceTo(owner) > 5.0F) {
            this.getMoveControl().setWantedPosition(owner.getX(), owner.getY() + (double) owner.getBbHeight(), owner.getZ(), followSpeed);
        } else {
            this.getNavigation().moveTo(owner, followSpeed);
        }

    }

    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        OgreEntity baby = (OgreEntity) ((EntityType) RPEntities.OGRE.get()).create(pLevel);
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
            } if (this.isDivineFighter() && ((OgreEntity) pOtherParent).isDivineFighter()) {
                baby.evolve();
            } if (this.isOni() && ((OgreEntity) pOtherParent).isOni()) {
                baby.evolve();
            } if (this.isDivineOni() && ((OgreEntity) pOtherParent).isDivineOni()) {
                baby.evolve();
            } if (this.isWickedOni() && ((OgreEntity) pOtherParent).isWickedOni()) {
                baby.evolve();
            }

            return baby;
        }
    }

    @Nullable
    public Item getEquipmentForArmorSlot(EquipmentSlot pSlot, int pChance) {
        Item var10000;
        switch (this.getGender().getId()) {
            case 0: {
                switch (pSlot) {
                    case HEAD -> var10000 = pChance == 2 ? Items.LEATHER_HELMET : null;
                    case CHEST ->
                            var10000 = RPItems.LEATHER_CHESTPLATE.get();
                    case LEGS ->
                            var10000 = RPItems.LEATHER_LEGGINGS.get();
                    case FEET ->
                            var10000 = pChance == 2 ? Items.LEATHER_BOOTS : (pChance == 4 ? Items.CHAINMAIL_BOOTS : null);
                    default -> var10000 = null;
                }
            }
            case 1: {
                switch (pSlot) {
                    case HEAD -> var10000 = pChance == 2 ? Items.LEATHER_HELMET : null;
                    case CHEST ->
                            var10000 = RPItems.JACKET_ARMOR.get();
                    case LEGS ->
                            var10000 = RPItems.LEATHER_WOMAN_LEGGINGS.get();
                    case FEET ->
                            var10000 = RPItems.SANDALS.get();
                    default -> var10000 = null;
                }
            }
            case 2: {
                switch (pSlot) {
                    case HEAD -> var10000 = pChance == 2 ? Items.LEATHER_HELMET : null;
                    case CHEST ->
                            var10000 = RPItems.KIMONO.get();
                    case LEGS ->
                            var10000 = RPItems.YUKATA.get();
                    case FEET ->
                            var10000 = RPItems.SANDALS.get();
                    default -> var10000 = null;
                }
            }
            default:
                var10000 = null;
        }

        return var10000;
    }

    public void gainAttackDamage(LivingEntity entity, double amount) {
        AttributeInstance damage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (damage != null) {
            damage.setBaseValue(amount);
        }

    }

    public void gainMovementSpeed(LivingEntity entity, double amount) {
        AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            speed.setBaseValue(amount);
        }

    }

    public void gainSwimSpeed(LivingEntity entity, double amount) {
        AttributeInstance swimSpeed = entity.getAttribute(ForgeMod.SWIM_SPEED.get());
        if (swimSpeed != null) {
            swimSpeed.setBaseValue(amount);
        }

    }

    public void gainJumpStrength(LivingEntity entity, double amount) {
        AttributeInstance jump = entity.getAttribute(Attributes.JUMP_STRENGTH);
        if (jump != null) {
            jump.setBaseValue(amount);
        }

    }

    public void evolve() {
        int current = this.getCurrentEvolutionState();
        if (current < this.getMaxEvolutionState()) {
            this.setCurrentEvolutionState(current + 1);
            SkillStorage storage = SkillAPI.getSkillsFrom(this);
            switch (current) {
                case 1: {
                    storage.learnSkill((ManasSkill) ExtraSkills.BLACK_FLAME.get());
                    storage.learnSkill((ManasSkill) UniqueSkills.CHEF.get());
                    this.gainMovementSpeed(this, KIJIN.getMovementSpeed());
                    this.gainSwimSpeed(this, 3.0F);
                    this.gainMaxHealth(this, KIJIN.getHP());
                    this.gainAttackDamage(this, KIJIN.getATTACK_DAMAGE());
                }
                case 2: {
                    storage.learnSkill((ManasSkill) ExtraSkills.STRENGTHEN_BODY.get());
                    this.gainMovementSpeed(this, ONI.getMovementSpeed());
                    this.gainSwimSpeed(this, 6.0F);
                    this.gainMaxHealth(this, ONI.getHP());
                    this.gainAttackDamage(this, ONI.getATTACK_DAMAGE());
                }
                case 3: {
                    this.gainMovementSpeed(this, DIVINE_ONI.getMovementSpeed());
                    this.gainSwimSpeed(this, 9.0F);
                    this.gainMaxHealth(this, DIVINE_ONI.getHP());
                    this.gainAttackDamage(this, DIVINE_ONI.getATTACK_DAMAGE());
                }
                case 4: {
                    this.gainMovementSpeed(this, WICKED_ONI.getMovementSpeed());
                    this.gainSwimSpeed(this, 12.0F);
                    this.gainMaxHealth(this, WICKED_ONI.getHP());
                    this.gainAttackDamage(this, WICKED_ONI.getATTACK_DAMAGE());
                }
                case 5: {
                    storage.learnSkill((ManasSkill) ExtraSkills.ULTRASPEED_REGENERATION.get());
                    storage.learnSkill((ManasSkill) UniqueSkills.MARTIAL_MASTER.get());
                    this.gainMovementSpeed(this, divineFighter.getMovementSpeed());
                    this.gainSwimSpeed(this, 15.0F);
                    this.gainMaxHealth(this, divineFighter.getHP());
                    this.gainAttackDamage(this, divineFighter.getATTACK_DAMAGE());
                }
            }


        }

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

    public Gender getGender() {
        return Gender.byId(this.entityData.get(GENDER));
    }

    public OgreVariant.Style getStyle(){
        return OgreVariant.Style.byId(this.entityData.get(STYLE));
    }

    public void setGender(int gender) {
        this.entityData.set(GENDER, gender);
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

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.populateDefaultEquipmentSlots(this.random, pDifficulty);
        this.randomTexture();
        RPMod.LOGGER.debug("Ogre spawned at " + this.blockPosition().getX() + ", " + this.blockPosition().getY() + ", " + this.blockPosition().getZ());
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    private void randomTexture() {
        this.setGender(this.random.nextInt(3));
        this.setStyle(this.random.nextInt(3));
    }

    public float getScale() {
        float multiplier = this.isKijin() || this.isOni() || this.isDivineOni() || this.isWickedOni() || this.isDivineFighter() ? 1.3333334F : 1.0F;
        return multiplier * (this.isBaby() ? 0.5F : 1.0F);
    }

    public boolean isKijin() {
        return this.getCurrentEvolutionState() >= 1;
    }

    public boolean isOni() {
        return this.getCurrentEvolutionState() >= 2;
    }
    public boolean isDivineOni() {
        return this.getCurrentEvolutionState() >= 3;
    }
    public boolean isWickedOni() {
        return this.getCurrentEvolutionState() >= 4;
    }
    public boolean isDivineFighter() {
        return this.getCurrentEvolutionState() >= 5;
    }

    public int getMiscAnimation() {
        return this.entityData.get(MISC_ANIMATION);
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
        animationData.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
        animationData.addAnimationController(new AnimationController(this, "miscController", 0.0F, this::miscPredicate));

    }

    public AnimationFactory getFactory() {
        return this.factory;
    }
}
