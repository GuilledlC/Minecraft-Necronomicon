package net.guille_dlc.necronomicon.client.events;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.client.entity.render.AngleRenderer;
import net.guille_dlc.necronomicon.common.dimension.NecronomiconDimensionSpecialEffects;
import net.guille_dlc.necronomicon.common.dimension.NecronomiconDimensions;
import net.guille_dlc.necronomicon.common.entity.NecronomiconEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

@Mod.EventBusSubscriber(modid = Necronomicon.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {

	private ClientModEvents() {}

	public static void BookScreen(BookViewScreen screen) {
			Minecraft.getInstance().setScreen(screen);
	}

	@SubscribeEvent
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		//event.registerLayerDefinition(AngleModel.LAYER_LOCATION, AngleModel::createBodyLayer);
	}

	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(NecronomiconEntities.ANGLE.get(), AngleRenderer::new);
	}

	@SubscribeEvent
	public static void RegisterDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
		event.register(NecronomiconDimensions.DAGON_EFFECTS, new NecronomiconDimensionSpecialEffects.DagonEffects());
	}
}
