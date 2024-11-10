package com.github.skillfi.reincarnation_plus;

import com.github.skillfi.reincarnation_plus.data.gen.RPLanguageProvider;
import com.github.skillfi.reincarnation_plus.init.*;
import com.github.skillfi.reincarnation_plus.registry.RPRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ReincarnationPlus.MODID)
public class ReincarnationPlus {
    public static final String MODID = "isekai_quartet";
    public static final Logger LOGGER = LogManager.getLogger(ReincarnationPlus.MODID);

    public ReincarnationPlus() {
    	MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ReincarnationPlusModItems.REGISTRY.register(modEventBus);
        ReincarnationPlusModMenus.REGISTRY.register(modEventBus);
        ReincarnationPlusModBlockEntities.REGISTRY.register(modEventBus);
        ReincarnationPlusModBlocks.REGISTRY.register(modEventBus);
        ReincarnationPlusModFluids.REGISTRY.register(modEventBus);
        ReincarnationPlusModFluidTypes.REGISTRY.register(modEventBus);
        ReincarnationPlusModTabs.load();
        RPRegistry.register(modEventBus);
        modEventBus.addListener(this::generateData);

    }

    public static ResourceLocation loc(String s) {
        return new ResourceLocation(MODID, s);
    }

    private void generateData(final GatherDataEvent event){
        event.getGenerator().addProvider(event.includeServer(), new RPLanguageProvider(event));
    }
}
