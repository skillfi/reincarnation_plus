package com.github.skillfi.reincarnation_plus.core.data.recipe.auto;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.AutoInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.data.pack.ReiData;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import lombok.Getter;
import net.minecraft.data.recipes.FinishedRecipe;
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
import java.util.function.Supplier;

public class AutoMagicInfusionRecipe extends AutoMagicInfuserRecipe implements Comparable<AutoMagicInfusionRecipe> {
    private static final Logger log = LogManager.getLogger(AutoMagicInfusionRecipe.class);
    public static final ResourceLocation EMPTY = new ResourceLocation("minecraft:air");
    public static final ResourceLocation INFUSION = new ResourceLocation(ReiMod.MODID, "infusion");
    @Getter private final ResourceLocation id;
    @Getter private final ResourceLocation primaryType;
    @Getter private final int primaryAmount;
    @Getter private final ResourceLocation secondaryType;
    @Getter private final int secondaryAmount;
    @Getter private final Ingredient input;
    @Getter private final int infusionTime;
    @Getter private final ItemStack output;

    public boolean matches(AutoInfuserBlockEntity pContainer, Level level) {
        ItemStack inputStack = pContainer.getItem(2).copy();
        ItemStack outputStack = pContainer.getItem(3);

        // Перевірка вихідного слоту
        if (!outputStack.isEmpty() && outputStack.getCount() + this.output.getCount() > outputStack.getMaxStackSize()) {
            return false;
        }

        // Перевірка відповідності вхідного предмета
        if (!this.input.test(inputStack)) {
            return false;
        }

        // Перевірка лівого слоту (secondaryType)
        boolean isSecondaryValid = pContainer.getCapability(ReiMod.MAGICULA_INFUSER_CAPABILITY).map(cap -> {
            Optional<ResourceLocation> leftBarId = cap.getLeftBarId();
            if (this.secondaryType.equals(EMPTY)) return true;

            if (leftBarId.isEmpty() || leftBarId.get().equals(EMPTY)) return false;
            if (!leftBarId.get().equals(this.secondaryType)) return false;
            return cap.getMoltenAmount() >= this.secondaryAmount;
        }).orElse(false);

        if (!isSecondaryValid) {
            return false;
        }

        // Перевірка правого слоту (primaryType)
        boolean isPrimaryValid = pContainer.getCapability(ReiMod.MAGICULA_INFUSER_CAPABILITY).map(cap -> {
            Optional<ResourceLocation> rightBarId = cap.getRightBarId();
            if (this.primaryType.equals(EMPTY)) return true;

            if (rightBarId.isEmpty() || rightBarId.get().equals(EMPTY)) return false;
            if (!rightBarId.get().equals(this.primaryType)) return false;
            return cap.getMagicMaterialAmount() >= this.primaryAmount;
        }).orElse(false);

        return isPrimaryValid;
    }



    public ItemStack assemble(AutoInfuserBlockEntity pContainer) {
        if (pContainer.doubleChanse())
            this.doubleInfuse(pContainer, this.primaryType, this.primaryAmount);
        this.infuse(pContainer, this.primaryType, this.primaryAmount);
        return ItemStack.EMPTY;
    }

    public void infuse(AutoInfuserBlockEntity pContainer, ResourceLocation type, int amount) {
        if (!type.equals(EMPTY)) {
            ReiData.getMagicInfuserMoltenMaterials()
                    .parallelStream()
                    .filter(material -> material.getMoltenType().equals(type))
                    .findFirst()
                    .ifPresentOrElse((material) -> {
                        pContainer.removeMagicMaterialAmount(amount);
                        pContainer.removeItem(2, 1);
                        ItemStack output  = pContainer.getItem(3).copy();
                        if (!output.isEmpty()){
                            output.grow(1);
                            pContainer.setItem(3, output);
                        } else {
                            pContainer.setItem(3, getResultItem());
                        }

                    }, () -> log.error("Could not assemble InfusionRecipe: {}", this));
        }
    }

