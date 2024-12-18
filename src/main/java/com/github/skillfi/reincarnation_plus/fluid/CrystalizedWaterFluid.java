
package com.github.skillfi.reincarnation_plus.fluid;

import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusModBlocks;
import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusModFluidTypes;
import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusModFluids;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

import com.github.skillfi.reincarnation_plus.init.ReincarnationPlusModItems;

public abstract class CrystalizedWaterFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> ReincarnationPlusModFluidTypes.CRYSTALIZED_WATER_TYPE.get(), () -> ReincarnationPlusModFluids.CRYSTALIZED_WATER.get(),
			() -> ReincarnationPlusModFluids.FLOWING_CRYSTALIZED_WATER.get()).explosionResistance(100f).bucket(() -> ReincarnationPlusModItems.CRYSTALIZED_WATER_BUCKET.get())
			.block(() -> (LiquidBlock) ReincarnationPlusModBlocks.CRYSTALIZED_WATER.get());

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
