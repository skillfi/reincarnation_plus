
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.core.registry.fluids;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.fluid.types.CrystalizedWaterFluidType;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReiFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ReiMod.MODID);
	public static final RegistryObject<FluidType> CRYSTALIZED_WATER_TYPE = REGISTRY.register("crystalized_water", () -> new CrystalizedWaterFluidType());
}
