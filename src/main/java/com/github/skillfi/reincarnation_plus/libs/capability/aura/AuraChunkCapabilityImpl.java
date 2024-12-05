package com.github.skillfi.reincarnation_plus.libs.capability.aura;

import com.github.manasmods.tensura.config.TensuraConfig;
import com.github.skillfi.reincarnation_plus.core.ReiConfig;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.api.aura.AuraEvent;
import com.github.skillfi.reincarnation_plus.core.network.ReiNetwork;
import com.github.skillfi.reincarnation_plus.core.network.play2client.SyncAuraPacket;
import lombok.Getter;
import lombok.NonNull;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the AuraChunkCapability, which manages aura-related logic
 * for chunks in a Minecraft-like environment.
 */
@Mod.EventBusSubscriber(
        modid = ReiMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class AuraChunkCapabilityImpl implements IAuraChunkCapability {
    public static final Capability<IAuraChunkCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<IAuraChunkCapability>() {
    });

    private static final ResourceLocation IDENTIFIER = new ResourceLocation(ReiMod.MODID, "aura");

    private double maxAura;
    private double regenerationRate;
    @Getter
    private int syncCounter;
    private long auraSeed = -1L;
    @Getter
    private double auraPercentage;
    @Nullable
    private LevelChunk chunk = null;
    private boolean dirty = false;
    private final Map<BlockPos, AuraCacheEntry> auraCache = new HashMap<>();

    /**
     * Constructor that initializes aura properties from configuration.
     */
    public AuraChunkCapabilityImpl() {
        this.maxAura = ReiConfig.INSTANCE.auraConfig.baseAura.get();
        this.regenerationRate = ReiConfig.INSTANCE.auraConfig.baseAuraRegeneration.get();
        this.syncCounter = ReiConfig.INSTANCE.auraConfig.modifierUpdateInterval.get();
    }

    /**
     * Synchronizes aura data with a player.
     *
     * @param player the player to synchronize with
     */
    @Override
    public void sync(ServerPlayer player) {
        if (player == null) {
            throw new NullPointerException("player is marked non-null but is null");
        } else if (this.chunk != null) {
            if (!this.chunk.getLevel().isClientSide()) {
                ReiNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncAuraPacket(this.chunk.getPos(), this.serializeNBT()));
            }
        }
    }

    /**
     * Gets the maximum aura value.
     *
     * @return the maximum aura
     */
    @Override
    public double getMaxAura() {
        return maxAura;
    }

    public void tick() {
        if (this.isInitialized()) {
            if (this.chunk != null) {
                this.regenerationTick();
                this.cacheTick();
                this.syncTick();
            }
        }
    }

    private void regenerationTick() {
        if (this.auraPercentage != (double)1.0F) {
            AuraEvent.Regeneration event = new AuraEvent.Regeneration(this.chunk, this);
            if (!MinecraftForge.EVENT_BUS.post(event)) {
                this.auraPercentage = (double)1.0F / this.maxAura * event.getNewAura();
                this.markDirty();
            }
        }
    }

    private void syncTick() {
        if (--this.syncCounter <= 0) {
            this.syncCounter = (Integer) ReiConfig.INSTANCE.auraConfig.AuraSyncInterval.get();
            if (this.dirty) {
                this.sync();
                this.dirty = false;
            }
        }
    }

    private void cacheTick() {
        this.auraCache.keySet().removeIf((pos) -> {
            AuraCacheEntry entry = (AuraCacheEntry)this.auraCache.get(pos);
            entry.tick();
            return entry.isOutdated();
        });
    }

    private boolean isInitialized() {
        if (this.chunk == null) {
            return true;
        } else if (this.chunk.getLevel().isClientSide()) {
            return true;
        } else {
            return this.auraSeed == (Long) ReiConfig.INSTANCE.auraConfig.auraSeed.get();
        }
    }

    /**
     * Consumes a specified amount of aura at a position.
     *
     * @param pos the position to consume aura from
     * @param amount the amount of aura to consume
     * @return true if aura was successfully consumed
     */
    public boolean consumeAura(BlockPos pos, double amount) {
        AuraCacheEntry entry = this.getOrCreateCacheEntry(pos);
        if (entry == null) {
            return false;
        } else {
            AuraEvent.Consume event = new AuraEvent.Consume(this.chunk, this, pos, amount);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return false;
            } else {
                double magicule = entry.getAura(this.maxAura, this.auraPercentage);
                if (magicule < event.getAmount()) {
                    return false;
                } else {
                    magicule -= event.getAmount();
                    this.auraPercentage = (double)1.0F / entry.getMaxAura(this.maxAura) * magicule;
                    this.markDirty();
                    return true;
                }
            }
        }
    }

    @Override
    public void setAura(BlockPos position, double amount) {
        AuraCacheEntry entry = this.getOrCreateCacheEntry(position);
        assert this.chunk != null;
        AuraEvent.Regeneration event = new AuraEvent.Regeneration(this.chunk, this);
        assert entry != null;
        double aura = entry.getAura(this.maxAura, this.auraPercentage);
        aura -= event.getNewAura();
        this.auraPercentage = (double)1.0F / entry.getMaxAura(this.maxAura) * aura;
        this.markDirty();
    }

    private void initialize(@NonNull LevelChunk chunk) {
        if (chunk == null) {
            throw new NullPointerException("chunk is marked non-null but is null");
        } else {
            this.chunk = chunk;
            if (!this.isInitialized()) {
                this.resetBaseValues();
                AuraEvent.Initialization event = new AuraEvent.Initialization(chunk, this);
                MinecraftForge.EVENT_BUS.post(event);
                this.maxAura = event.getNewMaxAura();
                this.regenerationRate = event.getNewRegenerationRate();
                this.auraPercentage = (double)1.0F;
                this.auraSeed = (Long) ReiConfig.INSTANCE.auraConfig.auraSeed.get();
                this.markDirty();
            }
        }
    }

    private void markDirty() {
        this.dirty = true;
        if (this.chunk != null) {
            this.chunk.setUnsaved(true);
        }
    }

    public static AuraChunkCapabilityImpl get(LevelChunk chunk) {
        AuraChunkCapabilityImpl capability = (AuraChunkCapabilityImpl)chunk.getCapability(CAPABILITY).orElseThrow(() -> new IllegalStateException("Aura capability not found!"));
        capability.initialize(chunk);
        return capability;
    }

    /**
     * Gets the aura regeneration rate.
     *
     * @return the regeneration rate
     */
    @Override
    public double getRegenerationRate() {
        return regenerationRate;
    }

    public void resetBaseValues() {
        this.maxAura = (Double) TensuraConfig.INSTANCE.magiculeConfig.baseMagicule.get();
        this.regenerationRate = (Double)TensuraConfig.INSTANCE.magiculeConfig.baseMagiculeRegeneration.get();
        this.syncCounter = (Integer)TensuraConfig.INSTANCE.magiculeConfig.magiculeSyncInterval.get();
        this.auraCache.clear();
        this.dirty = false;
    }

    @Nullable
    private AuraCacheEntry getOrCreateCacheEntry(BlockPos pos) {
        return (AuraCacheEntry)this.auraCache.computeIfAbsent(pos, (position) -> {
            if (this.chunk == null) {
                return null;
            } else {
                AuraEvent.RegisterModifier event = new AuraEvent.RegisterModifier(this.chunk, this, position);
                MinecraftForge.EVENT_BUS.post(event);
                return AuraCacheEntry.of(event.getModifiers());
            }
        });
    }

    /**
     * Gets the current aura value.
     *
     * @return the current aura
     */
    @Override
    public double getAura() {
        return auraCache.values().stream()
                .mapToDouble(entry -> entry.getAura(maxAura, entry.getAura(maxAura, regenerationRate)))
                .sum();
    }

    /**
     * Gets the aura value at the specified position.
     *
     * @param position the block position
     * @return the aura at the position
     */
    @Override
    public double getAura(BlockPos position) {
        AuraCacheEntry entry = this.getOrCreateCacheEntry(position);
        return entry == null ? this.maxAura * this.auraPercentage : entry.getAura(maxAura, entry.getAura(maxAura, regenerationRate));
    }

    /**
     * Gets the maximum aura at the specified position.
     *
     * @param position the block position
     * @return the max aura at the position
     */
    @Override
    public double getMaxAura(BlockPos position) {
        AuraCacheEntry entry = auraCache.get(position);
        return entry != null ? entry.getMaxAura(maxAura) : 0;
    }

    @SubscribeEvent
    static void attach(AttachCapabilitiesEvent<LevelChunk> e) {
        e.addCapability(IDENTIFIER, new AuraCapabilityProvider());
    }

    private void sync() {
        if (this.chunk != null) {
            if (!this.chunk.getLevel().isClientSide()) {
                ReiNetwork.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> this.chunk), new SyncAuraPacket(this.chunk.getPos(), this.serializeNBT()));
            }
        }
    }

    /**
     * Serializes the aura data to NBT format.
     *
     * @return the serialized NBT data
     */
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("auraSeed", this.auraSeed);
        tag.putDouble("maxAura", this.maxAura);
        tag.putDouble("regenerationRate", this.regenerationRate);
        tag.putDouble("auraPercentage", this.auraPercentage);
        return tag;
    }

    /**
     * Deserializes the aura data from NBT format.
     *
     * @param compoundTag the NBT data
     */
    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        this.auraSeed = compoundTag.getLong("auraSeed");
        this.maxAura = compoundTag.getDouble("maxAura");
        this.regenerationRate = compoundTag.getDouble("regenerationRate");
        this.auraPercentage = compoundTag.getDouble("auraPercentage");
    }


}