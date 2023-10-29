package net.guille_dlc.necronomicon.api.dimension;

import net.guille_dlc.necronomicon.Necronomicon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class NecronomiconDimensions {

    public static ResourceKey<Level> LOVECRAFT_COUNTRY = registerDimension("lovecraft_country");
    public static ResourceKey<DimensionType> LOVECRAFT_COUNTRY_TYPE = registerDimensionType("lovecraft_country");
    public static ResourceKey<LevelStem> LOVECRAFT_COUNTRY_STEM = registerLevelStem("lovecraft_country");

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
