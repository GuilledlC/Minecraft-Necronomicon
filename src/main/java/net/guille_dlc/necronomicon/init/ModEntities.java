package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.common.entity.NecronomiconEntities;
import net.guille_dlc.necronomicon.common.entity.custom.AngleEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import java.util.function.Supplier;

public class ModEntities {

    public static void setup() {
        registerEntities();
    }

    private static void registerEntities() {
        NecronomiconEntities.ANGLE = registerEntity(() ->
                        EntityType.Builder.of(
                                        AngleEntity::new,
                                        MobCategory.MONSTER)
                                .sized(0.6F, 1.95F)
                                .build(Necronomicon.id("angle").toString()),
                "angle");
    }

    private static <I extends Entity> Supplier<EntityType<I>> registerEntity(Supplier<EntityType<I>> typeSupplier, String name) {
        return Necronomicon.ENTITY_TYPE_REGISTER.register(name, typeSupplier);
    }
}
