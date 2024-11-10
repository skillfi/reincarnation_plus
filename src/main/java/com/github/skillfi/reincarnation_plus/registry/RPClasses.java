package com.github.skillfi.reincarnation_plus.registry;

import com.github.skillfi.isekaicore.api.classes.ClassesAPI;
import com.github.skillfi.isekaicore.api.classes.IsekaiClass;
import com.github.skillfi.reincarnation_plus.ReincarnationPlus;
import com.github.skillfi.reincarnation_plus.ability.classes.BlackSmithClass;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class RPClasses {
    private static final DeferredRegister<IsekaiClass> registy = DeferredRegister.create(ClassesAPI.getClassRegistryKey(), ReincarnationPlus.MODID);
    public static final RegistryObject<BlackSmithClass> BLACK_SMITH_CLASS = registy.register("blacksmith", BlackSmithClass::new);

    public static void init(IEventBus modEventBus){
        registy.register(modEventBus);
    }
}
