package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.Necronomicon;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.loading.FMLPaths;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModConfig {

	public static class GenerationConfig {

		public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
		public static final ModConfigSpec SPEC;
		public static final ModConfigSpec.IntValue overworldRegionWeight;
		static {
			BUILDER.comment("World generation related options.");
			BUILDER.push("overworld");
			overworldRegionWeight = BUILDER.comment("The weighting of primary bop biome regions in the overworld.").defineInRange("overworld_region_weight", 10, 0, Integer.MAX_VALUE);
			BUILDER.pop();

			SPEC = BUILDER.build();
		}
	}

	public static void setup() {
		createConfigDirectoryIfNecessary();
		ModLoadingContext.get().registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, GenerationConfig.SPEC, "biomesoplenty/generation.toml");
	}

	private static void createConfigDirectoryIfNecessary() {
		// Create the config folder
		try
		{
			Files.createDirectory(getConfigPath());
		}
		catch (FileAlreadyExistsException e)
		{
			// Do nothing
		}
		catch (IOException e)
		{
			Necronomicon.LOGGER.error("Failed to create config directory", e);
		}
	}

	private static Path getConfigPath() {
		Path configPath = FMLPaths.CONFIGDIR.get();
		return Paths.get(configPath.toAbsolutePath().toString(), "assets/necronomicon");
	}
}
