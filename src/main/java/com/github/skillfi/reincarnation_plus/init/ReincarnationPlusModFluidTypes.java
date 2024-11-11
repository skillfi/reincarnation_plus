
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package com.github.skillfi.reincarnation_plus.init;

import com.github.skillfi.reincarnation_plus.fluid.types.CrystalizedWaterFluidType;
import com.github.skillfi.reincarnation_plus.ReincarnationPlusMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ReincarnationPlusModFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ReincarnationPlusMod.MODID);
	public static final RegistryObject<FluidType> CRYSTALIZED_WATER_TYPE = REGISTRY.register("crystalized_water", () -> new CrystalizedWaterFluidType());
}
