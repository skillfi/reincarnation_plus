package com.github.skillfi.reincarnation_plus.entity.client;

import com.github.manasmods.tensura.entity.OrcEntity;
import com.github.skillfi.reincarnation_plus.RPMod;
import com.github.skillfi.reincarnation_plus.entity.OgreEntity;
import com.github.skillfi.reincarnation_plus.entity.variant.OgreVariant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class OgreModel<T extends OgreEntity> extends AnimatedGeoModel<T> {
    @Override
    public ResourceLocation getModelResource(T enity) {
        return (ResourceLocation) OgreVariant.Gender.getModelLocation(enity);
    }

    @Override
    public ResourceLocation getTextureResource(T ogreEntity) {
        return (ResourceLocation) OgreVariant.Skin.getTextureLocation(ogreEntity);
    }

    @Override
    public ResourceLocation getAnimationResource(T ogreEntity) {
        return new ResourceLocation(RPMod.MODID, "animations/ogre.animation.json");
    }

    public void setCustomAnimations(T bear, int instanceId, AnimationEvent customPredicate) {
        super.setCustomAnimations(bear, instanceId, customPredicate);
        boolean hasChestplate = !bear.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
        IBone breast = this.getAnimationProcessor().getBone("Breast");
        if (breast != null){
            if (hasChestplate != breast.isHidden()) {
                breast.setHidden(hasChestplate);
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
