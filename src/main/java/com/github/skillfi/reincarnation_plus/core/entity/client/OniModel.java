package com.github.skillfi.reincarnation_plus.core.entity.client;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.entity.OniEntity;
import com.github.skillfi.reincarnation_plus.core.entity.variant.ogre.Gender;
import com.github.skillfi.reincarnation_plus.core.entity.variant.ogre.Skin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class OniModel extends AnimatedGeoModel<OniEntity> {
    public ResourceLocation getModelResource(OniEntity enity) {
        return (ResourceLocation) Gender.getModelLocation(enity);
    }

    public ResourceLocation getTextureResource(OniEntity ogreEntity) {
        return (ResourceLocation) Skin.getTextureLocation(ogreEntity);
    }


    public ResourceLocation getAnimationResource(OniEntity ogreEntity) {
        return new ResourceLocation(ReiMod.MODID, "animations/ogre.animation.json");
    }

    public void setCustomAnimations(OniEntity bear, int instanceId, AnimationEvent customPredicate) {
        super.setCustomAnimations(bear, instanceId, customPredicate);
        boolean hasChestplate = !bear.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
        IBone breast = this.getAnimationProcessor().getBone("Brest");
        if (breast != null){
            if (hasChestplate != breast.isHidden()) {
                breast.setHidden(hasChestplate);
            }
        }
        IBone breastArmor = this.getAnimationProcessor().getBone("BreastArmor");
        if (breastArmor != null){
            if (hasChestplate != breastArmor.isHidden()) {
                breastArmor.setHidden(hasChestplate);
            }
        }


        EntityModelData extraData = (EntityModelData)customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            head.setRotationX(extraData.headPitch * (float)Math.PI / 180.0F);
            head.setRotationY(extraData.netHeadYaw * (float)Math.PI / 180.0F);
        }

    }
}
