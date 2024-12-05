package com.github.skillfi.reincarnation_plus.core.item;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.client.magicule_block.MagiculeCoreItemRenderer;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

import java.util.function.Consumer;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

/**
 * MagiculeCoreItem - клас для анімованого предмета, що базується на BlockItem.
 * Використовує Geckolib для створення анімацій.
 */
public class MagiculeCoreItem extends BlockItem implements IAnimatable {

	// Фабрика для створення анімаційних даних
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	@Getter
	private final ResourceLocation texture = new ResourceLocation(ReiMod.MODID, "textures/models/blocks/magicule_core.png");
	/**
	 * Конструктор MagiculeCoreItem.
	 *
	 * @param block    Блок, до якого прив'язаний предмет.
	 * @param settings Параметри для створення предмета.
	 */
	public MagiculeCoreItem(Block block, Properties settings) {
		super(block, settings);
	}

	/**
	 * Предикат анімації, що визначає, чи слід продовжувати анімацію.
	 *
	 * @param event Подія анімації.
	 * @return PlayState.CONTINUE для постійної анімації.
	 */
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	/**
	 * Ініціалізація клієнтських властивостей для предмета.
	 * Реалізує рендерер для відображення моделі предмета.
	 *
	 * @param consumer Споживач клієнтських розширень.
	 */
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			private final BlockEntityWithoutLevelRenderer renderer = new MagiculeCoreItemRenderer();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}

	/**
	 * Реєструє контролери анімацій для даного предмета.
	 *
	 * @param data Об'єкт AnimationData, що управляє анімаціями.
	 */
	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(
				this, "controller", 0, this::predicate
		));
	}

	/**
	 * Повертає фабрику анімацій для даного предмета.
	 *
	 * @return Фабрика анімацій.
	 */
	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
