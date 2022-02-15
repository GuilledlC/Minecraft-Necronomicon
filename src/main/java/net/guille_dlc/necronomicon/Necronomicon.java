package net.guille_dlc.necronomicon;

import net.guille_dlc.necronomicon.biome.TestBiomeProvider;
import net.guille_dlc.necronomicon.entity.ModEntityTypes;
import net.guille_dlc.necronomicon.entity.custom.AngleEntity;
import net.guille_dlc.necronomicon.item.ModItems;
import net.guille_dlc.necronomicon.item.NecronomiconBookItem;
import net.guille_dlc.necronomicon.particles.ModParticles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.BiomeProvider;
import terrablender.api.BiomeProviders;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Necronomicon.MOD_ID)
public class Necronomicon
{
    public static final String MOD_ID = "necronomicon";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Necronomicon() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModEntityTypes.register(eventBus);
        BiomeProviders.register(new TestBiomeProvider(new ResourceLocation(MOD_ID, "biome_provider"), 2));
        ModParticles.PARTICLE_TYPES.register(eventBus);

        eventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
