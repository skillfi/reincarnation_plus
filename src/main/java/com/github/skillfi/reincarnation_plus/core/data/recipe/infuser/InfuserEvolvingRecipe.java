package com.github.skillfi.reincarnation_plus.core.data.recipe.infuser;

import com.github.manasmods.tensura.data.pack.GearEPCount;
import com.github.manasmods.tensura.data.pack.TensuraData;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.data.pack.ReiData;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class InfuserEvolvingRecipe extends MagicInfuserRecipe implements Comparable<InfuserEvolvingRecipe> {
    private static final Logger log = LogManager.getLogger(InfuserEvolvingRecipe.class);
    public static final ResourceLocation EMPTY = new ResourceLocation("minecraft:air");
    @Getter private final ResourceLocation id;
    @Getter private final ResourceLocation primaryType;
    @Getter private final int primaryAmount;
    @Getter private final Ingredient input;

    public boolean matches(MagiculaInfuserBlockEntity pContainer, Level level) {
        ItemStack inputStack = pContainer.getItem(2).copy();

        // Перевірка вхідного предмета
        if (!this.input.test(inputStack)) {
            return false;
        }

        // Перевірка лівого слоту (primaryType)
        return pContainer.getCapability(ReiMod.MAGICULA_INFUSER_CAPABILITY).map(cap -> {
            Optional<ResourceLocation> leftBarId = cap.getLeftBarId();

            if (this.primaryType.equals(EMPTY)) {
                return true; // Якщо primaryType порожній, перевірка успішна
            }

            if (leftBarId.isEmpty() || leftBarId.get().equals(EMPTY)) {
                return false; // Якщо leftBarId порожній або EMPTY, рецепт не підходить
            }

            if (!leftBarId.get().equals(this.primaryType)) {
                return false; // Якщо тип у leftBarId не відповідає primaryType, рецепт не підходить
            }

            // Перевірка, чи достатньо moltenAmount
            return true;
        }).orElse(false); // Якщо capability недоступний, повертаємо false
    }


    public ItemStack assemble(MagiculaInfuserBlockEntity pContainer) {
        this.evolve(pContainer, this.primaryType, this.primaryAmount);
        return ItemStack.EMPTY;
    }

    public void evolve(MagiculaInfuserBlockEntity pContainer, ResourceLocation type, int amount) {
        if (!type.equals(EMPTY)) {
            ReiData.getMagicInfuserMoltenMaterials()
                .parallelStream()
                .filter(material -> material.getMoltenType().equals(type))
                .findFirst()
                .ifPresentOrElse((material) -> {
                    ItemStack itemStack = pContainer.getItem(2);
                    pContainer.removeMoltenMaterialAmount(1);

                    CompoundTag tag = itemStack.getOrCreateTag();
                    if (tag.getDouble("EP") <= (double) amount)
                        tag.putDouble("EP", tag.getDouble("EP") + (double) 1);
                    else{
                        ItemStack output = itemStack.copy();
                        output = initiateItemEP(output);
                        pContainer.setItem(3, output);
                        pContainer.removeItem(2, 1);
                    }

                }, () -> log.error("Could not assemble InfuserEvolvingRecipe: {}", this));
        }
    }

    private static ItemStack initiateItemEP(ItemStack stack) {
        for(GearEPCount gearEPCount : TensuraData.getGearEP()) {
            if (Objects.equals(ForgeRegistries.ITEMS.getKey(stack.getItem()), gearEPCount.getItem())) {
                CompoundTag tag = stack.getOrCreateTag();
                if (tag.getDouble("EP") <= (double)gearEPCount.getMinEP()) {
                    tag.putDouble("EP", (double)gearEPCount.getMinEP());
                }

                if (tag.getDouble("MaxEP") < (double)gearEPCount.getMaxEP()) {
                    tag.putDouble("MaxEP", (double)gearEPCount.getMaxEP());
                }
                ItemStack output = ((Item)Objects.requireNonNull((Item)ForgeRegistries.ITEMS.getValue(gearEPCount.getEvolvingItem()))).getDefaultInstance();
                CompoundTag outputTag = output.getOrCreateTag();
                if (outputTag.getDouble("EP") <= (double)gearEPCount.getMinEP()) {
                    outputTag.putDouble("EP", (double)gearEPCount.getMinEP());
                }

                if (outputTag.getDouble("MaxEP") < (double)gearEPCount.getMaxEP()) {
                    outputTag.putDouble("MaxEP", (double)gearEPCount.getMaxEP());
                }
                return output;
            }
        }
        return ItemStack.EMPTY;
    }


    public ItemStack getResultItem() {
        ItemStack output = ItemStack.EMPTY;
        for(GearEPCount gearEPCount : TensuraData.getGearEP()) {
            if (Objects.equals(ForgeRegistries.ITEMS.getKey(input.getItems()[0].getItem()), gearEPCount.getItem())) {
                output = ((Item)Objects.requireNonNull((Item)ForgeRegistries.ITEMS.getValue(gearEPCount.getEvolvingItem()))).getDefaultInstance();
                initiateItemEP(output);
                return output;
            }
        }
        return output;
    }

    public FinishedRecipe finishRecipe() {
        return new FinishedRecipe() {
            public void serializeRecipeData(JsonObject root) {
                if (!InfuserEvolvingRecipe.this.getPrimaryType().equals(InfuserEvolvingRecipe.EMPTY)) {
                    JsonObject input = new JsonObject();
                    input.addProperty("primaryType", InfuserEvolvingRecipe.this.primaryType.toString());
                    input.addProperty("primaryAmount", InfuserEvolvingRecipe.this.primaryAmount);
                    root.add("primary", input);
                }

                if (!InfuserEvolvingRecipe.this.input.isEmpty()) {
                    root.add("input", InfuserEvolvingRecipe.this.input.toJson());
                }
            }

            public ResourceLocation getId() {
                return InfuserEvolvingRecipe.this.id;
            }

            public RecipeSerializer<?> getType() {
                return InfuserEvolvingRecipe.this.getSerializer();
            }

            public @Nullable JsonObject serializeAdvancement() {
                return null;
            }

            public @Nullable ResourceLocation getAdvancementId() {
                return null;
            }
        };
    }

    public RecipeSerializer<?> getSerializer() {
        return ReiRecipeTypes.Serializer.INFUSER_EVOLVING.get();
    }

    public RecipeType<?> getType() {
        return ReiRecipeTypes.INFUSER_EVOLVING.get();
    }

    public int compareTo(@NotNull InfuserEvolvingRecipe o) {
        return Float.compare(this.primaryAmount, o.primaryAmount);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            InfuserEvolvingRecipe recipe = (InfuserEvolvingRecipe)o;
            if (!this.id.equals(recipe.id)) {
                return false;
            } else if (this.primaryAmount != recipe.primaryAmount) {
                return false;
            } else {
                return this.primaryType.equals(recipe.primaryType) && this.input.equals(recipe.input);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.primaryType, this.primaryAmount, this.input});
    }

    public InfuserEvolvingRecipe(ResourceLocation id, ResourceLocation primaryType, int primaryAmount, Ingredient ingredient) {
        this.id = id;
        this.primaryType = primaryType;
        this.primaryAmount = primaryAmount;
        this.input = ingredient;
    }

    public String toString() {
        ResourceLocation var10000 = this.getId();
        return "InfuserEvolvingRecipe(id=" + var10000 + ", PrimaryType=" + this.getPrimaryType() + ", PrimaryAmount=" + this.getPrimaryAmount() + ", Input=" + this.getInput() + ")";
    }

    public static class Serializer implements RecipeSerializer<InfuserEvolvingRecipe> {
        public Serializer() {
        }

        public InfuserEvolvingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ResourceLocation inputRight = InfuserEvolvingRecipe.EMPTY;
            int inputRightAmount = 0;
            if (pSerializedRecipe.has("primary")) {
                JsonObject inputLeftRoot = pSerializedRecipe.getAsJsonObject("primary");
                inputRight = ResourceLocation.tryParse(inputLeftRoot.get("primaryType").getAsString());
                inputRightAmount = inputLeftRoot.get("primaryAmount").getAsInt();
            }

            Ingredient input = Ingredient.fromJson(pSerializedRecipe.getAsJsonObject("input"));
            return new InfuserEvolvingRecipe(pRecipeId, inputRight, inputRightAmount, input);
        }

        public @Nullable InfuserEvolvingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new InfuserEvolvingRecipe(pRecipeId, pBuffer.readResourceLocation(), pBuffer.readInt(), Ingredient.fromNetwork(pBuffer));
        }

        public void toNetwork(FriendlyByteBuf pBuffer, InfuserEvolvingRecipe pRecipe) {
            pBuffer.writeResourceLocation(pRecipe.primaryType);
            pBuffer.writeInt(pRecipe.primaryAmount);
            pRecipe.input.toNetwork(pBuffer);
        }
    }

    public static class Type implements RecipeType<InfuserEvolvingRecipe> {
        public Type() {
        }
    }

    public static class Builder {
        private ResourceLocation primaryType;
        private int primaryAmount;
        private Ingredient input;

        public Builder magicules(ResourceLocation moltenType, int amount) {
            this.primaryType = moltenType;
            this.primaryAmount = amount;
            return this;
        }

        public Builder requires(Ingredient ingredient) {
            this.input = ingredient;
            return this;
        }

        public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
            consumer.accept((new InfuserEvolvingRecipe(new ResourceLocation(ReiMod.MODID, "infuser_evolving/" + id.getPath()), this.primaryType, this.primaryAmount, this.input == null ? Ingredient.EMPTY : this.input)).finishRecipe());
        }

        public void build(Consumer<FinishedRecipe> consumer, String fileName) {
            this.build(consumer, new ResourceLocation("tags/" + fileName));
        }

        private Builder(ResourceLocation moltenType, int moltenAmount) {
            this.primaryType = moltenType;
            this.primaryAmount = moltenAmount;
            this.input = Ingredient.EMPTY;
        }

        public static Builder of(ResourceLocation moltenType, int moltenAmount) {
            return new Builder(moltenType, moltenAmount);
        }
    }
}