    public void doubleInfuse(AutoInfuserBlockEntity pContainer, ResourceLocation type, int amount) {
        if (!type.equals(EMPTY)) {
            ReiData.getMagicInfuserMoltenMaterials()
                    .parallelStream()
                    .filter(material -> material.getMoltenType().equals(type))
                    .findFirst()
                    .ifPresentOrElse((material) -> {
                        pContainer.removeMagicMaterialAmount(amount);
                        pContainer.removeItem(2, 1);

                        ItemStack output = pContainer.getItem(3).copy();

                        // Якщо слот результату не пустий, збільшуємо його кількість на 2
                        if (!output.isEmpty()) {
                            output.grow(2); // Збільшення на 2 предмети
                            pContainer.setItem(3, output);
                        } else {
                            // Якщо слот результату пустий, створюємо новий ItemStack з 2 одиницями
                            ItemStack outputResult = getResultItem().copy();
                            outputResult.setCount(2); // Задаємо кількість 2
                            pContainer.setItem(3, outputResult);
                        }
                    }, () -> log.error("Could not assemble InfusionRecipe: {}", this));
        }
    }


    public ItemStack getResultItem() {
        return this.output.copy();
    }

    public FinishedRecipe finishRecipe() {
        return new FinishedRecipe() {
            public void serializeRecipeData(JsonObject root) {
                if (!AutoMagicInfusionRecipe.this.getPrimaryType().equals(AutoMagicInfusionRecipe.EMPTY)) {
                    JsonObject input = new JsonObject();
                    input.addProperty("primaryType", AutoMagicInfusionRecipe.this.primaryType.toString());
                    input.addProperty("primaryAmount", AutoMagicInfusionRecipe.this.primaryAmount);
                    root.add("primary", input);
                }
                if (!AutoMagicInfusionRecipe.this.getSecondaryType().equals(AutoMagicInfusionRecipe.EMPTY)) {
                    JsonObject input = new JsonObject();
                    input.addProperty("secondaryType", AutoMagicInfusionRecipe.this.secondaryType.toString());
                    input.addProperty("secondaryAmount", AutoMagicInfusionRecipe.this.secondaryAmount);
                    root.add("secondary", input);
                }

                if (!AutoMagicInfusionRecipe.this.input.isEmpty()) {
                    root.add("input", AutoMagicInfusionRecipe.this.input.toJson());
                }
                root.addProperty("infusionTime", AutoMagicInfusionRecipe.this.infusionTime);
                root.add("result", (JsonElement)ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, AutoMagicInfusionRecipe.this.output).result().orElseThrow(() -> new IllegalArgumentException("Could not serialize ItemStack: " + AutoMagicInfusionRecipe.this.output)));
            }

            public ResourceLocation getId() {
                return AutoMagicInfusionRecipe.this.id;
            }

            public RecipeSerializer<?> getType() {
                return AutoMagicInfusionRecipe.this.getSerializer();
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
        return ReiRecipeTypes.Serializer.MAGIC_INFUSION.get();
    }

    public RecipeType<?> getType() {
        return ReiRecipeTypes.MAGIC_INFUSION.get();
    }

    public int compareTo(@NotNull AutoMagicInfusionRecipe o) {
        return Float.compare(this.primaryAmount, o.primaryAmount);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            AutoMagicInfusionRecipe recipe = (AutoMagicInfusionRecipe)o;
            if (!this.id.equals(recipe.id)) {
                return false;
            } else if (this.primaryAmount != recipe.primaryAmount) {
                return false;
            } else {
                return this.primaryType.equals(recipe.primaryType) && this.input.equals(recipe.input) && this.output.equals(recipe.output, false);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.primaryType, this.primaryAmount, this.input, this.output, secondaryAmount, secondaryType, infusionTime});
    }

    public AutoMagicInfusionRecipe(ResourceLocation id, ResourceLocation primaryType, int primaryAmount, Ingredient ingredient, int infusionTime, ItemStack output, ResourceLocation secondaryType, int secondaryAmount) {
        this.id = id;
        this.primaryType = primaryType;
        this.primaryAmount = primaryAmount;
        this.secondaryType = secondaryType;
        this.secondaryAmount =secondaryAmount;
        this.input = ingredient;
        this.infusionTime = infusionTime;
        this.output = output;
    }

    public String toString() {
        ResourceLocation var10000 = this.getId();
        return "MagicInfusionRecipe(id=" + var10000 + ", PrimaryType=" + this.getPrimaryType() + ", PrimaryAmount=" + this.getPrimaryAmount() + ", Input=" + this.getInput() + ", InfusionTime="+this.getInfusionTime()+ ", Output=" + this.getOutput() + ", SecondaryType=" + this.getSecondaryType() + ", SecondaryAmount=" + this.getSecondaryAmount() + ")";
    }

    public static class Serializer implements RecipeSerializer<AutoMagicInfusionRecipe> {
        public Serializer() {
        }

