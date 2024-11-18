package com.github.skillfi.reincarnation_plus.entity.client;

import com.github.manasmods.tensura.item.custom.TempestScaleShieldItem;
import com.github.skillfi.reincarnation_plus.entity.KijinEntity;
import com.github.skillfi.reincarnation_plus.entity.OgreEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
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

public class KijinRenderer extends ExtendedGeoEntityRenderer<KijinEntity> {

    protected ItemStack mainHandItem;
    protected ItemStack offHandItem;
    protected ItemStack helmetItem;
    protected ItemStack chestplateItem;
    protected ItemStack leggingsItem;
    protected ItemStack bootsItem;

    public KijinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new OgreModel<>());
        this.shadowRadius = 0.2F;
    }

    protected boolean isArmorBone(GeoBone geoBone) {
        return false;
    }

    protected @Nullable ItemStack getArmorForBone(String boneName, OgreEntity animatable) {
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

    protected @Nullable ResourceLocation getTextureForBone(String s, KijinEntity kijinEntity) {
        return null;
    }

    protected @Nullable ItemStack getHeldItemForBone(String boneName, KijinEntity currentEntity) {
        ItemStack var10000;
        switch (boneName) {
            case "leftHand" -> var10000 = currentEntity.isLeftHanded() ? this.mainHandItem : this.offHandItem;
            case "rightHand" -> var10000 = currentEntity.isLeftHanded() ? this.offHandItem : this.mainHandItem;
            default -> var10000 = null;
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


    protected @Nullable BlockState getHeldBlockForBone(String s, KijinEntity ogreEntity) {
        return null;
    }


    protected void preRenderItem(PoseStack stack, ItemStack item, String s, KijinEntity ogreEntity, IBone iBone) {
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


    protected void preRenderBlock(PoseStack poseStack, BlockState blockState, String s, KijinEntity ogreEntity) {

    }


    protected void postRenderItem(PoseStack poseStack, ItemStack itemStack, String s, KijinEntity ogreEntity, IBone iBone) {

    }


    protected void postRenderBlock(PoseStack poseStack, BlockState blockState, String s, KijinEntity ogreEntity) {

    }

    public void renderEarly(KijinEntity kijin, PoseStack poseStack, float partialTick, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float partialTicks) {
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
}
