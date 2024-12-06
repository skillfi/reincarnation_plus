package com.github.skillfi.reincarnation_plus.libs.data.recipe;

import com.github.skillfi.reincarnation_plus.core.block.entity.MagicInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.registry.recipe.ReiRecipeTypes;
import com.github.skillfi.reincarnation_plus.libs.data.pack.ReiData;
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

public class MagicInfusionRecipe extends MagicInfuserRecipe implements Comparable<MagicInfusionRecipe>{
    private static final Logger log = LogManager.getLogger(MagicInfusionRecipe.class);
    public static final ResourceLocation EMPTY = new ResourceLocation("minecraft:air");
    public static final ResourceLocation MAGICULES = new ResourceLocation("reincarnation_plus:magicules");
    public static final ResourceLocation INFUSION = new ResourceLocation("reincarnation_plus:infusion");
    @Getter
    private final ResourceLocation id;
    @Getter
    private final ResourceLocation leftInput;
    @Getter
    private final int leftInputAmount;
    @Getter
    private final Ingredient input;
    @Getter
    private final int cookingTime;
    @Getter
    private final ItemStack output;

    public boolean matches(MagicInfuserBlockEntity pContainer, Level level) {
        // Перевірка, чи вхідний слот має потрібний ItemStack
        ItemStack inputStack = pContainer.getItem(2).copy();

        // Перевірка для лівого входу
        if (!this.leftInput.equals(EMPTY)) {
            // Якщо матеріалу недостатньо, рецепт не підходить
            if (pContainer.getMagicMaterialAmount() < this.leftInputAmount) {
                return false;
            }

            // Якщо вхідний предмет не відповідає рецепту, рецепт не підходить
            if (!this.input.test(inputStack)) {
                return false;
            }

            // Перевірка вихідного слоту
            ItemStack outputStack = pContainer.getItem(3);

            // Якщо вихідний слот не порожній і не збігається з очікуваним результатом
            if (!outputStack.isEmpty() && !ItemStack.isSame(outputStack, this.output)) {
                return false;
            }

            // Перевірка кількості вихідного предмета (не перевищує максимум)
            if (!outputStack.isEmpty() && outputStack.getCount() + this.output.getCount() > outputStack.getMaxStackSize()) {
                return false;
            }

            // Якщо всі перевірки пройдені
            return true;
        }

        // Якщо лівий вхід порожній, рецепт завжди підходить
        return true;
    }


    public ItemStack assemble(MagicInfuserBlockEntity pContainer) {
        this.infuse(pContainer, this.leftInput, this.leftInputAmount);
        return getResultItem();
    }

