package com.github.skillfi.reincarnation_plus.core.data.recipe.materials;

import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.manasmods.tensura.data.TensuraTags;
import com.github.manasmods.tensura.registry.blocks.TensuraBlocks;
import com.github.manasmods.tensura.registry.items.TensuraMaterialItems;
import com.github.manasmods.tensura.registry.items.TensuraMobDropItems;
import com.github.manasmods.tensura.registry.items.TensuraToolItems;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;

public class RecipeMaterials implements IRecipeMaterial{

    public static List<RecipeMaterials> meltingMaterials = List.of(
            new RecipeMaterials(1000*20, TensuraBlocks.MAGIC_ORE.get().asItem()),
            new RecipeMaterials(1000*20, TensuraBlocks.DEEPSLATE_MAGIC_ORE.get().asItem()),
            new RecipeMaterials(1000*20, TensuraMaterialItems.MAGIC_ORE.get()),
            new RecipeMaterials(9000*20, TensuraBlocks.MAGIC_ORE_BLOCK.get().asItem()),
            new RecipeMaterials(1000*20, TensuraMaterialItems.HIGH_MAGISTEEL_NUGGET.get()),
            new RecipeMaterials(9000*20, TensuraMaterialItems.HIGH_MAGISTEEL_INGOT.get()),
            new RecipeMaterials(4000*20, TensuraMaterialItems.PURE_MAGISTEEL_NUGGET.get()),
            new RecipeMaterials(36000*20, TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()),
            new RecipeMaterials(4000*20, TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get()),
            new RecipeMaterials(1000*20, TensuraToolItems.KANABO.get()),
            new RecipeMaterials(1000*20, (Item)TensuraToolItems.PURE_MAGISTEEL_KUNAI.get()),
            new RecipeMaterials(4000*20, (Item)TensuraToolItems.DRAGON_KNUCKLE.get()),

            new RecipeMaterials(2000*20, TensuraTags.Items.NUGGETS_MITHRIL, "mithril_nugget", true),
            new RecipeMaterials(2000*20*9, (Item)TensuraMaterialItems.MITHRIL_INGOT.get()),
            new RecipeMaterials(2000*20, TensuraTags.Items.NUGGETS_ORICHALCUM, "orichalcum_nugget", true),
            new RecipeMaterials(18000*20, (Item)TensuraMaterialItems.ORICHALCUM_INGOT.get()),
            new RecipeMaterials(8000*20, TensuraTags.Items.NUGGETS_ADAMANTITE, "adamantite_nugget", true),
            new RecipeMaterials(36000*20, (Item)TensuraMaterialItems.ADAMANTITE_INGOT.get()),
            new RecipeMaterials(12000*20, TensuraTags.Items.NUGGETS_HIHIIROKANE, "hihiirokane_nugget", true),
            new RecipeMaterials(54000*20, (Item)TensuraMaterialItems.HIHIIROKANE_INGOT.get()),
            new RecipeMaterials(7000, IafItemTags.DRAGON_HEARTS, "dragon_hearts", true),
            new RecipeMaterials(3000, IafItemTags.DRAGON_BLOODS, "dragon_bloods", true),

            new RecipeMaterials(25000, TensuraMobDropItems.DRAGON_ESSENCE.get()),
            new RecipeMaterials(2500, TensuraMobDropItems.MONSTER_LEATHER_D.get()),
            new RecipeMaterials(5000, TensuraMobDropItems.MONSTER_LEATHER_C.get()),
            new RecipeMaterials(7500, TensuraMobDropItems.MONSTER_LEATHER_B.get()),
            new RecipeMaterials(10000, TensuraMobDropItems.MONSTER_LEATHER_A.get()),
            new RecipeMaterials(2500, TensuraMobDropItems.LOW_QUALITY_MAGIC_CRYSTAL.get()),
            new RecipeMaterials(6000, TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()),
            new RecipeMaterials(10000, TensuraMobDropItems.MEDIUM_QUALITY_MAGIC_CRYSTAL.get()),
            new RecipeMaterials(2500*9, TensuraBlocks.LOW_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem()),
            new RecipeMaterials(6000*9, TensuraBlocks.MEDIUM_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem()),
            new RecipeMaterials(10000*9, TensuraBlocks.HIGH_QUALITY_MAGIC_CRYSTAL_BLOCK.get().asItem())
    );

    @Getter public ResourceLocation primaryType;
    @Getter public int primaryAmount;
    @Getter public ResourceLocation secondaryType;
    @Getter public int secondaryAmount;
    @Getter private String materialName;
    @Getter public boolean Kiln;
    @Getter public Item input;
    @Getter public String tagName;
    @Getter public TagKey<Item> itemTagKey;
    @Getter private boolean tag;

    // Конструктор для предметів
    public RecipeMaterials(int amount, Item item){
        this.primaryAmount = amount;
        this.input = item;
        this.itemTagKey = null;
        this.tagName = null;
        this.tag = false;
    }

    // Конструктор для тегів
    public RecipeMaterials(int amount, TagKey<Item> itemTagKey, String tagName, boolean isTag) {
        this.primaryAmount = amount;
        this.input = null;
        this.itemTagKey = itemTagKey;
        this.tagName = tagName;
        this.tag = isTag;
    }

}
