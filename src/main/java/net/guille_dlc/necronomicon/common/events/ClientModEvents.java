package net.guille_dlc.necronomicon.common.events;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.api.entity.NecronomiconEntities;
import net.guille_dlc.necronomicon.client.entity.render.AngleRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Necronomicon.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {

    private ClientModEvents() {}

    public static DistExecutor.SafeRunnable BookScreen(BookViewScreen screen) {
            return () -> Minecraft.getInstance().setScreen(screen);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //event.registerLayerDefinition(AngleModel.LAYER_LOCATION, AngleModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NecronomiconEntities.ANGLE.get(), AngleRenderer::new);
    }
}
