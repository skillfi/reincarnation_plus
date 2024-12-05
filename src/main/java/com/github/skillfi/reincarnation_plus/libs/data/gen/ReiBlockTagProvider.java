package com.github.skillfi.reincarnation_plus.libs.data.gen;

import com.github.manasmods.manascore.api.data.gen.BlockTagProvider;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.data.ReiTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.data.event.GatherDataEvent;

public class ReiBlockTagProvider extends BlockTagProvider {
    public ReiBlockTagProvider(GatherDataEvent gatherDataEvent) {
        super(gatherDataEvent, ReiMod.MODID);
    }

    @Override
    protected void generate() {
        this.tag(ReiTags.Blocks.DEEPSLATE).add(new Block[]{Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_BRICK_SLAB, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.POLISHED_DEEPSLATE});
    }
}
