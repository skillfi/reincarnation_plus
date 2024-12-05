package com.github.skillfi.reincarnation_plus.core.network.play2server;

import com.github.skillfi.reincarnation_plus.core.menu.MagicInfuserMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RequuestMagicInfuserActionPacket {
    private final Action action;

    public RequuestMagicInfuserActionPacket(FriendlyByteBuf buf) {
        this.action = (Action)buf.readEnum(Action.class);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(this.action);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> this.action.contextConsumer.accept((NetworkEvent.Context)ctx.get()));
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }

    public RequuestMagicInfuserActionPacket(Action action) {
        this.action = action;
    }

    public static enum Action {
        INFUSION_LEFT((context) -> {
            AbstractContainerMenu patt976$temp = context.getSender().containerMenu;
            if (patt976$temp instanceof MagicInfuserMenu menu) {
                if (!menu.blockEntity.hasPrevInfusionRecipe()) {
                    return;
                }

                menu.blockEntity.infusionPrevRecipe();
            }

        }),
        INFUSION_RIGHT((context) -> {
            AbstractContainerMenu patt1242$temp = context.getSender().containerMenu;
            if (patt1242$temp instanceof MagicInfuserMenu menu) {
                if (!menu.blockEntity.hasNextInfusionRecipe()) {
                    return;
                }

                menu.blockEntity.infusuinNextRecipe();
            }

        }),
        INFUSION((context -> {
            AbstractContainerMenu patt1242$temp = context.getSender().containerMenu;
            if (patt1242$temp instanceof MagicInfuserMenu menu) {
                if (!menu.blockEntity.checkInfusion()) {
                    return;
                }

                menu.blockEntity.checkInfusion();
            }
        }));

        private final Consumer<NetworkEvent.Context> contextConsumer;

        private Action(Consumer<NetworkEvent.Context> contextConsumer) {
            this.contextConsumer = contextConsumer;
        }
    }
}
