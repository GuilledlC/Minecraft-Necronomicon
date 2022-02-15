package net.guille_dlc.necronomicon.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.BiomeProvider;
import terrablender.api.ParameterUtils;
import terrablender.worldgen.TBClimate;

import java.util.List;
import java.util.function.Consumer;
import static terrablender.api.ParameterUtils.*;

public class TestBiomeProvider extends BiomeProvider {
    public TestBiomeProvider(ResourceLocation name, int weight) {
        super(name, weight);
    }

    @Override
    public void addOverworldBiomes(Registry<Biome> registry, Consumer<Pair<TBClimate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            List<Climate.ParameterPoint> points = new ParameterPointListBuilder()
                    .temperature(Temperature.COOL)
                    .humidity(Humidity.HUMID)
                    .continentalness(Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
                    .erosion(Erosion.EROSION_0)
                    .depth(Depth.FLOOR)
                    .buildVanilla();
            points.forEach(point -> builder.replaceBiome(point, TestBiomes.LOVECRAFT_COUNTRY));
        });

        /*
        Parameter temperature
        Parameter humidity
        Parameter continentalness
        Parameter erosion
        Parameter weirdness
        Parameter depth
        float offset
         */
    }
}
