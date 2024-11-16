package com.github.skillfi.reincarnation_plus.data.gen;

import com.github.manasmods.manascore.api.data.gen.LanguageProvider;
import com.github.skillfi.reincarnation_plus.RPMod;
import net.minecraftforge.data.event.GatherDataEvent;

public class RPLanguageProvider extends LanguageProvider {

    public RPLanguageProvider(GatherDataEvent gatherDataEvent) {
        super(gatherDataEvent, RPMod.MODID);
    }


    @Override
    protected void generate() {

    }
}
