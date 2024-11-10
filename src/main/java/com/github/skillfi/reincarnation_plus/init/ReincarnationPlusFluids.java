
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.init;

import com.github.skillfi.reincarnation_plus.ReincarnationPlus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

import com.github.skillfi.reincarnation_plus.fluid.CrystalizedWaterFluid;

public class ReincarnationPlusFluids {
	public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, ReincarnationPlus.MODID);
	public static final RegistryObject<FlowingFluid> CRYSTALIZED_WATER = REGISTRY.register("crystalized_water", () -> new CrystalizedWaterFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_CRYSTALIZED_WATER = REGISTRY.register("flowing_crystalized_water", () -> new CrystalizedWaterFluid.Flowing());

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			ItemBlockRenderTypes.setRenderLayer(CRYSTALIZED_WATER.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_CRYSTALIZED_WATER.get(), RenderType.translucent());
		}
	}
}
