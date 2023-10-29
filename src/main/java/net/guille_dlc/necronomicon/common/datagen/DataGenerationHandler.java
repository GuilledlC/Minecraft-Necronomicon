package net.guille_dlc.necronomicon.common.datagen;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.init.ModBiomes;
import net.guille_dlc.necronomicon.init.ModDimensions;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Necronomicon.MOD_ID)
public class DataGenerationHandler {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, ModBiomes::bootstrapBiomes)
            .add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapDimensionTypes)
            .add(Registries.LEVEL_STEM, ModDimensions::bootstrapStems)
            ;

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), BUILDER, Set.of(Necronomicon.MOD_ID)));
    }
}
