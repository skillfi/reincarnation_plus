package com.github.skillfi.reincarnation_plus.core.entity;

import com.github.manasmods.manascore.api.skills.ManasSkill;
import com.github.manasmods.manascore.api.skills.ManasSkillInstance;
import com.github.manasmods.manascore.api.skills.SkillAPI;
import com.github.manasmods.manascore.api.skills.capability.SkillStorage;
import com.github.manasmods.tensura.api.entity.ai.CrossbowAttackGoal;
import com.github.manasmods.tensura.api.entity.ai.TamableFollowParentGoal;
import com.github.manasmods.tensura.api.entity.ai.WanderingFollowOwnerGoal;
import com.github.manasmods.tensura.client.particle.TensuraParticleHelper;
import com.github.manasmods.tensura.config.SpawnRateConfig;
import com.github.manasmods.tensura.entity.template.HumanoidNPCEntity;
import com.github.manasmods.tensura.race.RaceHelper;
import com.github.manasmods.tensura.registry.attribute.TensuraAttributeRegistry;
import com.github.manasmods.tensura.registry.items.TensuraToolItems;
import com.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import lombok.Getter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class KijinEntity extends OgreEntity implements IAnimatable {

    private static final EntityDataAccessor<Integer> MISC_ANIMATION;
    private static final EntityDataAccessor<Integer> EVOLVING;
    private static final EntityDataAccessor<Integer> EVOLVE_STATE;
    private static ArrayList<ManasSkill> skills= new ArrayList<ManasSkill>();
    private static ArrayList<ManasSkill> instances= new ArrayList<ManasSkill>();
    private static final EntityDataAccessor<Boolean> CAN_EVOLVE;
    private Vec3 previousPosition = Vec3.ZERO;

    static {
        EVOLVING = SynchedEntityData.defineId(KijinEntity.class, EntityDataSerializers.INT);
        MISC_ANIMATION = SynchedEntityData.defineId(KijinEntity.class, EntityDataSerializers.INT);
        EVOLVE_STATE = SynchedEntityData.defineId(KijinEntity.class, EntityDataSerializers.INT);
        CAN_EVOLVE = SynchedEntityData.defineId(KijinEntity.class, EntityDataSerializers.BOOLEAN);
    }

    @Getter
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public int miscAnimationTicks = 0;

    public KijinEntity(EntityType<? extends KijinEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 10;
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes().
                add(Attributes.MAX_HEALTH, (KIJIN.getHP())).
                add(Attributes.ARMOR, KIJIN.getArmor()*2).
                add(Attributes.ATTACK_DAMAGE, KIJIN.getATTACK_DAMAGE()).
                add(Attributes.ATTACK_SPEED, KIJIN.getRace().getBaseAttackSpeed()).
                add(Attributes.MOVEMENT_SPEED, KIJIN.getRace().getMovementSpeed()).
                add((Attribute) TensuraAttributeRegistry.MAX_MAGICULE.get(), KIJIN.getRace().getMaxBaseMagicule()).
                add(Attributes.FOLLOW_RANGE, 16).
                add(TensuraAttributeRegistry.MAX_SPIRITUAL_HEALTH.get(), KIJIN.getSHP()).
                add(ForgeMod.ATTACK_RANGE.get(), 1.0).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new EatingItemGoal(this, (entity) -> {
            return this.shouldHeal();
        }, 3.0F));
        this.goalSelector.addGoal(3, new CrossbowAttackGoal(this, 1.2, 20.0F));
        this.goalSelector.addGoal(3, new RangedBowAttackGoal(this, 1.0, 20, 20.0F));
        this.goalSelector.addGoal(3, new SpearTypeAttackGoal(this, 1.0, 20, 20.0F) {
            public boolean canUse() {
                KijinEntity ogre = KijinEntity.this;
                LivingEntity target = ogre.getTarget();
                if (target == null) {
                    return false;
                } else {
                    return (ogre.isTame() || !(target.getY() - ogre.getY() < 5.0)) && super.canUse();
                }
            }
        });
        this.goalSelector.addGoal(3, new KijinAttackGoal());
        this.goalSelector.addGoal(4, new WanderingFollowOwnerGoal(this, 1.5, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.2, KijinEntity.class));
        this.goalSelector.addGoal(6, new TamableFollowParentGoal(this, 1.5));
        this.goalSelector.addGoal(7, new WanderAroundPosGoal(this));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.2, KijinEntity.class));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(1, new TensuraOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new TensuraOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new TensuraHurtByTargetGoal(this, KijinEntity.class)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (entity) -> entity instanceof LivingEntity && this.isAngryAt(entity)));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MISC_ANIMATION, 0);
        this.entityData.define(EVOLVING, 4);
        this.entityData.define(EVOLVE_STATE, 1);
        this.entityData.define(CAN_EVOLVE, true);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("MiscAnimation", this.getMiscAnimation());
        compoundTag.putInt("EvoState", this.getCurrentEvolutionState());
        compoundTag.putInt("Evolution", this.getSkin().getId());
        compoundTag.putInt("Evolving", this.getEvolving());
        compoundTag.putBoolean("CanEvolve", this.canEvolve());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setEvolving(compound.getInt("Evolving"));
        this.entityData.set(MISC_ANIMATION, compound.getInt("MiscAnimation"));
        this.entityData.set(EVOLVE_STATE, compound.getInt("Evolution"));
        this.entityData.set(CAN_EVOLVE, compound.getBoolean("CanEvolve"));
    }

    public ArrayList<ManasSkill> getSkills(){
        return skills;
    }

    public ArrayList<ManasSkill> getInstances(){
        return instances;
    }

    public void learnSkill(ManasSkill e){
        SkillStorage storage = SkillAPI.getSkillsFrom(this);
        storage.learnSkill(e);
    }

    public void activateInstance(ManasSkill skill){
        SkillStorage storage = SkillAPI.getSkillsFrom(this);
        ManasSkillInstance instance = new ManasSkillInstance(skill);
        storage.learnSkill(instance);
        instance.setToggled(true);
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

    public @NotNull AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        KijinEntity baby = (KijinEntity) ((EntityType<?>) ReiEntities.KIJIN.get()).create(pLevel);
        if (baby == null) {
            return null;
        } else {
            UUID uuid = this.getOwnerUUID();
            if (uuid != null) {
                baby.setOwnerUUID(uuid);
                baby.setTame(true);
                baby.setCanEvolve(true);
                baby.setSkin(true);
            }

            baby.randomTexture();
            if (this.isOni() && ((KijinEntity) pOtherParent).isOni()) {
                getGeneFromParents(baby,pOtherParent);
            }

            return baby;
        }
    }

    private void getGeneFromParents(KijinEntity baby, AgeableMob pOtherParent) {
        // Каст батьків до KijinEntity
        KijinEntity parentOther = (KijinEntity) pOtherParent;

        // Активуємо всі інстанси обох батьків
        Stream.concat(this.getInstances().stream(), parentOther.getInstances().stream())
                .forEach(baby::activateInstance);

        // Випадкові навички від обох батьків
        baby.learnSkill(this.getRandomSkill(this.getSkills()));
        baby.learnSkill(this.getRandomSkill(parentOther.getSkills()));

        // Випадкова навичка героя
        List<List<ManasSkill>> parentHeroes = List.of(this.getHeroSkills(), parentOther.getHeroSkills());
        baby.learnSkill(this.getRandomSkill(this.getRandomList(parentHeroes)));

        // Випадковий інстанс героя
        List<List<ManasSkill>> parentHeroInstances = List.of(this.getHeroInstances(), parentOther.getHeroInstances());
        baby.activateInstance(this.getRandomSkill(this.getRandomList(parentHeroInstances)));
    }

    // Допоміжний метод для отримання випадкового елемента списку
    private <T> T getRandomSkill(List<T> list) {
        return list.get(this.random.nextInt(list.size()));
    }

    // Допоміжний метод для отримання випадкового списку зі списку списків
    private <T> List<T> getRandomList(List<List<T>> lists) {
        return lists.get(this.random.nextInt(lists.size()));
    }


    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.CACTUS || super.isInvulnerableTo(source);
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



    public boolean doHurtTarget(Entity pEntity) {
        boolean flag = super.doHurtTarget(pEntity);
        if (flag && this.getMiscAnimation() == 0) {
            this.setMiscAnimation(1);
        }

        return flag;
    }

    public void tick() {
        super.tick();
        if (this.prevSwim != this.isInFluidType() && !this.isOnGround()) {
            this.refreshDimensions();
            this.prevSwim = this.isInFluidType() && !this.isOnGround();
        }

        this.miscAnimationHandler();
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

    public boolean canMate(Animal pOtherAnimal) {
        if (pOtherAnimal == this) {
            return false;
        } else if (pOtherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && pOtherAnimal.isInLove() && ((KijinEntity) pOtherAnimal).getMateGender() != this.getMateGender();
        }
    }

    public EntityDimensions getDimensions(Pose pPose) {
        EntityDimensions entitydimensions = super.getDimensions(pPose);
        if (this.isSleeping()) {
            return entitydimensions.scale(1.0F, 0.5F);
        } else if (this.shouldSwim()) {
            return entitydimensions.scale(1.0F, 0.25F);
        } else if (this.getClass() == KijinEntity.class && (this.isOrderedToSit() || this.isInSittingPose())) {
            return entitydimensions.scale(1.0F, 0.75F);
        } else if (this.getClass() == DivineFighterEntity.class) {
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


    public void evolving() {
        // Серіалізація та створення нового персонажа
        Level level = this.getLevel();
        RaceHelper.applyBaseAttribute(OniEntity.setAttributes(), this, true);
        RaceHelper.updateSpiritualHP(this);
        RaceHelper.updateEntityEPCount(this);
        this.setHealth(this.getMaxHealth());

        // Додавання до рівня та програвання звуку
        level.playSound(null, this.blockPosition(),
                SoundEvents.VILLAGER_CELEBRATE,
                SoundSource.PLAYERS, 1.0F, 1.0F);
        TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.FLASH);
    }


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
        return SpawnRateConfig.rollSpawn(SpawnRateConfig.INSTANCE.orcSpawnRate.get(), this.getRandom(), pSpawnReason) && super.checkSpawnRules(pLevel, pSpawnReason);
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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isSleeping()) {
            event.getController().setAnimation((new AnimationBuilder()).addAnimation("sleep", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else if (this.shouldSwim()) {
            event.getController().setAnimation((new AnimationBuilder()).addAnimation("swim", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isInSittingPose()) {
            event.getController().setAnimation((new AnimationBuilder()).addAnimation("sitting", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else {
            if (event.isMoving()) {
                if (this.isOnGround() && this.isAngry()) {
                    event.getController().setAnimation((new AnimationBuilder()).addAnimation("run", EDefaultLoopTypes.LOOP));
                } else if (this.isOnGround() || !this.isInFluidType()) {
                    event.getController().setAnimation((new AnimationBuilder()).addAnimation("walk", EDefaultLoopTypes.LOOP));
                }
            } else {
                event.getController().setAnimation((new AnimationBuilder()).addAnimation("idle", EDefaultLoopTypes.LOOP));
            }

            return PlayState.CONTINUE;
        }
    }

    private <E extends IAnimatable> PlayState miscPredicate(AnimationEvent<E> event) {
        if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            if (this.getMiscAnimation() == 1) {
                event.getController().setAnimation((new AnimationBuilder()).addAnimation("attack", EDefaultLoopTypes.PLAY_ONCE));
            } else if (this.getMiscAnimation() == 2) {
                event.getController().setAnimation((new AnimationBuilder()).addAnimation("shield", EDefaultLoopTypes.PLAY_ONCE));
            } else if (this.getMiscAnimation() == 3) {
                event.getController().setAnimation((new AnimationBuilder()).addAnimation("crossbow", EDefaultLoopTypes.PLAY_ONCE));
            } else if (this.getMiscAnimation() == 4 || this.isUsingItem() && this.isSpearType(this.getUseItem())) {
                event.getController().setAnimation((new AnimationBuilder()).addAnimation("spear", EDefaultLoopTypes.PLAY_ONCE));
            }
        }

        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
        data.addAnimationController(new AnimationController(this, "miscController", 0.0F, this::miscPredicate));
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    public void setCurrentEvolutionState(int state) {
        this.entityData.set(EVOLVE_STATE, 1);
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

    public int getMiscAnimation() {
        return this.entityData.get(MISC_ANIMATION);
    }

    class KijinAttackGoal extends HumanoidNPCEntity.NPCMeleeAttackGoal {
        private final KijinEntity kijin = KijinEntity.this;

        public KijinAttackGoal() {
            super(KijinEntity.this, (double)2.5F, true);
        }

        public boolean canUse() {
            return this.kijin.isOrderedToSit() ? false : super.canUse();
        }

        public boolean canContinueToUse() {
            return this.kijin.isOrderedToSit() ? false : super.canContinueToUse();
        }

        public void tick() {
            if (this.kijin.getMiscAnimation() == 0) {
                super.tick();
            }

        }

        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
            double distance = this.getAttackReachSqr(pEnemy);
            if (this.kijin.getMiscAnimation() == 0) {
                int randomAttack = this.randomAttack(pDistToEnemySqr, pEnemy);
                double var10000;
                switch (randomAttack) {
                    case 2:
                        this.kijin.getNavigation().stop();
                        var10000 = (double)225.0F;
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        this.kijin.getNavigation().stop();
                        var10000 = (double)3600.0F;
                        break;
                    default:
                        var10000 = distance;
                }

                double attackRange = var10000;
                if (pDistToEnemySqr <= attackRange && this.isTimeToAttack()) {
                    this.resetAttackCooldown();
                    this.kijin.setMiscAnimation(randomAttack);
                    if (randomAttack == 2) {
                        this.kijin.doHurtTarget(pEnemy);
                    } else if (randomAttack == 5) {
                        this.kijin.stopRiding();
                        this.kijin.moveTo(pEnemy.position());
                        TensuraParticleHelper.addServerParticlesAroundSelf(this.kijin, ParticleTypes.SQUID_INK);
                        this.kijin.hurtMarked = true;
                    }
                }
            }

        }

        protected int randomAttack(double distance, LivingEntity entity) {
            if (this.kijin.isBaby()) {
                return 2;
            } else {
                if (this.kijin.random.nextInt(10) == 1) {
                    if (entity.isOnGround() && this.kijin.isOnGround() && distance >= (double)200.0F && this.kijin.random.nextInt(10) == 1 && this.kijin.getShadowMotion() != null) {
                        return 10;
                    }
                    if (entity.isOnGround() && this.kijin.isOnGround() && distance >= (double)200.0F && this.kijin.random.nextInt(10) == 1 && this.kijin.getSteelStrenght() != null) {
                        return 9;
                    }
                    if (entity.isOnGround() && this.kijin.isOnGround() && distance >= (double)200.0F && this.kijin.random.nextInt(10) == 1 && this.kijin.getDemonLordHaki() != null) {
                        return 8;
                    }
                    if (entity.isOnGround() && this.kijin.isOnGround() && distance >= (double)200.0F && this.kijin.random.nextInt(10) == 1 && this.kijin.getStrengthenBody() != null) {
                        return 7;
                    }if (entity.isOnGround() && this.kijin.isOnGround() && distance >= (double)200.0F && this.kijin.random.nextInt(10) == 1 && this.kijin.getMultiLayerBarrier() != null) {
                        return 6;
                    }if (entity.isOnGround() && this.kijin.isOnGround() && distance >= (double)200.0F && this.kijin.random.nextInt(10) == 1 && this.kijin.getBlackFlame() != null) {
                        return 5;
                    }

                    if ((distance >= (double)200.0F || this.kijin.random.nextInt(15) == 1) && this.kijin.getBodyDouble() != null) {
                        return 3;
                    }

                    if (distance >= (double)36.0F || this.kijin.random.nextInt(20) == 1) {
                        return 2;
                    }
                }

                return 1;
            }
        }

        protected double getAttackReachSqr(LivingEntity pAttackTarget) {
            return (double)(this.mob.getBbWidth() * this.mob.getBbWidth() * 3.0F + pAttackTarget.getBbWidth());
        }
    }
}
