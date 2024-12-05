package com.github.skillfi.reincarnation_plus.libs.data.gen;
import com.github.manasmods.manascore.api.data.gen.CustomDataProvider;
import com.github.manasmods.tensura.data.pack.EntityEPCount;
import com.github.skillfi.reincarnation_plus.core.entity.RPEntitiesStats;
import com.github.skillfi.reincarnation_plus.core.entity.ReiEntities;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ReiEntityEPProvider extends CustomDataProvider{

    public ReiEntityEPProvider(GatherDataEvent gatherDataEvent) {
        super("entity/ep", gatherDataEvent.getGenerator());
    }


    protected void run(@NotNull BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {

        EntityEPCount.of(EntityType.getKey(ReiEntities.OGRE.get()),
                (int) RPEntitiesStats.Ogre.getMinEP(),
                (int) RPEntitiesStats.Ogre.getMaxEP(),
                RPEntitiesStats.Ogre.getList()).buildJson(biConsumer);
        EntityEPCount.of(EntityType.getKey(ReiEntities.KIJIN.get()),
                (int) RPEntitiesStats.Kijin.getMinEP(),
                (int) RPEntitiesStats.Kijin.getMaxEP(),
                RPEntitiesStats.Kijin.getList()).buildJson(biConsumer);
        EntityEPCount.of(EntityType.getKey(ReiEntities.ONI.get()),
                (int) RPEntitiesStats.MysticOni.getMinEP(),
                (int) RPEntitiesStats.MysticOni.getMaxEP(),
                RPEntitiesStats.MysticOni.getList()).buildJson(biConsumer);
        EntityEPCount.of(EntityType.getKey(ReiEntities.DIVINE_ONI.get()),
                (int) RPEntitiesStats.DivineOni.getMinEP(),
                (int) RPEntitiesStats.DivineOni.getMaxEP()).buildJson(biConsumer);
        EntityEPCount.of(EntityType.getKey(ReiEntities.DIVINE_FIGHTER.get()),
                (int) RPEntitiesStats.DivineFighter.getMinEP(),
                (int) RPEntitiesStats.DivineFighter.getMaxEP()).buildJson(biConsumer);

    }

    public @NotNull String getName() {
        return "Reincarnation Plus Entity EP";
    }
}
