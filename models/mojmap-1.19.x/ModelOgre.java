// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class ModelOgre<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "ogre"), "main");
	private final ModelPart body;
	private final ModelPart right_leg;
	private final ModelPart left_leg;
	private final ModelPart right_arm;
	private final ModelPart left_arm;
	private final ModelPart head;
	private final ModelPart HornRight;
	private final ModelPart HornLeft;

	public ModelOgre(ModelPart root) {
		this.body = root.getChild("body");
		this.right_leg = root.getChild("right_leg");
		this.left_leg = root.getChild("left_leg");
		this.right_arm = root.getChild("right_arm");
		this.left_arm = root.getChild("left_arm");
		this.head = root.getChild("head");
		this.HornRight = this.head.getChild("HornRight");
		this.HornLeft = this.head.getChild("HornLeft");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(
				-5.0F, -7.0F, -2.0F, 10.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 1.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32)
				.addBox(-3.75F, 4.0F, -2.25F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(28, 18).mirror()
				.addBox(-3.75F, -6.0F, -0.75F, 5.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(-1.25F, 18.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg",
				CubeListBuilder.create().texOffs(0, 32).mirror()
						.addBox(-1.25F, 4.0F, -2.25F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
						.texOffs(28, 18).addBox(-1.25F, -6.0F, -0.75F, 5.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(1.25F, 18.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(28, 0)
				.addBox(-2.0F, -1.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-7.0F, -1.0F, 1.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm",
				CubeListBuilder.create().texOffs(28, 0).mirror()
						.addBox(0.0F, -6.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(5.0F, 4.0F, 1.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(
				-4.0F, -8.0F, -1.75F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition HornRight = head.addOrReplaceChild("HornRight", CubeListBuilder.create(),
				PartPose.offsetAndRotation(3.0F, -7.25F, -2.5F, 0.0F, -0.829F, 0.0F));

		PartDefinition cube_r1 = HornRight.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(22, 32).mirror()
						.addBox(-0.25F, -2.0F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.75F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r2 = HornRight.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(26, 32).mirror()
						.addBox(-0.25F, -1.75F, -0.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, -1.8243F, -0.0113F, -0.1745F, 0.0F, 0.0F));

		PartDefinition HornLeft = head.addOrReplaceChild("HornLeft", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-3.0F, -7.25F, -2.5F, 0.0F, 0.829F, 0.0F));

		PartDefinition cube_r3 = HornLeft.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(26, 32).addBox(-0.75F, -1.75F, -0.25F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -1.8243F, -0.0113F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r4 = HornLeft
				.addOrReplaceChild("cube_r4",
						CubeListBuilder.create().texOffs(22, 32).addBox(-0.75F, -2.0F, -0.25F, 1.0F, 2.0F, 1.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 1.0F, 0.75F, 0.5672F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);
		this.left_leg.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.right_arm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
		this.right_leg.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.left_arm.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
	}
}