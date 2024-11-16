package com.github.skillfi.reincarnation_plus.event;

import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.client.armor.LeatherArmorRenderer;
import com.github.skillfi.reincarnation_plus.client.armor.JacketWomanArmorRenderer;
import com.github.skillfi.reincarnation_plus.item.custom.JacketWomanArmorItem;
import com.github.skillfi.reincarnation_plus.item.custom.LeatherArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {

    @SubscribeEvent
    public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event) {
        LeatherArmorRenderer.registerArmorRenderer(LeatherArmorItem.class, LeatherArmorRenderer::new);
        JacketWomanArmorRenderer.registerArmorRenderer(JacketWomanArmorItem.class, JacketWomanArmorRenderer::new);
    }
}
