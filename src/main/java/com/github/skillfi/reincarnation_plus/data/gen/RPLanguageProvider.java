package com.github.skillfi.reincarnation_plus.data.gen;

import com.github.manasmods.manascore.api.data.gen.LanguageProvider;
import com.github.skillfi.reincarnation_plus.ReincarnationPlus;
import com.github.skillfi.reincarnation_plus.ability.classes.IClass;
import com.github.skillfi.reincarnation_plus.registry.RPClasses;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

public class RPLanguageProvider extends LanguageProvider {

    public RPLanguageProvider(GatherDataEvent gatherDataEvent) {
        super(gatherDataEvent, ReincarnationPlus.MODID);
    }

    protected void generate(){
        addClass(RPClasses.BLACK_SMITH_CLASS, "Blacksmith", "Smithing crystals");
    }

    protected void addClass(RegistryObject<? extends IClass> iClass, String name, String description){
        add(String.format("%s.class.%s", iClass.getId().getNamespace(), iClass.getId().getPath().replace('/', '.')), name);
        add(String.format("%s.class.%s.description", iClass.getId().getNamespace(), iClass.getId().getPath().replace('/', '.')), description);
    }
}
