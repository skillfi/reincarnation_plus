package com.github.skillfi.reincarnation_plus.client.renderer;

import com.github.skillfi.reincarnation_plus.entity.OgreManEntity;
import com.github.skillfi.reincarnation_plus.client.model.ModelOgre;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class OgreManRenderer extends HumanoidMobRenderer<OgreManEntity, ModelOgre<OgreManEntity>> {
	public OgreManRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelOgre<>(context.bakeLayer(ModelOgre.LAYER_LOCATION)), 0.5f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelOgre.LAYER_LOCATION)), new HumanoidModel(context.bakeLayer(ModelOgre.LAYER_LOCATION))));
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(OgreManEntity entity) {
		return new ResourceLocation("reincarnation_plus:textures/entities/ogre_man1.png");
	}
}
