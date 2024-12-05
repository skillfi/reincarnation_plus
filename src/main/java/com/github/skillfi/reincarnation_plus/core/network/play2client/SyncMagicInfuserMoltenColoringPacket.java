package com.github.skillfi.reincarnation_plus.core.network.play2client;

import com.github.skillfi.reincarnation_plus.libs.data.pack.MagicInfuserMoltenMaterial;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SyncMagicInfuserMoltenColoringPacket {
    private static final Logger log = LogManager.getLogger(SyncMagicInfuserMoltenColoringPacket.class);
    private static final Codec<Map<ResourceLocation, MagicInfuserMoltenMaterial>> MAPPER;
    public static Map<ResourceLocation, MagicInfuserMoltenMaterial> SYNCED_DATA;
    private final Map<ResourceLocation, MagicInfuserMoltenMaterial> map;

    public SyncMagicInfuserMoltenColoringPacket(FriendlyByteBuf buf) {
        this.map = (Map)MAPPER.parse(NbtOps.INSTANCE, buf.readNbt()).result().orElse(new HashMap());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt((CompoundTag)MAPPER.encodeStart(NbtOps.INSTANCE, this.map).result().orElse(new CompoundTag()));
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(this::handleOnMainThread);
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }

    private void handleOnMainThread() {
        SYNCED_DATA = this.map;
        log.debug("Got {} MagicInfuserRecipeDefinitions from Server", this.map.size());
    }

    public SyncMagicInfuserMoltenColoringPacket(Map<ResourceLocation, MagicInfuserMoltenMaterial> map) {
        this.map = map;
    }

    static {
        MAPPER = Codec.unboundedMap(ResourceLocation.CODEC, MagicInfuserMoltenMaterial.CODEC);
        SYNCED_DATA = new HashMap();
    }
}
