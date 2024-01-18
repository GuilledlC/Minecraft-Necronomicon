package net.guille_dlc.necronomicon.common.biome;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.common.entity.NecronomiconEntities;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class NecronomiconBiomes {

	public static ResourceKey<Biome> ACADIAN_FOREST = register("acadian_forest");
	public static ResourceKey<Biome> MUDDY_WASTELAND = register("muddy_wasteland");

	public static Biome acadianForest(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
		MobSpawnSettings spawnSettings = new MobSpawnSettings.Builder()
				.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 10, 5, 60))
				.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 6, 3, 50))
				.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 15, 2, 40))
				.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(NecronomiconEntities.ANGLE.get(), 20, 10, 100))
				.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 20, 8, 20))
				.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 4, 10))
				.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CAT, 8, 4, 10))
				.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 8, 8, 10))
				.addMobCharge(EntityType.ZOMBIE, 1, 0.8)
				.addMobCharge(EntityType.SPIDER, 1, 0.8)
				.addMobCharge(NecronomiconEntities.ANGLE.get(), 1, 0.8)
				.build();

		//https://minecraft.fandom.com/wiki/Custom_biome
		BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(features, carvers)

				//Carvers
				.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)

				//Lakes
				.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND)

				//Local modifications
				.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.FOREST_ROCK)

				//Underground structures
				.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.FOSSIL_UPPER)
				.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.FOSSIL_LOWER)

				//Fluid springs
				.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER)

				//Vegetal decoration
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_TAIGA)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_WINDSWEPT_FOREST)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_BIRCH_AND_OAK)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_GROVE)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_WATER)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_BIRCH)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BIRCH_TALL)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_SWAMP)
				.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN)
				;

		//Underground ores
		BiomeDefaultFeatures.addDefaultOres(generationSettings);
		BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
		BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);



		return new Biome.BiomeBuilder()
				.temperature(0.3F)
				.downfall(0.8F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.skyColor(0x2c333b)
								.waterColor(0x334f48)
								.waterFogColor(0x494f2e)
								.fogColor(0x494f46)
								.grassColorOverride(0x5e4d28)
								.foliageColorOverride(0x834d28)
								.ambientParticle(new AmbientParticleSettings(ParticleTypes.ASH, 0.1F))
								.ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
								.ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2))
								.ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111))
								.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY))
								.build())
				.mobSpawnSettings(spawnSettings)
				.generationSettings(generationSettings.build())
				.build();
	}

	public static Biome muddyWasteland(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
		MobSpawnSettings spawnSettings = new MobSpawnSettings.Builder()
				.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 10, 5, 60))
				.build();

		//https://minecraft.fandom.com/wiki/Custom_biome
		BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(features, carvers)

				//Carvers

				//Lakes

				//Local modifications

				//Underground structures

				//Fluid springs

				//Vegetal decoration
				;

		//Underground ores
		BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);

		return new Biome.BiomeBuilder()
				.temperature(0.5F)
				.downfall(0.5F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.skyColor(0x284952)
								.waterColor(0x26414f)
								.waterFogColor(0x334352)
								.fogColor(0x46474f)
								.grassColorOverride(0x434d43)
								.foliageColorOverride(0x403c3d)
								//.ambientParticle(new AmbientParticleSettings(ParticleTypes.RAIN, 0.1F))
								.ambientLoopSound(SoundEvents.MUSIC_BIOME_DEEP_DARK)
								.ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_CAVE, 6000, 8, 2))
								.ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111))
								//.backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DEEP_DARK))
								.build())
				.mobSpawnSettings(spawnSettings)
				.generationSettings(generationSettings.build())
				.build();
	}

	private static ResourceKey<Biome> register(String name) {
		ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, new ResourceLocation(Necronomicon.MOD_ID, name));
		return key;
	}
}
