package net.guille_dlc.necronomicon.biome.old;

import net.guille_dlc.necronomicon.Necronomicon;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class TestBiomes {
    public static final ResourceKey<Biome> LOVECRAFT_COUNTRY = register("lovecraft_country");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, Necronomicon.id(name));
    }
}
