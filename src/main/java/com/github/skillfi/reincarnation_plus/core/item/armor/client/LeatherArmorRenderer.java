package com.github.skillfi.reincarnation_plus.core.item.armor.client;

import com.github.manasmods.tensura.item.armor.client.TensuraGeoArmorRenderer;
import com.github.skillfi.reincarnation_plus.core.item.armor.LeatherArmorItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.processor.IBone;

public class LeatherArmorRenderer extends TensuraGeoArmorRenderer<LeatherArmorItem> {


    public LeatherArmorRenderer() {
        super(new LeatherArmorModel());
    }

    @Override
    protected void fitToBiped() {
        if (this.entityLiving instanceof IAnimatable) {
            if (this.headBone != null) {
                IBone headBone = this.getGeoModelProvider().getBone(this.headBone);
                headBone.setPositionX(this.head.x);
                headBone.setPositionY(-this.head.y);
                headBone.setPositionZ(this.head.z);
            }

            if (this.bodyBone != null) {
                IBone bodyBone = this.getGeoModelProvider().getBone(this.bodyBone);
                bodyBone.setPositionX(this.body.x);
                bodyBone.setPositionY(-this.body.y);
                bodyBone.setPositionZ(this.body.z);
            }

            if (this.rightArmBone != null) {
                IBone rightArmBone = this.getGeoModelProvider().getBone(this.rightArmBone);
                rightArmBone.setPositionX(this.rightArm.x + 8.0F);
                rightArmBone.setPositionY(this.rightArm.y);
                rightArmBone.setPositionZ(this.rightArm.z);

            }
//
            if (this.leftArmBone != null) {
                IBone leftArmBone = this.getGeoModelProvider().getBone(this.leftArmBone);
                leftArmBone.setPositionX(this.leftArm.x - 8.0F);
                leftArmBone.setPositionY(this.leftArm.y);
                leftArmBone.setPositionZ(this.leftArm.z);
            }

            if (this.rightLegBone != null) {
                IBone rightLegBone = this.getGeoModelProvider().getBone(this.rightLegBone);
                rightLegBone.setPositionX(this.rightLeg.x + 2.0F);
                rightLegBone.setPositionY(12.0F - this.rightLeg.y);
                rightLegBone.setPositionZ(this.rightLeg.z);
                if (this.rightBootBone != null) {
                    IBone rightBootBone = this.getGeoModelProvider().getBone(this.rightBootBone);
                    rightBootBone.setPositionX(this.rightLeg.x + 2.0F);
                    rightBootBone.setPositionY(12.0F - this.rightLeg.y);
                    rightBootBone.setPositionZ(this.rightLeg.z);
                }
            }

            if (this.leftLegBone != null) {
                IBone leftLegBone = this.getGeoModelProvider().getBone(this.leftLegBone);
                leftLegBone.setPositionX(this.leftLeg.x - 2.0F);
                leftLegBone.setPositionY(12.0F - this.leftLeg.y);
                leftLegBone.setPositionZ(this.leftLeg.z);
                if (this.leftBootBone != null) {
                    IBone leftBootBone = this.getGeoModelProvider().getBone(this.leftBootBone);
                    leftBootBone.setPositionX(this.leftLeg.x - 2.0F);
                    leftBootBone.setPositionY(12.0F - this.leftLeg.y);
                    leftBootBone.setPositionZ(this.leftLeg.z);
                }
            }
        }
    }
}
