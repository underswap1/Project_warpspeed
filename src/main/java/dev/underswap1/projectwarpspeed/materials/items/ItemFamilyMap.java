package dev.underswap1.projectwarpspeed.materials.items;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;

public class ItemFamilyMap {

    private static final Map<Item, String> ITEM_TO_FAMILY = new HashMap<>();

    static {
        // COPPER
        String copper = "COPPER";
        ITEM_TO_FAMILY.put(Items.COPPER_INGOT, copper);
        ITEM_TO_FAMILY.put(Items.RAW_COPPER, copper);
        ITEM_TO_FAMILY.put(Items.RAW_COPPER_BLOCK, copper);
        ITEM_TO_FAMILY.put(Items.COPPER_BLOCK, copper);
        ITEM_TO_FAMILY.put(Items.CUT_COPPER, copper);
        ITEM_TO_FAMILY.put(Items.CUT_COPPER_SLAB, copper);
        ITEM_TO_FAMILY.put(Items.CUT_COPPER_STAIRS, copper);

        // IRON
        String iron = "IRON";
        ITEM_TO_FAMILY.put(Items.IRON_INGOT, iron);
        ITEM_TO_FAMILY.put(Items.RAW_IRON, iron);
        ITEM_TO_FAMILY.put(Items.RAW_IRON_BLOCK, iron);
        ITEM_TO_FAMILY.put(Items.IRON_NUGGET, iron);
        ITEM_TO_FAMILY.put(Items.IRON_BLOCK, iron);
        ITEM_TO_FAMILY.put(Items.IRON_AXE, iron);
        ITEM_TO_FAMILY.put(Items.IRON_HOE, iron);
        ITEM_TO_FAMILY.put(Items.IRON_SHOVEL, iron);
        ITEM_TO_FAMILY.put(Items.IRON_SWORD, iron);
        ITEM_TO_FAMILY.put(Items.IRON_PICKAXE, iron);
        ITEM_TO_FAMILY.put(Items.IRON_HORSE_ARMOR, iron);
        ITEM_TO_FAMILY.put(Items.IRON_HELMET, iron);
        ITEM_TO_FAMILY.put(Items.IRON_CHESTPLATE, iron);
        ITEM_TO_FAMILY.put(Items.IRON_LEGGINGS, iron);
        ITEM_TO_FAMILY.put(Items.IRON_BOOTS, iron);
        ITEM_TO_FAMILY.put(Items.IRON_BARS, iron);
        ITEM_TO_FAMILY.put(Items.IRON_DOOR, iron);
        ITEM_TO_FAMILY.put(Items.IRON_TRAPDOOR, iron);
        ITEM_TO_FAMILY.put(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, iron);

        // GOLD
        String gold = "GOLD";
        ITEM_TO_FAMILY.put(Items.GOLD_INGOT, gold);
        ITEM_TO_FAMILY.put(Items.RAW_GOLD, gold);
        ITEM_TO_FAMILY.put(Items.RAW_GOLD_BLOCK, gold);
        ITEM_TO_FAMILY.put(Items.GOLD_NUGGET, gold);
        ITEM_TO_FAMILY.put(Items.GOLD_BLOCK, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_AXE, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_SHOVEL, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_SWORD, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_PICKAXE, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_HOE, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_HELMET, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_CHESTPLATE, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_LEGGINGS, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_BOOTS, gold);
        ITEM_TO_FAMILY.put(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_HORSE_ARMOR, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_APPLE, gold);
        ITEM_TO_FAMILY.put(Items.ENCHANTED_GOLDEN_APPLE, gold);
        ITEM_TO_FAMILY.put(Items.GOLDEN_CARROT, gold);

        // NETHERITE
        String netherite = "NETHERITE";
        ITEM_TO_FAMILY.put(Items.NETHERITE_INGOT, netherite);
        ITEM_TO_FAMILY.put(Items.NETHERITE_SCRAP, netherite);
        ITEM_TO_FAMILY.put(Items.NETHERITE_BLOCK, netherite);
        ITEM_TO_FAMILY.put(Items.NETHERITE_AXE, netherite);
        ITEM_TO_FAMILY.put(Items.NETHERITE_PICKAXE, netherite);
        ITEM_TO_FAMILY.put(Items.NETHERITE_SWORD, netherite);
        ITEM_TO_FAMILY.put(Items.NETHERITE_SHOVEL, netherite);
        ITEM_TO_FAMILY.put(Items.NETHERITE_HOE, netherite);
        ITEM_TO_FAMILY.put(Items.NETHERITE_HELMET, netherite);
        ITEM_TO_FAMILY.put(Items.NETHERITE_CHESTPLATE, netherite);
        ITEM_TO_FAMILY.put(Items.NETHERITE_LEGGINGS, netherite);
        ITEM_TO_FAMILY.put(Items.NETHERITE_BOOTS, netherite);

        // Add more metals as needed
    }

    public static String getFamily(Item item) {
        return ITEM_TO_FAMILY.get(item);
    }
}