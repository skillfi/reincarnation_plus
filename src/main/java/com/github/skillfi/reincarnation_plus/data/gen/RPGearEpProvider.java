package com.github.skillfi.reincarnation_plus.data.gen;


import com.github.manasmods.manascore.api.data.gen.CustomDataProvider;
import com.github.manasmods.tensura.data.pack.GearEPCount;
import com.github.skillfi.reincarnation_plus.init.RPItems;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class RPGearEpProvider extends CustomDataProvider {

    public RPGearEpProvider(GatherDataEvent event) {
        super("gear/ep", event.getGenerator());
    }

    public String getName() {
        return "Reincarnation Plus Gear EP";
    }

    protected void run(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        GearEPCount.of(RPItems.JACKET_ARMOR.getId(), 1000, 5000, 0.01);
        GearEPCount.of(RPItems.LEATHER_CHESTPLATE.getId(), 1000, 5000, 0.01);
        GearEPCount.of(RPItems.LEATHER_LEGGINGS.getId(), 1000, 5000, 0.01);
        GearEPCount.of(RPItems.LEATHER_WOMAN_LEGGINGS.getId(), 1000, 5000, 0.01);
        GearEPCount.of(RPItems.YUKATA.getId(), 1000, 5000, 0.01);
        GearEPCount.of(RPItems.KIMONO.getId(), 1000, 5000, 0.01);
    }


}