        public AutoMagicInfusionRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ResourceLocation inputRight = AutoMagicInfusionRecipe.EMPTY;
            int inputRightAmount = 0;
            if (pSerializedRecipe.has("primary")) {
                JsonObject inputLeftRoot = pSerializedRecipe.getAsJsonObject("primary");
                inputRight = ResourceLocation.tryParse(inputLeftRoot.get("primaryType").getAsString());
                inputRightAmount = inputLeftRoot.get("primaryAmount").getAsInt();
            }
            ResourceLocation inputLeft = AutoMagicInfusionRecipe.EMPTY;
            int inputLeftAmount = 0;
            if (pSerializedRecipe.has("secondary")) {
                JsonObject inputLeftRoot = pSerializedRecipe.getAsJsonObject("secondary");
                inputLeft = ResourceLocation.tryParse(inputLeftRoot.get("secondaryType").getAsString());
                inputLeftAmount = inputLeftRoot.get("secondaryAmount").getAsInt();
            }

            Ingredient input = Ingredient.fromJson(pSerializedRecipe.getAsJsonObject("input"));

            int infusionTime = pSerializedRecipe.get("infusionTime").getAsInt();
            ItemStack output = (ItemStack)((Pair)ItemStack.CODEC.decode(JsonOps.INSTANCE, pSerializedRecipe.get("result")).result().orElseThrow(() -> new IllegalArgumentException("Could not load result ItemStack from: " + pRecipeId))).getFirst();
            return new AutoMagicInfusionRecipe(pRecipeId, inputRight, inputRightAmount, input, infusionTime, output, inputLeft, inputLeftAmount);
        }

        public @Nullable AutoMagicInfusionRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new AutoMagicInfusionRecipe(pRecipeId, pBuffer.readResourceLocation(), pBuffer.readInt(), Ingredient.fromNetwork(pBuffer), pBuffer.readInt(), pBuffer.readItem(), pBuffer.readResourceLocation(), pBuffer.readInt());
        }

        public void toNetwork(FriendlyByteBuf pBuffer, AutoMagicInfusionRecipe pRecipe) {
            pBuffer.writeResourceLocation(pRecipe.primaryType);
            pBuffer.writeFloat(pRecipe.primaryAmount);
            pBuffer.writeResourceLocation(pRecipe.secondaryType);
            pBuffer.writeFloat(pRecipe.secondaryAmount);
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.infusionTime);
            pBuffer.writeItemStack(pRecipe.output, false);
        }
    }

    public static class Type implements RecipeType<AutoMagicInfusionRecipe> {
        public Type() {
        }
    }

    public static class Builder {
        private final ItemStack output;
        private ResourceLocation primaryType;
        private int primaryAmount;
        private ResourceLocation secondaryType;
        private int secondaryAmount;
        private Ingredient input;
        private int infusionTime;

        public Builder magicules(ResourceLocation moltenType, int amount) {
            this.primaryType = moltenType;
            this.primaryAmount = amount;
            return this;
        }

        public Builder existence_points(ResourceLocation secondaryType, int secondaryAmount){
            this.secondaryType = secondaryType;
            this.secondaryAmount = secondaryAmount;
            return this;
        }

        public Builder requires(Ingredient ingredient) {
            this.input = ingredient;
            return this;
        }

        public Builder time(int InfusionTime){
            this.infusionTime = InfusionTime;
            return this;
        }

        public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
            consumer.accept((new AutoMagicInfusionRecipe(new ResourceLocation(id.getNamespace(), "auto_infusion/" + id.getPath()), this.primaryType, this.primaryAmount, this.input == null ? Ingredient.EMPTY : this.input, this.infusionTime, this.output, this.secondaryType, this.secondaryAmount)).finishRecipe());
        }

        public void build(Consumer<FinishedRecipe> consumer, String fileName) {
            this.build(consumer, new ResourceLocation("tags/" + fileName));
        }

        public void build(Consumer<FinishedRecipe> consumer) {
            this.build(consumer, (ResourceLocation)Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.output.getItem())));
        }

        public static Builder of(Item item) {
            return of(item.getDefaultInstance());
        }

        public static Builder of(Supplier<? extends Item> item) {
            return of((Item)item.get());
        }

        private Builder(ItemStack output) {
            this.primaryType = AutoMagicInfusionRecipe.EMPTY;
            this.primaryAmount = 0;
            this.secondaryType = AutoMagicInfusionRecipe.EMPTY;
            this.secondaryAmount = 0;
            this.input = Ingredient.EMPTY;
            this.infusionTime = 0;
            this.output = output;
        }

        public static Builder of(ItemStack output) {
            return new Builder(output);
        }
    }
}
