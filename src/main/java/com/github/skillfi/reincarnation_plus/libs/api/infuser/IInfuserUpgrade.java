package com.github.skillfi.reincarnation_plus.libs.api.infuser;

public interface IInfuserUpgrade {

    int getLevel();
    void setLevel(int level);
    IUpgradeType getType();
    void setType(IUpgradeType type);
}
