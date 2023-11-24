package net.guille_dlc.necronomicon.common.dimension;

import net.guille_dlc.necronomicon.api.dimension.NecronomiconDimensions;
import net.guille_dlc.necronomicon.common.biome.LovecraftBiomeBuilder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class Dimensions {

    //https://minecraft.fandom.com/wiki/Custom_dimension
    public static DimensionType lovecraftCountry() {
        return new DimensionType(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0F,
                true,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                new ResourceLocation("minecraft:overworld"),
                0F,
                new DimensionType.MonsterSettings(
                        false,
                        false,
                        UniformInt.of(0, 7),
                        2
                )
        );
    }

    public static LevelStem lovecraftCoutryStem(HolderGetter<Biome> biomes, HolderGetter<DimensionType> dimensionTypes, HolderGetter<NoiseGeneratorSettings> noise) {
        return new LevelStem(
                dimensionTypes.getOrThrow(NecronomiconDimensions.LOVECRAFT_COUNTRY_TYPE),
                new NoiseBasedChunkGenerator(
                        LovecraftBiomeBuilder.buildLovecraftBiomeSource(biomes),
                        noise.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
                ));
    }

    public static DimensionType dagon() {
        return new DimensionType(
                OptionalLong.of(12000),
                false,
                false,
                false,
                false,
                1.0F,
                false,
                true,
                0,
                256,
                256,
                BlockTags.INFINIBURN_NETHER,
                new ResourceLocation("minecraft:nether"),
                0.1F,
                new DimensionType.MonsterSettings(
                        false,
                        false,
                        UniformInt.of(0, 7),
                        0
                )
        );
    }

    public static LevelStem dagonStem(HolderGetter<Biome> biomes, HolderGetter<DimensionType> dimensionTypes, HolderGetter<NoiseGeneratorSettings> noise) {
        return new LevelStem(
                dimensionTypes.getOrThrow(NecronomiconDimensions.DAGON_TYPE),
                new NoiseBasedChunkGenerator(
                        LovecraftBiomeBuilder.buildDagonBiomeSource(biomes),
                        noise.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
                )
        );
    }
}
