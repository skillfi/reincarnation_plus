package com.github.skillfi.reincarnation_plus.core.data.recipe.auto;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.AutoInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.data.pack.MagicInfuserMoltenMaterial;
import com.github.skillfi.reincarnation_plus.core.data.pack.ReiData;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;


public class AutoInfuserMeltingRecipe extends AutoMagicInfuserRecipe {
    private static final Logger log = LogManager.getLogger(AutoInfuserMeltingRecipe.class);
    @Getter public final ResourceLocation id;
    @Getter private final Ingredient input;
    @Getter private final ResourceLocation moltenType;
    @Getter private final int moltenAmount;
    @Getter private final ResourceLocation secondaryType;
    @Getter private final int secondaryAmount;


    public boolean matches(AutoInfuserBlockEntity pContainer, Level pLevel) {
        ItemStack inputStack = pContainer.getItem(1).copy();
        if (!this.input.test(inputStack)) {
            return false;
        } else {
            boolean primaryPlaceable = this.sameOrEmpty(pContainer, this.moltenType, this.moltenAmount);
            if (this.secondaryType.equals(AutoMagicInfusionRecipe.EMPTY)) {
                return primaryPlaceable;
            } else {
                return !primaryPlaceable ? false : this.sameOrEmpty(pContainer, this.secondaryType, this.secondaryAmount);
            }
        }
    }

    public ItemStack getResultItem() {
        for (ItemStack stack: input.getItems()){
            if (stack == Items.ENCHANTED_BOOK.getDefaultInstance()){
                return Items.BOOK.getDefaultInstance();
            }
        }
        return input.getItems()[0];
    }

    private boolean sameOrEmpty(AutoInfuserBlockEntity container, ResourceLocation type, float amount) {
        // Отримуємо capability контейнера
        return container.getCapability(ReiMod.MAGICULA_INFUSER_CAPABILITY).map(cap -> {
            // Проходимо по всіх матеріалах
            for (MagicInfuserMoltenMaterial moltenMaterial : ReiData.getMagicInfuserMoltenMaterials()) {
                if (moltenMaterial.getMoltenType().equals(type)) {
                    Optional<ResourceLocation> containerMaterial = moltenMaterial.isRightBar() ? cap.getRightBarId() : cap.getLeftBarId();

                    // Якщо слот порожній або тип збігається з рецептом
                    if (containerMaterial.isEmpty() || containerMaterial.get().equals(AutoMagicInfusionRecipe.EMPTY)) {
                        return true;
                    }
                    if (!containerMaterial.get().equals(type)) {
                        return false;
                    }

                    int existingAmount = moltenMaterial.isRightBar() ? cap.getMagicMaterialAmount() : cap.getMoltenAmount();
                    int maxAmount = cap.getMaxMagicMaterialAmount() + cap.getAdditionalMagicMaterialAmount();

                    // Перевірка кількості
                    if (moltenMaterial.isRightBar()) {
                        if (amount >= maxAmount - existingAmount) {
                            return true;
                        }
                        if (existingAmount == maxAmount) {
                            return false;
                        }
                    }
                }
            }
            return false;
        }).orElse(false);
    }


