package com.github.skillfi.reincarnation_plus.libs.data.pack;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MagicInfuserMoltenMaterial {
    public static final Codec<MagicInfuserMoltenMaterial> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.BOOL.fieldOf("leftBar").forGetter(MagicInfuserMoltenMaterial::isLeftBar),
            ResourceLocation.CODEC.fieldOf("moltenType").
            forGetter(MagicInfuserMoltenMaterial::getMoltenType), Codec.INT.fieldOf("red").
            forGetter(MagicInfuserMoltenMaterial::getRed), Codec.INT.fieldOf("green").
            forGetter(MagicInfuserMoltenMaterial::getGreen), Codec.INT.fieldOf("blue").
            forGetter(MagicInfuserMoltenMaterial::getBlue), Codec.INT.fieldOf("alpha").
            forGetter(MagicInfuserMoltenMaterial::getAlpha),
            Codec.BOOL.fieldOf("rightBar").forGetter(MagicInfuserMoltenMaterial::isRightBar)).apply(instance, MagicInfuserMoltenMaterial::new));
    @Getter private final boolean leftBar;
    @Getter private final boolean rightBar;
    @Getter  private final ResourceLocation moltenType;
    @Getter private final int red;
    @Getter private final int green;
    @Getter private final int blue;
    @Getter private final int alpha;

    public static MagicInfuserMoltenMaterial of(ResourceLocation moltenType, boolean isLeftBar, Color color, boolean isRightBar) {
        return new MagicInfuserMoltenMaterial(isLeftBar, moltenType, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), isRightBar);
    }

    public void buildJson(BiConsumer<ResourceLocation, Supplier<JsonElement>> consumer) {
        consumer.accept(this.moltenType, (Supplier)() -> (JsonElement)CODEC.encodeStart(JsonOps.INSTANCE, this).result().orElseThrow(() -> new IllegalStateException("Could not serialize " + this)));
    }

    public MagicInfuserMoltenMaterial(boolean leftBar, ResourceLocation moltenType, int red, int green, int blue, int alpha, boolean rightBar) {
        this.leftBar = leftBar;
        this.moltenType = moltenType;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.rightBar = rightBar;
    }

    public String toString() {
        boolean var10000 = this.isLeftBar();
        return "MagicInfuserMoltenMaterial(leftBar=" + var10000 + ", moltenType=" + this.getMoltenType() + ", red=" + this.getRed() + ", green=" + this.getGreen() + ", blue=" + this.getBlue() + ", alpha=" + this.getAlpha() + ", rightBar="+isRightBar()+")";
    }
}
