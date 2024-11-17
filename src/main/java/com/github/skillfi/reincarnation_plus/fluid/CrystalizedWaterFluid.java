
package com.github.skillfi.reincarnation_plus.fluid;

import com.github.skillfi.reincarnation_plus.handler.RPBlocks;
import com.github.skillfi.reincarnation_plus.handler.RPFluids;
import com.github.skillfi.reincarnation_plus.handler.RPFluidTypes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

import com.github.skillfi.reincarnation_plus.handler.RPItems;

public abstract class CrystalizedWaterFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> RPFluidTypes.CRYSTALIZED_WATER_TYPE.get(), () -> RPFluids.CRYSTALIZED_WATER.get(),
			() -> RPFluids.FLOWING_CRYSTALIZED_WATER.get()).explosionResistance(100f).bucket(() -> RPItems.CRYSTALIZED_WATER_BUCKET.get())
			.block(() -> (LiquidBlock) RPBlocks.CRYSTALIZED_WATER.get());

	private CrystalizedWaterFluid() {
		super(PROPERTIES);
	}

	public static class Source extends CrystalizedWaterFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends CrystalizedWaterFluid {
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
		}

		public boolean isSource(FluidState state) {
			return false;
		}
	}
}
