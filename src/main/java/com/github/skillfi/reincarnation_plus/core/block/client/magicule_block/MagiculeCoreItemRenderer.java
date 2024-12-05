package com.github.skillfi.reincarnation_plus.core.block.client.magicule_block;

import com.github.skillfi.reincarnation_plus.core.item.MagiculeCoreItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

/**
 * MagiculeCoreItemRenderer - рендерер для предмета MagiculeCore, що використовує Geckolib для анімацій.
 */
public class MagiculeCoreItemRenderer extends GeoItemRenderer<MagiculeCoreItem> {

	/**
	 * Конструктор рендерера. Ініціалізує модель для MagiculeCoreItem.
	 */
	public MagiculeCoreItemRenderer() {
		super(new MagiculeCoreModel());
	}

	/**
	 * Визначає тип рендера для предмета, забезпечуючи напівпрозорість (translucent).
	 *
	 * @param animatable   Поточний екземпляр MagiculeCoreItem, що рендериться.
	 * @param partialTick  Частка часу між тиками для плавності анімацій.
	 * @param poseStack    Стек трансформацій для рендерингу.
	 * @param bufferSource Джерело буфера для створення геометрії.
	 * @param buffer       Консумер вершин для виводу геометрії.
	 * @param packedLight  Інформація про освітлення для рендерингу.
	 * @param texture      Текстура для рендерингу.
	 * @return RenderType  Тип рендерингу для предмета (з напівпрозорістю).
	 */
	@Override
	public RenderType getRenderType(MagiculeCoreItem animatable, float partialTick, PoseStack poseStack,
									MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight,
									ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}

