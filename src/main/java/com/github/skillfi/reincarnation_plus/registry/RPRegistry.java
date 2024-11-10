package com.github.skillfi.reincarnation_plus.registry;

import net.minecraftforge.eventbus.api.IEventBus;

public class RPRegistry {

    public static void register(IEventBus modEventBus){
        RPClasses.init(modEventBus);
    }
}
