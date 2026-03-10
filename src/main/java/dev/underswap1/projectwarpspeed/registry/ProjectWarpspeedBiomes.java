package dev.underswap1.projectwarpspeed.registry;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.*;

import java.util.HashMap;
import java.util.Map;

import static dev.underswap1.projectwarpspeed.ProjectWarpspeed.MOD_ID;

public class ProjectWarpspeedBiomes {

    private static final Map<RegistryKey<Biome>, Biome> BIOMES = new HashMap<>();

    // UNIVERSAL BIOME FACTORY
    private static Biome make(
            float temperature,
            float downfall,
            boolean hasPrecipitation,

            int skyColor,
            int fogColor,
            int waterColor,
            int waterFogColor,
            Integer grassColor,
            Integer foliageColor,

            ParticleEffect particle,
            float particleProbability,

            RegistryEntry.Reference<SoundEvent> loopSound,
            RegistryEntry.Reference<SoundEvent> moodSound,
            int moodDelay,
            RegistryEntry.Reference<SoundEvent> additionsSound,
            float additionsChance,
            MusicSound music,

            SpawnSettings spawnSettings,
            GenerationSettings generationSettings
    ) {

        BiomeEffects.Builder effects = new BiomeEffects.Builder()
                .skyColor(skyColor)
                .fogColor(fogColor)
                .waterColor(waterColor)
                .waterFogColor(waterFogColor);

        if (grassColor != null) effects.grassColor(grassColor);
        if (foliageColor != null) effects.foliageColor(foliageColor);
        if (particle != null) effects.particleConfig(new BiomeParticleConfig(particle, particleProbability));
        if (loopSound != null) effects.loopSound(loopSound);
        if (moodSound != null) effects.moodSound(new BiomeMoodSound(moodSound, moodDelay, 8, 2));
        if (additionsSound != null) effects.additionsSound(new BiomeAdditionsSound(additionsSound, additionsChance));
        if (music != null) effects.music(music);

        return new Biome.Builder()
                .temperature(temperature)
                .downfall(downfall)
                .precipitation(hasPrecipitation)
                .effects(effects.build())
                .spawnSettings(spawnSettings)
                .generationSettings(generationSettings)
                .build();
    }

    // Helper to add biomes
    private static void add(String name, Biome biome) {
        RegistryKey<Biome> key = RegistryKey.of(
                RegistryKeys.BIOME,
                new Identifier(MOD_ID, name)
        );
        BIOMES.put(key, biome);
    }

    // DEFINE ALL YOUR BIOMES HERE
    public static void createBiomes() {

        //add("example_biome", make(
                //0.8f, 0.4f, Biome.Precipitation.RAIN,

                //0x77AAFF, 0xC0D8FF, 0x3F76E4, 0x050533,
                //0x55FF55, 0x33CC33,

                //ParticleTypes.ASH, 0.01f,

                //SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP,
                //SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000,
                //SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.01f,
                //MusicType.GAME,

                //new SpawnSettings.Builder().build(),
                //new GenerationSettings.Builder().build()
        //));
        add("flooded_tropical_forest", make(
                1.8f, 1.0f, true,
                0x78A7FF, 0xC0D8FF, 0x2A5F4D, 0x04332A,
                0x1E8C44, 0x1A7A38,
                ParticleTypes.DRIPPING_WATER, 0.008f,
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("lowland_rainforest", make(
                1.7f, 0.95f, true,
                0x79A8FF, 0xC1D9FF, 0x3C6F52, 0x043733,
                0x209A4B, 0x1D8E3F,
                ParticleTypes.RAIN, 0.009f,
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("tropical_peat_swamp", make(
                1.6f, 0.85f, true,
                0x77A6FF, 0xC0D8FF, 0x2F5E48, 0x042F2A,
                0x1A9743, 0x17863B,
                ParticleTypes.MYCELIUM, 0.007f, // closest to swamp particles
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("coastal_rainforest", make(
                1.5f, 0.75f, true,
                0x78A6FF, 0xC0D8FF, 0x3F7550, 0x04322F,
                0x37A654, 0x32974C,
                null, 0f,
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("dry_deciduous_forest", make(
                1.3f, 0.35f, true,
                0x7EA1FF, 0xC0D8FF, 0x3B6B44, 0x042E27,
                0x92BC5A, 0x87B851,
                null, 0f,
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("hill_dipterocarp_forest", make(
                1.6f, 0.7f, true,
                0x7AA5FF, 0xC0D8FF, 0x3D6C50, 0x04312F,
                0x31A44B, 0x2D9A41,
                ParticleTypes.FALLING_SPORE_BLOSSOM, 0.006f, // pollen → closest vanilla
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("subtropical_broadleaf_forest", make(
                0.9f, 0.6f, true,
                0x7CA6FF, 0xC0D8FF, 0x3F6E55, 0x04332F,
                0x53A14F, 0x4F9B49,
                ParticleTypes.WHITE_ASH, 0.005f, // mist → closest vanilla
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("oak_hickory_forest", make(
                0.65f, 0.55f, true,
                0x7BA4FF, 0xC0D8FF, 0x3F6D50, 0x04312F,
                0x64A74F, 0x5FA246,
                null, 0f,
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("beech_fir_forest", make(
                0.55f, 0.5f, true,
                0x7AA5FF, 0xC0D8FF, 0x3F6F52, 0x04332F,
                0x5EA34F, 0x59A14B,
                null, 0.004f,
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("larch_forest", make(
                0.3f, 0.35f, true,
                0x81A7FF, 0xC0D8FF, 0x3F6A50, 0x04322E,
                0x74AA50, 0x6FA34A,
                ParticleTypes.SNOWFLAKE, 0.002f,
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("temperate_rainforest", make(
                0.7f, 0.9f, true,
                0x7CA6FF, 0xC0D8FF, 0x2E6B57, 0x042A50,
                0x2E8B57, 0x2A7F50,
                ParticleTypes.DRIPPING_WATER, 0.005f,
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("montane_forest", make(
                1.5f, 0.85f, true,
                0x78A6FF, 0xC0D8FF, 0x3C6F52, 0x043733,
                null, null,
                null, 0f,
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("bamboo_forest", make(
                1.6f, 0.75f, true,
                0x79A7FF, 0xC0D8FF, 0x3B7050, 0x04312F,
                0x31A25B, 0x2D964F,
                ParticleTypes.FALLING_SPORE_BLOSSOM, 0.006f, // pollen → closest vanilla
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("cloud_forest", make(
                0.6f, 0.95f, true,
                0x7AAAFF, 0xC0DAFF, 0x2F6B54, 0x042F2E,
                0x2FA04C, 0x2B9441,
                ParticleTypes.WHITE_ASH, 0.007f, // mist → closest vanilla
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("subalpine_mixed_forest", make(
                0.4f, 0.5f, true,
                0x83AAFF, 0xC0D9FF, 0x3F6E55, 0x04332F,
                0x89B76B, 0x83AF64,
                ParticleTypes.SNOWFLAKE, 0.003f,
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));

        add("wet_broadleaf_forest", make(
                1.4f, 0.9f, true,
                0x78ABFF, 0xC0DFFF, 0x3E6E50, 0x04332F,
                0x33A653, 0x2F9850,
                ParticleTypes.RAIN, 0.008f,
                null, null, 0,
                null, 0f,
                null,
                new SpawnSettings.Builder().build(),
                new GenerationSettings.Builder().build()
        ));
    }

    // ONE-TIME REGISTRATION
    public static void registerAll() {
        BIOMES.forEach((key, biome) -> {
            // Your logger handles the actual registration
        });
    }
}
