package com.github.skillfi.reincarnation_plus.libs.capability.subordinates;

import com.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.entity.work.Work;
import com.github.skillfi.reincarnation_plus.core.network.ReiNetwork;
import com.github.skillfi.reincarnation_plus.core.network.SyncSubordinateCapabilityPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubordinateCapability implements ISubordinateCapability{
    private final List<Subordinate> subordinates = new ArrayList<>();
    public static final Capability<ISubordinateCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<ISubordinateCapability>() {
    });
    private static final ResourceLocation ID = new ResourceLocation(ReiMod.MODID, "subordinate_cap");


    public List<Subordinate> getSubordinates() {
        return subordinates;
    }

    public Subordinate getSubordinate(Subordinate subordinate) {
        // Шукаємо subordinate в списку subordinates
        return Objects.requireNonNull(getSubordinates().stream()
                .filter(s -> s.equals(subordinate)) // Порівнюємо за умовою equals
                .findFirst()                        // Повертаємо перший знайдений
                .orElse(null));                      // Якщо не знайдено, повертаємо null
    }



    public void removeSubordinate(Subordinate entity) {
        subordinates.remove(entity);
    }

    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof Player) {
            e.addCapability(ID, new SubordinatesCapabilityProvider());
        }

    }

    public static LazyOptional<ISubordinateCapability> getFrom(Player player) {
        return player.getCapability(CAPABILITY);
    }

    public static void sync(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            getFrom(serverPlayer).ifPresent((data) -> ReiNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> serverPlayer), new SyncSubordinateCapabilityPacket(data, serverPlayer.getId())));
        }

    }


    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag subordinatesList = new ListTag();
        for (Subordinate subordinate: getSubordinates()){
            subordinatesList.add(subordinate.entity.serializeNBT());
        }
        tag.put("subordinates", subordinatesList);
        return tag;
    }

    public void addSubordinate(TensuraTamableEntity entity){
        Subordinate subordinate = new Subordinate(entity, Work.WorkType.EMPTY);
        subordinates.add(subordinate);
    }


    public void deserializeNBT(CompoundTag compoundTag) {
        ListTag subordinatesList = compoundTag.getList("subordinates", 10); // Тип 10 — CompoundTag
        if (subordinatesList != null) {
            subordinates.clear(); // Очистити список перед десеріалізацією

            for (int i = 0; i < subordinatesList.size(); ++i) {
                CompoundTag subordinateTag = subordinatesList.getCompound(i);

                // Відновлення сутності зі світу
                Entity subordinate = EntityType.loadEntityRecursive(subordinateTag, getWorld(), (entity) -> entity);
                if (subordinate instanceof TensuraTamableEntity tamable) {
                    addSubordinate(tamable);
                }
            }
        }
    }

    /**
     * Метод для отримання світу з капабіліті.
     */
    private ServerLevel getWorld() {
        return getSubordinates().isEmpty() ? null : (ServerLevel) getSubordinates().get(0).entity.level;
    }
}
