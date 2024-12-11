package com.github.skillfi.reincarnation_plus.core.network;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.network.play2client.ItemStackSyncS2CPacket;
import com.github.skillfi.reincarnation_plus.core.network.play2client.SyncAuraPacket;
import com.github.skillfi.reincarnation_plus.core.network.play2client.SyncMagicInfuserMoltenColoringPacket;
import com.github.skillfi.reincarnation_plus.core.network.play2server.RequuestMagicInfuserActionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ReiNetwork {
    private static final String PROTOCOL_VERSION = ModList.get().getModFileById(ReiMod.MODID).versionString().replaceAll("\\.", "");
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ReiMod.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public ReiNetwork(){}

    public static void  register(){
        int i = 0;
        ++i;
        INSTANCE.registerMessage(i, SyncSubordinateCapabilityPacket.class, SyncSubordinateCapabilityPacket::toBytes, SyncSubordinateCapabilityPacket::new, SyncSubordinateCapabilityPacket::handle);
        ++i;
        INSTANCE.registerMessage(i, SyncAuraPacket.class, SyncAuraPacket::toBytes, SyncAuraPacket::new, SyncAuraPacket::handle);
        ++i;
        INSTANCE.registerMessage(i, SyncMagicInfuserMoltenColoringPacket.class, SyncMagicInfuserMoltenColoringPacket::toBytes, SyncMagicInfuserMoltenColoringPacket::new, SyncMagicInfuserMoltenColoringPacket::handle);
        ++i;
        INSTANCE.registerMessage(i, RequuestMagicInfuserActionPacket.class, RequuestMagicInfuserActionPacket::toBytes, RequuestMagicInfuserActionPacket::new, RequuestMagicInfuserActionPacket::handle);
        ++i;
        INSTANCE.registerMessage(i, ItemStackSyncS2CPacket.class, ItemStackSyncS2CPacket::toBytes, ItemStackSyncS2CPacket::new, ItemStackSyncS2CPacket::handle);
    }
}
