package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.api.dimension.NecronomiconDimensions;
import net.guille_dlc.necronomicon.common.dimension.Dimensions;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class ModDimensions {

    public static void setup() {

    }

    public static void bootstrapDimensionTypes(BootstapContext<DimensionType> context) {
        context.register(NecronomiconDimensions.LOVECRAFT_COUNTRY_TYPE, Dimensions.lovecraftCountry());
    }

    public static void bootstrapStems(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noise = context.lookup(Registries.NOISE_SETTINGS);

        context.register(NecronomiconDimensions.LOVECRAFT_COUNTRY_STEM, Dimensions.lovecraftCoutryStem(biomes, dimensionTypes, noise));
    }

}
