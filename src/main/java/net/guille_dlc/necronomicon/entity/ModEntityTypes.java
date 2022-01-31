package net.guille_dlc.necronomicon.entity;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.entity.custom.AngleEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    private ModEntityTypes() {}

    public static DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Necronomicon.MOD_ID);

    public static final RegistryObject<EntityType<AngleEntity>> ANGLE =
            ENTITIES.register("angle",
            () -> EntityType.Builder.of(AngleEntity::new,
                    MobCategory.MONSTER).sized(0.6F, 1.95F)
                    .build(new ResourceLocation(Necronomicon.MOD_ID, "angle").toString()));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
