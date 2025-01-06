package com.github.skillfi.reincarnation_plus.core.data.recipe.auto;

import com.github.manasmods.tensura.data.pack.GearEPCount;
import com.github.manasmods.tensura.data.pack.TensuraData;
import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.AutoInfuserBlockEntity;
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

public class AutoInfuserEvolvingRecipe extends AutoMagicInfuserRecipe implements Comparable<AutoInfuserEvolvingRecipe> {
    private static final Logger log = LogManager.getLogger(AutoInfuserEvolvingRecipe.class);
    public static final ResourceLocation EMPTY = new ResourceLocation("minecraft:air");
    @Getter private final ResourceLocation id;
    @Getter private final ResourceLocation primaryType;
    @Getter private final int primaryAmount;
    @Getter private final Ingredient input;

    public boolean matches(AutoInfuserBlockEntity pContainer, Level level) {
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
            return cap.getMoltenAmount() >= this.primaryAmount;
        }).orElse(false); // Якщо capability недоступний, повертаємо false
    }


    public ItemStack assemble(AutoInfuserBlockEntity pContainer) {
        this.evolve(pContainer, this.primaryType, this.primaryAmount);
        return ItemStack.EMPTY;
    }

    public void evolve(AutoInfuserBlockEntity pContainer, ResourceLocation type, int amount) {
        if (!type.equals(EMPTY)) {
            ReiData.getMagicInfuserMoltenMaterials()
                .parallelStream()
                .filter(material -> material.getMoltenType().equals(type))
                .findFirst()
                .ifPresentOrElse((material) -> {
                    ItemStack itemStack = pContainer.getItem(2);
                    pContainer.removeMoltenMaterialAmount(1);

                    if (itemStack.hasTag()){
                        CompoundTag tag = itemStack.getTag();
                        assert tag != null;
                        if (tag.getDouble("EP") <= amount)
                            tag.putDouble("EP", tag.getDouble("EP") + (double) 1);
                        else{
                            pContainer.setItem(3, getResultItem());
                            pContainer.removeItem(2, 1);
                        }

                    }

                }, () -> log.error("Could not assemble InfuserEvolvingRecipe: {}", this));
        }
    }

    private static void initiateItemEP(ItemStack stack) {
        for(GearEPCount gearEPCount : TensuraData.getGearEP()) {
            if (Objects.equals(ForgeRegistries.ITEMS.getKey(stack.getItem()), gearEPCount.getItem())) {
                CompoundTag tag = stack.getOrCreateTag();
                if (tag.getDouble("EP") <= (double)gearEPCount.getMinEP()) {
                    tag.putDouble("EP", (double)gearEPCount.getMinEP());
                }

                if (tag.getDouble("MaxEP") < (double)gearEPCount.getMaxEP()) {
                    tag.putDouble("MaxEP", (double)gearEPCount.getMaxEP());
                }
                break;
            }
        }
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
                if (!AutoInfuserEvolvingRecipe.this.getPrimaryType().equals(AutoInfuserEvolvingRecipe.EMPTY)) {
                    JsonObject input = new JsonObject();
                    input.addProperty("primaryType", AutoInfuserEvolvingRecipe.this.primaryType.toString());
                    input.addProperty("primaryAmount", AutoInfuserEvolvingRecipe.this.primaryAmount);
                    root.add("primary", input);
                }

                if (!AutoInfuserEvolvingRecipe.this.input.isEmpty()) {
                    root.add("input", AutoInfuserEvolvingRecipe.this.input.toJson());
                }
            }

            public ResourceLocation getId() {
                return AutoInfuserEvolvingRecipe.this.id;
            }

            public RecipeSerializer<?> getType() {
                return AutoInfuserEvolvingRecipe.this.getSerializer();
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

    public int compareTo(@NotNull AutoInfuserEvolvingRecipe o) {
        return Float.compare(this.primaryAmount, o.primaryAmount);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            AutoInfuserEvolvingRecipe recipe = (AutoInfuserEvolvingRecipe)o;
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

    public AutoInfuserEvolvingRecipe(ResourceLocation id, ResourceLocation primaryType, int primaryAmount, Ingredient ingredient) {
        this.id = id;
        this.primaryType = primaryType;
        this.primaryAmount = primaryAmount;
        this.input = ingredient;
    }

    public String toString() {
        ResourceLocation var10000 = this.getId();
        return "InfuserEvolvingRecipe(id=" + var10000 + ", PrimaryType=" + this.getPrimaryType() + ", PrimaryAmount=" + this.getPrimaryAmount() + ", Input=" + this.getInput() + ")";
    }

    public static class Serializer implements RecipeSerializer<AutoInfuserEvolvingRecipe> {
        public Serializer() {
        }

        public AutoInfuserEvolvingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ResourceLocation inputRight = AutoInfuserEvolvingRecipe.EMPTY;
            int inputRightAmount = 0;
            if (pSerializedRecipe.has("primary")) {
                JsonObject inputLeftRoot = pSerializedRecipe.getAsJsonObject("primary");
                inputRight = ResourceLocation.tryParse(inputLeftRoot.get("primaryType").getAsString());
                inputRightAmount = inputLeftRoot.get("primaryAmount").getAsInt();
            }

            Ingredient input = Ingredient.fromJson(pSerializedRecipe.getAsJsonObject("input"));
            return new AutoInfuserEvolvingRecipe(pRecipeId, inputRight, inputRightAmount, input);
        }

        public @Nullable AutoInfuserEvolvingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new AutoInfuserEvolvingRecipe(pRecipeId, pBuffer.readResourceLocation(), pBuffer.readInt(), Ingredient.fromNetwork(pBuffer));
        }

        public void toNetwork(FriendlyByteBuf pBuffer, AutoInfuserEvolvingRecipe pRecipe) {
            pBuffer.writeResourceLocation(pRecipe.primaryType);
            pBuffer.writeInt(pRecipe.primaryAmount);
            pRecipe.input.toNetwork(pBuffer);
        }
    }

    public static class Type implements RecipeType<AutoInfuserEvolvingRecipe> {
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
            consumer.accept((new AutoInfuserEvolvingRecipe(new ResourceLocation(ReiMod.MODID, "auto_evolving/" + id.getPath()), this.primaryType, this.primaryAmount, this.input == null ? Ingredient.EMPTY : this.input)).finishRecipe());
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
