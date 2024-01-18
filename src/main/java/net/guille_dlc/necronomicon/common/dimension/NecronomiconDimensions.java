package net.guille_dlc.necronomicon.common.dimension;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.common.biome.NecronomiconBiomeBuilder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class NecronomiconDimensions {

	public static ResourceKey<Level> LOVECRAFT_COUNTRY = registerDimension("lovecraft_country");
	public static ResourceKey<DimensionType> LOVECRAFT_COUNTRY_TYPE = registerDimensionType("lovecraft_country");
	public static ResourceKey<LevelStem> LOVECRAFT_COUNTRY_STEM = registerLevelStem("lovecraft_country");

	public static ResourceKey<Level> DAGON = registerDimension("dagon");
	public static ResourceKey<DimensionType> DAGON_TYPE = registerDimensionType("dagon");
	public static ResourceKey<LevelStem> DAGON_STEM = registerLevelStem("dagon");
	public static final ResourceLocation DAGON_EFFECTS = Necronomicon.id("dagon_effects");


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
						NecronomiconBiomeBuilder.buildLovecraftBiomeSource(biomes),
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
						NecronomiconBiomeBuilder.buildDagonBiomeSource(biomes),
						noise.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
				)
		);
	}


	private static ResourceKey<Level> registerDimension(String name) {
		ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, Necronomicon.id(name));
		return key;
	}

	private static ResourceKey<DimensionType> registerDimensionType(String name) {
		ResourceKey<DimensionType> key = ResourceKey.create(Registries.DIMENSION_TYPE, Necronomicon.id(name));
		return key;
	}

	private static ResourceKey<LevelStem> registerLevelStem(String name) {
		ResourceKey<LevelStem> key = ResourceKey.create(Registries.LEVEL_STEM, Necronomicon.id(name));
		return key;
	}
}
