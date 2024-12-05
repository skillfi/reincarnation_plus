package com.github.skillfi.reincarnation_plus.core.network.play2client;

import com.github.skillfi.reincarnation_plus.libs.capability.aura.AuraChunkCapabilityImpl;
import com.github.skillfi.reincarnation_plus.libs.capability.subordinates.SubordinateCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class ClientAccess {
    static final Minecraft minecraft = Minecraft.getInstance();

    ClientAccess() {
    }

    public static void updateSubordinateCapability(int entityId, CompoundTag tag) {
        if (minecraft.level != null) {
            Entity entity = minecraft.level.getEntity(entityId);
            if (entity instanceof Player) {
                Player player = (Player)entity;
                SubordinateCapability.getFrom(player).ifPresent((data) -> data.deserializeNBT(tag));
                player.refreshDimensions();
            }

        }
    }

    public static void updateAura(SyncAuraPacket packet) {
        if (minecraft.level != null) {
            AuraChunkCapabilityImpl.get(minecraft.level.getChunk(packet.getPos().x, packet.getPos().z)).deserializeNBT(packet.getTag());
        }
    }
}
