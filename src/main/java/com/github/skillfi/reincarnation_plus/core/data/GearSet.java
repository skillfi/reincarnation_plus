package com.github.skillfi.reincarnation_plus.core.data;

import com.github.manasmods.tensura.registry.items.TensuraArmorItems;
import com.github.manasmods.tensura.registry.items.TensuraToolItems;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.*;

public class GearSet {

    public static final Item[] ironGear = new Item[]{
            Items.IRON_HELMET,
            Items.IRON_CHESTPLATE,
            Items.IRON_LEGGINGS,
            Items.IRON_BOOTS,
            Items.IRON_PICKAXE,
            Items.IRON_AXE,
            Items.IRON_SHOVEL,
            Items.IRON_HOE,
            TensuraToolItems.IRON_SICKLE.get(),
            Items.IRON_SWORD,
            TensuraToolItems.IRON_SHORT_SWORD.get(),
            TensuraToolItems.IRON_LONG_SWORD.get(),
            TensuraToolItems.IRON_GREAT_SWORD.get(),
            TensuraToolItems.IRON_KATANA.get(),
            TensuraToolItems.IRON_KODACHI.get(),
            TensuraToolItems.IRON_TACHI.get(),
            TensuraToolItems.IRON_ODACHI.get(),
            TensuraToolItems.IRON_SPEAR.get()
    };
    public static final Item[] silverGear = new Item[]{
            TensuraArmorItems.SILVER_HELMET.get(),
            TensuraArmorItems.SILVER_CHESTPLATE.get(),
            TensuraArmorItems.SILVER_LEGGINGS.get(),
            TensuraArmorItems.SILVER_BOOTS.get(),
            TensuraToolItems.SILVER_PICKAXE.get(),
            TensuraToolItems.SILVER_AXE.get(),
            TensuraToolItems.SILVER_SHOVEL.get(),
            TensuraToolItems.SILVER_HOE.get(),
            TensuraToolItems.SILVER_SICKLE.get(),
            TensuraToolItems.SILVER_SWORD.get(),
            TensuraToolItems.SILVER_SHORT_SWORD.get(),
            TensuraToolItems.SILVER_LONG_SWORD.get(),
            TensuraToolItems.SILVER_GREAT_SWORD.get(),
            TensuraToolItems.SILVER_KATANA.get(),
            TensuraToolItems.SILVER_KODACHI.get(),
            TensuraToolItems.SILVER_TACHI.get(),
            TensuraToolItems.SILVER_ODACHI.get(),
            TensuraToolItems.SILVER_SPEAR.get()
    };
    public static final Item[] netheriteGear = new Item[]{
            Items.NETHERITE_HELMET,
            Items.NETHERITE_CHESTPLATE,
            Items.NETHERITE_LEGGINGS,
            Items.NETHERITE_BOOTS,
            Items.NETHERITE_PICKAXE,
            Items.NETHERITE_AXE,
            Items.NETHERITE_SHOVEL,
            Items.NETHERITE_HOE,
            TensuraToolItems.NETHERITE_SICKLE.get(),
            Items.NETHERITE_SWORD,
            TensuraToolItems.NETHERITE_SHORT_SWORD.get(),
            TensuraToolItems.NETHERITE_LONG_SWORD.get(),
            TensuraToolItems.NETHERITE_GREAT_SWORD.get(),
            TensuraToolItems.NETHERITE_KATANA.get(),
            TensuraToolItems.NETHERITE_KODACHI.get(),
            TensuraToolItems.NETHERITE_TACHI.get(),
            TensuraToolItems.NETHERITE_ODACHI.get(),
            TensuraToolItems.NETHERITE_SPEAR.get()
    };

