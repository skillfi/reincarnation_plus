package com.github.skillfi.reincarnation_plus.client.armor;

import com.github.skillfi.reincarnation_plus.item.custom.KimonoWomanArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class KimonoWomanArmorRenderer extends GeoArmorRenderer<KimonoWomanArmorItem> {


    public KimonoWomanArmorRenderer() {
        super(new KimonoWomanArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";
    }
}
