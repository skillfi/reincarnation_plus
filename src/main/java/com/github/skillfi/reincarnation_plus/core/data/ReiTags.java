package com.github.skillfi.reincarnation_plus.core.data;

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
        public static final TagKey<Item> DEEPSLATE = forgeTag("deepslate");
        public static final TagKey<Item> LOW_MAGISTEEL = modTag("ep/low_magisteel");
        public static final TagKey<Item> HIGH_MAGISTEEL = modTag("ep/high_magisteel");
        public static final TagKey<Item> PURE_MAGISTEEL = modTag("ep/pure_magisteel");
        public static final TagKey<Item> ADAMANTITE = modTag("ep/adamantite");
        public static final TagKey<Item> MITHRIL = modTag("ep/mithril");
        public static final TagKey<Item> CHARYBDIS = modTag("ep/charybdis");
        public static final TagKey<Item> ORICHALCUM = modTag("ep/orichalcum");
        public static final TagKey<Item> HIHIIROKANE = modTag("ep/hihiirokane");
        public static final TagKey<Item> GOLD = modTag("ep/gold");
        public static final TagKey<Item> IRON = modTag("ep/iron");
        public static final TagKey<Item> SILVER = modTag("ep/silver");
        public static final TagKey<Item> NETHERITE = modTag("ep/netherite");
        public static final TagKey<Item> DIAMOND = modTag("ep/diamond");
        public static final TagKey<Item> MONSTER_LEATHER_D = modTag("ep/monster_leather_d");
        public static final TagKey<Item> MONSTER_LEATHER_C = modTag("ep/monster_leather_c");
        public static final TagKey<Item> MONSTER_LEATHER_B = modTag("ep/monster_leather_b");
        public static final TagKey<Item> MONSTER_LEATHER_A = modTag("ep/monster_leather_a");
        public static final TagKey<Item> MONSTER_LEATHER_SA = modTag("ep/monster_leather_sa");

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
