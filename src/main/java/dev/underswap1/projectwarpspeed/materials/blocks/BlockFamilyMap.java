package dev.underswap1.projectwarpspeed.materials.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class BlockFamilyMap {

    private static final Map<Block, String> BLOCK_TO_FAMILY = new HashMap<>();

    static {
        // COPPER
        String copperFamily = "COPPER";
        BLOCK_TO_FAMILY.put(Blocks.COPPER_BLOCK, copperFamily);
        BLOCK_TO_FAMILY.put(Blocks.RAW_COPPER_BLOCK, copperFamily);
        BLOCK_TO_FAMILY.put(Blocks.CUT_COPPER, copperFamily);
        BLOCK_TO_FAMILY.put(Blocks.CUT_COPPER_SLAB, copperFamily);
        BLOCK_TO_FAMILY.put(Blocks.CUT_COPPER_STAIRS, copperFamily);

        // EXPOSED COPPER
        String exposed = "EXPOSED_COPPER";
        BLOCK_TO_FAMILY.put(Blocks.EXPOSED_COPPER, exposed);
        BLOCK_TO_FAMILY.put(Blocks.EXPOSED_CUT_COPPER, exposed);
        BLOCK_TO_FAMILY.put(Blocks.EXPOSED_CUT_COPPER_SLAB, exposed);
        BLOCK_TO_FAMILY.put(Blocks.EXPOSED_CUT_COPPER_STAIRS, exposed);

        // WEATHERED COPPER
        String weathered = "WEATHERED_COPPER";
        BLOCK_TO_FAMILY.put(Blocks.WEATHERED_COPPER, weathered);
        BLOCK_TO_FAMILY.put(Blocks.WEATHERED_CUT_COPPER, weathered);
        BLOCK_TO_FAMILY.put(Blocks.WEATHERED_CUT_COPPER_SLAB, weathered);
        BLOCK_TO_FAMILY.put(Blocks.WEATHERED_CUT_COPPER_STAIRS, weathered);

        // OXIDIZED COPPER
        String oxidized = "OXIDIZED_COPPER";
        BLOCK_TO_FAMILY.put(Blocks.OXIDIZED_COPPER, oxidized);
        BLOCK_TO_FAMILY.put(Blocks.OXIDIZED_CUT_COPPER, oxidized);
        BLOCK_TO_FAMILY.put(Blocks.OXIDIZED_CUT_COPPER_SLAB, oxidized);
        BLOCK_TO_FAMILY.put(Blocks.OXIDIZED_CUT_COPPER_STAIRS, oxidized);

        // IRON
        String iron = "IRON";
        BLOCK_TO_FAMILY.put(Blocks.IRON_BLOCK, iron);
        BLOCK_TO_FAMILY.put(Blocks.IRON_BARS, iron);
        BLOCK_TO_FAMILY.put(Blocks.IRON_DOOR, iron);
        BLOCK_TO_FAMILY.put(Blocks.IRON_TRAPDOOR, iron);
        BLOCK_TO_FAMILY.put(Blocks.RAW_IRON_BLOCK, iron);
        BLOCK_TO_FAMILY.put(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, iron);

        // GOLD
        String gold = "GOLD";
        BLOCK_TO_FAMILY.put(Blocks.GOLD_BLOCK, gold);
        BLOCK_TO_FAMILY.put(Blocks.RAW_GOLD_BLOCK, gold);
        BLOCK_TO_FAMILY.put(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, gold);

        // NETHERITE
        String netherite = "NETHERITE";
        BLOCK_TO_FAMILY.put(Blocks.NETHERITE_BLOCK, netherite);
    }

    public static String getFamily(Block block) {
        return BLOCK_TO_FAMILY.get(block);
    }
}