package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.Necronomicon;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModConfig {

    public static class GenerationConfig {

        public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        public static final ForgeConfigSpec SPEC;
        public static final ForgeConfigSpec.IntValue overworldRegionWeight;
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
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, GenerationConfig.SPEC, "biomesoplenty/generation.toml");
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
        return Paths.get(configPath.toAbsolutePath().toString(), "necronomicon");
    }
}
