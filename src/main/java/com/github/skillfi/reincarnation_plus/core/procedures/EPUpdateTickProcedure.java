package com.github.skillfi.reincarnation_plus.core.procedures;

import com.github.skillfi.reincarnation_plus.core.ReiMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class EPUpdateTickProcedure {

    /**
     * Оновлює структуру на основі рівня магікулів.
     *
     * @param level       Серверний рівень (ServerLevel)
     * @param corePos     Позиція ядра
     * @param magicules   Кількість магікулів у ядрі
     */
    public static void execute(ServerLevel level, BlockPos corePos, int magicules) {
        // Визначення розміру розширення
        int requiredExpansion = magicules / 1000; // Кожні 1000 магікулів — один рівень розширення
        if (requiredExpansion <= 0) return;

        // Перевірка, чи є сторона з Cobblestone
        Direction expansionDirection = findCobblestoneSide(level, corePos);
        if (expansionDirection == null) return;

        // Розширення структури
        for (int i = 1; i <= requiredExpansion; i++) {
            BlockPos newPartPos = corePos.relative(expansionDirection, i * structureWidth());
            addRoom(level, newPartPos);
        }
    }

    /**
     * Перевіряє, чи блок знаходиться в межах структури
     *
     * @param level   Серверний рівень
     * @param pos     Позиція блоку
     * @return        true, якщо блок є частиною структури
     */
    private static boolean isBlockInEPCaveStructure(ServerLevel level, BlockPos pos) {
        try {
            // Отримання менеджера структур
            StructureManager structureManager = level.structureManager();

            // Перевірка наявності структури за заданою позицією
//            if (structureManager.getStructureAt(pos, RPStructures.getStructureByResourceKey()).isValid()) {
//                // Додаткові перевірки (якщо необхідно) можна вставити тут
//                return true;
//            }
        } catch (Exception e) {
            // Логування або обробка винятків
            ReiMod.LOGGER.warn("Failed to check block in structure: ", e);
        }

        // Блок не належить до структури
        return false;
    }



    /**
     * Визначає сторону, де на підлозі є Cobblestone.
     */
    private static Direction findCobblestoneSide(ServerLevel level, BlockPos corePos) {
        for (Direction direction : Direction.values()) {
            // Отримуємо позицію стіни на відповідній стороні
            BlockPos wallPos = corePos.relative(direction);
            // Отримуємо позицію підлоги (на рівень нижче)
            BlockPos floorPos = wallPos.below();

            // Перевіряємо, чи на підлозі є Cobblestone
            if (level.getBlockState(floorPos).is(Blocks.COBBLESTONE)) {
                return direction;
            }
        }
        return null; // Якщо Cobblestone не знайдено
    }


    /**
     * Додає кімнату, використовуючи існуючу стіну як базову.
     */
    private static void addRoom(ServerLevel level, BlockPos existingWallPos) {
        // Ресурси для готових стін
        ResourceLocation wallWithDoor = new ResourceLocation("reincarnation_plus", "wall_with_door");
        ResourceLocation solidWall = new ResourceLocation("reincarnation_plus", "solid_wall");

        // Починаємо будівництво кімнати від існуючої стіни
        addWallStructure(level, existingWallPos.offset(0, 0, -10), solidWall); // Перша стіна
        addWallStructure(level, existingWallPos.offset(10, 0, 0), wallWithDoor); // Стіна з проємом
        addWallStructure(level, existingWallPos.offset(10, 0, -10), solidWall); // Друга стіна
    }

    /**
     * Додає готову стіну в певному положенні.
     */
    private static void addWallStructure(ServerLevel level, BlockPos pos, ResourceLocation wallType) {
        StructureTemplate template = level.getStructureManager().getOrCreate(wallType);
        if (template != null) {
            template.placeInWorld(
                    level,
                    pos,
                    pos,
                    new StructurePlaceSettings()
                            .setRotation(Rotation.NONE)
                            .setMirror(Mirror.NONE)
                            .addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR),
                    level.getRandom(),
                    2
            );
        }
    }



    /**
     * Отримує ширину структури.
     */
    private static int structureWidth() {
        return 10; // Наприклад, ширина шаблону
    }
}