    public void infuse(MagicInfuserBlockEntity pContainer, ResourceLocation type, int amount) {
        if (!type.equals(EMPTY)) {
            ReiData.getMagicInfuserMoltenMaterials()
                    .parallelStream()
                    .filter(material -> material.getMoltenType().equals(type))
                    .findFirst()
                    .ifPresentOrElse((material) -> {
                        pContainer.removeMoltenMaterialAmount(amount);
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

    public ItemStack getResultItem() {
        return this.output.copy();
    }

    public FinishedRecipe finishRecipe() {
        return new FinishedRecipe() {
            public void serializeRecipeData(JsonObject root) {
                if (!MagicInfusionRecipe.this.getLeftInput().equals(MagicInfusionRecipe.EMPTY)) {
                    JsonObject input = new JsonObject();
                    input.addProperty("moltenType", MagicInfusionRecipe.this.leftInput.toString());
                    input.addProperty("amount", MagicInfusionRecipe.this.leftInputAmount);
                    root.add("inputLeft", input);
                }

                if (!MagicInfusionRecipe.this.input.isEmpty()) {
                    root.add("input", MagicInfusionRecipe.this.input.toJson());
                }
                root.addProperty("cookingTime", MagicInfusionRecipe.this.cookingTime);
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
        return Float.compare(this.leftInputAmount, o.leftInputAmount);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            MagicInfusionRecipe recipe = (MagicInfusionRecipe)o;
            if (!this.id.equals(recipe.id)) {
                return false;
            } else if (this.leftInputAmount != recipe.leftInputAmount) {
                return false;
            } else {
                return this.leftInput.equals(recipe.leftInput) && this.input.equals(recipe.input) && this.output.equals(recipe.output, false);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.leftInput, this.leftInputAmount, this.input, this.output});
    }

    public MagicInfusionRecipe(ResourceLocation id, ResourceLocation leftInput, int leftInputAmount, Ingredient ingredient, int cookingTime, ItemStack output) {
        this.id = id;
        this.leftInput = leftInput;
        this.leftInputAmount = leftInputAmount;
        this.input = ingredient;
        this.cookingTime = cookingTime;
        this.output = output;
    }

    public String toString() {
        ResourceLocation var10000 = this.getId();
        return "MagicInfusionRecipe(id=" + var10000 + ", leftInput=" + this.getLeftInput() + ", leftInputAmount=" + this.getLeftInputAmount() + ", rightInput=" + this.getInput() + ", cookingTime="+this.getCookingTime()+ ", output=" + this.getOutput() + ")";
    }

    public static class Serializer implements RecipeSerializer<MagicInfusionRecipe> {
        public Serializer() {
        }

        public MagicInfusionRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ResourceLocation inputLeft = MagicInfusionRecipe.EMPTY;
            int inputLeftAmount = 0;
            if (pSerializedRecipe.has("inputLeft")) {
                JsonObject inputLeftRoot = pSerializedRecipe.getAsJsonObject("inputLeft");
                inputLeft = ResourceLocation.tryParse(inputLeftRoot.get("moltenType").getAsString());
                inputLeftAmount = inputLeftRoot.get("amount").getAsInt();
            }

            Ingredient input = Ingredient.fromJson(pSerializedRecipe.getAsJsonObject("input"));

            int infusionTime = pSerializedRecipe.get("cookingTime").getAsInt();
            ItemStack output = (ItemStack)((Pair)ItemStack.CODEC.decode(JsonOps.INSTANCE, pSerializedRecipe.get("result")).result().orElseThrow(() -> new IllegalArgumentException("Could not load result ItemStack from: " + pRecipeId))).getFirst();
            return new MagicInfusionRecipe(pRecipeId, inputLeft, inputLeftAmount, input, infusionTime, output);
        }

        public @Nullable MagicInfusionRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new MagicInfusionRecipe(pRecipeId, pBuffer.readResourceLocation(), pBuffer.readInt(), Ingredient.fromNetwork(pBuffer), pBuffer.readInt(), pBuffer.readItem());
        }

        public void toNetwork(FriendlyByteBuf pBuffer, MagicInfusionRecipe pRecipe) {
            pBuffer.writeResourceLocation(pRecipe.leftInput);
            pBuffer.writeFloat(pRecipe.leftInputAmount);
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.cookingTime);
            pBuffer.writeItemStack(pRecipe.output, false);
        }
    }

    public static class Type implements RecipeType<MagicInfusionRecipe> {
        public Type() {
        }
    }

    public static class Builder {
        private final ItemStack output;
        private ResourceLocation leftInput;
        private int leftAmount;
        private Ingredient input;
        private int infusionTime;

        public Builder magicules(ResourceLocation moltenType, int amount) {
            this.leftInput = moltenType;
            this.leftAmount = amount;
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
            consumer.accept((new MagicInfusionRecipe(new ResourceLocation(id.getNamespace(), "magic_infusion/" + id.getPath()), this.leftInput, this.leftAmount, this.input == null ? Ingredient.EMPTY : this.input, this.infusionTime, this.output)).finishRecipe());
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
            this.leftInput = MagicInfusionRecipe.EMPTY;
            this.leftAmount = 0;
            this.input = Ingredient.EMPTY;
            this.infusionTime = 0;
            this.output = output;
        }

        public static Builder of(ItemStack output) {
            return new Builder(output);
        }
    }
}
