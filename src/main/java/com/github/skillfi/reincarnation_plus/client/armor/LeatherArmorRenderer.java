package com.github.skillfi.reincarnation_plus.client.armor;

import com.github.skillfi.reincarnation_plus.item.custom.LeatherArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class LeatherArmorRenderer extends GeoArmorRenderer<LeatherArmorItem> {


    public LeatherArmorRenderer() {
        super(new LeatherArmorModel());

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
