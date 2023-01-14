package net.guille_dlc.necronomicon.biome;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.biome.old.TestBiomes;
import net.guille_dlc.necronomicon.biome.old.TestOverworldBiomes;
import net.guille_dlc.necronomicon.entity.ModEntityTypes;
import net.guille_dlc.necronomicon.particles.ModParticles;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
//import net.minecraftforge.event.RegistryEvent;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomes {
    protected static DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(Registry.BIOME_REGISTRY, Necronomicon.MOD_ID);

    public static ResourceKey<Biome> ACADIAN_FOREST = register("acadianforest", ModBiomes::acadianforest);

    private static ResourceKey<Biome> register(String name, Supplier<Biome> biomeSupplier) {
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, Necronomicon.id(name));
        BIOME_REGISTER.register(key.location().getPath(), biomeSupplier);
        return key;
    }

    public static Biome acadianforest() {
        MobSpawnSettings spawnSettings = new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 40, 5, 10))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.ANGLE.get(), 60, 10, 20))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 20, 5, 10))
                .addMobCharge(EntityType.ZOMBIE, 1, 0.8)
                .addMobCharge(ModEntityTypes.ANGLE.get(), 1, 0.8)
                .build();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder()
                .addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.DISK_GRASS)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, TreePlacements.SPRUCE_CHECKED)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, TreePlacements.BIRCH_CHECKED)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, TreePlacements.OAK_CHECKED);

        BiomeDefaultFeatures.addMossyStoneBlock(generationSettings);
        BiomeDefaultFeatures.addForestGrass(generationSettings);
        BiomeDefaultFeatures.addExtraGold(generationSettings);

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(1)
                .downfall(3)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .skyColor(0x1b473c)
                                .waterColor(0x386b5b)
                                .waterFogColor(0x264738)
                                .grassColorOverride(0x233621)
                                .foliageColorOverride(0x2c3621)
                                .ambientParticle(new AmbientParticleSettings(ModParticles.BLOOD_PARTICLE.get(), 0.01F))
                                .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
                                .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2))
                                .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111))
                                .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY))
                                .build())
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings.build())
                .build();
    }

    /*//@SubscribeEvent 1.19???
    public static void registerBiomes()
    {
        register(TestBiomes.LOVECRAFT_COUNTRY, TestOverworldBiomes::lovecraftCountry);
    }

    public static RegistryObject<Biome> register(ResourceKey<Biome> key, Supplier<Biome> biomeSupplier)
    {
        return BIOME_REGISTER.register(key.location().getPath(), biomeSupplier);
    }*/
}

