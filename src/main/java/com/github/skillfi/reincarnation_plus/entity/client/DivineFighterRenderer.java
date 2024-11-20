package com.github.skillfi.reincarnation_plus.entity.client;

import com.github.manasmods.tensura.item.custom.TempestScaleShieldItem;
import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.entity.DivineFighterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

public class DivineFighterRenderer extends ExtendedGeoEntityRenderer<DivineFighterEntity> {

    protected ItemStack mainHandItem;
    protected ItemStack offHandItem;
    protected ItemStack helmetItem;
    protected ItemStack chestplateItem;
    protected ItemStack leggingsItem;
    protected ItemStack bootsItem;

    public DivineFighterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new OgreModel<>());
        this.shadowRadius = 0.2F;
    }

    protected boolean isArmorBone(GeoBone bone) {
        return bone.getName().endsWith("Armor");
    }

    protected ItemStack getArmorForBone(String boneName, DivineFighterEntity kijinEntity) {
        ItemStack var10000;
        switch (boneName) {
            case "LeftBootArmor":
            case "RightBootArmor":
                var10000 = this.bootsItem;
                break;
            case "LeftLegArmor":
            case "RightLegArmor":
                var10000 = this.leggingsItem;
                break;
            case "ChestArmor":
            case "RightArmArmor":
            case "LeftArmArmor":
                var10000 = this.chestplateItem;
                break;
            case "HeadArmor":
                var10000 = this.helmetItem;
                break;
            default:
                var10000 = null;
        }

        return var10000;
    }

    protected @Nullable ResourceLocation getTextureForBone(String boneName, DivineFighterEntity kijinEntity) {
        if (boneName.equals("ChestArmor")) {
            return new ResourceLocation(RPMod.MODID, "textures/models/armor/jacket_woman_armor_texture.png");
        }
        // додаткова логіка для інших частин обладунку
        return null;
    }

    protected @Nullable ItemStack getHeldItemForBone(String boneName, DivineFighterEntity currentEntity) {
        ItemStack var10000;
        switch (boneName) {
            case "leftHand" -> var10000 = currentEntity.isLeftHanded() ? this.mainHandItem : this.offHandItem;
            case "rightHand" -> var10000 = currentEntity.isLeftHanded() ? this.offHandItem : this.mainHandItem;
            default -> var10000 = null;
        }

        return var10000;
    }

    protected ModelPart getArmorPartForBone(String name, HumanoidModel<?> armorModel) {
        ModelPart var10000;
        switch (name) {
            case "LeftBootArmor":
            case "LeftLegArmor":
                var10000 = armorModel.leftLeg;
                break;
            case "RightBootArmor":
            case "RightLegArmor":
                var10000 = armorModel.rightLeg;
                break;
            case "RightArmArmor":
                var10000 = armorModel.rightArm;
                break;
            case "LeftArmArmor":
                var10000 = armorModel.leftArm;
                break;
            case "ChestArmor":
                var10000 = armorModel.body;
                break;
            case "HeadArmor":
                var10000 = armorModel.head;
                break;
            default:
                var10000 = null;
        }

        return var10000;
    }

    protected ItemTransforms.TransformType getCameraTransformForItemAtBone(ItemStack itemStack, String boneName) {
        ItemTransforms.TransformType var10000;
        switch (boneName) {
            case "leftHand":
            case "rightHand":
                var10000 = ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
                break;
            default:
                var10000 = ItemTransforms.TransformType.NONE;
        }

        return var10000;
    }


    protected @Nullable BlockState getHeldBlockForBone(String s, DivineFighterEntity kijinEntity) {
        return null;
    }


    protected void preRenderItem(PoseStack stack, ItemStack item, String s, DivineFighterEntity kijinEntity, IBone iBone) {
        if (item == this.mainHandItem) {
            stack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            if (item.getItem() instanceof ShieldItem || item.getItem() instanceof TempestScaleShieldItem) {
                stack.translate(0.0F, 0.0F, -0.25F);
            }
        } else if (item == this.offHandItem) {
            stack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            if (item.getItem() instanceof ShieldItem || item.getItem() instanceof TempestScaleShieldItem) {
                stack.translate(0.0F, 0.0F, 0.25F);
                stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            }
        }
    }


    protected void preRenderBlock(PoseStack poseStack, BlockState blockState, String s, DivineFighterEntity ogreEntity) {

    }


    protected void postRenderItem(PoseStack poseStack, ItemStack itemStack, String s, DivineFighterEntity ogreEntity, IBone iBone) {

    }


    protected void postRenderBlock(PoseStack poseStack, BlockState blockState, String s, DivineFighterEntity ogreEntity) {

    }

    public void renderEarly(DivineFighterEntity kijin, PoseStack poseStack, float partialTick, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float partialTicks) {
        super.renderEarly(kijin, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);
        this.mainHandItem = kijin.getItemBySlot(EquipmentSlot.MAINHAND);
        this.offHandItem = kijin.getItemBySlot(EquipmentSlot.OFFHAND);
        this.helmetItem = kijin.getItemBySlot(EquipmentSlot.HEAD);
        this.chestplateItem = kijin.getItemBySlot(EquipmentSlot.CHEST);
        this.leggingsItem = kijin.getItemBySlot(EquipmentSlot.LEGS);
        this.bootsItem = kijin.getItemBySlot(EquipmentSlot.FEET);
        if (kijin.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }

        int tick = 40 - kijin.getCurrentEvolutionState();
        if (kijin.getCurrentEvolutionState() > 0 && tick > 0) {
            float scale = 1.0F + 0.5F * ((float) tick / 40.0F);
            poseStack.scale(scale, scale, scale);
        }

    }

    protected EquipmentSlot getEquipmentSlotForArmorBone(String boneName, DivineFighterEntity currentEntity) {
        EquipmentSlot var10000;
        switch (boneName) {
            case "LeftBootArmor":
            case "RightBootArmor":
                var10000 = EquipmentSlot.FEET;
                break;
            case "LeftLegArmor":
            case "RightLegArmor":
                var10000 = EquipmentSlot.LEGS;
                break;
            case "LeftArmArmor":
                var10000 = !currentEntity.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                break;
            case "RightArmArmor":
                var10000 = currentEntity.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                break;
            case "ChestArmor":
                var10000 = EquipmentSlot.CHEST;
                break;
            case "HeadArmor":
                var10000 = EquipmentSlot.HEAD;
                break;
            default:
                var10000 = null;
        }

        return var10000;
    }
}
