package com.github.skillfi.reincarnation_plus.ability.classes;

import com.github.skillfi.isekaicore.api.classes.IsekaiClass;

public class IClass extends IsekaiClass{
    public double Agility;
    public double Endurance;
    public double Opposite;
    public double Speed;
    public double Power;
    public IClass(double Agility, double Endurance,
                  double Opposite, double Speed,
                  double Power){
        this.Agility = Agility;
        this.Endurance = Endurance;
        this.Opposite =Opposite;
        this.Speed = Speed;
        this.Power =Power;
    }
}
