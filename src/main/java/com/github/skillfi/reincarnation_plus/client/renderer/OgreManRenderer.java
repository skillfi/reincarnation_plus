
package com.github.skillfi.reincarnation_plus.client.renderer;

import com.github.skillfi.reincarnation_plus.entity.OgreManEntity;
import com.github.skillfi.reincarnation_plus.client.model.ModelOgre;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OgreManRenderer extends MobRenderer<OgreManEntity, ModelOgre<OgreManEntity>> {
	public OgreManRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelOgre(context.bakeLayer(ModelOgre.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(OgreManEntity entity) {
		return new ResourceLocation("reincarnation_plus:textures/entities/ogre_man1.png");
	}
}
