package com.github.skillfi.reincarnation_plus.libs.handler;

import com.github.manasmods.manascore.api.world.chunk.LevelChunkTickEvent;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.capability.aura.AuraChunkCapabilityImpl;
import com.github.skillfi.reincarnation_plus.libs.capability.aura.IAuraChunkCapability;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = ReiMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class OnTickHandler {
    public OnTickHandler() {
    }

    @SubscribeEvent
    static void onChunkTick(LevelChunkTickEvent e) {
        LevelChunk chunk = e.getChunk();
        Level level = chunk.getLevel();
        if (!chunk.getLevel().isClientSide()) {
            if (e.phase == TickEvent.Phase.END) {
                IAuraChunkCapability cap = AuraChunkCapabilityImpl.get(chunk);
                ((AuraChunkCapabilityImpl)cap).tick();
            }
        }
    }
}
