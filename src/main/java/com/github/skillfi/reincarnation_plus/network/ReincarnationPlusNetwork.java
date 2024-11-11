package com.github.skillfi.reincarnation_plus.network;

import com.github.skillfi.isekaicore.network.toclient.SyncClassesPacket;
import com.github.skillfi.reincarnation_plus.ReincarnationPlusMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ReincarnationPlusNetwork {
    private static final String PROTOCOL_VERSION = ModList.get().getModFileById(ReincarnationPlusMod.MODID).versionString().replaceAll("\\.", "");
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ReincarnationPlusMod.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void  register(){
        int i = 0;

        INSTANCE.registerMessage(++i, ReincarnationPlusModVariables.PlayerVariablesSyncMessage.class, ReincarnationPlusModVariables.PlayerVariablesSyncMessage::buffer, ReincarnationPlusModVariables.PlayerVariablesSyncMessage::new, ReincarnationPlusModVariables.PlayerVariablesSyncMessage::handler);
        INSTANCE.registerMessage(++i, SyncClassesPacket.class, SyncClassesPacket::toBytes, SyncClassesPacket::new, SyncClassesPacket::handle);
    }
}
