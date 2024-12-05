package com.github.skillfi.reincarnation_plus.core.registry;

import com.github.skillfi.reincarnation_plus.core.entity.ReiEntities;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlocks;
import com.github.skillfi.reincarnation_plus.core.registry.fluids.ReiFluidTypes;
import com.github.skillfi.reincarnation_plus.core.registry.fluids.ReiFluids;
import com.github.skillfi.reincarnation_plus.core.registry.menu.ReiMenuTypes;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
import net.minecraftforge.eventbus.api.IEventBus;

public class ReiRegistry {

    public static void register(IEventBus modEventBus){
        ReiGems.register();
        ReiBiomeAuraModifier.init(modEventBus);
        ReiLevelAuraModifier.init(modEventBus);
        ReiItems.ITEMS.register(modEventBus);
        ReiBlockEntities.init(modEventBus);
        ReiBlocks.BLOCKS.register(modEventBus);
//        ReiMenus.REGISTRY.register(modEventBus);
        ReiFluids.REGISTRY.register(modEventBus);
        ReiFluidTypes.REGISTRY.register(modEventBus);
        // Ініціалізація сутностей
        ReiEntities.init(modEventBus);
        // Реєстрація вкладок
//        ReiProcessors.PROCESSORS.register(modEventBus);
//        ReiStructureTypes.STRUCTURE_TYPES.register(modEventBus);
        ReiRecipeTypes.init(modEventBus);
        ReiMenuTypes.init(modEventBus);

    }
}
