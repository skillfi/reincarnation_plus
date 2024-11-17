package com.github.skillfi.reincarnation_plus.gem;

import com.github.skillfi.reincarnation_plus.api.gem.GemType;
import com.github.skillfi.reincarnation_plus.handler.RPItems;
import net.minecraft.world.item.ItemStack;

public class StaticalGemType extends GemType {

    public StaticalGemType(String id) {
        super(id);
    }

    @Override
    public ItemStack getGem() {
        return RPItems.GEM_STONE.get().getDefaultInstance();
    }
}
