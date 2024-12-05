package com.github.skillfi.reincarnation_plus.core.network;

import com.github.skillfi.reincarnation_plus.libs.capability.subordinates.ISubordinateCapability;
import com.github.skillfi.reincarnation_plus.core.network.play2client.ClientAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncSubordinateCapabilityPacket {
    private final CompoundTag tag;
    private final int entityId;

    public SyncSubordinateCapabilityPacket(FriendlyByteBuf buf) {
        this.tag = buf.readAnySizeNbt();
        this.entityId = buf.readInt();
    }

    public SyncSubordinateCapabilityPacket(ISubordinateCapability data, int entityId) {
        this.tag = data.serializeNBT();
        this.entityId = entityId;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(this.tag);
        buf.writeInt(this.entityId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.updateSubordinateCapability(this.entityId, this.tag)));
        ctx.get().setPacketHandled(true);
    }

}
