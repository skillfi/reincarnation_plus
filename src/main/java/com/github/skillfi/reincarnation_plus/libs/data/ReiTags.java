package com.github.skillfi.reincarnation_plus.libs.data;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ReiTags {

    public ReiTags() {}

    public static class Items {
        public static final TagKey<Item> DEEPSLATE = modTag("deepslate");

        static TagKey<Item> modTag(String name) {
            return ItemTags.create(new ResourceLocation(ReiMod.MODID, name));
        }

        static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> DEEPSLATE = create("deepslate");

        private static TagKey<Block> create(String pName) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(pName));
        }

        public static TagKey<Block> create(ResourceLocation name) {
            return TagKey.create(Registry.BLOCK_REGISTRY, name);
        }
    }
}
