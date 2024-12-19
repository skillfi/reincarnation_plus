package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.manasmods.manascore.api.data.gen.CustomDataProvider;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.data.pack.MagicInfuserMoltenMaterial;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.event.GatherDataEvent;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ReiMoltenMaterialProvider extends CustomDataProvider {
    public static final ResourceLocation MOLTEN_MAGICULES = new ResourceLocation(ReiMod.MODID, "magicules");
    public static final ResourceLocation EP = new ResourceLocation(ReiMod.MODID, "existence_points");

    public ReiMoltenMaterialProvider(GatherDataEvent gatherDataEvent) {
        super("magic_infuser/materials", gatherDataEvent.getGenerator());
    }

    @Override
    protected void run(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        MagicInfuserMoltenMaterial.of(MOLTEN_MAGICULES, false, new Color(0, 233, 255, 255), true).buildJson(biConsumer);
        MagicInfuserMoltenMaterial.of(EP, true, Color.CYAN, false).buildJson(biConsumer);
    }

    public String getName() {
        return "Reincarnation Plus Molten Materials";
    }
}
