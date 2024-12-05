package com.github.skillfi.reincarnation_plus.core.network.play2client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncAuraPacket {

    private final ChunkPos pos;
    private final CompoundTag tag;

    public SyncAuraPacket(FriendlyByteBuf buf) {
        this.pos = buf.readChunkPos();
        this.tag = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeChunkPos(this.pos);
        buf.writeNbt(this.tag);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> ClientAccess.updateAura(this));
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }

    public SyncAuraPacket(ChunkPos pos, CompoundTag tag) {
        this.pos = pos;
        this.tag = tag;
    }

    public ChunkPos getPos() {
        return this.pos;
    }

    public CompoundTag getTag() {
        return this.tag;
    }
}
