package com.github.skillfi.reincarnation_plus.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class ModelOgre<T extends LivingEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {
    // This layer location should be baked with EntityRendererProvider.Context in
    // the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("reincarnation_plus", "model_ogre"), "main");
    public final ModelPart Body;
    public final ModelPart Armor;
    public final ModelPart waist;
    public final ModelPart LegRight;
    public final ModelPart LegLeft;
    public final ModelPart ArmRight;
    public final ModelPart right_arm;
    public final ModelPart ArmLeft;
    public final ModelPart left_arm;
    public final ModelPart Head;
    public final ModelPart HornRight;
    public final ModelPart HornLeft;

    public ModelOgre(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Armor = this.Body.getChild("Armor");
        this.waist = this.Body.getChild("waist");
        this.LegRight = root.getChild("LegRight");
        this.LegLeft = root.getChild("LegLeft");
        this.ArmRight = root.getChild("ArmRight");
        this.right_arm = this.ArmRight.getChild("right_arm");
        this.ArmLeft = root.getChild("ArmLeft");
        this.left_arm = this.ArmLeft.getChild("left_arm");
        this.Head = root.getChild("Head");
        this.HornRight = this.Head.getChild("HornRight");
        this.HornLeft = this.Head.getChild("HornLeft");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -7.0F, -2.0F, 10.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 1.0F));
        PartDefinition Armor = Body.addOrReplaceChild("Armor", CubeListBuilder.create().texOffs(0, 40).addBox(-5.0F, -7.0F, -2.75F, 10.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition waist = Body.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(26, 32).addBox(-5.0F, 1.5F, -2.5F, 10.0F, 7.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition LegRight = partdefinition.addOrReplaceChild("LegRight",
                CubeListBuilder.create().texOffs(0, 32).addBox(-3.75F, 4.0F, -2.25F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(28, 18).mirror().addBox(-3.75F, -6.0F, -0.75F, 5.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(-1.25F, 18.0F, 0.0F));
        PartDefinition LegLeft = partdefinition.addOrReplaceChild("LegLeft",
                CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-1.25F, 4.0F, -2.25F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(28, 18).addBox(-1.25F, -6.0F, -0.75F, 5.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.25F, 18.0F, 0.0F));
        PartDefinition ArmRight = partdefinition.addOrReplaceChild("ArmRight", CubeListBuilder.create().texOffs(28, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -1.0F, 1.0F));
        PartDefinition right_arm = ArmRight.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(31, 32).mirror().addBox(-2.0F, -1.0F, -2.5F, 4.0F, 14.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition ArmLeft = partdefinition.addOrReplaceChild("ArmLeft", CubeListBuilder.create().texOffs(28, 0).mirror().addBox(0.0F, -6.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 4.0F, 1.0F));
        PartDefinition left_arm = ArmLeft.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(31, 32).addBox(-2.0F, -1.0F, -2.5F, 4.0F, 14.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -5.0F, 0.0F));
        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, -8.0F, -1.75F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition HornRight = Head.addOrReplaceChild("HornRight", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -7.25F, -2.5F, 0.0F, -0.829F, 0.0F));
        PartDefinition cube_r1 = HornRight.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(22, 32).mirror().addBox(-0.25F, -2.0F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.75F, 0.5672F, 0.0F, 0.0F));
        PartDefinition cube_r2 = HornRight.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(26, 32).mirror().addBox(-0.25F, -1.75F, -0.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, -1.8243F, -0.0113F, -0.1745F, 0.0F, 0.0F));
        PartDefinition HornLeft = Head.addOrReplaceChild("HornLeft", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -7.25F, -2.5F, 0.0F, 0.829F, 0.0F));
        PartDefinition cube_r3 = HornLeft.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(26, 32).addBox(-0.75F, -1.75F, -0.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -1.8243F, -0.0113F, -0.1745F, 0.0F, 0.0F));
        PartDefinition cube_r4 = HornLeft.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(22, 32).addBox(-0.75F, -2.0F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.75F, 0.5672F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        LegRight.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        LegLeft.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        ArmRight.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        ArmLeft.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.Head, this.HornLeft, this.HornRight);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.Body, this.ArmRight, this.ArmLeft, this.LegRight, this.LegLeft);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.ArmLeft.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
        this.Head.yRot = netHeadYaw / (180F / (float) Math.PI);
        this.Head.xRot = headPitch / (180F / (float) Math.PI);
        this.LegLeft.xRot = Mth.cos(limbSwing) * -1.0F * limbSwingAmount;
        this.ArmRight.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
        this.LegRight.yRot = Mth.cos(limbSwing) * 1.0F * limbSwingAmount;
    }

    public void setAllVisible(boolean pVisible) {
        this.Head.visible = pVisible;
        this.Body.visible = pVisible;
        this.ArmRight.visible = pVisible;
        this.ArmLeft.visible = pVisible;
        this.LegRight.visible = pVisible;
        this.LegLeft.visible = pVisible;
    }

    @Override
    public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
        this.getArm(humanoidArm).translateAndRotate(poseStack);
    }

    protected ModelPart getArm(HumanoidArm pSide) {
        return pSide == HumanoidArm.LEFT ? this.ArmLeft : this.ArmRight;
    }

    @Override
    public ModelPart getHead() {
        return this.Head;
    }
}
