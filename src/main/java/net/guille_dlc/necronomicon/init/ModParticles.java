package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.api.particle.NecronomiconParticles;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.neoforge.registries.RegistryObject;

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
