package com.github.skillfi.reincarnation_plus.libs.api.infuser;

import lombok.Getter;
import lombok.Setter;

public class InfuserUpgrade implements IInfuserUpgrade{

    @Getter
    @Setter
    private int level;
    @Getter
    @Setter
    private IUpgradeType type;
}
