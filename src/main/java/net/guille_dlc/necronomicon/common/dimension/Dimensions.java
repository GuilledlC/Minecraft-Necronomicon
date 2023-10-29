package net.guille_dlc.necronomicon.common.dimension;

import net.guille_dlc.necronomicon.api.dimension.NecronomiconDimensions;
import net.guille_dlc.necronomicon.common.biome.LovecraftBiomeBuilder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.OptionalLong;

public class Dimensions {

    //https://minecraft.fandom.com/wiki/Custom_dimension
    public static DimensionType lovecraftCountry() {
        return new DimensionType(
                OptionalLong.empty(),
                false,
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
                new ResourceLocation("minecraft:nether"),
                0.8F,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 2)
        );
    }

    public static LevelStem lovecraftCoutryStem(HolderGetter<Biome> biomes, HolderGetter<DimensionType> dimensionTypes, HolderGetter<NoiseGeneratorSettings> noise) {
        return new LevelStem(
                dimensionTypes.getOrThrow(NecronomiconDimensions.LOVECRAFT_COUNTRY_TYPE),
                new NoiseBasedChunkGenerator(
                        LovecraftBiomeBuilder.buildBiomeSource(biomes),
                        noise.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
                ));
    }
}
