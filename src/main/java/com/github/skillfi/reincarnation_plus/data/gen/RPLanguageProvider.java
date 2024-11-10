package com.github.skillfi.reincarnation_plus.data.gen;

import com.github.manasmods.manascore.api.data.gen.LanguageProvider;
import com.github.skillfi.reincarnation_plus.ReincarnationPlus;
import net.minecraftforge.data.event.GatherDataEvent;

public class RPLanguageProvider extends LanguageProvider {

    public RPLanguageProvider(GatherDataEvent gatherDataEvent) {
        super(gatherDataEvent, ReincarnationPlus.MODID);
    }

    protected void generate(){}

    protected void addClass(){}
}
