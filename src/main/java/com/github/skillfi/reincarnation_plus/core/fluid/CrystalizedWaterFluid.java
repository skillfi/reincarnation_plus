
package com.github.skillfi.reincarnation_plus.core.fluid;

import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks;
import com.github.skillfi.reincarnation_plus.core.registry.fluids.ReiFluids;
import com.github.skillfi.reincarnation_plus.core.registry.fluids.ReiFluidTypes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

import com.github.skillfi.reincarnation_plus.core.registry.items.ReiItems;

public abstract class CrystalizedWaterFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> ReiFluidTypes.CRYSTALIZED_WATER_TYPE.get(), () -> ReiFluids.CRYSTALIZED_WATER.get(),
			() -> ReiFluids.FLOWING_CRYSTALIZED_WATER.get()).explosionResistance(100f).bucket(() -> ReiItems.CRYSTALIZED_WATER_BUCKET.get())
			.block(() -> (LiquidBlock) ReiBlocks.CRYSTALIZED_WATER.get());

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
