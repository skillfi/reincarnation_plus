package com.github.skillfi.reincarnation_plus.api.gem;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GemHandler {

    public static Map<String, IGemType> gemTypes = new HashMap<>();
    public static ArrayList<IGemType> gemTypesList = new ArrayList<>();
    public static Map<String, IPolishingType> gemPolishing = new HashMap<>();
    public static ArrayList<IPolishingType> gemPolishingList = new ArrayList<>();
    public static ArrayList<Item> gemItems = new ArrayList<>();

    public static void addType(String id, IGemType type) {
        gemTypes.put(id, type);
        gemTypesList.add(type);
    }

    public static IGemType getType(int id) {
        return gemTypes.get(id);
    }

    public static IGemType getType(String id) {
        return gemTypes.get(id);
    }

    public static void registerType(IGemType type) {
        gemTypes.put(type.getId(), type);
        gemTypesList.add(type);
    }

    public static int sizeType() {
        return gemTypes.size();
    }

    public static ArrayList<IGemType> getTypes() {
        return gemTypesList;
    }

    public static void addPolishing(String id, PolishingType type) {
        gemPolishing.put(id, type);
        gemPolishingList.add(type);
    }

    public static IPolishingType getPolishing(int id) {
        return gemPolishing.get(id);
    }

    public static IPolishingType getPolishing(String id) {
        return gemPolishing.get(id);
    }

    public static void registerPolishing(IPolishingType type) {
        gemPolishing.put(type.getId(), type);
        gemPolishingList.add(type);
    }

    public static int sizePolishing() {
        return gemPolishing.size();
    }

    public static ArrayList<IPolishingType> getPolishings() {
        return gemPolishingList;
    }

    public static void addItem(Item item) {
        gemItems.add(item);
    }

    public static ArrayList<Item> getItems() {
        return gemItems;
    }
}
