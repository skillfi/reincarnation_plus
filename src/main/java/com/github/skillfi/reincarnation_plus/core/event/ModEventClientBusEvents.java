package com.github.skillfi.reincarnation_plus.core.event;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.item.armor.client.KimonoWomanArmorRenderer;
import com.github.skillfi.reincarnation_plus.core.item.armor.client.LeatherArmorRenderer;
import com.github.skillfi.reincarnation_plus.core.item.armor.client.JacketWomanArmorRenderer;
import com.github.skillfi.reincarnation_plus.core.item.armor.JacketWomanArmorItem;
import com.github.skillfi.reincarnation_plus.core.item.armor.KimonoWomanArmorItem;
import com.github.skillfi.reincarnation_plus.core.item.armor.LeatherArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ReiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {

    @SubscribeEvent
    public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event) {
        LeatherArmorRenderer.registerArmorRenderer(LeatherArmorItem.class, LeatherArmorRenderer::new);
        JacketWomanArmorRenderer.registerArmorRenderer(JacketWomanArmorItem.class, JacketWomanArmorRenderer::new);
        KimonoWomanArmorRenderer.registerArmorRenderer(KimonoWomanArmorItem.class, KimonoWomanArmorRenderer::new);
    }
}
