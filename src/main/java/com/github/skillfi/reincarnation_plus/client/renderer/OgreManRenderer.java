
package com.github.skillfi.reincarnation_plus.client.renderer;

import com.github.skillfi.reincarnation_plus.entity.OgreManEntity;
import com.github.skillfi.reincarnation_plus.client.model.ModelOgre;

public class OgreManRenderer extends MobRenderer<OgreManEntity, ModelOgre<OgreManEntity>> {
	public OgreManRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelOgre(context.bakeLayer(ModelOgre.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(OgreManEntity entity) {
		return new ResourceLocation("reincarnation_plus:textures/entities/ogre_man1.png");
	}
}
