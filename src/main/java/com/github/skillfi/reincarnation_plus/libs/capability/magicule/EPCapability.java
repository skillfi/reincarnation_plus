package com.github.skillfi.reincarnation_plus.libs.capability.magicule;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.EPCoreTileEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class EPCapability {
    private final BlockEPStorage instance = new BlockEPStorage();
    private final LazyOptional<IBlockEPStorage> capabilityInstance = LazyOptional.of(() -> instance);
    public static final Capability<IBlockEPStorage> CAPABILITY = CapabilityManager.get(new CapabilityToken<IBlockEPStorage>() {
    });
    private static final ResourceLocation ID = new ResourceLocation(ReiMod.MODID, "block_ep_cap");

    // Метод обробник для підключення можливості до BlockEntity
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof EPCoreTileEntity) {
            EPCapabilityProvider provider = new EPCapabilityProvider();
            event.addCapability(ID, provider);
            event.addListener(provider::invalidate); // Зробити неактивним при видаленні
        }
    }

    public static LazyOptional<IBlockEPStorage> getFrom(EPCoreTileEntity epCoreEntity) {
        return epCoreEntity.getCapability(CAPABILITY);
    }

    public IBlockEPStorage getEpStorage() {
        return instance;
    }
}

// Провайдер для можливостей BlockEntity
class EPCapabilityProvider implements ICapabilityProvider {
    private final LazyOptional<IBlockEPStorage> instance = LazyOptional.of(BlockEPStorage::new);

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == EPCapability.CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    public void invalidate() {
        instance.invalidate();
    }
}