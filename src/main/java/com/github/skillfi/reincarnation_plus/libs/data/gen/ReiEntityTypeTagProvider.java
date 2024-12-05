package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.manasmods.tensura.data.TensuraTags;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.entity.ReiEntities;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

public class ReiEntityTypeTagProvider extends EntityTypeTagsProvider {
    public ReiEntityTypeTagProvider(DataGenerator pGenerator, String modId, ExistingFileHelper existingFileHelper) {
        super(pGenerator, modId, existingFileHelper);
    }

    public ReiEntityTypeTagProvider(GatherDataEvent event) {
        this(event.getGenerator(), ReiMod.MODID, event.getExistingFileHelper());
    }

    public void addTags() {
        tag(TensuraTags.EntityTypes.DROP_CRYSTAL).add(ReiEntities.OGRE.get(),
                ReiEntities.KIJIN.get(), ReiEntities.ONI.get(),
                 ReiEntities.DIVINE_ONI.get(), ReiEntities.DIVINE_FIGHTER.get());
        tag(TensuraTags.EntityTypes.NAMEABLE).add(ReiEntities.OGRE.get(),
                ReiEntities.KIJIN.get(), ReiEntities.ONI.get(),
                ReiEntities.DIVINE_ONI.get(), ReiEntities.DIVINE_FIGHTER.get());

    }
}
