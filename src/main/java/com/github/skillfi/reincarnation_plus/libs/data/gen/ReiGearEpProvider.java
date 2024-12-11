package com.github.skillfi.reincarnation_plus.libs.data.gen;


import com.github.manasmods.manascore.api.data.gen.CustomDataProvider;
import com.github.manasmods.tensura.data.pack.GearEPCount;
import com.github.skillfi.reincarnation_plus.core.registry.items.ReiItems;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ReiGearEpProvider extends CustomDataProvider {

    public ReiGearEpProvider(GatherDataEvent event) {
        super("gear/ep", event.getGenerator());
    }

    public @NotNull String getName() {
        return "Reincarnation Plus Gear EP";
    }

    protected void run(@NotNull BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        monsterLeatherD(biConsumer);
    }

    protected static void monsterLeatherD(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        GearEPCount.of(ReiItems.MONSTER_LEATHER_JACKET_ARMOR_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.HAKAMA_CHESTPLATE_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.HAKAMA_LEGGINGS_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.MONSTER_LEATHER_WOMAN_LEGGINGS_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.YUKATA_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.KIMONO_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
    }

}
