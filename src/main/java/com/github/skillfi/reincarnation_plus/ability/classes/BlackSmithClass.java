package com.github.skillfi.reincarnation_plus.ability.classes;

import com.github.skillfi.isekaicore.api.classes.ClassesAPI;
import com.github.skillfi.reincarnation_plus.ReincarnationPlusMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.UUID;

public class BlackSmithClass extends  IClass{
    protected static final UUID BLACSMITH = UUID.randomUUID();
    public BlackSmithClass(){
        super(5.0F, 5.0F, 5.0F, 5.0F, 5.0F);
        this.addHeldAttributeModifier(Attributes.ARMOR, BLACSMITH.toString(), this.Opposite, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addHeldAttributeModifier(Attributes.ATTACK_DAMAGE, BLACSMITH.toString(), this.Power, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addHeldAttributeModifier(Attributes.ATTACK_SPEED, BLACSMITH.toString(), this.Speed, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addHeldAttributeModifier(Attributes.MAX_HEALTH, BLACSMITH.toString(), this.Endurance, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addHeldAttributeModifier(Attributes.MOVEMENT_SPEED, BLACSMITH.toString(), this.Agility + this.Speed, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    private void unlock(final LivingEntityUseItemEvent.Finish e){
        if (e.getEntity() instanceof ServerPlayer player) {
            if (e.getItem().is(Items.APPLE)) {
                if (ClassesAPI.getClassesFrom(player).learnClass(this)) {
                    ReincarnationPlusMod.LOGGER.debug("Unlocked Blacksmith class for player {}", player.getName().getString());
                }
            }
        }
    }
}
