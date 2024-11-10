package com.github.skillfi.reincarnation_plus.registry;

import com.github.skillfi.isekaicore.api.classes.ClassesAPI;
import com.github.skillfi.isekaicore.api.classes.IsekaiClass;
import com.github.skillfi.reincarnation_plus.ReincarnationPlus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class RPClasses {
    private static final DeferredRegister<IsekaiClass> registy = DeferredRegister.create(ClassesAPI.getClassRegistryKey(), ReincarnationPlus.MODID);


    public static void init(IEventBus modEventBus){
        registy.register(modEventBus);
    }
}
