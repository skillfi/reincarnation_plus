package com.github.skillfi.reincarnation_plus.core.data.recipe.infuser;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
import com.github.skillfi.reincarnation_plus.core.data.pack.ReiData;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MagicInfusionRecipe extends MagicInfuserRecipe implements Comparable<MagicInfusionRecipe> {
    private static final Logger log = LogManager.getLogger(MagicInfusionRecipe.class);
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

    public boolean matches(MagiculaInfuserBlockEntity pContainer, Level level) {
        // Перевірка, чи вхідний слот має потрібний ItemStack
        ItemStack inputStack = pContainer.getItem(2).copy();


        // Перевірка вихідного слоту
        ItemStack outputStack = pContainer.getItem(3);

        // Перевірка кількості вихідного предмета (не перевищує максимум)
        if (!outputStack.isEmpty() && outputStack.getCount() + this.output.getCount() > outputStack.getMaxStackSize()) {
            return false;
        }

        if (!this.input.test(inputStack)) {
            return false;
        }

        if (!this.secondaryType.equals(EMPTY)) {
            if (pContainer.getLeftBarId().isEmpty() || ((ResourceLocation)pContainer.getLeftBarId().get()).equals(EMPTY)) {
                return false;
            }

            if (!((ResourceLocation)pContainer.getLeftBarId().get()).equals(this.secondaryType)) {
                return false;
            }

            if (pContainer.getMoltenAmount() < this.secondaryAmount) {
                return false;
            }
        }

        // Перевірка для лівого входу
        if (!this.primaryType.equals(EMPTY)) {
            if (!pContainer.getRightBarId().isEmpty() && !((ResourceLocation)pContainer.getRightBarId().get()).equals(EMPTY)) {
                if (!((ResourceLocation)pContainer.getRightBarId().get()).equals(this.primaryType)) {
                    return false;
                } else {
                    return pContainer.getMagicMaterialAmount() >= this.primaryAmount;
                }
            } else {
                return false;
            }
        }

        // Якщо лівий вхід порожній, рецепт завжди підходить
        return true;
    }


    public ItemStack assemble(MagiculaInfuserBlockEntity pContainer) {
        if (pContainer.doubleChanse())
            this.doubleInfuse(pContainer, this.primaryType, this.primaryAmount);
        this.infuse(pContainer, this.primaryType, this.primaryAmount);
        return ItemStack.EMPTY;
    }

    public void infuse(MagiculaInfuserBlockEntity pContainer, ResourceLocation type, int amount) {
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

    public void doubleInfuse(MagiculaInfuserBlockEntity pContainer, ResourceLocation type, int amount) {
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
                if (!MagicInfusionRecipe.this.getPrimaryType().equals(MagicInfusionRecipe.EMPTY)) {
                    JsonObject input = new JsonObject();
                    input.addProperty("primaryType", MagicInfusionRecipe.this.primaryType.toString());
                    input.addProperty("primaryAmount", MagicInfusionRecipe.this.primaryAmount);
                    root.add("primary", input);
                }
                if (!MagicInfusionRecipe.this.getSecondaryType().equals(MagicInfusionRecipe.EMPTY)) {
                    JsonObject input = new JsonObject();
                    input.addProperty("secondaryType", MagicInfusionRecipe.this.secondaryType.toString());
                    input.addProperty("secondaryAmount", MagicInfusionRecipe.this.secondaryAmount);
                    root.add("secondary", input);
                }

                if (!MagicInfusionRecipe.this.input.isEmpty()) {
                    root.add("input", MagicInfusionRecipe.this.input.toJson());
                }
                root.addProperty("infusionTime", MagicInfusionRecipe.this.infusionTime);
                root.add("result", (JsonElement)ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, MagicInfusionRecipe.this.output).result().orElseThrow(() -> new IllegalArgumentException("Could not serialize ItemStack: " + MagicInfusionRecipe.this.output)));
            }

            public ResourceLocation getId() {
                return MagicInfusionRecipe.this.id;
            }

            public RecipeSerializer<?> getType() {
                return MagicInfusionRecipe.this.getSerializer();
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

    public int compareTo(@NotNull MagicInfusionRecipe o) {
        return Float.compare(this.primaryAmount, o.primaryAmount);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            MagicInfusionRecipe recipe = (MagicInfusionRecipe)o;
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

    public MagicInfusionRecipe(ResourceLocation id, ResourceLocation primaryType, int primaryAmount, Ingredient ingredient, int infusionTime, ItemStack output, ResourceLocation secondaryType, int secondaryAmount) {
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

    public static class Serializer implements RecipeSerializer<MagicInfusionRecipe> {
        public Serializer() {
        }

        public MagicInfusionRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ResourceLocation inputRight = MagicInfusionRecipe.EMPTY;
            int inputRightAmount = 0;
            if (pSerializedRecipe.has("primary")) {
                JsonObject inputLeftRoot = pSerializedRecipe.getAsJsonObject("primary");
                inputRight = ResourceLocation.tryParse(inputLeftRoot.get("primaryType").getAsString());
                inputRightAmount = inputLeftRoot.get("primaryAmount").getAsInt();
            }
            ResourceLocation inputLeft = MagicInfusionRecipe.EMPTY;
            int inputLeftAmount = 0;
            if (pSerializedRecipe.has("secondary")) {
                JsonObject inputLeftRoot = pSerializedRecipe.getAsJsonObject("secondary");
                inputLeft = ResourceLocation.tryParse(inputLeftRoot.get("secondaryType").getAsString());
                inputLeftAmount = inputLeftRoot.get("secondaryAmount").getAsInt();
            }

            Ingredient input = Ingredient.fromJson(pSerializedRecipe.getAsJsonObject("input"));

            int infusionTime = pSerializedRecipe.get("infusionTime").getAsInt();
            ItemStack output = (ItemStack)((Pair)ItemStack.CODEC.decode(JsonOps.INSTANCE, pSerializedRecipe.get("result")).result().orElseThrow(() -> new IllegalArgumentException("Could not load result ItemStack from: " + pRecipeId))).getFirst();
            return new MagicInfusionRecipe(pRecipeId, inputRight, inputRightAmount, input, infusionTime, output, inputLeft, inputLeftAmount);
        }

        public @Nullable MagicInfusionRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new MagicInfusionRecipe(pRecipeId, pBuffer.readResourceLocation(), pBuffer.readInt(), Ingredient.fromNetwork(pBuffer), pBuffer.readInt(), pBuffer.readItem(), pBuffer.readResourceLocation(), pBuffer.readInt());
        }

        public void toNetwork(FriendlyByteBuf pBuffer, MagicInfusionRecipe pRecipe) {
            pBuffer.writeResourceLocation(pRecipe.primaryType);
            pBuffer.writeFloat(pRecipe.primaryAmount);
            pBuffer.writeResourceLocation(pRecipe.secondaryType);
            pBuffer.writeFloat(pRecipe.secondaryAmount);
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.infusionTime);
            pBuffer.writeItemStack(pRecipe.output, false);
        }
    }

    public static class Type implements RecipeType<MagicInfusionRecipe> {
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
            consumer.accept((new MagicInfusionRecipe(new ResourceLocation(id.getNamespace(), "magic_infusion/" + id.getPath()), this.primaryType, this.primaryAmount, this.input == null ? Ingredient.EMPTY : this.input, this.infusionTime, this.output, this.secondaryType, this.secondaryAmount)).finishRecipe());
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
            this.primaryType = MagicInfusionRecipe.EMPTY;
            this.primaryAmount = 0;
            this.secondaryType = MagicInfusionRecipe.EMPTY;
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
