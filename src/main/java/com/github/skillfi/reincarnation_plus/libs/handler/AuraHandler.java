package com.github.skillfi.reincarnation_plus.libs.handler;

import com.github.skillfi.reincarnation_plus.core.ReiConfig;
import com.github.skillfi.reincarnation_plus.libs.api.aura.AuraEvent;
import com.github.skillfi.reincarnation_plus.libs.capability.aura.AuraChunkCapabilityImpl;
import com.github.skillfi.reincarnation_plus.libs.capability.aura.IAuraChunkCapability;
import com.github.skillfi.reincarnation_plus.libs.data.pack.BiomeAuraModifier;
import com.github.skillfi.reincarnation_plus.libs.data.pack.LevelAuraModifier;
import com.github.skillfi.reincarnation_plus.core.registry.ReiBiomeAuraModifier;
import com.github.skillfi.reincarnation_plus.core.registry.ReiLevelAuraModifier;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber
public class AuraHandler {

    private static final Map<ChunkPos, Long> lastAuraTransfer = new ConcurrentHashMap<>();
    private static final long TRANSFER_COOLDOWN_MS = 60000; // Кулдаун 60 секунд
    private static final double TRANSFER_PERCENTAGE = 0.1; // Передається 10% від різниці

    public AuraHandler() {}

    @SubscribeEvent
    static void onAuraInit(AuraEvent.Initialization e) {
        Level world = e.level;
        if (!world.isClientSide()) {
            applyLevelModifier(e, world);
            applyBiomeModifier(e, world);
//            distributeAuraToNeighbors(e.chunk, world);
        }
    }

    private static void distributeAuraToNeighbors(LevelChunk chunk, Level world) {
        ChunkPos chunkPos = chunk.getPos();
        long currentTime = System.currentTimeMillis();

        // Перевіряємо кулдаун для цього чанка
        lastAuraTransfer.compute(chunkPos, (pos, lastTransferTime) -> {
            if (lastTransferTime != null && lastTransferTime + TRANSFER_COOLDOWN_MS > currentTime) {
                return lastTransferTime; // Ще не минув кулдаун
            }

            IAuraChunkCapability cap = AuraChunkCapabilityImpl.get(chunk);
            double currentAura = cap.getAura();

            // Передаємо ауру сусіднім чанкам
            for (Direction direction : Direction.values()) {
                ChunkPos neighborPos = new ChunkPos(chunkPos.x + direction.getStepX(), chunkPos.z + direction.getStepZ());
                LevelChunk neighborChunk = getLoadedChunk(world, neighborPos);
                if (neighborChunk == null) continue;

                IAuraChunkCapability neighborCap = AuraChunkCapabilityImpl.get(neighborChunk);
                double neighborAura = neighborCap.getAura();

                if (currentAura > neighborAura) {
                    double difference = currentAura - neighborAura;
                    double transferAmount = difference * TRANSFER_PERCENTAGE;

                    // Передаємо ауру
                    cap.consumeAura(chunkPos.getWorldPosition(), transferAmount); // Віднімаємо ауру з поточного чанка
                    neighborCap.setAura(neighborPos.getWorldPosition(), transferAmount); // Додаємо ауру до сусіднього чанка
                }
            }

            return currentTime;
        });
    }

    // Метод для отримання завантаженого чанка
    private static LevelChunk getLoadedChunk(Level world, ChunkPos pos) {
        return (LevelChunk) world.getChunk(pos.x, pos.z, ChunkStatus.FULL, false);
    }

    private static void applyBiomeModifier(AuraEvent.Initialization e, Level world) {
        LevelChunk chunk = e.chunk;
        Map<Biome, Integer> biomeProbes = new HashMap();
        AtomicInteger totalProbes = new AtomicInteger(0);

        for(LevelChunkSection chunkSection : chunk.getSections()) {
            chunkSection.getBiomes().getAll((biomeHolder) -> {
                Biome biome = (Biome)biomeHolder.get();
                biomeProbes.put(biome, (Integer)biomeProbes.getOrDefault(biome, 0) + 1);
                totalProbes.incrementAndGet();
            });
        }

        int maxProbes = totalProbes.get();
        if (maxProbes != 0) {
            Registry<BiomeAuraModifier> modifierRegistry = world.registryAccess().registryOrThrow(ReiBiomeAuraModifier.REGISTRY_KEY);
            Registry<Biome> biomeRegistry = world.registryAccess().registryOrThrow(ForgeRegistries.BIOMES.getRegistryKey());
            AtomicDouble totalAura = new AtomicDouble((double)0.0F);
            AtomicDouble regenerationRateResult = new AtomicDouble((double)0.0F);
            biomeProbes.forEach((biome, probes) -> {
                ResourceLocation biomeId = biomeRegistry.getKey(biome);
                if (biomeId != null) {
                    BiomeAuraModifier modifier = (BiomeAuraModifier)modifierRegistry.get(biomeId);
                    double partialSize = (double)1.0F / (double)maxProbes * (double)probes;
                    if (modifier == null) {
                        totalAura.addAndGet((Double) ReiConfig.INSTANCE.auraConfig.baseAura.get() * partialSize);
                        regenerationRateResult.addAndGet((Double) ReiConfig.INSTANCE.auraConfig.baseAuraRegeneration.get() * partialSize);
                    } else {
                        totalAura.addAndGet(modifier.getMaxAura(e.getNewMaxAura()) * partialSize);
                        regenerationRateResult.addAndGet(modifier.getRegenerationRate(e.getNewRegenerationRate()) * partialSize);
                    }
                }
            });
            e.setNewMaxAura(totalAura.get());
            e.setNewRegenerationRate(regenerationRateResult.get());
        }
    }

    private static void applyLevelModifier(AuraEvent.Initialization e, Level world) {
        Registry<LevelAuraModifier> registry = world.registryAccess().registryOrThrow(ReiLevelAuraModifier.REGISTRY_KEY);
        LevelAuraModifier modifier = (LevelAuraModifier)registry.get(world.dimension().location());
        if (modifier != null) {
            e.setNewMaxAura(modifier.getMaxAura(e.getNewMaxAura()));
            e.setNewRegenerationRate(modifier.getRegenerationRate(e.getNewRegenerationRate()));
        }
    }
}
