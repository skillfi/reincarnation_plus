package com.github.skillfi.reincarnation_plus.libs.data.recipe.infuser;

import com.github.manasmods.tensura.data.recipe.KilnMixingRecipe;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
import com.github.skillfi.reincarnation_plus.libs.data.pack.MagicInfuserMoltenMaterial;
import com.github.skillfi.reincarnation_plus.libs.data.pack.ReiData;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import lombok.Getter;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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

@Getter
public class MagicInfuserMeltingRecipe extends MagicInfuserRecipe {
    private static final Logger log = LogManager.getLogger(MagicInfuserMeltingRecipe.class);
    public final ResourceLocation id;
    private final Ingredient input;
    private final ResourceLocation moltenType;
    private final int moltenAmount;
    private final ResourceLocation secondaryType;
    private final int secondaryAmount;
    private final ItemStack output;


    public boolean matches(MagiculaInfuserBlockEntity pContainer, Level pLevel) {
        ItemStack inputStack = pContainer.getItem(1).copy();
        if (!this.input.test(inputStack)) {
            return false;
        } else {
            boolean primaryPlaceable = this.sameOrEmpty(pContainer, this.moltenType, this.moltenAmount);
            if (this.secondaryType.equals(KilnMixingRecipe.EMPTY)) {
                return primaryPlaceable;
            } else {
                return !primaryPlaceable ? false : this.sameOrEmpty(pContainer, this.secondaryType, this.secondaryAmount);
            }
        }
    }

    public ItemStack getResultItem() {
        return output;
    }

    private boolean sameOrEmpty(MagiculaInfuserBlockEntity container, ResourceLocation type, float amount) {
        for(MagicInfuserMoltenMaterial moltenMaterial : ReiData.getMagicInfuserMoltenMaterials()) {
            if (moltenMaterial.getMoltenType().equals(type)) {
                Optional<ResourceLocation> containerMaterial = moltenMaterial.isRightBar() ? container.getRightBarId() : container.getLeftBarId();
                if (containerMaterial.isEmpty()) {
                    return true;
                }

                if (((ResourceLocation)containerMaterial.get()).equals(KilnMixingRecipe.EMPTY)) {
                    return true;
                }

                if (!((ResourceLocation)containerMaterial.get()).equals(type)) {
                    return false;
                }

                int existingAmount = moltenMaterial.isRightBar() ? container.getMagicMaterialAmount() : container.getExistencePointsAmount();
                return existingAmount + amount <= 144;
            }
        }

        return false;
    }

    public ItemStack assemble(MagiculaInfuserBlockEntity pContainer) {
        this.melt(pContainer, this.moltenType, this.moltenAmount);
        this.melt(pContainer, this.secondaryType, this.secondaryAmount);
        pContainer.removeItem(1, 1);
        pContainer.setItem(3, getResultItem());
        return ItemStack.EMPTY.copy();
    }

    public int calculateEpFromEnchantments(ItemStack itemStack, int amount) {
        // Перевірка на наявність чарів]
        if (!itemStack.hasTag() || !itemStack.getTag().contains("Enchantments", 9)) {
            return 0; // Якщо немає чарів, досвід дорівнює 0
        }

        // Отримуємо список чарів
        ListTag enchantments = itemStack.getTag().getList("Enchantments", 10);

        int totalXp = amount;
        int totalLVl = 0;

        // Розрахунок досвіду для кожного зачарування
        for (int i = 0; i < enchantments.size(); i++) {
            CompoundTag enchantment = enchantments.getCompound(i);
            int level = enchantment.getInt("lvl"); // Рівень зачарування
            totalLVl += level;
        }

        return totalXp * totalLVl;
    }

