package dev.underswap1.projectwarpspeed.materials.items;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;

public class ItemMaterialMap {

    @SuppressWarnings("unused")
    public static class ItemMaterialOverride {
        public final String baseMaterial;
        public Double density20C = null;
        public Double electroconductivity = null;
        public Double meltingPointK = null;
        public Double boilingPointK = null;
        public Double specificHeat = null;
        public Double thermalConductivity = null;
        public Double thermalExpansion = null;
        public Double hardness = null;
        public Double youngsModulus = null;
        public Double poissonsRatio = null;
        public Double toughness = null;
        public Double ductility = null;


        private ItemMaterialOverride(String baseMaterial) {
            this.baseMaterial = baseMaterial;
        }

        // builder methods
        public ItemMaterialMap.ItemMaterialOverride density(double val) { this.density20C = val; return this; }
        public ItemMaterialMap.ItemMaterialOverride conductivity(double val) { this.electroconductivity = val; return this; }
        public ItemMaterialMap.ItemMaterialOverride melting(double val) { this.meltingPointK = val; return this; }
        public ItemMaterialMap.ItemMaterialOverride boiling(double val) { this.boilingPointK = val; return this; }
        public ItemMaterialMap.ItemMaterialOverride specificHeat(double val) { this.specificHeat = val; return this; }
        public ItemMaterialMap.ItemMaterialOverride thermalConductivity(double val) { this.thermalConductivity = val; return this; }
        public ItemMaterialMap.ItemMaterialOverride thermalExpansion(double val) { this.thermalExpansion = val; return this; }
        public ItemMaterialMap.ItemMaterialOverride hardness(double val) { this.hardness = val; return this; }
        public ItemMaterialMap.ItemMaterialOverride youngsModulus(double val) { this.youngsModulus = val; return this; }
        public ItemMaterialMap.ItemMaterialOverride poissonsRatio(double val) { this.poissonsRatio = val; return this; }
        public ItemMaterialMap.ItemMaterialOverride toughness(double val) { this.toughness = val; return this; }
        public ItemMaterialMap.ItemMaterialOverride ductility(double val) { this.ductility = val; return this; }

        public static ItemMaterialMap.ItemMaterialOverride of(String baseMaterial) {
            return new ItemMaterialMap.ItemMaterialOverride(baseMaterial);
        }
    }


    // Overrides for families
    private static final Map<String, ItemMaterialMap.ItemMaterialOverride> FAMILY_OVERRIDES = new HashMap<>();

    // Overrides for standalone items
    private static final Map<Item, ItemMaterialMap.ItemMaterialOverride> ITEM_OVERRIDES = new HashMap<>();

    static {
        // item family override
        FAMILY_OVERRIDES.put("COPPER", ItemMaterialMap.ItemMaterialOverride.of("copper"));
        FAMILY_OVERRIDES.put("IRON", ItemMaterialMap.ItemMaterialOverride.of("iron"));
        FAMILY_OVERRIDES.put("GOLD", ItemMaterialMap.ItemMaterialOverride.of("gold"));
        FAMILY_OVERRIDES.put("NETHERITE", ItemMaterialMap.ItemMaterialOverride.of("netherite"));

        // individual override
        ITEM_OVERRIDES.put(Items.IRON_INGOT, ItemMaterialMap.ItemMaterialOverride.of("iron"));
        ITEM_OVERRIDES.put(Items.GOLD_INGOT, ItemMaterialMap.ItemMaterialOverride.of("gold"));
        ITEM_OVERRIDES.put(Items.COPPER_INGOT, ItemMaterialMap.ItemMaterialOverride.of("copper"));
    }

    @SuppressWarnings("unused")
    public static ItemMaterialOverride getMaterialName(Item item) {
        return ITEM_OVERRIDES.get(item);
    }
    public static ItemMaterialMap.ItemMaterialOverride get(Item item) {
        // 1. Standalone item
        if (ITEM_OVERRIDES.containsKey(item)) {
            return ITEM_OVERRIDES.get(item);
        }

        // 2. Family-based override
        String family = ItemFamilyMap.getFamily(item);
        if (family != null) {
            return FAMILY_OVERRIDES.get(family);
        }

        // 3. No override found
        return null;
    }
    @SuppressWarnings("unused")
    public static ItemMaterialMap.ItemMaterialOverride get(String family) {
        return FAMILY_OVERRIDES.get(family);
    }
}