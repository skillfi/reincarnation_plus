package com.github.skillfi.reincarnation_plus.libs.capability.subordinates;

import com.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface ISubordinateCapability extends INBTSerializable<CompoundTag> {
    List<Subordinate> getSubordinates();

    void addSubordinate(TensuraTamableEntity entity);

    void removeSubordinate(Subordinate entity);

    Subordinate getSubordinate(Subordinate subordinate);
}
