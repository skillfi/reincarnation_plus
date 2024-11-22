package com.github.skillfi.reincarnation_plus;

import com.github.skillfi.reincarnation_plus.client.ClientProxy;
import com.github.skillfi.reincarnation_plus.config.EntityConfig;
import com.github.skillfi.reincarnation_plus.config.SpawnRateConfig;
import com.github.skillfi.reincarnation_plus.data.gen.*;
import com.github.skillfi.reincarnation_plus.entity.RPEntities;
import com.github.skillfi.reincarnation_plus.entity.RPEntityHandler;
import com.github.skillfi.reincarnation_plus.entity.client.*;
import com.github.skillfi.reincarnation_plus.handler.*;
import com.github.skillfi.reincarnation_plus.network.ReincarnationPlusNetwork;
import com.github.skillfi.reincarnation_plus.registry.RPRegistry;
import lombok.Getter;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(RPMod.MODID)
@Mod.EventBusSubscriber(modid = RPMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RPMod {

    public static final String MODID = "reincarnation_plus";
    @Getter
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    private static final CommonProxy PROXY = DistExecutor.safeRunForDist(()->ClientProxy::new, ()->CommonProxy::new);

    public RPMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        // Реєстрація конфігурацій
        setupConfig();

        // Реєстрація всіх необхідних компонентів
        registerComponents(modEventBus);

        // Реєстрація подій
        modEventBus.addListener(RPEntityHandler::registerEntityPlacements);
        modEventBus.addListener(RPEntityHandler::entityAttributeEvent);
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::generateData);
        modEventBus.addListener(this::setupComplete);

        GeckoLib.initialize();
    }

    private void setupConfig() {
        FileUtils.getOrCreateDirectory(FMLPaths.CONFIGDIR.get().resolve(MODID), MODID);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RPConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SpawnRateConfig.SPEC, getConfigFileName("spawnrate-common"));
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EntityConfig.SPEC, getConfigFileName("entity-common"));
    }

    private void registerComponents(IEventBus modEventBus) {
        // Реєстрація об'єктів
        RPItems.ITEMS.register(modEventBus);
        RPBlocks.BLOCKS.register(modEventBus);
        RPBlockEntities.REGISTRY.register(modEventBus);
        RPFluids.REGISTRY.register(modEventBus);
        RPFluidTypes.REGISTRY.register(modEventBus);
        RPMenus.REGISTRY.register(modEventBus);

        // Ініціалізація сутностей
        RPEntities.init(modEventBus);

        // Реєстрація вкладок
        RPTabs.load();

        // Реєстрація в реєстрах мода
        RPRegistry.register(modEventBus);
    }

    private void setupComplete(final FMLLoadCompleteEvent event) {
        PROXY.postInit();
    }

    private void generateData(final GatherDataEvent event) {
        var generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new RPLanguageProvider(event));
        generator.addProvider(event.includeServer(), new RPEntityEPProvider(event));
        generator.addProvider(event.includeServer(), new RPEntityTypeTagProvider(event));
        generator.addProvider(event.includeServer(), new RPGearEpProvider(event));
    }

    private void setup(final FMLCommonSetupEvent event) {
        ReincarnationPlusNetwork.register();
        event.enqueueWork(PROXY::setup);
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        LOGGER.info(RPEntities.LOADED_ENTITIES);
    }

    private String getConfigFileName(String name) {
        return String.format("%s/%s.toml", MODID, name);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(RPEntities.OGRE.get(), OgreRenderer::new);
        EntityRenderers.register(RPEntities.KIJIN.get(), KijinRenderer::new);
        EntityRenderers.register(RPEntities.ONI.get(), OniRenderer::new);
        EntityRenderers.register(RPEntities.DIVINE_ONI.get(), DivineOniRenderer::new);
        EntityRenderers.register(RPEntities.DIVINE_FIGHTER.get(), DivineFighterRenderer::new);
    }
}
