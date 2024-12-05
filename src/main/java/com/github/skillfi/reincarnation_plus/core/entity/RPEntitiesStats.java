package com.github.skillfi.reincarnation_plus.core.entity;

import com.github.manasmods.tensura.ability.TensuraSkill;
import com.github.manasmods.tensura.race.Race;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import com.github.manasmods.tensura.race.ogre.*;

@Getter
public class RPEntitiesStats {

    public static RPEntitiesStats Ogre = new RPEntitiesStats(new OgreRace());
    public static RPEntitiesStats Kijin = new RPEntitiesStats(new KijinRace());
    public static RPEntitiesStats MysticOni = new RPEntitiesStats(new MysticOniRace());
    public static RPEntitiesStats DivineOni = new RPEntitiesStats(new DivineOniRace());
    public static RPEntitiesStats DivineFighter = new RPEntitiesStats(new DivineFighterRace());

    public static RPEntitiesStats[] Entities = {Ogre, Kijin, MysticOni, DivineOni, DivineFighter};


    private final static String[] Ranks = {"Special S","S","Special A","A", "B", "C", "D", "E"};
    private double SHP;
    @Getter
    private double HP;
    @Getter
    private double MinEP;
    @Getter
    private double MaxEP;
    @Getter
    private double Armor;
    @Getter
    private double MovementSpeed;
    @Getter
    private double ATTACK_DAMAGE;
    public List<ResourceLocation> list;
    private final Race race;

    RPEntitiesStats(Race race) {
        this.race = race;
        this.SHP = race.getAdditionalSpiritualHealth();
        this.HP = race.getBaseHealth();
        this.MaxEP = race.getMaxBaseMagicule();
        this.MinEP = race.getMaxBaseMagicule();
        this.MovementSpeed = race.getMovementSpeed();
        this.ATTACK_DAMAGE = race.getBaseAttackDamage();
        this.Armor = 10;
        this.list = new ArrayList<>();
        for (TensuraSkill skill: race.getIntrinsicSkills()){
            this.addToList(skill.getRegistryName());
        }
    }

    public void addToList(ResourceLocation loc) {
        list.add(loc);
    }


}
