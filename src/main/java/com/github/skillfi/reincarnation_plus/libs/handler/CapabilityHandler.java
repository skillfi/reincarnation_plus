package com.github.skillfi.reincarnation_plus.libs.handler;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.libs.capability.magicule.IBlockEPStorage;
import com.github.skillfi.reincarnation_plus.libs.capability.subordinates.ISubordinateCapability;
import com.github.skillfi.reincarnation_plus.libs.capability.subordinates.SubordinateCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(
        modid = ReiMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class CapabilityHandler {
    public CapabilityHandler() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent e){
        e.register(ISubordinateCapability.class);
        e.register(IBlockEPStorage.class);
    }

    @SubscribeEvent
    static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e){
        SubordinateCapability.sync(e.getEntity());

    }

    @SubscribeEvent
    static void onPlayerTrack(PlayerEvent.StartTracking e) {
        Entity var2 = e.getTarget();
        if (var2 instanceof Player player){
            SubordinateCapability.sync(player);
        }
    }

    @SubscribeEvent
    static void onPlayerClone(PlayerEvent.Clone e) {
        e.getOriginal().reviveCaps();
        SubordinateCapability.getFrom(e.getOriginal()).ifPresent((oldData)->{
            SubordinateCapability.getFrom(e.getEntity()).ifPresent((data)->{
                data.deserializeNBT((CompoundTag)oldData.serializeNBT());
            });
        });
        e.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent e) {
        SubordinateCapability.sync(e.getEntity());
    }

    @SubscribeEvent
    static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent e) {
        SubordinateCapability.sync(e.getEntity());
    }

    @Nullable
    public static <T> T getCapability(Entity entity, Capability<T> capability) {
        return (T)(entity.getCapability(capability).isPresent() ? entity.getCapability(capability).orElseThrow(() -> new IllegalArgumentException("Lazy optional must not be empty")) : null);
    }
}
