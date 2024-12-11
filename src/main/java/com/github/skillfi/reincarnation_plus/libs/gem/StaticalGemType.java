package com.github.skillfi.reincarnation_plus.libs.gem;

import com.github.skillfi.reincarnation_plus.libs.api.gem.GemType;
import com.github.skillfi.reincarnation_plus.core.registry.items.ReiItems;
import net.minecraft.world.item.ItemStack;

public class StaticalGemType extends GemType {

    public StaticalGemType(String id) {
        super(id);
    }

    @Override
    public ItemStack getGem() {
        return ReiItems.GEM_STONE.get().getDefaultInstance();
    }
}
