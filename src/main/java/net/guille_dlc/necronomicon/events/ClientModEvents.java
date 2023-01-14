package net.guille_dlc.necronomicon.events;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.entity.ModEntityTypes;
import net.guille_dlc.necronomicon.entity.model.AngleModel;
import net.guille_dlc.necronomicon.entity.render.AngleRenderer;
import net.guille_dlc.necronomicon.item.ModCreativeModeTab;
import net.guille_dlc.necronomicon.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
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
        event.registerEntityRenderer(ModEntityTypes.ANGLE.get(), AngleRenderer::new);
    }
}
