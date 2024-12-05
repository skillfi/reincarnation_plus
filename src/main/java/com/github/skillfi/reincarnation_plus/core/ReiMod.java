package com.github.skillfi.reincarnation_plus.core;

import com.github.manasmods.tensura.data.gen.TensuraBlockTagProvider;
import com.github.manasmods.tensura.data.gen.TensuraItemTagProvider;
import com.github.skillfi.reincarnation_plus.core.block.client.magicule_block.MagiculeCoreRenderer;
import com.github.skillfi.reincarnation_plus.core.client.ClientProxy;
import com.github.skillfi.reincarnation_plus.core.config.EntityConfig;
import com.github.skillfi.reincarnation_plus.core.config.SpawnRateConfig;
import com.github.skillfi.reincarnation_plus.core.entity.client.*;
import com.github.skillfi.reincarnation_plus.core.entity.ReiEntities;
import com.github.skillfi.reincarnation_plus.core.entity.RPEntityHandler;
import com.github.skillfi.reincarnation_plus.core.network.play2client.SyncMagicInfuserMoltenColoringPacket;
import com.github.skillfi.reincarnation_plus.core.registry.*;
import com.github.skillfi.reincarnation_plus.core.registry.blocks.ReiBlockEntities;
import com.github.skillfi.reincarnation_plus.libs.data.gen.*;
import com.github.skillfi.reincarnation_plus.libs.data.pack.ReiData;
import com.github.skillfi.reincarnation_plus.libs.handler.*;
import com.github.skillfi.reincarnation_plus.core.network.ReiNetwork;
import lombok.Getter;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
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

@Mod(ReiMod.MODID)
@Mod.EventBusSubscriber(modid = ReiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReiMod {

    public static final String MODID = "reincarnation_plus";
    @Getter
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    private static final CommonProxy PROXY = DistExecutor.safeRunForDist(()->ClientProxy::new, ()->CommonProxy::new);

    public ReiMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        // Реєстрація конфігурацій
        setupConfig();

        // Реєстрація всіх необхідних компонентів
        registerComponents(modEventBus);

        // Реєстрація подій
        modEventBus.addListener(CapabilityHandler::registerCapabilities);
        modEventBus.addListener(RPEntityHandler::registerEntityPlacements);
        modEventBus.addListener(RPEntityHandler::entityAttributeEvent);
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::generateData);
        modEventBus.addListener(this::setupComplete);
        ReiData.MAGIC_INFUSER_MOLTEN_MATERIALS.subscribeAsSyncable(ReiNetwork.INSTANCE, SyncMagicInfuserMoltenColoringPacket::new);

        GeckoLib.initialize();
    }

    private void setupConfig() {
        FileUtils.getOrCreateDirectory(FMLPaths.CONFIGDIR.get().resolve(MODID), MODID);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ReiConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SpawnRateConfig.SPEC, getConfigFileName("spawnrate-common"));
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EntityConfig.SPEC, getConfigFileName("entity-common"));
    }

    private void registerComponents(IEventBus modEventBus) {
        // Реєстрація в реєстрах мода
        ReiRegistry.register(modEventBus);
    }

    private void setupComplete(final FMLLoadCompleteEvent event) {
        PROXY.postInit();
    }

    private void generateData(final GatherDataEvent event) {
        var generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new ReiLanguageProvider(event));
        generator.addProvider(event.includeServer(), new ReiEntityEPProvider(event));
        generator.addProvider(event.includeServer(), new ReiEntityTypeTagProvider(event));
        generator.addProvider(event.includeServer(), new ReiGearEpProvider(event));
        generator.addProvider(event.includeServer(), new ReiRecipeProvider(event));
        generator.addProvider(event.includeServer(), new ReiLevelAuraModifiersProvider(event));
        generator.addProvider(event.includeServer(), new ReiBiomeAuraModifiersProvider(event));
        generator.addProvider(event.includeServer(), new ReiMoltenMaterialProvider(event));
        ReiBlockTagProvider blockTagProvider = new ReiBlockTagProvider(event);
        event.getGenerator().addProvider(event.includeServer(), blockTagProvider);
        event.getGenerator().addProvider(event.includeServer(), new ReiItemTagProvider(event, blockTagProvider));
    }

    private void setup(final FMLCommonSetupEvent event) {
        ReiNetwork.register();
        event.enqueueWork(PROXY::setup);
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        LOGGER.info(ReiEntities.LOADED_ENTITIES);
    }

    private String getConfigFileName(String name) {
        return String.format("%s/%s.toml", MODID, name);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ReiEntities.OGRE.get(), OgreRenderer::new);
        EntityRenderers.register(ReiEntities.KIJIN.get(), KijinRenderer::new);
        EntityRenderers.register(ReiEntities.ONI.get(), OniRenderer::new);
        EntityRenderers.register(ReiEntities.DIVINE_ONI.get(), DivineOniRenderer::new);
        EntityRenderers.register(ReiEntities.DIVINE_FIGHTER.get(), DivineFighterRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ReiBlockEntities.CORE_BLOCK_ENTITY.get(), MagiculeCoreRenderer::new);
    }
}
