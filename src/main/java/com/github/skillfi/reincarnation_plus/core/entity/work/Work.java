package com.github.skillfi.reincarnation_plus.core.entity.work;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class Work {
    private final WorkType type;

    public Work(WorkType type){
        this.type = type;
    }

    public WorkType getType() {
        return this.type;
    }

    public static enum WorkType{

        HUNT("hunt", ChatFormatting.GREEN),
        MINE("mine", ChatFormatting.GOLD),
        EMPTY("empty", ChatFormatting.BLACK);


        private final String namespace;
        private final ChatFormatting chatFormatting;

        public String getNamespace() {
            return this.namespace;
        }

        public MutableComponent getName() {
            return Component.translatable("reincarnation_plus.work.type." + this.namespace).withStyle(this.chatFormatting);
        }

        private WorkType(String namespace, ChatFormatting chatFormatting) {
            this.namespace = namespace;
            this.chatFormatting = chatFormatting;
        }
    }
}
