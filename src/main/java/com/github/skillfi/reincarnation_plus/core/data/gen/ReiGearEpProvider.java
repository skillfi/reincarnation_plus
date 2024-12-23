package com.github.skillfi.reincarnation_plus.core.data.gen;


import com.github.manasmods.manascore.api.data.gen.CustomDataProvider;
import com.github.manasmods.tensura.data.pack.GearEPCount;
import com.github.manasmods.tensura.registry.items.TensuraArmorItems;
import com.github.manasmods.tensura.registry.items.TensuraToolItems;
import com.github.skillfi.reincarnation_plus.core.data.GearSet;
import com.github.skillfi.reincarnation_plus.core.registry.items.ReiItems;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
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

    public static final ResourceLocation[] lowMagisteel = new ResourceLocation[]{
            TensuraArmorItems.LOW_MAGISTEEL_HELMET.getId(),
            TensuraArmorItems.LOW_MAGISTEEL_CHESTPLATE.getId(),
            TensuraArmorItems.LOW_MAGISTEEL_LEGGINGS.getId(),
            TensuraArmorItems.LOW_MAGISTEEL_BOOTS.getId(),
            TensuraToolItems.LOW_MAGISTEEL_PICKAXE.getId(),
            TensuraToolItems.LOW_MAGISTEEL_AXE.getId(),
            TensuraToolItems.LOW_MAGISTEEL_SHOVEL.getId(),
            TensuraToolItems.LOW_MAGISTEEL_HOE.getId(),
            TensuraToolItems.LOW_MAGISTEEL_SICKLE.getId(),
            TensuraToolItems.LOW_MAGISTEEL_SWORD.getId(),
            TensuraToolItems.LOW_MAGISTEEL_SHORT_SWORD.getId(),
            TensuraToolItems.LOW_MAGISTEEL_LONG_SWORD.getId(),
            TensuraToolItems.LOW_MAGISTEEL_GREAT_SWORD.getId(),
            TensuraToolItems.LOW_MAGISTEEL_KATANA.getId(),
            TensuraToolItems.LOW_MAGISTEEL_KODACHI.getId(),
            TensuraToolItems.LOW_MAGISTEEL_TACHI.getId(),
            TensuraToolItems.LOW_MAGISTEEL_ODACHI.getId(),
            TensuraToolItems.LOW_MAGISTEEL_SPEAR.getId()
    };
    public static final ResourceLocation[] pureMagisteel = new ResourceLocation[]{
            TensuraArmorItems.PURE_MAGISTEEL_HELMET.getId(),
            TensuraArmorItems.PURE_MAGISTEEL_CHESTPLATE.getId(),
            TensuraArmorItems.PURE_MAGISTEEL_LEGGINGS.getId(),
            TensuraArmorItems.PURE_MAGISTEEL_BOOTS.getId(),
            TensuraToolItems.PURE_MAGISTEEL_PICKAXE.getId(),
            TensuraToolItems.PURE_MAGISTEEL_AXE.getId(),
            TensuraToolItems.PURE_MAGISTEEL_SHOVEL.getId(),
            TensuraToolItems.PURE_MAGISTEEL_HOE.getId(),
            TensuraToolItems.PURE_MAGISTEEL_SICKLE.getId(),
            TensuraToolItems.PURE_MAGISTEEL_SWORD.getId(),
            TensuraToolItems.PURE_MAGISTEEL_SHORT_SWORD.getId(),
            TensuraToolItems.PURE_MAGISTEEL_LONG_SWORD.getId(),
            TensuraToolItems.PURE_MAGISTEEL_GREAT_SWORD.getId(),
            TensuraToolItems.PURE_MAGISTEEL_KATANA.getId(),
            TensuraToolItems.PURE_MAGISTEEL_KODACHI.getId(),
            TensuraToolItems.PURE_MAGISTEEL_TACHI.getId(),
            TensuraToolItems.PURE_MAGISTEEL_ODACHI.getId(),
            TensuraToolItems.PURE_MAGISTEEL_SPEAR.getId()
    };
    public static GearSet silverIron = new GearSet(silverGear, ironGear);
    public static Map<ResourceLocation, ResourceLocation> silverIronMap = silverIron.toMap();
    public static GearSet ironLowMagisteel = new GearSet(ironGear, lowMagisteel);
    public static Map<ResourceLocation, ResourceLocation> ironLowMagisteelMap = ironLowMagisteel.toMap();
    public static GearSet netheritePureMagisteel = new GearSet(netheriteGear, pureMagisteel);
    public static Map<ResourceLocation, ResourceLocation> netheritePureMagisteelMap = netheritePureMagisteel.toMap();


    public @NotNull String getName() {
        return "Reincarnation Plus Gear EP";
    }

    protected void run(@NotNull BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        monsterLeatherD(biConsumer);
        silver(biConsumer);
        iron(biConsumer);
        netherite(biConsumer);
    }

    protected static void monsterLeatherD(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        GearEPCount.of(ReiItems.MONSTER_LEATHER_JACKET_ARMOR_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.HAKAMA_CHESTPLATE_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.HAKAMA_LEGGINGS_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.MONSTER_LEATHER_WOMAN_LEGGINGS_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.YUKATA_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);
        GearEPCount.of(ReiItems.KIMONO_D.getId(), 1000, 2500, 0.005).buildJson(biConsumer);

    }

    protected static void silver(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer){
        for (Map.Entry<ResourceLocation, ResourceLocation> entry : silverIronMap.entrySet()) {
            GearEPCount.of(entry.getKey(), 5000, 25000, 0.015, entry.getValue()).buildJson(biConsumer);
        }
    }

    protected static void iron(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer){
        for (Map.Entry<ResourceLocation, ResourceLocation> entry : ironLowMagisteelMap.entrySet()) {
            GearEPCount.of(entry.getKey(), 10000, 50000, 0.015, entry.getValue()).buildJson(biConsumer);
        }
    }

    protected static void netherite(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer){
        for (Map.Entry<ResourceLocation, ResourceLocation> entry : netheritePureMagisteelMap.entrySet()) {
            GearEPCount.of(entry.getKey(), 10000, 50000, 0.015, entry.getValue()).buildJson(biConsumer);
        }
    }
}
