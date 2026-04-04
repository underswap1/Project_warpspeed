package dev.underswap1.projectwarpspeed.registry;


import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import static dev.underswap1.projectwarpspeed.ProjectWarpspeed.MOD_ID;

public class ProjectWarpspeedBiomes {

    public static final RegistryKey<Biome> FLOODED_TROPICAL_FOREST = key("flooded_tropical_forest");
    public static final RegistryKey<Biome> LOWLAND_RAINFOREST = key("lowland_rainforest");
    public static final RegistryKey<Biome> TROPICAL_PEAT_SWAMP = key("tropical_peat_swamp");
    public static final RegistryKey<Biome> COASTAL_RAINFOREST = key("coastal_rainforest");
    public static final RegistryKey<Biome> DRY_DECIDUOUS_FOREST = key("dry_deciduous_forest");
    public static final RegistryKey<Biome> HILL_DIPTEROCARP_FOREST = key("hill_dipterocarp_forest");
    public static final RegistryKey<Biome> SUBTROPICAL_BROADLEAF_FOREST = key("subtropical_broadleaf_forest");
    public static final RegistryKey<Biome> OAK_HICKORY_FOREST = key("oak_hickory_forest");
    public static final RegistryKey<Biome> BEECH_FIR_FOREST = key("beech_fir_forest");
    public static final RegistryKey<Biome> LARCH_FOREST = key("larch_forest");
    public static final RegistryKey<Biome> TEMPERATE_RAINFOREST = key("temperate_rainforest");
    public static final RegistryKey<Biome> MONTANE_FOREST = key("montane_forest");
    public static final RegistryKey<Biome> BAMBOO_FOREST = key("bamboo_forest");
    public static final RegistryKey<Biome> CLOUD_FOREST = key("cloud_forest");
    public static final RegistryKey<Biome> SUBALPINE_MIXED_FOREST = key("subalpine_mixed_forest");
    public static final RegistryKey<Biome> WET_BROADLEAF_FOREST = key("wet_broadleaf_forest");

    private static RegistryKey<Biome> key(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(MOD_ID, name));
    }
}

