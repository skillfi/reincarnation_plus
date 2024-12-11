package com.github.skillfi.reincarnation_plus.libs.api.infuser;

import lombok.Getter;
import lombok.Setter;

public class UpgradeType implements IUpgradeType{
    @Getter
    @Setter
    private int id;
    @Setter
    @Getter
    private int param;
}
