package com.github.skillfi.reincarnation_plus.data.gen;

import com.github.manasmods.tensura.data.TensuraTags;
import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.entity.RPEntities;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

public class RPEntityTypeTagProvider extends EntityTypeTagsProvider {
    public RPEntityTypeTagProvider(DataGenerator pGenerator, String modId, ExistingFileHelper existingFileHelper) {
        super(pGenerator, modId, existingFileHelper);
    }

    public RPEntityTypeTagProvider(GatherDataEvent event) {
        this(event.getGenerator(), RPMod.MODID, event.getExistingFileHelper());
    }

    public void addTags() {
        tag(TensuraTags.EntityTypes.DROP_CRYSTAL).add(RPEntities.OGRE.get(), RPEntities.KIJIN.get());
        tag(TensuraTags.EntityTypes.NAMEABLE).add(RPEntities.OGRE.get());

    }
}
