package dev.underswap1.projectwarpspeed.materials.blocks;

import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class BlockMaterialMap {

    @SuppressWarnings("unused")
    public static class BlockMaterialOverride {
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

        private BlockMaterialOverride(String baseMaterial) {
            this.baseMaterial = baseMaterial;
        }

        // builder methods
        public BlockMaterialOverride density(double val) { this.density20C = val; return this; }
        public BlockMaterialOverride conductivity(double val) { this.electroconductivity = val; return this; }
        public BlockMaterialOverride melting(double val) { this.meltingPointK = val; return this; }
        public BlockMaterialOverride boiling(double val) { this.boilingPointK = val; return this; }
        public BlockMaterialOverride specificHeat(double val) { this.specificHeat = val; return this; }
        public BlockMaterialOverride thermalConductivity(double val) { this.thermalConductivity = val; return this; }
        public BlockMaterialOverride thermalExpansion(double val) { this.thermalExpansion = val; return this; }
        public BlockMaterialOverride hardness(double val) { this.hardness = val; return this; }
        public BlockMaterialOverride youngsModulus(double val) { this.youngsModulus = val; return this; }
        public BlockMaterialOverride poissonsRatio(double val) { this.poissonsRatio = val; return this; }
        public BlockMaterialOverride toughness(double val) { this.toughness = val; return this; }
        public BlockMaterialOverride ductility(double val) { this.ductility = val; return this; }

        public static BlockMaterialOverride of(String baseMaterial) {
            return new BlockMaterialOverride(baseMaterial);
        }
    }

    // Overrides for families
    private static final Map<String, BlockMaterialOverride> FAMILY_OVERRIDES = new HashMap<>();

    // Overrides for standalone blocks
    private static final Map<Block, BlockMaterialOverride> BLOCK_OVERRIDES = new HashMap<>();

    static {
        // ----- FAMILY OVERRIDES -----
        FAMILY_OVERRIDES.put("COPPER", BlockMaterialOverride.of("copper"));
        FAMILY_OVERRIDES.put("EXPOSED_COPPER", BlockMaterialOverride.of("copper").density(8950.0).conductivity(5.5e7).hardness(3.0).ductility(30.0));
        FAMILY_OVERRIDES.put("WEATHERED_COPPER", BlockMaterialOverride.of("copper").density(8900.0).conductivity(5.0e7).hardness(3.2).ductility(25.0));
        FAMILY_OVERRIDES.put("OXIDIZED_COPPER", BlockMaterialOverride.of("copper").density(8850.0).conductivity(4.8e7).hardness(3.5).ductility(20.0));
        FAMILY_OVERRIDES.put("IRON", BlockMaterialOverride.of("iron"));
        FAMILY_OVERRIDES.put("GOLD", BlockMaterialOverride.of("gold"));
        FAMILY_OVERRIDES.put("NETHERITE", BlockMaterialOverride.of("netherite"));

        // ----- STANDALONE BLOCK OVERRIDES -----
        //BLOCK_OVERRIDES.put(Blocks.IRON_BLOCK,
        //        BlockMaterialOverride.of("iron").density(7874).conductivity(1.0e7).hardness(5.0));
    }
    public static BlockMaterialOverride get(Block block) {
        // 1. Standalone block
        if (BLOCK_OVERRIDES.containsKey(block)) {
            return BLOCK_OVERRIDES.get(block);
        }

        // 2. Family-based override
        String family = BlockFamilyMap.getFamily(block);
        if (family != null) {
            return FAMILY_OVERRIDES.get(family);
        }

        // 3. No override found
        return null;
    }
    @SuppressWarnings("unused")
    public static BlockMaterialOverride get(String family) {
        return FAMILY_OVERRIDES.get(family);
    }
}