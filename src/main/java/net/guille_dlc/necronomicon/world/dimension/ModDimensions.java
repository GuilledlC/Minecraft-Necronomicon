package net.guille_dlc.necronomicon.world.dimension;

import net.guille_dlc.necronomicon.Necronomicon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import java.awt.*;

public class ModDimensions {
    public static final ResourceKey<Level> LOVECRAFTCOUNTRY_KEY =
            ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(Necronomicon.MOD_ID, "lovecraftcountry"));

    public static final ResourceKey<DimensionType> LOVECRAFTCOUNTRY_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, LOVECRAFTCOUNTRY_KEY.location());

    public static void register() {
        System.out.println("Registering Mod Dimensions for " + Necronomicon.MOD_ID); //Not sure why we need this
    }
}
