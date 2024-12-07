package com.github.skillfi.reincarnation_plus.core.entity;

import com.github.manasmods.manascore.api.skills.ManasSkill;
import com.github.manasmods.manascore.api.skills.ManasSkillInstance;
import com.github.manasmods.manascore.api.skills.SkillAPI;
import com.github.manasmods.manascore.api.skills.capability.SkillStorage;
import com.github.manasmods.tensura.api.entity.ai.CrossbowAttackGoal;
import com.github.manasmods.tensura.api.entity.ai.TamableFollowParentGoal;
import com.github.manasmods.tensura.api.entity.ai.WanderingFollowOwnerGoal;
import com.github.manasmods.tensura.api.entity.subclass.IRanking;
import com.github.manasmods.tensura.capability.ep.TensuraEPCapability;
import com.github.manasmods.tensura.client.particle.TensuraParticleHelper;
import com.github.manasmods.tensura.config.SpawnRateConfig;
import com.github.manasmods.tensura.entity.template.HumanoidNPCEntity;
import com.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import com.github.manasmods.tensura.race.RaceHelper;
import com.github.manasmods.tensura.registry.attribute.TensuraAttributeRegistry;
import com.github.manasmods.tensura.registry.battlewill.MeleeArts;
import com.github.manasmods.tensura.registry.items.TensuraToolItems;
import com.github.manasmods.tensura.registry.skill.*;
import com.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.entity.variant.ogre.*;
import com.github.skillfi.reincarnation_plus.core.registry.ReiItems;
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
import net.minecraft.world.item.Item;
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
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class OgreEntity extends HumanoidNPCEntity implements IRanking, IAnimatable {

    //    Evolution
    public static final RPEntitiesStats OGRE = RPEntitiesStats.Ogre;
    public static final RPEntitiesStats KIJIN = RPEntitiesStats.Kijin;
    public static final RPEntitiesStats ONI = RPEntitiesStats.MysticOni;
    public static final RPEntitiesStats DIVINE_ONI = RPEntitiesStats.DivineOni;
    public static final RPEntitiesStats DIVINE_FIGHTER = RPEntitiesStats.DivineFighter;
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.STRING);
    public static ArrayList<ManasSkill> instances= new ArrayList<>();
    public static ArrayList<ManasSkill> heroSkills = new ArrayList<>();
    public static ArrayList<ManasSkill> heroInstances = new ArrayList<>();
    public static final double MOVEMENT_THRESHOLD = 0.01;
    //    Animations
    private static final EntityDataAccessor<Integer> MISC_ANIMATION;
    //    Evolving
    private static final EntityDataAccessor<Integer> EVOLVING;
    private static final EntityDataAccessor<Integer> KNOWN_HERO;
    private static final EntityDataAccessor<Boolean> CAN_EVOLVE;
    //    Mate
    private static final EntityDataAccessor<Integer> GENDER_FOR_MATE;
    //    Textures
    private static final EntityDataAccessor<Integer> FACE;
    private static final EntityDataAccessor<Integer> HAIR;
    private static final EntityDataAccessor<Integer> CLOTHING;
    private static final EntityDataAccessor<Boolean> SKIN;
    private static final EntityDataAccessor<Integer> GENDER;
    //    Skills
    public static ArrayList<ManasSkill> skills= new ArrayList<>();

    static {
        EVOLVING = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        KNOWN_HERO = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        GENDER_FOR_MATE = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        MISC_ANIMATION = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        CAN_EVOLVE = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.BOOLEAN);
        //        Textures
        SKIN = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.BOOLEAN);
        GENDER = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        FACE = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        HAIR = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
        CLOTHING = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
    }

    public boolean prevSwim = false;
    public int miscAnimationTicks = 0;
    private Vec3 previousPosition = Vec3.ZERO;


    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public OgreEntity(EntityType<? extends OgreEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 5;
        this.maxUpStep = 1.0F;
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes().
                add(Attributes.MAX_HEALTH, (OGRE.getHP())).
                add(Attributes.ATTACK_DAMAGE, OGRE.getATTACK_DAMAGE()).
                add(Attributes.ARMOR, OGRE.getArmor()).
                add(Attributes.FOLLOW_RANGE, 32.0F).
                add(Attributes.ATTACK_SPEED, OGRE.getRace().getBaseAttackSpeed()).
                add(Attributes.MOVEMENT_SPEED, 0.2F).
                add(TensuraAttributeRegistry.MAX_MAGICULE.get(), OGRE.getRace().getMaxBaseMagicule()).
                add((Attribute) TensuraAttributeRegistry.MAX_SPIRITUAL_HEALTH.get(), OGRE.getSHP()).
                add(ForgeMod.ATTACK_RANGE.get(), 1.0F).build();
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
        this.goalSelector.addGoal(3, new OgreAttackGoal());
        this.goalSelector.addGoal(4, new WanderingFollowOwnerGoal(this, 1.5, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(6, new TamableFollowParentGoal(this, 1.5));
        this.goalSelector.addGoal(7, new TensuraTamableEntity.WanderAroundPosGoal(this));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.2, OgreEntity.class));
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
        this.entityData.define(EVOLVING, 5);
        this.entityData.define(KNOWN_HERO, 0);
        this.entityData.define(GENDER_FOR_MATE, 0);
        this.entityData.define(CAN_EVOLVE, true);
        //        Textures
        this.entityData.define(GENDER, 0);
        this.entityData.define(SKIN, false);
        this.entityData.define(FACE, 0);
        this.entityData.define(HAIR, 0);
        this.entityData.define(CLOTHING, 0);
        this.entityData.define(ANIMATION, "undefined");
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("MiscAnimation", this.getMiscAnimation());
        compoundTag.putInt("EvoState", this.getCurrentEvolutionState());
        compoundTag.putInt("GenderForMate", this.entityData.get(GENDER_FOR_MATE));
        compoundTag.putInt("Evolving", this.getEvolving());
        compoundTag.putBoolean("CanEvolve", this.canEvolve());
        //        Textures
        compoundTag.putBoolean("Skin", this.getSkin().isEvolve());
        compoundTag.putInt("Gender", this.entityData.get(GENDER));
        compoundTag.putInt("Face", this.entityData.get(FACE));
        compoundTag.putInt("Hair", this.entityData.get(HAIR));
        compoundTag.putInt("Clothing", this.entityData.get(CLOTHING));
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setEvolving(compound.getInt("Evolving"));
        this.entityData.set(MISC_ANIMATION, compound.getInt("MiscAnimation"));
        this.entityData.set(GENDER_FOR_MATE, compound.getInt("GenderForMate"));
        this.entityData.set(CAN_EVOLVE, compound.getBoolean("CanEvolve"));
        //        Textures
        this.entityData.set(SKIN, compound.getBoolean("Skin"));
        this.entityData.set(GENDER, compound.getInt("Gender"));
        this.entityData.set(FACE, compound.getInt("Face"));
        this.entityData.set(HAIR, compound.getInt("Hair"));
        this.entityData.set(CLOTHING, compound.getInt("Clothing"));
    }

    /**
     Max Evolving {@link Evolving#DIVINE_FIGHTER} = 1
     <p>
     {@link Evolving#DIVINE_ONI} = 2
     <p>
     {@link Evolving#ONI} = 3
     <p>
     {@link Evolving#KIJIN} = 4
     <p>
     {@link Evolving#OGRE} = 5
     @return Evolving number
     **/
    public int getEvolving() {
        return this.entityData.get(EVOLVING);
    }


    /**
     * Max Evolving {@link Evolving#DIVINE_FIGHTER} = 0
     * <p>
     * {@link Evolving#DIVINE_ONI} = 2
     * <p>
     * {@link Evolving#ONI} = 3
     * <p>
     * {@link Evolving#KIJIN} = 4
     * <p>
     * {@link Evolving#OGRE} = 5
     *
     * @param tick
     */
    public void setEvolving(int tick) {
        this.entityData.set(EVOLVING, tick);
    }

    public void setCanEvolve(boolean evolve) {
        this.entityData.set(CAN_EVOLVE, evolve);
    }

    /** Available Heroes
     * <br>
     * Benimaru [0] - {@link KnownHeroes#BENIMARU}
     * <br>
     * Shouei [1] - {@link KnownHeroes#SOUEI}
     * <br>
     * Shuna [2] - {@link KnownHeroes#SHUNA}
     * <br>
     * Shion [3] - {@link KnownHeroes#SHION}
     * @return Hero {@link KnownHeroes}
     */
    public KnownHeroes getHero() {
        return KnownHeroes.byId(this.entityData.get(KNOWN_HERO));
    }

    public void setHero(int num) {
        this.entityData.set(KNOWN_HERO, num);
    }

    /**
     *
     * @return Texture {@link Skin}
     */
    public Skin getSkin() {
        return Skin.evolved(this.entityData.get(SKIN));
    }

    public void setSkin(boolean skin) {
        this.entityData.set(SKIN, skin);
    }

    public ArrayList<ManasSkill> getSkills(){
        return skills;
    }

    public void addSkills(ManasSkill e){
        skills.add(e);
    }

    public ArrayList<ManasSkill> getInstances(){
        return instances;
    }

    public ArrayList<ManasSkill> getHeroSkills(){
        return heroSkills;
    }

    public ArrayList<ManasSkill> getHeroInstances(){
        return heroInstances;
    }

    public Face getFace() {
        return Face.byId(this.entityData.get(FACE));
    }

    public void setFace(int face) {
        this.entityData.set(FACE, face);
    }

    public Hair getHair() {
        return Hair.byId(this.entityData.get(HAIR));
    }

    public void setHair(int hair) {
        this.entityData.set(HAIR, hair);
    }

    public Clothing getClothing() {
        return Clothing.byId(this.entityData.get(CLOTHING));
    }

    public void setClothing(int clothing) {
        this.entityData.set(CLOTHING, clothing);
    }

    public RPEntitiesStats getEvolvedStats(){
        int evolved_id = this.entityData.get(EVOLVING) - 1;
        return Evolving.byId(evolved_id).getStats();
    }


    public int getMaxEvolutionState() {
        return 1;
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
                baby.setSkin(true);
                baby.setCanEvolve(true);

            }

            baby.randomTexture();
            if (this.isKijin() && ((OgreEntity) pOtherParent).isKijin()) {
                getGeneFromParents(baby, pOtherParent);
            }

            return baby;
        }
    }

    private void getGeneFromParents(KijinEntity baby, AgeableMob pOtherParent) {
        // Каст батьків до OgreEntity
        OgreEntity parentOther = (OgreEntity) pOtherParent;

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


    public boolean isFood(ItemStack pStack) {
        return pStack.getItem().isEdible();
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.CACTUS || super.isInvulnerableTo(source);
    }

    public boolean canSleep() {
        return true;
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

    public int getAnimationTick(int miscAnimation) {
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

    public boolean canMate(Animal pOtherAnimal) {
        if (pOtherAnimal == this) {
            return false;
        } else if (pOtherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && pOtherAnimal.isInLove() && ((OgreEntity)pOtherAnimal).getMateGender() != this.getMateGender();
        }
    }

    /**
    Evolve Baby to {@link KijinEntity}
     */
    public void evolve() {
        this.gainSwimSpeed(this, 1.0F);
        if (this.canEvolve()){
            this.setCanEvolve(false);
            this.evolving();
        }
    }


    public EntityDimensions getDimensions(Pose pPose) {
        EntityDimensions entitydimensions = super.getDimensions(pPose);
        if (this.isSleeping()) {
            return entitydimensions.scale(1.0F, 0.5F);
        } else if (this.shouldSwim()) {
            return entitydimensions.scale(1.0F, 0.25F);
        } else {
            return entitydimensions.scale(1.0F, 0.75F);
        }
    }


    public void evolving() {
        int randomIndex = ThreadLocalRandom.current().nextInt(heroSkills.size());
        int instanceIndex = ThreadLocalRandom.current().nextInt(heroInstances.size());
        SkillStorage storage = SkillAPI.getSkillsFrom(this);

        storage.learnSkill(heroSkills.get(randomIndex));
        addSkills(heroSkills.get(randomIndex));
        ManasSkillInstance instance = new ManasSkillInstance(heroInstances.get(instanceIndex));
        instances.add(heroInstances.get(instanceIndex));
        storage.learnSkill(instance);
        instance.setToggled(true);
        heroSkills.remove(randomIndex);
        heroInstances.remove(instanceIndex);

        RaceHelper.applyBaseAttribute(KijinEntity.setAttributes(), this, true);
        RaceHelper.updateSpiritualHP(this);
        this.setHealth(this.getMaxHealth());
        TensuraEPCapability.setLivingEP(this, getEvolvedStats().getMinEP());
        RaceHelper.updateEntityEPCount(this);
        this.setSkin(true);
        TensuraParticleHelper.addServerParticlesAroundSelf(this, ParticleTypes.FLASH);
    }

    public boolean canEvolve(){
        return this.entityData.get(CAN_EVOLVE);
    }

    // Припустимо, що цей метод викликається для персонажа kijin
    public void addBenimaruSkills() {
        // Список усіх скілів
        heroSkills.add((ManasSkill) ExtraSkills.DEMON_LORD_HAKI.get());
        heroSkills.add((ManasSkill) ExtraSkills.MULTILAYER_BARRIER.get());
        heroSkills.add((ManasSkill) ExtraSkills.STRENGTHEN_BODY.get());
        heroSkills.add((ManasSkill) CommonSkills.THOUGHT_COMMUNICATION.get());
        heroSkills.add((ManasSkill) UniqueSkills.COMMANDER.get());
        heroSkills.add((ManasSkill) ExtraSkills.BLACK_FLAME.get());
        heroSkills.add((ManasSkill) ExtraSkills.FLAME_MANIPULATION.get());
        heroSkills.add((ManasSkill) ExtraSkills.FLAME_DOMINATION.get());
        heroSkills.add((ManasSkill) ExtraSkills.HAKI.get());
        heroSkills.add((ManasSkill) ExtraSkills.SPATIAL_MOTION.get());
        heroSkills.add((ManasSkill) IntrinsicSkills.FLAME_BREATH.get());
        heroInstances.add((ManasSkill) ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.get());
        heroInstances.add((ManasSkill) ResistanceSkills.HOLY_ATTACK_RESISTANCE.get());
        heroInstances.add((ManasSkill) ResistanceSkills.PAIN_NULLIFICATION.get());
        heroInstances.add((ManasSkill) ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.get());
        heroInstances.add((ManasSkill) ResistanceSkills.SPATIAL_ATTACK_RESISTANCE.get());
        heroInstances.add((ManasSkill) MeleeArts.OGRE_SWORD_GUILLOTINE.get());
    }

    public void addSoueiSkills(){
        heroSkills.add((ManasSkill) ExtraSkills.BODY_DOUBLE.get());
        heroSkills.add((ManasSkill) ExtraSkills.SHADOW_MOTION.get());
        heroSkills.add((ManasSkill) ExtraSkills.SPATIAL_MOTION.get());
        heroSkills.add((ManasSkill) ExtraSkills.MULTILAYER_BARRIER.get());
        heroInstances.add((ManasSkill) ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.get());
        heroInstances.add((ManasSkill) ResistanceSkills.PAIN_NULLIFICATION.get());
        heroInstances.add((ManasSkill) ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.get());
        heroInstances.add((ManasSkill) ResistanceSkills.SPATIAL_ATTACK_NULLIFICATION.get());
        heroSkills.add((ManasSkill) UniqueSkills.MARTIAL_MASTER.get());
        heroSkills.add((ManasSkill) ExtraSkills.STEEL_STRENGTH.get());
    }

    public void addShionSkills() {
        heroSkills.add(ExtraSkills.DEMON_LORD_HAKI.get());
        heroInstances.add(ExtraSkills.INFINITE_REGENERATION.get());
        heroSkills.add(ExtraSkills.MULTILAYER_BARRIER.get());
        heroSkills.add((ManasSkill) ExtraSkills.UNIVERSAL_PERCEPTION.get());
        heroSkills.add((ManasSkill) ExtraSkills.THOUGHT_ACCELERATION.get());
        heroSkills.add((ManasSkill) ExtraSkills.SPATIAL_MANIPULATION.get());
        heroSkills.add((ManasSkill) ExtraSkills.ALL_SEEING_EYE.get());
        heroSkills.add((ManasSkill) ExtraSkills.MORTAL_FEAR.get());
        heroSkills.add((ManasSkill) UniqueSkills.COOK.get());
        heroSkills.add((ManasSkill) UniqueSkills.DIVINE_BERSERKER.get());
        heroSkills.add((ManasSkill) IntrinsicSkills.OGRE_BERSERKER.get());
        heroSkills.add((ManasSkill) ExtraSkills.HAKI.get());
        heroSkills.add((ManasSkill) ExtraSkills.STEEL_STRENGTH.get());
        heroSkills.add((ManasSkill) ExtraSkills.STRENGTHEN_BODY.get());
        heroInstances.add((ManasSkill) ResistanceSkills.PAIN_NULLIFICATION.get());
        heroInstances.add((ManasSkill) ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.get());
        heroInstances.add((ManasSkill) ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get());
        heroInstances.add((ManasSkill) ResistanceSkills.HOLY_ATTACK_NULLIFICATION.get());
        heroInstances.add((ManasSkill) ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.get());
    }

    public void addShunaSkills(){
        heroSkills.add((ManasSkill) ExtraSkills.ANALYTICAL_APPRAISAL.get());
        heroSkills.add((ManasSkill) ExtraSkills.MAJESTY.get());
        heroSkills.add((ManasSkill) ExtraSkills.SPATIAL_MOTION.get());
        heroSkills.add((ManasSkill) ExtraSkills.MULTILAYER_BARRIER.get());
        heroSkills.add((ManasSkill) CommonSkills.THOUGHT_COMMUNICATION.get());
        heroInstances.add((ManasSkill) ResistanceSkills.HOLY_ATTACK_RESISTANCE.get());
        heroInstances.add((ManasSkill) ResistanceSkills.SPIRITUAL_ATTACK_RESISTANCE.get());
        heroInstances.add((ManasSkill) ResistanceSkills.ABNORMAL_CONDITION_RESISTANCE.get());
        heroInstances.add((ManasSkill) IntrinsicSkills.FLAME_BREATH.get());
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        if (this.getEvolving() < 5){
            return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        }
        this.populateDefaultEquipmentSlots(this.random, pDifficulty);
        this.randomTexture();
        this.randomHero(this.random);
        ReiMod.LOGGER.info("Ogre spawned at " + this.blockPosition().getX() + ", " + this.blockPosition().getY() + ", " + this.blockPosition().getZ());
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

    /**
     * Set random hero from {@link KnownHeroes#getId()}
     * @param pRandom
     */
    protected void randomHero(RandomSource pRandom){
        int i = pRandom.nextInt(3);
        switch (i){
            case 0 -> addBenimaruSkills();
            case 1 -> addSoueiSkills();
            case 2 -> addShionSkills();
            case 3 -> addShunaSkills();
        }
        this.setHero(i);
    }

    @Nullable
    public Item getEquipmentForArmorSlot(EquipmentSlot pSlot, int pChance) {
        Item item;
        switch (this.getGender().getId()) {
            case 0: {
                switch (pSlot) {
                    case HEAD -> item = Items.LEATHER_HELMET;
                    case CHEST -> item = ReiItems.HAKAMA_CHESTPLATE_D.get();
                    case LEGS -> item = ReiItems.HAKAMA_LEGGINGS_D.get();
                    case FEET ->
                            item = pChance == 2 ? Items.LEATHER_BOOTS : (pChance == 4 ? Items.CHAINMAIL_BOOTS : null);
                    default -> item = null;
                }
            }
            case 1: {
                switch (pSlot) {
                    case HEAD -> item = Items.LEATHER_HELMET;
                    case CHEST -> item = ReiItems.MONSTER_LEATHER_JACKET_ARMOR_D.get();
                    case LEGS -> item = ReiItems.MONSTER_LEATHER_WOMAN_LEGGINGS_D.get();
                    case FEET -> item = ReiItems.MONSTER_LEATHER_SANDALS_D.get();
                    default -> item = null;
                }
            }
            case 2: {
                switch (pSlot) {
                    case HEAD -> item = Items.LEATHER_HELMET;
                    case CHEST -> item = ReiItems.KIMONO_D.get();
                    case LEGS -> item = ReiItems.YUKATA_D.get();
                    case FEET -> item = ReiItems.MONSTER_LEATHER_SANDALS_D.get();
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
            return SoundEvents.VILLAGER_AMBIENT;
        }
    }

    public void setMiscAnimation(int tick) {
        this.entityData.set(MISC_ANIMATION, tick);
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

    @Override
    public void move(MoverType type, Vec3 movement) {
        super.move(type, movement);
        this.setDeltaMovement(movement);
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

    public void tick() {
        super.tick();
        this.refreshDimensions();
        this.miscAnimationHandler();
    }

    public String getSyncedAnimation() {
        return this.entityData.get(ANIMATION);
    }

    public void setAnimation(String animation) {
        this.entityData.set(ANIMATION, animation);
    }


    public AnimationFactory getFactory() {
        return this.factory;
    }

    public Gender getGender() {
        return Gender.byId(this.entityData.get(GENDER));
    }

    public void setGender(int gender) {
        this.entityData.set(GENDER, gender);
        if (gender == 2 || gender == 1){
            this.setMateGender(1);
        } else {
            this.setMateGender(0);
        }
    }

    public void setMateGender(int gender){
        this.entityData.set(GENDER_FOR_MATE, gender);
    }

    public int getMateGender(){
        return this.entityData.get(GENDER_FOR_MATE);
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

    void randomTexture() {
        this.setGender(this.random.nextInt(3));
        this.setSkin(false);
        this.setFace(Face.getRandom(this.getGender(), this));
        this.setHair(Hair.getRandom(this.getGender(), this));
        this.setClothing(Clothing.getRandom(this.getGender(), this));
    }

    public float getScale() {
        float multiplier = this.isKijin() || this.isOni() || this.isNextOni() || this.isDivineFighter() ? 1.3333334F : 1.0F;
        return multiplier * (this.isBaby() ? 0.5F : 1.0F);
    }

    public boolean isKijin() {
        return this.getEvolving() <= 4;
    }

    public boolean isOni() {
        return this.getCurrentEvolutionState() <= 3;
    }

    public boolean isNextOni() {
        return this.getCurrentEvolutionState() <= 2;
    }

    public boolean isDivineFighter() {
        return this.getCurrentEvolutionState() <= 1;
    }

    public int getMiscAnimation() {
        return this.entityData.get(MISC_ANIMATION);
    }

    @Nullable
    public ManasSkillInstance getDemonLordHaki() {
        Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(this).getSkill((ManasSkill)ExtraSkills.DEMON_LORD_HAKI.get());
        if (skill.isEmpty()) {
            return null;
        } else {
            return !((ManasSkillInstance)skill.get()).canInteractSkill(this) ? null : (ManasSkillInstance)skill.get();
        }
    }

    @Nullable
    public ManasSkillInstance getMultiLayerBarrier() {
        Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(this).getSkill((ManasSkill)ExtraSkills.MULTILAYER_BARRIER.get());
        if (skill.isEmpty()) {
            return null;
        } else {
            return !((ManasSkillInstance)skill.get()).canInteractSkill(this) ? null : (ManasSkillInstance)skill.get();
        }
    }

    @Nullable
    public ManasSkillInstance getStrengthenBody() {
        Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(this).getSkill((ManasSkill)ExtraSkills.STRENGTHEN_BODY.get());
        if (skill.isEmpty()) {
            return null;
        } else {
            return !((ManasSkillInstance)skill.get()).canInteractSkill(this) ? null : (ManasSkillInstance)skill.get();
        }
    }

    @Nullable
    public ManasSkillInstance getThoughtComunication() {
        Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(this).getSkill((ManasSkill)CommonSkills.THOUGHT_COMMUNICATION.get());
        if (skill.isEmpty()) {
            return null;
        } else {
            return !((ManasSkillInstance)skill.get()).canInteractSkill(this) ? null : (ManasSkillInstance)skill.get();
        }
    }

    @Nullable
    public ManasSkillInstance getShadowMotion() {
        Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(this).getSkill((ManasSkill)ExtraSkills.SHADOW_MOTION.get());
        if (skill.isEmpty()) {
            return null;
        } else {
            return !((ManasSkillInstance)skill.get()).canInteractSkill(this) ? null : (ManasSkillInstance)skill.get();
        }
    }

    @Nullable
    public ManasSkillInstance getBlackFlame(){
        Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(this).getSkill((ManasSkill)ExtraSkills.BLACK_FLAME.get());
        if (skill.isEmpty()) {
            return null;
        } else {
            return !((ManasSkillInstance)skill.get()).canInteractSkill(this) ? null : (ManasSkillInstance)skill.get();
        }
    }

    @Nullable
    public ManasSkillInstance getBodyDouble(){
        Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(this).getSkill((ManasSkill)ExtraSkills.BODY_DOUBLE.get());
        if (skill.isEmpty()) {
            return null;
        } else {
            return !((ManasSkillInstance)skill.get()).canInteractSkill(this) ? null : (ManasSkillInstance)skill.get();
        }
    }

    @Nullable
    public ManasSkillInstance getSteelStrenght(){
        Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(this).getSkill((ManasSkill)ExtraSkills.STEEL_STRENGTH.get());
        if (skill.isEmpty()) {
            return null;
        } else {
            return !((ManasSkillInstance)skill.get()).canInteractSkill(this) ? null : (ManasSkillInstance)skill.get();
        }
    }

    @Nullable
    public ManasSkillInstance getOgreBerserker(){
        Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(this).getSkill((ManasSkill)IntrinsicSkills.OGRE_BERSERKER.get());
        if (skill.isEmpty()) {
            return null;
        } else {
            return !((ManasSkillInstance)skill.get()).canInteractSkill(this) ? null : (ManasSkillInstance)skill.get();
        }
    }

    @Nullable
    public ManasSkillInstance getDivineBerserker(){
        Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(this).getSkill((ManasSkill)UniqueSkills.DIVINE_BERSERKER.get());
        if (skill.isEmpty()) {
            return null;
        } else {
            return !((ManasSkillInstance)skill.get()).canInteractSkill(this) ? null : (ManasSkillInstance)skill.get();
        }
    }

    class OgreAttackGoal extends HumanoidNPCEntity.NPCMeleeAttackGoal {
        private final OgreEntity ogre = OgreEntity.this;

        public OgreAttackGoal() {
            super(OgreEntity.this, (double)2.5F, true);
        }

        public boolean canUse() {
            return this.ogre.isOrderedToSit() ? false : super.canUse();
        }

        public boolean canContinueToUse() {
            return this.ogre.isOrderedToSit() ? false : super.canContinueToUse();
        }

        public void tick() {
            if (this.ogre.getMiscAnimation() == 0) {
                super.tick();
            }

        }

        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
            double distance = this.getAttackReachSqr(pEnemy);
            if (this.ogre.getMiscAnimation() == 0) {
                int randomAttack = this.randomAttack(pDistToEnemySqr, pEnemy);
                double var10000;
                switch (randomAttack) {
                    case 2:
                        this.ogre.getNavigation().stop();
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
                        this.ogre.getNavigation().stop();
                        var10000 = (double)3600.0F;
                        break;
                    default:
                        var10000 = distance;
                }

                double attackRange = var10000;
                if (pDistToEnemySqr <= attackRange && this.isTimeToAttack()) {
                    this.resetAttackCooldown();
                    this.ogre.setMiscAnimation(randomAttack);
                    if (randomAttack == 2) {
                        this.ogre.doHurtTarget(pEnemy);
                    } else if (randomAttack == 5) {
                        this.ogre.stopRiding();
                        this.ogre.moveTo(pEnemy.position());
                        TensuraParticleHelper.addServerParticlesAroundSelf(this.ogre, ParticleTypes.SQUID_INK);
                        this.ogre.hurtMarked = true;
                    }
                }
            }

        }

        protected int randomAttack(double distance, LivingEntity entity) {
            if (this.ogre.isBaby()) {
                return 2;
            } else {
                if (this.ogre.random.nextInt(10) == 1) {
                    if (entity.isOnGround() && this.ogre.isOnGround() && distance >= (double)200.0F && this.ogre.random.nextInt(10) == 1 && this.ogre.getShadowMotion() != null) {
                        return 10;
                    }
                    if (entity.isOnGround() && this.ogre.isOnGround() && distance >= (double)200.0F && this.ogre.random.nextInt(10) == 1 && this.ogre.getSteelStrenght() != null) {
                        return 9;
                    }
                    if (entity.isOnGround() && this.ogre.isOnGround() && distance >= (double)200.0F && this.ogre.random.nextInt(10) == 1 && this.ogre.getDemonLordHaki() != null) {
                        return 8;
                    }
                    if (entity.isOnGround() && this.ogre.isOnGround() && distance >= (double)200.0F && this.ogre.random.nextInt(10) == 1 && this.ogre.getStrengthenBody() != null) {
                        return 7;
                    }if (entity.isOnGround() && this.ogre.isOnGround() && distance >= (double)200.0F && this.ogre.random.nextInt(10) == 1 && this.ogre.getMultiLayerBarrier() != null) {
                        return 6;
                    }if (entity.isOnGround() && this.ogre.isOnGround() && distance >= (double)200.0F && this.ogre.random.nextInt(10) == 1 && this.ogre.getBlackFlame() != null) {
                        return 5;
                    }

                    if ((distance >= (double)200.0F || this.ogre.random.nextInt(15) == 1) && this.ogre.getBodyDouble() != null) {
                        return 3;
                    }

                    if (distance >= (double)36.0F || this.ogre.random.nextInt(20) == 1) {
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