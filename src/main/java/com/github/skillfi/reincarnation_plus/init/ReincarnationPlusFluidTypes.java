
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.init;

import com.github.skillfi.reincarnation_plus.ReincarnationPlus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fluids.FluidType;

import com.github.skillfi.reincarnation_plus.fluid.types.CrystalizedWaterFluidType;

public class ReincarnationPlusFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ReincarnationPlus.MODID);
	public static final RegistryObject<FluidType> CRYSTALIZED_WATER_TYPE = REGISTRY.register("crystalized_water", () -> new CrystalizedWaterFluidType());
}
