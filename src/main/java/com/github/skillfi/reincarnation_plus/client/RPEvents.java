package com.github.skillfi.reincarnation_plus.client;

import com.github.skillfi.reincarnation_plus.RPMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RPEvents {

    public static ModelLayerLocation HIGH_WOMAN = new ModelLayerLocation(
            new ResourceLocation(RPMod.MODID+"high_woman"), "main");
    public static ModelLayerLocation SHORT_WOMAN = new ModelLayerLocation(
            new ResourceLocation(RPMod.MODID+ "short_woman"), "main");
    public static ModelLayerLocation SHORT_MAN = new ModelLayerLocation(
            new ResourceLocation(RPMod.MODID+ "short_man"), "main");

}
