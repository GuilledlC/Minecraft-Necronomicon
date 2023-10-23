package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.api.biome.NecronomiconBiomes;
import net.guille_dlc.necronomicon.common.biome.OverworldBiomes;
import net.guille_dlc.necronomicon.common.worldgen.OverworldRegion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.*;
//import net.minecraftforge.event.RegistryEvent;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.minecraft.resources.ResourceKey;
import terrablender.api.Regions;

public class ModBiomes {

    public static void setupBiomes() {
        //Register the regions
        Regions.register(new OverworldRegion(ModConfig.GenerationConfig.overworldRegionWeight.get()));

        //Register the surface rules
        /**To do*/
    }

    public static void bootstrapBiomes(BootstapContext<Biome> context) {
        HolderGetter<ConfiguredWorldCarver<?>> carverGetter = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> placedFeatureGetter = context.lookup(Registries.PLACED_FEATURE);

        register(context, NecronomiconBiomes.ACADIAN_FOREST, OverworldBiomes.acadianforest(placedFeatureGetter, carverGetter));
    }

    public static void register(BootstapContext<Biome> context, ResourceKey<Biome> key, Biome biome) {
        context.register(key, biome);
    }
}

