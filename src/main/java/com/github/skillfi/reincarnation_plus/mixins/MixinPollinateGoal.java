package com.github.skillfi.reincarnation_plus.mixins;

import com.github.manasmods.tensura.api.magicule.MagiculeAPI;
import com.github.manasmods.tensura.capability.ep.TensuraEPCapability;
import com.github.manasmods.tensura.registry.attribute.TensuraAttributeRegistry;
import cy.jdkdigital.productivebees.common.entity.bee.ConfigurableBee;
import cy.jdkdigital.productivebees.common.entity.bee.ProductiveBee;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(value = ProductiveBee.PollinateGoal.class)
public abstract class MixinPollinateGoal {

    @Inject(method = "stop", at = @At("TAIL"), remap = false)
    private void injectConsumeMagicule(CallbackInfo ci) {
        try {
            // Використання reflection для отримання зовнішнього класу
            Field outerField = ProductiveBee.PollinateGoal.class.getDeclaredField("this$0");
            outerField.setAccessible(true);

            // Отримання екземпляра зовнішнього класу (ConfigurableBee)
            if (outerField.get(this) instanceof ConfigurableBee productiveBee){
                String beeName = productiveBee.getBeeName();
                if (beeName.equals("magic") || beeName.equals("mithril") || beeName.equals("hihiirokane")
                        || beeName.equals("orichalcum") || beeName.equals("pure_magic_steel") || beeName.equals("low_magic_steel")
                        || beeName.equals("adamantite") || beeName.equals("high_magic_steel")) {
                    // Виклик методу API для витрати магікулів
                    MagiculeAPI.consumeMagicule(productiveBee, 20.0);
                    TensuraEPCapability.getFrom(productiveBee).ifPresent((cap)->{
                        cap.setGainedEP(cap.getGainedEP() + 20.0);
                        double gainedEP = cap.getGainedEP();
                        double points = (double)((int)gainedEP / 10000);
                        if (points > (double)0.0F) {
                            cap.setGainedEP(gainedEP - points * (double)10000.0F);
                            cap.setCurrentEP(productiveBee, cap.getCurrentEP() + cap.getGainedEP());
                            AttributeInstance armor = productiveBee.getAttribute(Attributes.ARMOR);
                            if (armor != null) {
                                armor.setBaseValue(armor.getBaseValue() + points);
                            }

                            AttributeInstance attack = productiveBee.getAttribute(Attributes.ATTACK_DAMAGE);
                            if (attack != null) {
                                attack.setBaseValue(attack.getBaseValue() + points);
                            }

                            AttributeInstance HP = productiveBee.getAttribute(Attributes.MAX_HEALTH);
                            if (HP != null) {
                                HP.setBaseValue(HP.getBaseValue() + points * (double)10.0F);
                            }

                            AttributeInstance SHP = productiveBee.getAttribute((Attribute) TensuraAttributeRegistry.MAX_SPIRITUAL_HEALTH.get());
                            if (SHP != null) {
                                SHP.setBaseValue(SHP.getBaseValue() + points * (double)20.0F);
                            }

                            productiveBee.heal((float)points * 10.0F);
                        }

                        TensuraEPCapability.sync(productiveBee);
                    });
                }
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

