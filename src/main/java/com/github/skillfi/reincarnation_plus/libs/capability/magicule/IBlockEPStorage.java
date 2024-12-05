package com.github.skillfi.reincarnation_plus.libs.capability.magicule;

public interface IBlockEPStorage {
    int getMagiculesStored();
    int getMaxMagiculesStored();
    int getMaxTier();
    int getTier();
    void receiveMagicules(int magicules);
    int extractMagicules(int magicules);
    void updateCapacity(int magicules);
    void updateTier(int tier, int additionalMaxEP);
}

