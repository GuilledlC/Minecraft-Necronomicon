package net.guille_dlc.necronomicon.old.biome.old;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;

import javax.annotation.Nullable;

public class TestOverworldBiomes {

    /*@Nullable
    private static final Music NORMAL_MUSIC = null;

    protected static int calculateSkyColor(float color)
    {
        float $$1 = color / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }
    private static Biome biome(boolean precipitation, float temperature, float downfall, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music) {
        return biome(precipitation, temperature, downfall, 4159204, 329011, spawnBuilder, biomeBuilder, music);
    }

    private static Biome biome(boolean precipitation, float temperature, float downfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music) {
        return (new Biome.BiomeBuilder())
                .hasPrecipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(waterColor)
                        .waterFogColor(waterFogColor)
                        .fogColor(12638463)
                        .skyColor(calculateSkyColor(temperature))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(music).build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(biomeBuilder.build())
                .build();
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder)
    {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome lovecraftCountry() {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        globalOverworldGeneration(biomeBuilder);

        BiomeDefaultFeatures.addBirchTrees(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
        BiomeDefaultFeatures.addDefaultGrass(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        BiomeDefaultFeatures.addMossyStoneBlock(biomeBuilder);
        BiomeDefaultFeatures.addMountainForestTrees(biomeBuilder);
        BiomeDefaultFeatures.addRareBerryBushes(biomeBuilder);
        BiomeDefaultFeatures.addWaterTrees(biomeBuilder);
        BiomeDefaultFeatures.addSnowyTrees(biomeBuilder);

        return biome(true, 1.0F, 1.0F, spawnBuilder, biomeBuilder, NORMAL_MUSIC);
    }*/
}