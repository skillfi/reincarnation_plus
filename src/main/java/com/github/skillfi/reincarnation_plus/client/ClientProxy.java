package com.github.skillfi.reincarnation_plus.client;

import com.github.skillfi.reincarnation_plus.CommonProxy;
import com.github.skillfi.reincarnation_plus.RPMod;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPMod.MODID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void init(){

    }

    public void clientInit(){

    }

    public Player getClientSidePlayer(){
        return null;}
}
