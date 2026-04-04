package dev.underswap1.projectwarpspeed.worldgen;

import dev.underswap1.projectwarpspeed.ProjectWarpspeed;
import dev.underswap1.projectwarpspeed.registry.ProjectWarpspeedBiomes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

//public class WarpspeedRegion extends Region {
//
//    public WarpspeedRegion(int weight) {
//        super(new Identifier(ProjectWarpspeed.MOD_ID, "overworld"), RegionType.OVERWORLD, weight);
//    }
//
//    @Override
//    public void addBiomes(Registry<Biome> registry,
//                          Consumer<net.minecraft.util.math.Pair<MultiNoiseUtil.NoiseHypercube,
//                                  RegistryKey<Biome>>> mapper) {
//        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
//            builder.replaceBiome(
//                    new MultiNoiseUtil.NoiseHypercube(
//                            MultiNoiseUtil.ParameterRange.of(0.7f, 1.0f),   // temperature
//                            MultiNoiseUtil.ParameterRange.of(0.7f, 1.0f),   // humidity
//                            MultiNoiseUtil.ParameterRange.of(-1.0f, 1.0f),  // continentalness
//                            MultiNoiseUtil.ParameterRange.of(-1.0f, 1.0f),  // erosion
//                            MultiNoiseUtil.ParameterRange.of(0.0f, 0.0f),   // depth
//                            MultiNoiseUtil.ParameterRange.of(-1.0f, 1.0f),  // weirdness
//                            0L                                               // offset
//                    ),
//                    ProjectWarpspeedBiomes.FLOODED_TROPICAL_FOREST
//            );
//        });
//    }
//}