    public ItemStack assemble(AutoInfuserBlockEntity pContainer) {
        if (pContainer.getItem(1).isEnchanted() || pContainer.getItem(1) == Items.ENCHANTED_BOOK.getDefaultInstance()){
            this.melt(pContainer, this.secondaryType, this.secondaryAmount);
            pContainer.setItem(3, getResultItem());
            pContainer.removeItem(1, 1);
        } else {
            this.melt(pContainer, this.moltenType, this.moltenAmount);
            pContainer.removeItem(1, 1);
        }
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

    private void melt(AutoInfuserBlockEntity container, ResourceLocation type, int amount) {
        if (!type.equals(AutoMagicInfusionRecipe.EMPTY)) {
            ReiData.getMagicInfuserMoltenMaterials().parallelStream().
                    filter((moltenMaterial) -> moltenMaterial.getMoltenType().equals(type)).
                    findFirst().ifPresentOrElse((moltenMaterial) -> {
                        container.getCapability(ReiMod.MAGICULA_INFUSER_CAPABILITY).ifPresent((cap)->{
                            if (moltenMaterial.isRightBar()) {
                                cap.setRightBarId(Optional.of(moltenMaterial.getMoltenType()));
                                int maxMagicAmount = cap.getMaxMagicMaterialAmount();
                                int addMagicAmount = cap.getAdditionalMagicMaterialAmount();
                                int currentMagicAmount = cap.getMagicMaterialAmount();
                                if (maxMagicAmount + addMagicAmount < amount){
                                    container.addMagicMaterialAmount((maxMagicAmount+addMagicAmount)-currentMagicAmount);
                                }
                                else {
                                    container.addMagicMaterialAmount(amount);
                                }
                            } else {
                                ItemStack stack = container.getItem(1);
                                cap.setLeftBarId(Optional.of(moltenMaterial.getMoltenType()));
                                container.addMoltenMaterialAmount(calculateEpFromEnchantments(stack, amount));

                            }
                        });


            }, () -> log.error("Could not assemble MeltingRecipe: {}", this));
        }
    }

    public RecipeSerializer<?> getSerializer() {
        return (RecipeSerializer)ReiRecipeTypes.Serializer.MAGIC_INFUSER_MELTING.get();
    }

    public RecipeType<?> getType() {
        return (RecipeType) ReiRecipeTypes.MAGIC_INFUSER_MELTING.get();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.moltenType, this.moltenAmount, this.input, this.secondaryType, this.secondaryAmount});
    }

    public FinishedRecipe finishRecipe() {
        return new FinishedRecipe() {
            public void serializeRecipeData(JsonObject root) {
                root.add("input", AutoInfuserMeltingRecipe.this.input.toJson());
                root.addProperty("moltenType", AutoInfuserMeltingRecipe.this.moltenType.toString());
                root.addProperty("moltenAmount", AutoInfuserMeltingRecipe.this.moltenAmount);
                if (!AutoInfuserMeltingRecipe.this.secondaryType.equals(AutoMagicInfusionRecipe.EMPTY)) {
                    root.addProperty("secondaryType", AutoInfuserMeltingRecipe.this.secondaryType.toString());
                    root.addProperty("secondaryAmount", AutoInfuserMeltingRecipe.this.secondaryAmount);
                }
            }

            public ResourceLocation getId() {
                return AutoInfuserMeltingRecipe.this.id;
            }

            public RecipeSerializer<?> getType() {
                return AutoInfuserMeltingRecipe.this.getSerializer();
            }

            public @Nullable JsonObject serializeAdvancement() {
                return null;
            }

            public @Nullable ResourceLocation getAdvancementId() {
                return null;
            }
        };
    }

    public AutoInfuserMeltingRecipe(ResourceLocation id, Ingredient input, ResourceLocation moltenType, int moltenAmount, ResourceLocation secondaryType, int secondaryAmount) {
        this.id = id;
        this.input = input;
        this.moltenType = moltenType;
        this.moltenAmount = moltenAmount;
        this.secondaryType = secondaryType;
        this.secondaryAmount = secondaryAmount;
    }

    public String toString() {
        ResourceLocation var10000 = this.getId();
        return "MagicInfuserMeltingRecipe(id=" + var10000 + ", Input=" + this.getInput() + ", moltenType=" + this.getMoltenType() + ", moltenAmount=" + this.getMoltenAmount() + ", secondaryType=" + this.getSecondaryType() + ", secondaryAmount=" + this.getSecondaryAmount() +  ")";
    }

    public static class Serializer implements RecipeSerializer<AutoInfuserMeltingRecipe> {
        public Serializer() {
        }

        public AutoInfuserMeltingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));
            ResourceLocation moltenType = ResourceLocation.tryParse(GsonHelper.getAsString(pSerializedRecipe, "moltenType"));
            int moltenAmount = GsonHelper.getAsInt(pSerializedRecipe, "moltenAmount");
            ResourceLocation secondaryType = AutoMagicInfusionRecipe.EMPTY;
            int secondaryAmount = 0;
            if (pSerializedRecipe.has("secondaryType")) {
                secondaryType = ResourceLocation.tryParse(GsonHelper.getAsString(pSerializedRecipe, "secondaryType"));
                secondaryAmount = GsonHelper.getAsInt(pSerializedRecipe, "secondaryAmount");
            }

            return new AutoInfuserMeltingRecipe(pRecipeId, input, moltenType, moltenAmount, secondaryType, secondaryAmount);
        }

        public @Nullable AutoInfuserMeltingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new AutoInfuserMeltingRecipe(pRecipeId, Ingredient.fromNetwork(pBuffer), pBuffer.readResourceLocation(), pBuffer.readInt(), pBuffer.readResourceLocation(), pBuffer.readInt());
        }

        public void toNetwork(FriendlyByteBuf pBuffer, AutoInfuserMeltingRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeResourceLocation(pRecipe.moltenType);
            pBuffer.writeInt(pRecipe.moltenAmount);
            pBuffer.writeResourceLocation(pRecipe.secondaryType);
            pBuffer.writeInt(pRecipe.secondaryAmount);
        }
    }

    public static class Type implements RecipeType<AutoInfuserMeltingRecipe> {
        public Type() {
        }
    }

    public static class Builder {
        private Ingredient input;
        private final ResourceLocation moltenType;
        private final int moltenAmount;
        private ResourceLocation secondaryType;
        private int secondaryAmount;


        public Builder requires(Ingredient ingredient) {
            this.input = ingredient;
            return this;
        }

        public Builder secondary(ResourceLocation secondaryType, int secondaryAmount){
            this.secondaryType = secondaryType;
            this.secondaryAmount = secondaryAmount;
            return this;
        }

        public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
            consumer.accept((new AutoInfuserMeltingRecipe(id, this.input == null ? Ingredient.EMPTY : this.input, this.moltenType, this.moltenAmount, this.secondaryType, this.secondaryAmount)).finishRecipe());
        }

        public void build(Consumer<FinishedRecipe> consumer, String fileName) {
            this.build(consumer, new ResourceLocation(this.moltenType.getNamespace(), "auto_melting/" + fileName));
        }

        private Builder(ResourceLocation moltenType, int moltenAmount) {
            this.secondaryType = AutoMagicInfusionRecipe.EMPTY;
            this.secondaryAmount = 0;
            this.moltenType = moltenType;
            this.moltenAmount = moltenAmount;
        }

        public static Builder of(ResourceLocation moltenType, int moltenAmount) {
            return new Builder(moltenType, moltenAmount);
        }
    }
}
