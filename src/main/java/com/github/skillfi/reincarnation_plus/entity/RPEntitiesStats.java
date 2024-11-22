package com.github.skillfi.reincarnation_plus.entity;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RPEntitiesStats {

    public static RPEntitiesStats Ogre = new RPEntitiesStats(72, 26, 6000,
            10000, 10, 0.3, 3, new ArrayList<>(), new ResourceLocation(""));
    public static RPEntitiesStats Kijin = new RPEntitiesStats(82, 30, 10000,
            100000, 15, Ogre.getMovementSpeed()*2, Ogre.ATTACK_DAMAGE*2, new ArrayList<>(),
            new ResourceLocation(""));
    public static RPEntitiesStats Oni = new RPEntitiesStats(3600, 1300, 100000,
            1000000, 50, Kijin.getMovementSpeed()*2, Kijin.ATTACK_DAMAGE*2, new ArrayList<>(),
            new ResourceLocation(""));
    public static RPEntitiesStats DivineOni = new RPEntitiesStats(36000, 6500, 1000000,
            10000000, 100, Oni.getMovementSpeed()*2, Oni.ATTACK_DAMAGE*2, new ArrayList<>(),
            new ResourceLocation(""));
    public static RPEntitiesStats DivineFighter = new RPEntitiesStats(36000, 6500, 400000,
            1000000, 100, Oni.getMovementSpeed()*2, Oni.ATTACK_DAMAGE*2, new ArrayList<>(),
            new ResourceLocation(""));

    public static RPEntitiesStats[] Entities = {Ogre, Kijin, Oni, DivineOni, DivineFighter};


    private final static String[] Ranks = {"Special S","S","Special A","A", "B", "C", "D", "E"};
    private final int SHP;
    private final int HP;
    private final int MinEP;
    private final int MaxEP;
    private final int Armor;
    private final double MovementSpeed;
    private final int ATTACK_DAMAGE;
    public List<ResourceLocation> list;
    private final ResourceLocation evo;

    RPEntitiesStats(int SHP, int HP, int MinEP, int MaxEP, int Armor, double MovementSpeed, int ATTACK_DAMAGE, List<ResourceLocation> list,
                    ResourceLocation evo) {
        this.SHP = SHP;
        this.HP = HP;
        this.MinEP = MinEP;
        this.MaxEP = MaxEP;
        this.Armor = Armor;
        this.MovementSpeed = MovementSpeed;
        this.ATTACK_DAMAGE = ATTACK_DAMAGE;
        this.list = list;
        this.evo = evo;
    }

    public void addToList(ResourceLocation loc) {
        list.add(loc);
    }
}
