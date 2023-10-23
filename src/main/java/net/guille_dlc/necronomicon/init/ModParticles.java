package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.api.particle.NecronomiconParticles;
import net.guille_dlc.necronomicon.client.particle.BloodParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModParticles {

    public static void setup() {
        registerParticles();
    }

    private static void registerParticles() {
        NecronomiconParticles.BLOOD_PARTICLE = registerParticle(() ->
                new SimpleParticleType(true),
                "blood_particle");
    }

    private static <I extends ParticleType> RegistryObject<I> registerParticle(Supplier<I> typeSupplier, String name) {
        return Necronomicon.PARTICLES_REGISTER.register(name, typeSupplier);
    }

}
