package net.guille_dlc.necronomicon;

import net.guille_dlc.necronomicon.common.item.NecronomiconItems;
import net.guille_dlc.necronomicon.common.events.ModEvents;
import net.guille_dlc.necronomicon.common.util.BetterBrewingRecipe;
import net.guille_dlc.necronomicon.init.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Necronomicon.MOD_ID)
public class Necronomicon
{
    public static final String MOD_ID = "necronomicon";

    public static ResourceLocation id (String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static final DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(Registries.BIOME, MOD_ID);
    public static final DeferredRegister<DimensionType> DIMENSION_TYPE_REGISTER = DeferredRegister.create(Registries.DIMENSION_TYPE, MOD_ID);
    public static final DeferredRegister<Level> DIMENSION_REGISTER = DeferredRegister.create(Registries.DIMENSION, MOD_ID);
    public static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(Registries.BLOCK, MOD_ID);
    public static final DeferredRegister<WorldCarver<?>> CARVER_REGISTER = DeferredRegister.create(Registries.CARVER, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredRegister<ConfiguredWorldCarver<?>> CONFIGURED_CARVER_REGISTER = DeferredRegister.create(Registries.CONFIGURED_CARVER, MOD_ID);
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE_REGISTER = DeferredRegister.create(Registries.CONFIGURED_FEATURE, MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURE_REGISTER = DeferredRegister.create(Registries.FEATURE, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, MOD_ID);
    public static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(Registries.ITEM, MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES_REGISTER = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENT_REGISTER = DeferredRegister.create(Registries.SOUND_EVENT, MOD_ID);
    public static final DeferredRegister<DamageType> DAMAGE_TYPE_REGISTER = DeferredRegister.create(Registries.DAMAGE_TYPE, MOD_ID);
    public static final DeferredRegister<Potion> POTION_REGISTER = DeferredRegister.create(Registries.POTION, MOD_ID);

    public static Necronomicon instance;
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public Necronomicon() {

        instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        BIOME_REGISTER.register(modEventBus);
        DIMENSION_TYPE_REGISTER.register(modEventBus);
        DIMENSION_REGISTER.register(modEventBus);
        BLOCK_REGISTER.register(modEventBus);
        CARVER_REGISTER.register(modEventBus);
        CREATIVE_TAB_REGISTER.register(modEventBus);
        CONFIGURED_CARVER_REGISTER.register(modEventBus);
        CONFIGURED_FEATURE_REGISTER.register(modEventBus);
        FEATURE_REGISTER.register(modEventBus);
        ENTITY_TYPE_REGISTER.register(modEventBus);
        ITEM_REGISTER.register(modEventBus);
        PARTICLES_REGISTER.register(modEventBus);
        SOUND_EVENT_REGISTER.register(modEventBus);
        DAMAGE_TYPE_REGISTER.register(modEventBus);
        POTION_REGISTER.register(modEventBus);

        ModConfig.setup();
        ModEntities.setup();
        ModItems.setup();
        ModBlocks.setup();
        ModParticles.setup();

        ModCreativeModeTab.setup();

        // Register ourselves for server and other game events we are interested in
        NeoForge.EVENT_BUS.addListener(ModEvents::livingHurt);
        NeoForge.EVENT_BUS.addListener(ModEvents::playerTick);
        NeoForge.EVENT_BUS.addListener(ModEvents::playerRightClickItem);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModBiomes.setupBiomes();
            BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.WATER, Items.WHEAT, NecronomiconItems.BEER.get()));
            /**Old: Regions.register(new ModRegion());*/
        });
    }

}
