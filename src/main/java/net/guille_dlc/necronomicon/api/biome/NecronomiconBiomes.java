package net.guille_dlc.necronomicon.api.biome;

import net.guille_dlc.necronomicon.Necronomicon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class NecronomiconBiomes {

    private static List<ResourceKey<Biome>> allBiomes = new ArrayList<>();

    public static ResourceKey<Biome> ACADIAN_FOREST = register("acadian_forest");
    public static ResourceKey<Biome> MUDDY_WASTELAND = register("muddy_wasteland");

    private static ResourceKey<Biome> register(String name) {
        ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, new ResourceLocation(Necronomicon.MOD_ID, name));
        allBiomes.add(key);
        return key;
    }
}
