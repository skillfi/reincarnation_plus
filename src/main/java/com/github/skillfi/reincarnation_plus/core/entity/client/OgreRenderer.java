package com.github.skillfi.reincarnation_plus.core.entity.client;

import com.github.manasmods.tensura.item.custom.TempestScaleShieldItem;
import com.github.skillfi.reincarnation_plus.core.entity.OgreEntity;
import com.github.skillfi.reincarnation_plus.core.entity.client.layers.ClothesLayer;
import com.github.skillfi.reincarnation_plus.core.entity.client.layers.EyesLayer;
import com.github.skillfi.reincarnation_plus.core.entity.client.layers.HairLayer;
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
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

public class OgreRenderer extends ExtendedGeoEntityRenderer<OgreEntity> {

    protected ItemStack mainHandItem;
    protected ItemStack offHandItem;
    protected ItemStack helmetItem;
    protected ItemStack chestplateItem;
    protected ItemStack leggingsItem;
    protected ItemStack bootsItem;


    public OgreRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new OgreModel());
        this.shadowRadius = 0.2F;
        this.addLayer(new EyesLayer<>(this));
        this.addLayer(new HairLayer<>(this));
        this.addLayer(new ClothesLayer<>(this));
    }


    protected boolean isArmorBone(GeoBone bone) {
        return bone.getName().endsWith("Armor");
    }

    protected ItemStack getArmorForBone(String boneName, OgreEntity entity) {
        ItemStack armor;
        switch (boneName) {
            case "LeftBootArmor":
            case "RightBootArmor":
                armor = this.bootsItem;
                break;
            case "LeftLegArmor":
            case "RightLegArmor":
                armor = this.leggingsItem;
                break;
            case "ChestArmor":
            case "RightArmArmor":
            case "LeftArmArmor":
                armor = this.chestplateItem;
                break;
            case "HeadArmor":
                armor = this.helmetItem;
                break;
            default:
                armor = null;
        }

        return armor;
    }

    protected ResourceLocation getTextureForBone(String s, OgreEntity ogreEntity) {
        return null;
    }

    protected ItemStack getHeldItemForBone(String boneName, OgreEntity currentEntity) {
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


    protected BlockState getHeldBlockForBone(String s, OgreEntity ogreEntity) {
        return null;
    }


    protected void preRenderItem(PoseStack stack, ItemStack item, String s, OgreEntity ogreEntity, IBone iBone) {
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


    protected void preRenderBlock(PoseStack poseStack, BlockState blockState, String s, OgreEntity ogreEntity) {

    }


    protected void postRenderItem(PoseStack poseStack, ItemStack itemStack, String boneName, OgreEntity entity, IBone iBone) {
    }



    protected void postRenderBlock(PoseStack poseStack, BlockState blockState, String s, OgreEntity ogreEntity) {

    }

    public void renderEarly(OgreEntity ogre, PoseStack poseStack, float partialTick, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float partialTicks) {
        super.renderEarly(ogre, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);
        this.mainHandItem = ogre.getItemBySlot(EquipmentSlot.MAINHAND);
        this.offHandItem = ogre.getItemBySlot(EquipmentSlot.OFFHAND);
        this.helmetItem = ogre.getItemBySlot(EquipmentSlot.HEAD);
        this.chestplateItem = ogre.getItemBySlot(EquipmentSlot.CHEST);
        this.leggingsItem = ogre.getItemBySlot(EquipmentSlot.LEGS);
        this.bootsItem = ogre.getItemBySlot(EquipmentSlot.FEET);
        if (ogre.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
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

    protected EquipmentSlot getEquipmentSlotForArmorBone(String boneName, OgreEntity currentEntity) {
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
