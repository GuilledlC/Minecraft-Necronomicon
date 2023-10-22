package net.guille_dlc.necronomicon.old.dimension;

import net.guille_dlc.necronomicon.Necronomicon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModDimensions {
    public static ResourceKey<Level> LOVECRAFTCOUNTRY = ResourceKey.create(Registries.DIMENSION,
            Necronomicon.id("lovecraftcountry"));
}
