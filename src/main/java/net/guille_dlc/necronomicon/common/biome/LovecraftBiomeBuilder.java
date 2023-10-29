package net.guille_dlc.necronomicon.common.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.guille_dlc.necronomicon.api.biome.NecronomiconBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;

import java.util.function.Consumer;

public class LovecraftBiomeBuilder {

    /**Copied from BOP
     * Maybe use this instead: https://minecraft.fandom.com/wiki/Biome
     * */

    protected static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);

    /* Terminology:
        Continentalness: Low to generate near coasts, far to generate away from coasts
        Erosion: Low is hilly terrain, high is flat terrain
     */

    protected static final Climate.Parameter[] temperatures = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.45F),
            Climate.Parameter.span(-0.45F, -0.15F),
            Climate.Parameter.span(-0.15F, 0.2F),
            Climate.Parameter.span(0.2F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };

    protected static final Climate.Parameter[] humidities = new Climate.Parameter[]{
            Climate.Parameter.span(0.0F, 0.0F),
            Climate.Parameter.span(-0.35F, -0.1F),
            Climate.Parameter.span(-0.1F, 0.1F),
            Climate.Parameter.span(0.1F, 0.3F),
            Climate.Parameter.span(0.3F, 1.0F)
    };

    protected static final Climate.Parameter[] depth = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.78F),
            Climate.Parameter.span(-0.78F, -0.375F),
            Climate.Parameter.span(-0.375F, -0.2225F),
            Climate.Parameter.span(-0.2225F, 0.05F),
            Climate.Parameter.span(0.05F, 0.45F),
            Climate.Parameter.span(0.45F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };

    protected static final Climate.Parameter[] erosions = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.78F),
            Climate.Parameter.span(-0.78F, -0.375F),
            Climate.Parameter.span(-0.375F, -0.2225F),
            Climate.Parameter.span(-0.2225F, 0.05F),
            Climate.Parameter.span(0.05F, 0.45F),
            Climate.Parameter.span(0.45F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };

    protected static final Climate.Parameter FROZEN_RANGE = temperatures[0];
    protected static final Climate.Parameter UNFROZEN_RANGE = Climate.Parameter.span(temperatures[1], temperatures[4]);
    protected static final Climate.Parameter mushroomFieldsContinentalness = Climate.Parameter.span(-1.2F, -1.05F);
    protected static final Climate.Parameter deepOceanContinentalness = Climate.Parameter.span(-1.05F, -0.455F);
    protected static final Climate.Parameter oceanContinentalness = Climate.Parameter.span(-0.455F, -0.19F);
    protected static final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
    protected static final Climate.Parameter inlandContinentalness = Climate.Parameter.span(-0.11F, 0.55F);
    protected static final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
    protected static final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
    protected static final Climate.Parameter farInlandContinentalness = Climate.Parameter.span(0.3F, 1.0F);

    public void addBiomes(Registry<Biome> biomeRegistry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {

        this.addSurfaceBiome(mapper, temperatures[1], humidities[1], midInlandContinentalness, erosions[3], Climate.Parameter.span(0.2F, 0.7F), 0, NecronomiconBiomes.ACADIAN_FOREST);
    }

    protected void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome)
    {
        mapper.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(0.0F), weirdness, offset), biome));
        mapper.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(1.0F), weirdness, offset), biome));
    }

    public static BiomeSource buildBiomeSource(HolderGetter<Biome> biomes) {
        return MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(ImmutableList.of(
                Pair.of(Climate.parameters(temperatures[1], humidities[1], midInlandContinentalness, erosions[3], Climate.Parameter.span(0.2F, 0.7F), Climate.Parameter.point(0), 0.0F), biomes.getOrThrow(NecronomiconBiomes.ACADIAN_FOREST))
        )));
    }
}
