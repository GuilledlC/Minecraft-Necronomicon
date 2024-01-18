package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.common.dimension.NecronomiconDimensions;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class ModDimensions {

	public static void setup() {

	}

	public static void bootstrapDimensionTypes(BootstapContext<DimensionType> context) {
		context.register(NecronomiconDimensions.LOVECRAFT_COUNTRY_TYPE, NecronomiconDimensions.lovecraftCountry());
		context.register(NecronomiconDimensions.DAGON_TYPE, NecronomiconDimensions.dagon());
	}

	public static void bootstrapStems(BootstapContext<LevelStem> context) {
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noise = context.lookup(Registries.NOISE_SETTINGS);

		context.register(NecronomiconDimensions.LOVECRAFT_COUNTRY_STEM, NecronomiconDimensions.lovecraftCoutryStem(biomes, dimensionTypes, noise));
		context.register(NecronomiconDimensions.DAGON_STEM, NecronomiconDimensions.dagonStem(biomes, dimensionTypes, noise));
	}

}
