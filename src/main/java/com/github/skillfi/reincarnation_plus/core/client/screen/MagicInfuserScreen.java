package com.github.skillfi.reincarnation_plus.core.client.screen;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import com.github.skillfi.reincarnation_plus.core.block.entity.AutomaticMagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.block.entity.MagiculaInfuserBlockEntity;
import com.github.skillfi.reincarnation_plus.core.menu.MagicInfuserMenu;
import com.github.skillfi.reincarnation_plus.libs.data.pack.MagicInfuserMoltenMaterial;
import com.github.skillfi.reincarnation_plus.libs.data.pack.ReiData;
import com.github.skillfi.reincarnation_plus.libs.data.recipe.MagicInfusionRecipe;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.awt.*;
import java.util.Optional;

import static com.github.skillfi.reincarnation_plus.libs.data.gen.ReiMoltenMaterialProvider.INFUSION;
import static com.github.skillfi.reincarnation_plus.libs.data.gen.ReiMoltenMaterialProvider.MOLTEN_MAGICULES;

public class MagicInfuserScreen extends AbstractContainerScreen<MagicInfuserMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ReiMod.MODID, "textures/gui/magic_infuser/magic_infuser_gui.png");
    private static final ResourceLocation FLUID = new ResourceLocation(ReiMod.MODID, "textures/gui/magic_infuser/molten.png");
    private Optional<MagicInfuserMoltenMaterial> leftBarMaterial;
    private Optional<MagicInfuserMoltenMaterial> rightBarMaterial;


    /**
     * Конструктор для ініціалізації екрану магічного інфузора.
     *
     * @param menu меню, прив'язане до цього екрану
     * @param inventory інвентар гравця
     * @param component назва або компонент, що відображається на екрані
     */
    public MagicInfuserScreen(MagicInfuserMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);

        // Ініціалізація матеріалів
        this.leftBarMaterial = resolveBarMaterial(menu.blockEntity, menu.automaticblockEntity, true);
        this.rightBarMaterial = resolveBarMaterial(menu.blockEntity, menu.automaticblockEntity, false);

        this.imageWidth = 256;
        this.imageHeight = 145;
    }

    /**
     * Метод для отримання матеріалу для лівої або правої панелі.
     *
     * @param blockEntity основний блок
     * @param automaticBlockEntity автоматичний блок
     * @param isLeft вказує, чи лівий це бар
     * @return опціональний матеріал
     */
    private Optional<MagicInfuserMoltenMaterial> resolveBarMaterial(BlockEntity blockEntity, BlockEntity automaticBlockEntity, boolean isLeft) {
        if (blockEntity != null) {
            return getBarMaterial(blockEntity, isLeft);
        } else if (automaticBlockEntity != null) {
            return getBarMaterial(automaticBlockEntity, isLeft);
        }
        return Optional.empty();
    }

    /**
     * Метод для отримання матеріалу з конкретного блоку.
     *
     * @param entity блок сутності
     * @param isLeft вказує, чи лівий це бар
     * @return опціональний матеріал
     */
    private Optional<MagicInfuserMoltenMaterial> getBarMaterial(BlockEntity entity, boolean isLeft) {
        if (entity instanceof AutomaticMagiculaInfuserBlockEntity automate)
            return isLeft
                    ? automate.getLeftBarId().flatMap(location -> filterMoltenMaterial(location))
                    : automate.getRightBarId().flatMap(location -> filterMoltenMaterial(location));
            return isLeft
                    ? ((MagiculaInfuserBlockEntity) entity).getLeftBarId().flatMap(location -> filterMoltenMaterial(location))
                    : ((MagiculaInfuserBlockEntity) entity).getRightBarId().flatMap(location -> filterMoltenMaterial(location));
    }

    /**
     * Метод для фільтрації матеріалів за типом.
     *
     * @param location ідентифікатор типу матеріалу
     * @return опціональний матеріал
     */
    private Optional<MagicInfuserMoltenMaterial> filterMoltenMaterial(ResourceLocation location) {
        return ReiData.getMagicInfuserMoltenMaterials()
                .stream()
                .filter(material -> material.getMoltenType().equals(location))
                .findFirst();
    }


    protected void init() {
        super.init();
    }

    @Override
    protected void containerTick() {
        super.containerTick();

        // Оновлення матеріалів для лівої та правої панелей
        updateBarMaterials(menu.blockEntity, MOLTEN_MAGICULES, INFUSION);
        updateBarMaterials(menu.automaticblockEntity, MOLTEN_MAGICULES, INFUSION);
    }

    /**
     * Оновлює матеріали для лівої та правої панелей блоків.
     *
     * @param blockEntity сутність блоку
     * @param leftMaterial матеріал для лівої панелі
     * @param rightMaterial матеріал для правої панелі
     */
    private void updateBarMaterials(BlockEntity blockEntity, ResourceLocation leftMaterial, ResourceLocation rightMaterial) {
        if (blockEntity == null) return;
        if (blockEntity instanceof MagiculaInfuserBlockEntity infuser){
            if (hasChanged(infuser.getLeftBarId(), this.leftBarMaterial)) {
                this.leftBarMaterial = materialOf(Optional.of(leftMaterial));
            }

            if (hasChanged(infuser.getRightBarId(), this.rightBarMaterial)) {
                this.rightBarMaterial = materialOf(Optional.of(rightMaterial));
            }
        } else {
            if (hasChanged(((AutomaticMagiculaInfuserBlockEntity) blockEntity).getLeftBarId(), this.leftBarMaterial)) {
                this.leftBarMaterial = materialOf(Optional.of(leftMaterial));
            }

            if (hasChanged(((AutomaticMagiculaInfuserBlockEntity) blockEntity).getRightBarId(), this.rightBarMaterial)) {
                this.rightBarMaterial = materialOf(Optional.of(rightMaterial));
            }
        }
    }

    private static Optional<MagicInfuserMoltenMaterial> materialOf(Optional<ResourceLocation> id) {
        return id.flatMap((location) -> ReiData.getMagicInfuserMoltenMaterials().stream().filter((moltenMaterial) -> moltenMaterial.getMoltenType().equals(location)).findFirst());
    }

    /**
     * Перевіряє, чи змінився стан матеріалу магічного інфузора.
     *
     * @param barId ідентифікатор ресурсу для поточного стану матеріальної панелі
     * @param material поточний матеріал на панелі магічного інфузора
     * @return {@code true} якщо стан матеріалу змінився, {@code false} інакше
     */
    private static boolean hasChanged(Optional<ResourceLocation> barId, Optional<MagicInfuserMoltenMaterial> material) {
        if (barId.isEmpty()) {
            return material.isPresent();
        } else if (material.isEmpty()) {
            return true;
        } else {
            return !material.get().getMoltenType().equals(barId.get());
        }
    }

    /**
     * Відображає фонове зображення та інші елементи на екрані чарівного інфузера.
     *
     * @param pPoseStack стек для малювання позицій
     * @param pPartialTick частковий відлік часу, що використовується для анімацій
     * @param pMouseX координата x миші
     * @param pMouseY координата y миші
     */
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, x, y, 0, 0, 256, 168);
        this.renderProgress(pPoseStack, x, y);
        this.renderFire(pPoseStack, x, y);
        this.renderMolten(pPoseStack, x, y);
        this.renderInfusion(pPoseStack, x, y);
    }

    /**
     * Візуалізує процес плавлення на екрані чарівного інфузера.
     *
     * @param pPoseStack стек для малювання позицій
     * @param x координата x де починається малювання
     * @param y координата y де починається малювання
     */
    private void renderProgress(PoseStack pPoseStack, int x, int y) {
        if (this.menu.isSmelting()) {
            this.blit(pPoseStack, x + 31, y + 71 - this.menu.getScaledProgress(), 78, 246 - this.menu.getScaledProgress(), 24, this.menu.getScaledProgress());
        }
    }

    private void renderMolten(PoseStack stack, int x, int y) {
        this.leftBarMaterial.ifPresent((moltenMaterial) -> {
            int width = 13;
            int height = this.menu.getMagiculesProgress();
            if (this.menu.getMagiculesProgress() > 0 && height < 1) {
                height = 1;
            }

            int renderX = x + 96;
            int renderY = y + 80 - height;
            float u = 0.07692308F * (float)width;
            float v = 0.013513514F * (float)height;
            Matrix4f pMatrix = stack.last().pose();
            BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderTexture(0, FLUID);
            RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
            bufferbuilder.vertex(pMatrix, (float)renderX, (float)renderY + (float)height, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(0.0F, v).endVertex();
            bufferbuilder.vertex(pMatrix, (float)renderX + (float)width, (float)renderY + (float)height, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(u, v).endVertex();
            bufferbuilder.vertex(pMatrix, (float)renderX + (float)width, (float)renderY, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(u, 0.0F).endVertex();
            bufferbuilder.vertex(pMatrix, (float)renderX, (float)renderY, 0.0F).color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha()).uv(0.0F, 0.0F).endVertex();
            BufferUploader.drawWithShader(bufferbuilder.end());
            RenderSystem.disableBlend();
        });
    }

    /**
     * Відображає процес інфузії в інтерфейсі користувача.
     *
     * @param stack графічний стек позицій, що використовуються для малювання
     * @param x координата X для початку відображення
     * @param y координата Y для початку відображення
     */
    private void renderInfusion(PoseStack stack, int x, int y) {
        this.rightBarMaterial.ifPresent((moltenMaterial) -> {
            if (this.menu.hasInfuse()){
                int width = 13; // Встановлює ширину смуги відображення
                int height = this.menu.getInfuseProgress(); // Отримує поточний прогрес інфузії

                // Переконується, що висота не є меншою за 1, якщо прогрес більший за 0.
                if (this.menu.getInfuseProgress() > 0 && height < 1) {
                    height = 1;
                }

                int renderX = x + 223; // Встановлює координату X для відображення
                int renderY = y + 80 - height; // Встановлює координату Y, беручи до уваги висоту прогресу
                float u = 0.07692308F * (float)width; // Розраховує u координату текстури
                float v = 0.013513514F * (float)height; // Розраховує v координату текстури
                Matrix4f pMatrix = stack.last().pose(); // Отримує останню матрицю з позиційного стека

                // Підготовка до малювання об'єкту
                BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                RenderSystem.enableBlend(); // Увімкнення режиму змішування кольорів
                RenderSystem.defaultBlendFunc(); // Встановлення функції змішування кольорів за замовчуванням
                RenderSystem.setShaderTexture(0, FLUID); // Вибір текстури для відображення
                RenderSystem.setShader(GameRenderer::getPositionColorTexShader); // Установка шейдера для текстури

                // Початок складання буфера
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);

                // Додає вершини для відображення квадрата із відповідним кольором і текстурною координатою
                bufferbuilder.vertex(pMatrix, (float)renderX, (float)renderY + (float)height, 0.0F)
                        .color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha())
                        .uv(0.0F, v).endVertex();

                bufferbuilder.vertex(pMatrix, (float)renderX + (float)width, (float)renderY + (float)height, 0.0F)
                        .color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha())
                        .uv(u, v).endVertex();

                bufferbuilder.vertex(pMatrix, (float)renderX + (float)width, (float)renderY, 0.0F)
                        .color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha())
                        .uv(u, 0.0F).endVertex();

                bufferbuilder.vertex(pMatrix, (float)renderX, (float)renderY, 0.0F)
                        .color(moltenMaterial.getRed(), moltenMaterial.getGreen(), moltenMaterial.getBlue(), moltenMaterial.getAlpha())
                        .uv(0.0F, 0.0F).endVertex();

                // Відображення складаного буфера
                BufferUploader.drawWithShader(bufferbuilder.end());

                RenderSystem.disableBlend(); // Вимкнення режиму змішування кольорів
            }
        });
    }

    private void renderFire(PoseStack pPoseStack, int x, int y) {
        if (((MagicInfuserMenu)this.menu).hasFuel()) {
            this.blit(pPoseStack, x + 37, y + 89 - ((MagicInfuserMenu)this.menu).getScaledFuelProgress(), 78, 181 - ((MagicInfuserMenu)this.menu).getScaledFuelProgress(), 13, ((MagicInfuserMenu)this.menu).getScaledFuelProgress());
        }
    }

    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        this.renderTooltip(pPoseStack, mouseX, mouseY);
    }

    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        String[] title = this.title.getString().split(" ");
        this.font.draw(pPoseStack, title[0], 128.0F, (float)this.titleLabelY + 1.0F, (new Color(225, 225, 225)).getRGB());
        this.font.draw(pPoseStack, title[1], 173.0F, (float)this.titleLabelY + 1.0F, (new Color(225, 225, 225)).getRGB());
        this.font.draw(pPoseStack, Component.translatable("reincarnation_plus.magicula_infuser.smeltery_label"), 22.0F, (float)this.titleLabelY + 9.0F, (new Color(198, 198, 198)).getRGB());
        this.font.draw(pPoseStack, this.playerInventoryTitle, 142.0F, (float)this.inventoryLabelY + 2.0F, (new Color(63, 63, 64)).getRGB());
    }

    /**
     * Відображає підказки, пов'язані з поточною позицією мишею в інтерфейсі користувача.
     *
     * @param pPoseStack стек позицій для графічного відображення
     * @param pMouseX координата X положення миші
     * @param pMouseY координата Y положення миші
     */
    protected void renderTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        // Перевіряє, чи не несе меню об'єкт (або предмет в слоті), та якщо курсор знаходиться на слоті з предметом.
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            // Відображає підказку для предмету в слоті, на якому знаходиться курсор миші.
            this.renderTooltip(pPoseStack, this.hoveredSlot.getItem(), pMouseX, pMouseY);
        }
        if (menu.blockEntity != null){
            // Перевіряє, чи курсор миші знаходиться в області лівої смуги інфузії.
            if (this.isHovering(95, 5, 15, 76, (double)pMouseX, (double)pMouseY)) {
                // Перевіряє, чи лівий бар переповнений і містить матеріал.
                if (!this.menu.blockEntity.getLeftBarId().equals(Optional.of(MagicInfusionRecipe.EMPTY))
                        && this.menu.blockEntity.getMagicMaterialAmount() > 0) {
                    // Формує текстове значення для відображення рівня матеріалу.
                    float magicAmount = this.menu.blockEntity.getMagicMaterialAmount();
                    String valueText = magicAmount + "/" + (this.menu.blockEntity.getMaxMagicMaterialAmount()+this.menu.blockEntity.getAdditionalMagicMaterialAmount());
                    // Відображає інструментальну підказку з інформацією про матеріал у лівій смузі.
                    this.leftBarMaterial.ifPresent((moltenMaterial) ->
                            this.renderMaterialTooltip(pPoseStack, pMouseX, pMouseY, moltenMaterial, valueText));
                } else {
                    // Відображає підказку, коли лівий бар порожній.
                    this.renderEmptyMaterialToolTip(pPoseStack, pMouseX, pMouseY);
                }
            }

            // Перевіряє, чи курсор миші знаходиться в області правої смуги інфузії.
            if (this.isHovering(220, 5, 15, 76, (double)pMouseX, (double)pMouseY)) {
                // Перевіряє, чи правий бар переповнений і містить матеріал.
                if (!((MagicInfuserMenu)this.menu).blockEntity.getRightBarId().equals(Optional.of(MagicInfusionRecipe.EMPTY))
                        && ((MagicInfuserMenu)this.menu).blockEntity.getInfusionTime() > 0) {
                    // Формує текстове значення для відображення рівня магічного матеріалу.
                    float infusionTime = (float)((MagicInfuserMenu)this.menu).blockEntity.getInfusionTime();
                    String valueText = infusionTime + "/" + this.menu.blockEntity.getMaxInfusionTime();
                    // Відображає інструментальну підказку з інформацією про матеріал у правій смузі.
                    this.rightBarMaterial.ifPresent((moltenMaterial) ->
                            this.renderInfusionTime(pPoseStack, pMouseX, pMouseY, moltenMaterial, valueText));
                } else {
                    // Відображає підказку, коли правий бар порожній.
                    this.renderEmptyMaterialToolTip(pPoseStack, pMouseX, pMouseY);
                }
            }
        }
        if (menu.automaticblockEntity != null){
            // Перевіряє, чи курсор миші знаходиться в області лівої смуги інфузії.
            if (this.isHovering(95, 5, 15, 76, (double)pMouseX, (double)pMouseY)) {
                // Перевіряє, чи лівий бар переповнений і містить матеріал.
                if (!this.menu.automaticblockEntity.getLeftBarId().equals(Optional.of(MagicInfusionRecipe.EMPTY))
                        && this.menu.automaticblockEntity.getMagicMaterialAmount() > 0) {
                    // Формує текстове значення для відображення рівня матеріалу.
                    float magicAmount = this.menu.automaticblockEntity.getMagicMaterialAmount();
                    String valueText = magicAmount + "/" + (this.menu.automaticblockEntity.getMaxMagicMaterialAmount()+this.menu.blockEntity.getAdditionalMagicMaterialAmount());
                    // Відображає інструментальну підказку з інформацією про матеріал у лівій смузі.
                    this.leftBarMaterial.ifPresent((moltenMaterial) ->
                            this.renderMaterialTooltip(pPoseStack, pMouseX, pMouseY, moltenMaterial, valueText));
                } else {
                    // Відображає підказку, коли лівий бар порожній.
                    this.renderEmptyMaterialToolTip(pPoseStack, pMouseX, pMouseY);
                }
            }

            // Перевіряє, чи курсор миші знаходиться в області правої смуги інфузії.
            if (this.isHovering(220, 5, 15, 76, (double)pMouseX, (double)pMouseY)) {
                // Перевіряє, чи правий бар переповнений і містить матеріал.
                if (!((MagicInfuserMenu)this.menu).automaticblockEntity.getRightBarId().equals(Optional.of(MagicInfusionRecipe.EMPTY))
                        && ((MagicInfuserMenu)this.menu).automaticblockEntity.getInfusionTime() > 0) {
                    // Формує текстове значення для відображення рівня магічного матеріалу.
                    float infusionTime = (float)((MagicInfuserMenu)this.menu).automaticblockEntity.getInfusionTime();
                    String valueText = infusionTime + "/" + this.menu.automaticblockEntity.getMaxInfusionTime();
                    // Відображає інструментальну підказку з інформацією про матеріал у правій смузі.
                    this.rightBarMaterial.ifPresent((moltenMaterial) ->
                            this.renderInfusionTime(pPoseStack, pMouseX, pMouseY, moltenMaterial, valueText));
                } else {
                    // Відображає підказку, коли правий бар порожній.
                    this.renderEmptyMaterialToolTip(pPoseStack, pMouseX, pMouseY);
                }
            }
        }

    }

    private void renderEmptyMaterialToolTip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.renderTooltip(pPoseStack, Component.translatable("tooltip.reincarnation_plus.magicula_infuser.empty"), pMouseX, pMouseY);
    }

    private void renderMaterialTooltip(PoseStack poseStack, int mouseX, int mouseY, MagicInfuserMoltenMaterial material, String valueText) {
        MutableComponent materialNameComponent = Component.translatable(String.format("%s.molten.%s.material", material.getMoltenType().getNamespace(), material.getMoltenType().getPath()));
        MutableComponent component = Component.translatable("tooltip.reincarnation_plus.magicula_infuser.molten_item", new Object[]{valueText, materialNameComponent}).withStyle(emtpyStyleWithMaterialColor(material));
        this.renderTooltip(poseStack, component, mouseX, mouseY);
    }
    private void renderInfusionTime(PoseStack poseStack, int mouseX, int mouseY, MagicInfuserMoltenMaterial material, String valueText) {
        MutableComponent component = Component.translatable("reincarnation_plus.molten.infusion.time", new Object[]{valueText}).withStyle(emtpyStyleWithMaterialColor(material));
        this.renderTooltip(poseStack, component, mouseX, mouseY);
    }

    private static Style emtpyStyleWithMaterialColor(MagicInfuserMoltenMaterial material) {
        return Style.EMPTY.withColor((new Color(material.getRed(), material.getGreen(), material.getBlue())).getRGB());
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return this.minecraft != null && this.minecraft.options.keyInventory.isActiveAndMatches(InputConstants.getKey(pKeyCode, pScanCode)) ? true : super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
}
