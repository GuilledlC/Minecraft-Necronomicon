package net.guille_dlc.necronomicon.common.worldgen;

import com.mojang.datafixers.util.Pair;
import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.common.biome.LovecraftBiomeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class OverworldRegion extends Region {

    public static ResourceLocation LOCATION = new ResourceLocation(Necronomicon.MOD_ID, "overworld");

    public OverworldRegion(int weight) {
        super(LOCATION, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        (new LovecraftBiomeBuilder()).addBiomes(registry, mapper);
    }
}
