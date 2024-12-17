package com.github.skillfi.reincarnation_plus.libs.data.recipe;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
import com.github.skillfi.reincarnation_plus.libs.data.pack.MagicInfuserMoltenMaterial;
import com.github.skillfi.reincarnation_plus.libs.data.pack.ReiData;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class MagicInfuserMeltingRecipe extends MagicInfuserRecipe {
    private static final Logger log = LogManager.getLogger(MagicInfuserMeltingRecipe.class);
    @Getter
    public final ResourceLocation id;
    @Getter
    private final Ingredient input;
    @Getter
    private final ResourceLocation magiculesId;
    @Getter
    private final float magicules;


    public boolean matches(MagiculaInfuserBlockEntity pContainer, Level pLevel) {
        ItemStack inputStack = pContainer.getItem(1).copy();
        if (!this.input.test(inputStack)) {
            return false;
        } else {
            return this.sameOrEmpty(pContainer, this.magiculesId, this.magicules);
        }
    }

    private boolean sameOrEmpty(MagiculaInfuserBlockEntity container, ResourceLocation type, float amount) {
        for(MagicInfuserMoltenMaterial moltenMaterial : ReiData.getMagicInfuserMoltenMaterials()) {
            if (moltenMaterial.getMoltenType().equals(type)) {
                Optional<ResourceLocation> containerMaterial = moltenMaterial.isLeftBar() ? container.getLeftBarId() : Optional.empty();
                if (containerMaterial.isEmpty()) {
                    return true;
                }

                if (((ResourceLocation)containerMaterial.get()).equals(MagicInfusionRecipe.EMPTY)) {
                    return true;
                }

                float existingAmount = container.getMagicMaterialAmount();
                if (amount > (container.getMaxMagicMaterialAmount() + container.getAdditionalMagicMaterialAmount()) - existingAmount && (container.getMaxMagicMaterialAmount() + container.getAdditionalMagicMaterialAmount()) - existingAmount != 0){
                    return true;
                }
                return existingAmount + amount <= (container.getMaxMagicMaterialAmount() + container.getAdditionalMagicMaterialAmount());
            }
        }

        return false;
    }

    public ItemStack assemble(MagiculaInfuserBlockEntity pContainer) {
        this.melt(pContainer, this.magiculesId, this.magicules);
        pContainer.removeItem(1, 1);
        return ItemStack.EMPTY.copy();
    }

    private void melt(MagiculaInfuserBlockEntity container, ResourceLocation type, float amount) {
        if (!type.equals(MagicInfusionRecipe.EMPTY)) {
            ReiData.getMagicInfuserMoltenMaterials().parallelStream().
                    filter((moltenMaterial) -> moltenMaterial.getMoltenType().equals(type)).
                    findFirst().ifPresentOrElse((moltenMaterial) -> {
                if (moltenMaterial.isLeftBar()) {
                    container.setLeftBarId(Optional.of(moltenMaterial.getMoltenType()));
                    container.addMoltenMaterialAmount(Math.min(amount, (container.getMaxMagicMaterialAmount() + container.getAdditionalMagicMaterialAmount()) - container.getMagicMaterialAmount()));
                }

            }, () -> log.error("Could not assemble MeltingRecipe: {}", this));
        }
    }

    public RecipeSerializer<?> getSerializer() {
        return (RecipeSerializer)ReiRecipeTypes.Serializer.MAGIC_INFUSER_MELTING.get();
    }

    public RecipeType<?> getType() {
        return (RecipeType) ReiRecipeTypes.MAGIC_INFUSER_MELTING.get();
    }

    public FinishedRecipe finishRecipe() {
        return new FinishedRecipe() {
            public void serializeRecipeData(JsonObject root) {
                root.add("input", MagicInfuserMeltingRecipe.this.input.toJson());
                root.addProperty("magiculesId", MagicInfuserMeltingRecipe.this.magiculesId.toString());
                root.addProperty("magicules", MagicInfuserMeltingRecipe.this.magicules);
            }

            public ResourceLocation getId() {
                return MagicInfuserMeltingRecipe.this.id;
            }

            public RecipeSerializer<?> getType() {
                return MagicInfuserMeltingRecipe.this.getSerializer();
            }

            public @Nullable JsonObject serializeAdvancement() {
                return null;
            }

            public @Nullable ResourceLocation getAdvancementId() {
                return null;
            }
        };
    }

    public MagicInfuserMeltingRecipe(ResourceLocation id, Ingredient input, ResourceLocation magiculesId, float magicules) {
        this.id = id;
        this.input = input;
        this.magiculesId = magiculesId;
        this.magicules = magicules;

    }

    public String toString() {
        ResourceLocation var10000 = this.getId();
        return "MagicInfuserMeltingRecipe(id=" + var10000 + ", Input=" + this.getInput() + ", MagiculesId=" + this.getMagiculesId() + ", Magicules=" + this.getMagicules() + ")";
    }

    public static class Serializer implements RecipeSerializer<MagicInfuserMeltingRecipe> {
        public Serializer() {
        }

        public MagicInfuserMeltingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));
            ResourceLocation moltenType = ResourceLocation.tryParse(GsonHelper.getAsString(pSerializedRecipe, "magiculesId"));
            float moltenAmount = GsonHelper.getAsFloat(pSerializedRecipe, "magicules");
            return new MagicInfuserMeltingRecipe(pRecipeId, input, moltenType, moltenAmount);
        }

        public @Nullable MagicInfuserMeltingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
    return new MagicInfuserMeltingRecipe(pRecipeId, Ingredient.fromNetwork(pBuffer), pBuffer.readResourceLocation(), pBuffer.readInt());
        }

        public void toNetwork(FriendlyByteBuf pBuffer, MagicInfuserMeltingRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeResourceLocation(pRecipe.magiculesId);
            pBuffer.writeFloat(pRecipe.magicules);

        }
    }

    public static class Type implements RecipeType<MagicInfuserMeltingRecipe> {
        public Type() {
        }
    }

    public static class Builder {
        private Ingredient input;
        private final ResourceLocation moltenType;
        private final float moltenAmount;


        public Builder requires(Ingredient ingredient) {
            this.input = ingredient;
            return this;
        }

        public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
            consumer.accept((new MagicInfuserMeltingRecipe(id, this.input == null ? Ingredient.EMPTY : this.input, this.moltenType, this.moltenAmount)).finishRecipe());
        }

        public void build(Consumer<FinishedRecipe> consumer, String fileName) {
            this.build(consumer, new ResourceLocation(this.moltenType.getNamespace(), "magic_melting/" + fileName));
        }

        private Builder(ResourceLocation moltenType, float moltenAmount) {
            this.moltenType = moltenType;
            this.moltenAmount = moltenAmount;
        }

        public static Builder of(ResourceLocation moltenType, float moltenAmount) {
            return new Builder(moltenType, moltenAmount);
        }
    }
}
