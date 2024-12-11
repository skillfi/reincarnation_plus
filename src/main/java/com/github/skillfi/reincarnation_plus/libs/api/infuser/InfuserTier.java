package com.github.skillfi.reincarnation_plus.libs.api.infuser;

import lombok.Getter;
import lombok.Setter;

public class InfuserTier implements IInfuserTier{
    @Getter
    @Setter
    private int tier;
    @Getter
    @Setter
    private float speedBoost;
}