    private void melt(MagiculaInfuserBlockEntity container, ResourceLocation type, int amount) {
        if (!type.equals(MagicInfusionRecipe.EMPTY)) {
            ReiData.getMagicInfuserMoltenMaterials().parallelStream().
                    filter((moltenMaterial) -> moltenMaterial.getMoltenType().equals(type)).
                    findFirst().ifPresentOrElse((moltenMaterial) -> {
                        if (moltenMaterial.isRightBar()) {
                            container.setRightBarId(Optional.of(moltenMaterial.getMoltenType()));
                            container.addMoltenMaterialAmount(amount);
                        } else {
                            ItemStack stack = container.getItem(1);
                            if (stack.isEnchanted()){
                                container.setLeftBarId(Optional.of(moltenMaterial.getMoltenType()));
                                container.addExperiencePointsAmount(calculateEpFromEnchantments(stack, amount));
                            }

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
                root.addProperty("moltenType", MagicInfuserMeltingRecipe.this.moltenType.toString());
                root.addProperty("magicules", MagicInfuserMeltingRecipe.this.moltenAmount);
                if (!MagicInfuserMeltingRecipe.this.secondaryType.equals(KilnMixingRecipe.EMPTY)) {
                    root.addProperty("secondaryType", MagicInfuserMeltingRecipe.this.secondaryType.toString());
                    root.addProperty("secondaryAmount", MagicInfuserMeltingRecipe.this.secondaryAmount);
                }
                root.add("result", ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, MagicInfuserMeltingRecipe.this.output).result().orElseThrow(() -> new IllegalArgumentException("Could not serialize ItemStack: " + MagicInfuserMeltingRecipe.this.output)));
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

    public MagicInfuserMeltingRecipe(ResourceLocation id, Ingredient input, ResourceLocation moltenType, int moltenAmount, ResourceLocation secondaryType, int secondaryAmount, ItemStack output) {
        this.id = id;
        this.input = input;
        this.moltenType = moltenType;
        this.moltenAmount = moltenAmount;
        this.secondaryType = secondaryType;
        this.secondaryAmount = secondaryAmount;
        this.output = output;
    }

    public String toString() {
        ResourceLocation var10000 = this.getId();
        return "MagicInfuserMeltingRecipe(id=" + var10000 + ", Input=" + this.getInput() + ", moltenType=" + this.getMoltenType() + ", moltenAmount=" + this.getMoltenAmount() + ", secondaryType=" + this.getSecondaryType() + ", secondaryAmount=" + this.getSecondaryAmount() +  ")";
    }

    public static class Serializer implements RecipeSerializer<MagicInfuserMeltingRecipe> {
        public Serializer() {
        }

        public MagicInfuserMeltingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));
            ResourceLocation moltenType = ResourceLocation.tryParse(GsonHelper.getAsString(pSerializedRecipe, "moltenType"));
            int moltenAmount = GsonHelper.getAsInt(pSerializedRecipe, "moltenAmount");
            ResourceLocation secondaryType = MagicInfusionRecipe.EMPTY;
            int secondaryAmount = 0;
            if (pSerializedRecipe.has("secondaryType")) {
                secondaryType = ResourceLocation.tryParse(GsonHelper.getAsString(pSerializedRecipe, "secondaryType"));
                secondaryAmount = GsonHelper.getAsInt(pSerializedRecipe, "secondaryAmount");
            }

            ItemStack output = (ItemStack)((Pair)ItemStack.CODEC.decode(JsonOps.INSTANCE, pSerializedRecipe.get("result")).result().orElseThrow(() -> new IllegalArgumentException("Could not load result ItemStack from: " + pRecipeId))).getFirst();
            return new MagicInfuserMeltingRecipe(pRecipeId, input, moltenType, moltenAmount, secondaryType, secondaryAmount, output);
        }

        public @Nullable MagicInfuserMeltingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new MagicInfuserMeltingRecipe(pRecipeId, Ingredient.fromNetwork(pBuffer), pBuffer.readResourceLocation(), pBuffer.readInt(), pBuffer.readResourceLocation(), pBuffer.readInt(), pBuffer.readItem());
        }

        public void toNetwork(FriendlyByteBuf pBuffer, MagicInfuserMeltingRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeResourceLocation(pRecipe.moltenType);
            pBuffer.writeInt(pRecipe.moltenAmount);
            pBuffer.writeResourceLocation(pRecipe.secondaryType);
            pBuffer.writeInt(pRecipe.secondaryAmount);
            pBuffer.writeItemStack(pRecipe.output, false);
        }
    }

    public static class Type implements RecipeType<MagicInfuserMeltingRecipe> {
        public Type() {
        }
    }

    public static class Builder {
        private Ingredient input;
        private final ResourceLocation moltenType;
        private final int moltenAmount;
        private ResourceLocation secondaryType;
        private int secondaryAmount;
        private ItemStack output;


        public Builder requires(Ingredient ingredient) {
            this.input = ingredient;
            return this;
        }

        public Builder outputRequires(ItemStack stack){
            this.output = stack;
            return this;
        }

        public Builder secondary(ResourceLocation secondaryType, int secondaryAmount){
            this.secondaryType = secondaryType;
            this.secondaryAmount = secondaryAmount;
            return this;
        }

        public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
            consumer.accept((new MagicInfuserMeltingRecipe(id, this.input == null ? Ingredient.EMPTY : this.input, this.moltenType, this.moltenAmount, this.secondaryType, this.secondaryAmount, this.output == null ? ItemStack.EMPTY : this.output)).finishRecipe());
        }

        public void build(Consumer<FinishedRecipe> consumer, String fileName) {
            this.build(consumer, new ResourceLocation(this.moltenType.getNamespace(), "magic_melting/" + fileName));
        }

        private Builder(ResourceLocation moltenType, int moltenAmount) {
            this.secondaryType = MagicInfusionRecipe.EMPTY;
            this.secondaryAmount = 0;
            this.moltenType = moltenType;
            this.moltenAmount = moltenAmount;
        }

        public static Builder of(ResourceLocation moltenType, int moltenAmount) {
            return new Builder(moltenType, moltenAmount);
        }
    }
}
