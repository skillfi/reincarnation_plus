package com.github.skillfi.reincarnation_plus.data.gen;
import com.github.manasmods.manascore.api.data.gen.CustomDataProvider;
import com.github.manasmods.tensura.data.pack.EntityEPCount;
import com.github.manasmods.tensura.registry.skill.*;
import com.github.skillfi.reincarnation_plus.entity.RPEntitiesStats;
import com.github.skillfi.reincarnation_plus.entity.RPEntities;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class RPEntityEPProvider extends CustomDataProvider{

    public RPEntityEPProvider(GatherDataEvent gatherDataEvent) {
        super("entity/ep", gatherDataEvent.getGenerator());
    }

    public static void add() {
        RPEntitiesStats.Ogre.addToList(CommonSkills.STRENGTH.getId());
        RPEntitiesStats.Ogre.addToList(UniqueSkills.FIGHTER.getId());
        RPEntitiesStats.Ogre.addToList(IntrinsicSkills.OGRE_BERSERKER.getId());
    }

    protected void run(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        RPEntityEPProvider.add();
        EntityEPCount.of(EntityType.getKey(RPEntities.OGRE.get()),
                RPEntitiesStats.Ogre.getMinEP(),
                RPEntitiesStats.Ogre.getMaxEP(),
                RPEntitiesStats.Ogre.getList()).buildJson(biConsumer);

    }

    public String getName() {
        return "Reincarnation Plus Entity EP";
    }
}
