package com.github.skillfi.reincarnation_plus.libs.data.pack;

import com.github.manasmods.manascore.api.datapack.CodecJsonDataManager;
import com.github.manasmods.tensura.data.pack.KilnMoltenMaterial;
import com.github.manasmods.tensura.network.play2client.SyncKilnMoltenColoringPacket;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.network.play2client.SyncMagicInfuserMoltenColoringPacket;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

@Mod.EventBusSubscriber(
        modid = ReiMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class ReiData {
    public static final CodecJsonDataManager<MagicInfuserMoltenMaterial> MAGIC_INFUSER_MOLTEN_MATERIALS;

    public ReiData() {}

    public static Collection<MagicInfuserMoltenMaterial> getMagicInfuserMoltenMaterials() {
        return (Collection) DistExecutor.unsafeRunForDist(() -> () -> SyncMagicInfuserMoltenColoringPacket.SYNCED_DATA.values(), () -> () -> MAGIC_INFUSER_MOLTEN_MATERIALS.getData().values());
    }

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent e) {
        e.addListener(MAGIC_INFUSER_MOLTEN_MATERIALS);
    }

    static {
        MAGIC_INFUSER_MOLTEN_MATERIALS = new CodecJsonDataManager("magic_infuser/materials", MagicInfuserMoltenMaterial.CODEC);
    }
}
