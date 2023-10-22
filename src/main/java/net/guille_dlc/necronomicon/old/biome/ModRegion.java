package net.guille_dlc.necronomicon.old.biome;

import com.mojang.datafixers.util.Pair;
import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.api.biome.NecronomiconBiomes;
import net.guille_dlc.necronomicon.init.ModBiomes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class ModRegion /*extends Region*/ {

    /*public ModRegion() {
        super(Necronomicon.id("overworld"), RegionType.OVERWORLD, 15);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addBiome(mapper,
                Climate.parameters(1, 5, 5, 0, 5, 0.2F, 0),
                NecronomiconBiomes.ACADIAN_FOREST);
    }*/
}
