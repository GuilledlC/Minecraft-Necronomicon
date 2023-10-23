package net.guille_dlc.necronomicon.common.events;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.api.entity.NecronomiconEntities;
import net.guille_dlc.necronomicon.common.entity.custom.AngleEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Necronomicon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(NecronomiconEntities.ANGLE.get(), AngleEntity.createAttributes().build());
    }
}
