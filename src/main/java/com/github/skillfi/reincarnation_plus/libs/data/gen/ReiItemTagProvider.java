package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.manasmods.manascore.api.data.gen.BlockTagProvider;
import com.github.manasmods.manascore.api.data.gen.ItemTagProvider;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.data.ReiTags;
import net.minecraftforge.data.event.GatherDataEvent;

public class ReiItemTagProvider extends ItemTagProvider {
    public ReiItemTagProvider(GatherDataEvent gatherDataEvent, BlockTagProvider blockTagProvider) {
        super(gatherDataEvent, ReiMod.MODID, blockTagProvider);
    }

    @Override
    protected void generate() {
        this.copy(ReiTags.Blocks.DEEPSLATE, ReiTags.Items.DEEPSLATE);
    }
}
