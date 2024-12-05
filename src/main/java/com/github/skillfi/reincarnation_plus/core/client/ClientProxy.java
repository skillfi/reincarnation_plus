package com.github.skillfi.reincarnation_plus.core.client;

import com.github.skillfi.reincarnation_plus.core.CommonProxy;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ReiMod.MODID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void init(){

    }

    public void clientInit(){

    }

    public Player getClientSidePlayer(){
        return null;}
}
