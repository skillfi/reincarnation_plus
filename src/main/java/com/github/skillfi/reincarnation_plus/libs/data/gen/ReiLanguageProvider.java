package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.manasmods.manascore.api.data.gen.LanguageProvider;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import net.minecraftforge.data.event.GatherDataEvent;

public class ReiLanguageProvider extends LanguageProvider {

    public ReiLanguageProvider(GatherDataEvent gatherDataEvent) {
        super(gatherDataEvent, ReiMod.MODID);
    }


    @Override
    protected void generate() {

    }
}
