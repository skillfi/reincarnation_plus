package com.github.skillfi.reincarnation_plus.core.data.gen;


import com.github.manasmods.manascore.api.data.gen.CustomDataProvider;
import com.github.manasmods.tensura.data.pack.GearEPCount;
import com.github.manasmods.tensura.registry.items.TensuraArmorItems;
import com.github.manasmods.tensura.registry.items.TensuraToolItems;
import com.github.skillfi.reincarnation_plus.core.data.GearSet;
import com.github.skillfi.reincarnation_plus.core.data.ReiTags;
import com.github.skillfi.reincarnation_plus.core.registry.items.ReiItems;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ReiGearEpProvider extends CustomDataProvider {

    public ReiGearEpProvider(GatherDataEvent event) {
        super("gear/ep", event.getGenerator());
    }
    public static final ResourceLocation[] ironGear = new ResourceLocation[]{
            ForgeRegistries.ITEMS.getKey(Items.IRON_HELMET),
            ForgeRegistries.ITEMS.getKey(Items.IRON_CHESTPLATE),
            ForgeRegistries.ITEMS.getKey(Items.IRON_LEGGINGS),
            ForgeRegistries.ITEMS.getKey(Items.IRON_BOOTS),
            ForgeRegistries.ITEMS.getKey(Items.IRON_PICKAXE),
            ForgeRegistries.ITEMS.getKey(Items.IRON_AXE),
            ForgeRegistries.ITEMS.getKey(Items.IRON_SHOVEL),
            ForgeRegistries.ITEMS.getKey(Items.IRON_HOE),
            ForgeRegistries.ITEMS.getKey(TensuraToolItems.IRON_SICKLE.get()),
            ForgeRegistries.ITEMS.getKey(Items.IRON_SWORD),
            TensuraToolItems.IRON_SHORT_SWORD.getId(),
            TensuraToolItems.IRON_LONG_SWORD.getId(),
            TensuraToolItems.IRON_GREAT_SWORD.getId(),
            TensuraToolItems.IRON_KATANA.getId(),
            TensuraToolItems.IRON_KODACHI.getId(),
            TensuraToolItems.IRON_TACHI.getId(),
            TensuraToolItems.IRON_ODACHI.getId(),
            TensuraToolItems.IRON_SPEAR.getId()
    };
    public static final ResourceLocation[] goldGear = new ResourceLocation[]{
            ForgeRegistries.ITEMS.getKey(Items.GOLDEN_HELMET),
            ForgeRegistries.ITEMS.getKey(Items.GOLDEN_CHESTPLATE),
            ForgeRegistries.ITEMS.getKey(Items.GOLDEN_LEGGINGS),
            ForgeRegistries.ITEMS.getKey(Items.GOLDEN_BOOTS),
            ForgeRegistries.ITEMS.getKey(Items.GOLDEN_PICKAXE),
            ForgeRegistries.ITEMS.getKey(Items.GOLDEN_AXE),
            ForgeRegistries.ITEMS.getKey(Items.GOLDEN_SHOVEL),
            ForgeRegistries.ITEMS.getKey(Items.GOLDEN_HOE),
            ForgeRegistries.ITEMS.getKey(TensuraToolItems.GOLDEN_SICKLE.get()),
            ForgeRegistries.ITEMS.getKey(Items.GOLDEN_SWORD),
            TensuraToolItems.GOLDEN_SHORT_SWORD.getId(),
            TensuraToolItems.GOLDEN_LONG_SWORD.getId(),
            TensuraToolItems.GOLDEN_GREAT_SWORD.getId(),
            TensuraToolItems.GOLDEN_KATANA.getId(),
            TensuraToolItems.GOLDEN_KODACHI.getId(),
            TensuraToolItems.GOLDEN_TACHI.getId(),
            TensuraToolItems.GOLDEN_ODACHI.getId(),
            TensuraToolItems.GOLDEN_SPEAR.getId()
    };
    public static final ResourceLocation[] silverGear = new ResourceLocation[]{
            TensuraArmorItems.SILVER_HELMET.getId(),
            TensuraArmorItems.SILVER_CHESTPLATE.getId(),
            TensuraArmorItems.SILVER_LEGGINGS.getId(),
            TensuraArmorItems.SILVER_BOOTS.getId(),
            TensuraToolItems.SILVER_PICKAXE.getId(),
            TensuraToolItems.SILVER_AXE.getId(),
            TensuraToolItems.SILVER_SHOVEL.getId(),
            TensuraToolItems.SILVER_HOE.getId(),
            TensuraToolItems.SILVER_SICKLE.getId(),
            TensuraToolItems.SILVER_SWORD.getId(),
            TensuraToolItems.SILVER_SHORT_SWORD.getId(),
            TensuraToolItems.SILVER_LONG_SWORD.getId(),
            TensuraToolItems.SILVER_GREAT_SWORD.getId(),
            TensuraToolItems.SILVER_KATANA.getId(),
            TensuraToolItems.SILVER_KODACHI.getId(),
            TensuraToolItems.SILVER_TACHI.getId(),
            TensuraToolItems.SILVER_ODACHI.getId(),
            TensuraToolItems.SILVER_SPEAR.getId()
    };
    public static final ResourceLocation[] netheriteGear = new ResourceLocation[]{
            ForgeRegistries.ITEMS.getKey(Items.NETHERITE_HELMET),
            ForgeRegistries.ITEMS.getKey(Items.NETHERITE_CHESTPLATE),
            ForgeRegistries.ITEMS.getKey(Items.NETHERITE_LEGGINGS),
            ForgeRegistries.ITEMS.getKey(Items.NETHERITE_BOOTS),
            ForgeRegistries.ITEMS.getKey(Items.NETHERITE_PICKAXE),
            ForgeRegistries.ITEMS.getKey(Items.NETHERITE_AXE),
            ForgeRegistries.ITEMS.getKey(Items.NETHERITE_SHOVEL),
            ForgeRegistries.ITEMS.getKey(Items.NETHERITE_HOE),
            TensuraToolItems.NETHERITE_SICKLE.getId(),
            ForgeRegistries.ITEMS.getKey(Items.NETHERITE_SWORD),
            TensuraToolItems.NETHERITE_SHORT_SWORD.getId(),
            TensuraToolItems.NETHERITE_LONG_SWORD.getId(),
            TensuraToolItems.NETHERITE_GREAT_SWORD.getId(),
            TensuraToolItems.NETHERITE_KATANA.getId(),
            TensuraToolItems.NETHERITE_KODACHI.getId(),
            TensuraToolItems.NETHERITE_TACHI.getId(),
            TensuraToolItems.NETHERITE_ODACHI.getId(),
            TensuraToolItems.NETHERITE_SPEAR.getId()
    };
    public static final ResourceLocation[] diamontGear = new ResourceLocation[]{
            ForgeRegistries.ITEMS.getKey(Items.DIAMOND_HELMET),
            ForgeRegistries.ITEMS.getKey(Items.DIAMOND_CHESTPLATE),
            ForgeRegistries.ITEMS.getKey(Items.DIAMOND_LEGGINGS),
            ForgeRegistries.ITEMS.getKey(Items.DIAMOND_BOOTS),
            ForgeRegistries.ITEMS.getKey(Items.DIAMOND_PICKAXE),
            ForgeRegistries.ITEMS.getKey(Items.DIAMOND_AXE),
            ForgeRegistries.ITEMS.getKey(Items.DIAMOND_SHOVEL),
            ForgeRegistries.ITEMS.getKey(Items.DIAMOND_HOE),
            TensuraToolItems.DIAMOND_SICKLE.getId(),
            ForgeRegistries.ITEMS.getKey(Items.DIAMOND_SWORD),
            TensuraToolItems.DIAMOND_SHORT_SWORD.getId(),
            TensuraToolItems.DIAMOND_LONG_SWORD.getId(),
            TensuraToolItems.DIAMOND_GREAT_SWORD.getId(),
            TensuraToolItems.DIAMOND_KATANA.getId(),
            TensuraToolItems.DIAMOND_KODACHI.getId(),
            TensuraToolItems.DIAMOND_TACHI.getId(),
            TensuraToolItems.DIAMOND_ODACHI.getId(),
            TensuraToolItems.DIAMOND_SPEAR.getId()
    };
    public static GearSet gearSet = new GearSet(ironGear, goldGear);
    public static Map<ResourceLocation, ResourceLocation> ironGold = gearSet.toMap();
    public static GearSet goldSet = new GearSet(goldGear, silverGear);
    public static Map<ResourceLocation, ResourceLocation> goldSilver = goldSet.toMap();
    public static GearSet diamondSet = new GearSet(diamontGear, netheriteGear);
    public static Map<ResourceLocation, ResourceLocation> diamondNetherite = diamondSet.toMap();


    public @NotNull String getName() {
        return "Reincarnation Plus Gear EP";
    }

    protected void run(@NotNull BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        monsterLeatherD(biConsumer);
        iron(biConsumer);
        gold(biConsumer);
        diamond(biConsumer);
    }

    protected static void monsterLeatherD(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        GearEPCount.of(ReiItems.MONSTER_LEATHER_JACKET_ARMOR_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.HAKAMA_CHESTPLATE_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.HAKAMA_LEGGINGS_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.MONSTER_LEATHER_WOMAN_LEGGINGS_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.YUKATA_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.KIMONO_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);

    }

    protected static void iron(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer){
        for (Map.Entry<ResourceLocation, ResourceLocation> entry : ironGold.entrySet()) {
            GearEPCount.of(entry.getKey(), 500, 2000, 0.015, entry.getValue()).buildJson(biConsumer);
        }
    }

    protected static void gold(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer){
        for (Map.Entry<ResourceLocation, ResourceLocation> entry : goldSilver.entrySet()) {
            GearEPCount.of(entry.getKey(), 2000, 5000, 0.015, entry.getValue()).buildJson(biConsumer);
        }
    }

    protected static void diamond(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer){
        for (Map.Entry<ResourceLocation, ResourceLocation> entry : diamondNetherite.entrySet()) {
            GearEPCount.of(entry.getKey(), 4000, 50000, 0.015, entry.getValue()).buildJson(biConsumer);
        }
    }
}
