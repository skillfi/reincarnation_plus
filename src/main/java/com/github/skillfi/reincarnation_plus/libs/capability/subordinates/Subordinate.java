package com.github.skillfi.reincarnation_plus.libs.capability.subordinates;

import com.github.manasmods.tensura.capability.ep.ITensuraEPCapability;
import com.github.manasmods.tensura.capability.ep.TensuraEPCapability;
import com.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import com.github.skillfi.reincarnation_plus.core.entity.work.Work;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Subordinate {

    public TensuraTamableEntity entity;
    public Work.WorkType workType;

    public Subordinate(TensuraTamableEntity entity, Work.WorkType workType){
        this.entity = entity;
        this.workType = workType;
    }

    @Nullable
    public Optional<String> getName() {
        return entity.getCapability(TensuraEPCapability.CAPABILITY)
                .map(ITensuraEPCapability::getName);
    }

}
