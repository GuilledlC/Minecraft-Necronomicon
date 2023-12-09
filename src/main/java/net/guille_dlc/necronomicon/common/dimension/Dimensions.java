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
                OptionalLong.empty(), //Fixed time
                true, //Has skylight
                false, //Has ceiling
                false, //Ultrawarm
                true, //Natural
                1.0F, //Coordinate scale
                true, //Bed works
                false, //Respawn anchor works
                0, //MinY
                256, //Height
                256, //Logical height
                BlockTags.INFINIBURN_OVERWORLD, //Infiniburn
                new ResourceLocation("minecraft:overworld"), //Effects
                0F, //Ambient light
                new DimensionType.MonsterSettings(
                        false,
                        false,
                        UniformInt.of(0, 7),
                        2
                ) //Monster settings
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
                OptionalLong.of(18000), //Fixed time
                false, //Has skylight
                false, //Has ceiling
                false, //Ultrawarm
                false, //Natural
                1.0F, //Coordinate scale
                false, //Bed works
                true, //Respawn anchor works
                0, //MinY
                128, //Height
                128, //Logical height
                BlockTags.INFINIBURN_NETHER, //Infiniburn
                NecronomiconDimensions.DAGON_EFFECTS, //Effects
                0.075F, //Ambient light
                new DimensionType.MonsterSettings(
                        false,
                        false,
                        UniformInt.of(0, 7),
                        0
                ) //Monster settings
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
