package com.github.skillfi.reincarnation_plus;


import com.github.skillfi.reincarnation_plus.config.SpawnRateConfig;
import com.github.skillfi.reincarnation_plus.data.gen.RPEntityEPProvider;
import com.github.skillfi.reincarnation_plus.data.gen.RPEntityTypeTagProvider;
import com.github.skillfi.reincarnation_plus.data.gen.RPGearEpProvider;
import com.github.skillfi.reincarnation_plus.data.gen.RPLanguageProvider;
import com.github.skillfi.reincarnation_plus.entity.client.OgreRenderer;
import com.github.skillfi.reincarnation_plus.handler.RPEntityHandler;
import com.github.skillfi.reincarnation_plus.init.*;
import com.github.skillfi.reincarnation_plus.network.ReincarnationPlusNetwork;
import com.github.skillfi.reincarnation_plus.registry.RPRegistry;
import lombok.Getter;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(RPMod.MODID)
public class RPMod {
    public static final String MODID = "reincarnation_plus";
    @Getter
    public static final Logger LOGGER = LogManager.getLogger(RPMod.MODID);

    public static final String CONFIG_DIR = "reincarnation-plus";

    public RPMod() {
        FileUtils.getOrCreateDirectory(FMLPaths.CONFIGDIR.get().resolve(CONFIG_DIR), CONFIG_DIR);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RPConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SpawnRateConfig.SPEC, this.getConfigFileName("spawnrate-common"));
    	MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RPItems.ITEMS.register(modEventBus);
        RPMenus.REGISTRY.register(modEventBus);
        RPBlockEntities.REGISTRY.register(modEventBus);
        RPBlocks.BLOCKS.register(modEventBus);
        RPFluids.REGISTRY.register(modEventBus);
        RPFluidTypes.REGISTRY.register(modEventBus);
        RPEntities.init(modEventBus);
        modEventBus.addListener(RPEntityHandler::entityAttributeEvent);
        modEventBus.addListener(RPEntityHandler::registerEntityPlacements);
        RPTabs.load();
        GeckoLib.initialize();
        RPRegistry.register(modEventBus);
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::generateData);

    }

    private void generateData(final GatherDataEvent event){
        event.getGenerator().addProvider(event.includeServer(), new RPLanguageProvider(event));
        event.getGenerator().addProvider(event.includeServer(), new RPEntityEPProvider(event));
        event.getGenerator().addProvider(event.includeServer(), new RPEntityTypeTagProvider(event));
        event.getGenerator().addProvider(event.includeServer(), new RPGearEpProvider(event));
    }

    private void setup(final FMLCommonSetupEvent e){
        ReincarnationPlusNetwork.register();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            EntityRenderers.register(RPEntities.OGRE.get(), OgreRenderer::new);
        }
    }

    private String getConfigFileName(String name) {
        return String.format("%s/%s.toml", "reincarnation-plus", name);
    }
}