    public static final Item[] lowMagisteel = new Item[]{
            TensuraArmorItems.LOW_MAGISTEEL_HELMET.get(),
            TensuraArmorItems.LOW_MAGISTEEL_CHESTPLATE.get(),
            TensuraArmorItems.LOW_MAGISTEEL_LEGGINGS.get(),
            TensuraArmorItems.LOW_MAGISTEEL_BOOTS.get(),
            TensuraToolItems.LOW_MAGISTEEL_PICKAXE.get(),
            TensuraToolItems.LOW_MAGISTEEL_AXE.get(),
            TensuraToolItems.LOW_MAGISTEEL_SHOVEL.get(),
            TensuraToolItems.LOW_MAGISTEEL_HOE.get(),
            TensuraToolItems.LOW_MAGISTEEL_SICKLE.get(),
            TensuraToolItems.LOW_MAGISTEEL_SWORD.get(),
            TensuraToolItems.LOW_MAGISTEEL_SHORT_SWORD.get(),
            TensuraToolItems.LOW_MAGISTEEL_LONG_SWORD.get(),
            TensuraToolItems.LOW_MAGISTEEL_GREAT_SWORD.get(),
            TensuraToolItems.LOW_MAGISTEEL_KATANA.get(),
            TensuraToolItems.LOW_MAGISTEEL_KODACHI.get(),
            TensuraToolItems.LOW_MAGISTEEL_TACHI.get(),
            TensuraToolItems.LOW_MAGISTEEL_ODACHI.get(),
            TensuraToolItems.LOW_MAGISTEEL_SPEAR.get()
    };
    public static final Item[] pureMagisteel = new Item[]{
            TensuraArmorItems.PURE_MAGISTEEL_HELMET.get(),
            TensuraArmorItems.PURE_MAGISTEEL_CHESTPLATE.get(),
            TensuraArmorItems.PURE_MAGISTEEL_LEGGINGS.get(),
            TensuraArmorItems.PURE_MAGISTEEL_BOOTS.get(),
            TensuraToolItems.PURE_MAGISTEEL_PICKAXE.get(),
            TensuraToolItems.PURE_MAGISTEEL_AXE.get(),
            TensuraToolItems.PURE_MAGISTEEL_SHOVEL.get(),
            TensuraToolItems.PURE_MAGISTEEL_HOE.get(),
            TensuraToolItems.PURE_MAGISTEEL_SICKLE.get(),
            TensuraToolItems.PURE_MAGISTEEL_SWORD.get(),
            TensuraToolItems.PURE_MAGISTEEL_SHORT_SWORD.get(),
            TensuraToolItems.PURE_MAGISTEEL_LONG_SWORD.get(),
            TensuraToolItems.PURE_MAGISTEEL_GREAT_SWORD.get(),
            TensuraToolItems.PURE_MAGISTEEL_KATANA.get(),
            TensuraToolItems.PURE_MAGISTEEL_KODACHI.get(),
            TensuraToolItems.PURE_MAGISTEEL_TACHI.get(),
            TensuraToolItems.PURE_MAGISTEEL_ODACHI.get(),
            TensuraToolItems.PURE_MAGISTEEL_SPEAR.get()
    };
    public static final Item[] HIGH_MAGISTEEL = new Item[]{TensuraArmorItems.HIGH_MAGISTEEL_HELMET.get(), TensuraArmorItems.HIGH_MAGISTEEL_CHESTPLATE.get(), TensuraArmorItems.HIGH_MAGISTEEL_LEGGINGS.get(), TensuraArmorItems.HIGH_MAGISTEEL_BOOTS.get(), TensuraToolItems.HIGH_MAGISTEEL_PICKAXE.get(), TensuraToolItems.HIGH_MAGISTEEL_AXE.get(), TensuraToolItems.HIGH_MAGISTEEL_SHOVEL.get(), TensuraToolItems.HIGH_MAGISTEEL_HOE.get(), TensuraToolItems.HIGH_MAGISTEEL_SICKLE.get(), TensuraToolItems.HIGH_MAGISTEEL_SWORD.get(), TensuraToolItems.HIGH_MAGISTEEL_SHORT_SWORD.get(), TensuraToolItems.HIGH_MAGISTEEL_LONG_SWORD.get(), TensuraToolItems.HIGH_MAGISTEEL_GREAT_SWORD.get(), TensuraToolItems.HIGH_MAGISTEEL_KATANA.get(), TensuraToolItems.HIGH_MAGISTEEL_KODACHI.get(), TensuraToolItems.HIGH_MAGISTEEL_TACHI.get(), TensuraToolItems.HIGH_MAGISTEEL_ODACHI.get(), TensuraToolItems.HIGH_MAGISTEEL_SPEAR.get()};
    public static final Item[] MITHRIL = new Item[]{TensuraArmorItems.MITHRIL_HELMET.get(), TensuraArmorItems.MITHRIL_CHESTPLATE.get(), TensuraArmorItems.MITHRIL_LEGGINGS.get(), TensuraArmorItems.MITHRIL_BOOTS.get(), TensuraToolItems.MITHRIL_PICKAXE.get(), TensuraToolItems.MITHRIL_AXE.get(), TensuraToolItems.MITHRIL_SHOVEL.get(), TensuraToolItems.MITHRIL_HOE.get(), TensuraToolItems.MITHRIL_SICKLE.get(), TensuraToolItems.MITHRIL_SWORD.get(), TensuraToolItems.MITHRIL_SHORT_SWORD.get(), TensuraToolItems.MITHRIL_LONG_SWORD.get(), TensuraToolItems.MITHRIL_GREAT_SWORD.get(), TensuraToolItems.MITHRIL_KATANA.get(), TensuraToolItems.MITHRIL_KODACHI.get(), TensuraToolItems.MITHRIL_TACHI.get(), TensuraToolItems.MITHRIL_ODACHI.get(), TensuraToolItems.MITHRIL_SPEAR.get()};
    public static final Item[] CHARYBDIS = new Item[]{TensuraArmorItems.CHARYBDIS_SCALEMAIL_HELMET.get(), TensuraArmorItems.CHARYBDIS_SCALEMAIL_BOOTS.get(), TensuraArmorItems.CHARYBDIS_SCALEMAIL_LEGGINGS.get(), TensuraArmorItems.CHARYBDIS_SCALEMAIL_CHESTPLATE.get()};
    public static final Item[] ORICHALCUM = new Item[]{TensuraArmorItems.ORICHALCUM_HELMET.get(), TensuraArmorItems.ORICHALCUM_CHESTPLATE.get(), TensuraArmorItems.ORICHALCUM_LEGGINGS.get(), TensuraArmorItems.ORICHALCUM_BOOTS.get(), TensuraToolItems.ORICHALCUM_PICKAXE.get(), TensuraToolItems.ORICHALCUM_AXE.get(), TensuraToolItems.ORICHALCUM_SHOVEL.get(), TensuraToolItems.ORICHALCUM_HOE.get(), TensuraToolItems.ORICHALCUM_SICKLE.get(), TensuraToolItems.ORICHALCUM_SWORD.get(), TensuraToolItems.ORICHALCUM_SHORT_SWORD.get(), TensuraToolItems.ORICHALCUM_LONG_SWORD.get(), TensuraToolItems.ORICHALCUM_GREAT_SWORD.get(), TensuraToolItems.ORICHALCUM_KATANA.get(), TensuraToolItems.ORICHALCUM_KODACHI.get(), TensuraToolItems.ORICHALCUM_TACHI.get(), TensuraToolItems.ORICHALCUM_ODACHI.get(), TensuraToolItems.ORICHALCUM_SPEAR.get()};
    public static final Item[] HIHIIROKANE = new Item[]{TensuraArmorItems.HIHIIROKANE_HELMET.get(), TensuraArmorItems.HIHIIROKANE_CHESTPLATE.get(), TensuraArmorItems.HIHIIROKANE_LEGGINGS.get(), TensuraArmorItems.HIHIIROKANE_BOOTS.get(), TensuraToolItems.HIHIIROKANE_PICKAXE.get(), TensuraToolItems.HIHIIROKANE_AXE.get(), TensuraToolItems.HIHIIROKANE_SHOVEL.get(), TensuraToolItems.HIHIIROKANE_HOE.get(), TensuraToolItems.HIHIIROKANE_SICKLE.get(), TensuraToolItems.HIHIIROKANE_SWORD.get(), TensuraToolItems.HIHIIROKANE_SHORT_SWORD.get(), TensuraToolItems.HIHIIROKANE_LONG_SWORD.get(), TensuraToolItems.HIHIIROKANE_GREAT_SWORD.get(), TensuraToolItems.HIHIIROKANE_KATANA.get(), TensuraToolItems.HIHIIROKANE_KODACHI.get(), TensuraToolItems.HIHIIROKANE_TACHI.get(), TensuraToolItems.HIHIIROKANE_ODACHI.get(), TensuraToolItems.HIHIIROKANE_SPEAR.get()};
    public static final Item[] ADAMANTITE = new Item[]{TensuraArmorItems.ADAMANTITE_HELMET.get(), TensuraArmorItems.ADAMANTITE_CHESTPLATE.get(), TensuraArmorItems.ADAMANTITE_LEGGINGS.get(), TensuraArmorItems.ADAMANTITE_BOOTS.get(), TensuraToolItems.ADAMANTITE_PICKAXE.get(), TensuraToolItems.ADAMANTITE_AXE.get(), TensuraToolItems.ADAMANTITE_SHOVEL.get(), TensuraToolItems.ADAMANTITE_HOE.get(), TensuraToolItems.ADAMANTITE_SICKLE.get(), TensuraToolItems.ADAMANTITE_SWORD.get(), TensuraToolItems.ADAMANTITE_SHORT_SWORD.get(), TensuraToolItems.ADAMANTITE_LONG_SWORD.get(), TensuraToolItems.ADAMANTITE_GREAT_SWORD.get(), TensuraToolItems.ADAMANTITE_KATANA.get(), TensuraToolItems.ADAMANTITE_KODACHI.get(), TensuraToolItems.ADAMANTITE_TACHI.get(), TensuraToolItems.ADAMANTITE_ODACHI.get(), TensuraToolItems.ADAMANTITE_SPEAR.get()};
    public static final Item[] MONSTER_LEATHER_D = new Item[]{TensuraArmorItems.MONSTER_LEATHER_D_HELMET.get(), TensuraArmorItems.MONSTER_LEATHER_D_BOOTS.get(), TensuraArmorItems.MONSTER_LEATHER_D_LEGGINGS.get(), TensuraArmorItems.MONSTER_LEATHER_D_CHESTPLATE.get()};
    public static final Item[] MONSTER_LEATHER_C = new Item[]{TensuraArmorItems.MONSTER_LEATHER_C_HELMET.get(), TensuraArmorItems.MONSTER_LEATHER_C_BOOTS.get(), TensuraArmorItems.MONSTER_LEATHER_C_LEGGINGS.get(), TensuraArmorItems.MONSTER_LEATHER_C_CHESTPLATE.get()};
    public static final Item[] MONSTER_LEATHER_B = new Item[]{TensuraArmorItems.MONSTER_LEATHER_B_HELMET.get(), TensuraArmorItems.MONSTER_LEATHER_B_BOOTS.get(), TensuraArmorItems.MONSTER_LEATHER_B_LEGGINGS.get(), TensuraArmorItems.MONSTER_LEATHER_B_CHESTPLATE.get()};
    public static final Item[] MONSTER_LEATHER_A = new Item[]{TensuraArmorItems.MONSTER_LEATHER_A_HELMET.get(), TensuraArmorItems.MONSTER_LEATHER_A_BOOTS.get(), TensuraArmorItems.MONSTER_LEATHER_A_LEGGINGS.get(), TensuraArmorItems.MONSTER_LEATHER_A_CHESTPLATE.get()};
    public static final Item[] MONSTER_LEATHER_SPECIAL_A = new Item[]{TensuraArmorItems.MONSTER_LEATHER_SPECIAL_A_HELMET.get(), TensuraArmorItems.MONSTER_LEATHER_SPECIAL_A_BOOTS.get(), TensuraArmorItems.MONSTER_LEATHER_SPECIAL_A_LEGGINGS.get(), TensuraArmorItems.MONSTER_LEATHER_SPECIAL_A_CHESTPLATE.get()};
    @Getter private final ResourceLocation[] firstGear;
    @Getter private final ResourceLocation[] evolveGear;

    public GearSet(ResourceLocation[] firstGear, ResourceLocation[] evolveGear) {
        this.firstGear = firstGear;
        this.evolveGear = evolveGear;
    }

    // Метод для перетворення у мапу
    public Map<ResourceLocation, ResourceLocation> toMap() {
        Map<ResourceLocation, ResourceLocation> gearMap = new HashMap<>();

        // Об'єднання firstGear як ключів і evolveGear як значень
        for (int i = 0; i < Math.min(firstGear.length, evolveGear.length); i++) {
            gearMap.put(firstGear[i], evolveGear[i]);
        }

        return gearMap;
    }
}

