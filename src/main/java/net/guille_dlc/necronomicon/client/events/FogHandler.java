package net.guille_dlc.necronomicon.client.events;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.common.dimension.NecronomiconDimensionSpecialEffects;
import net.guille_dlc.necronomicon.common.dimension.NecronomiconDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FogType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Necronomicon.MOD_ID, value = Dist.CLIENT)
public class FogHandler {

    private static boolean SKY_CHUNK_LOADED = false;
    private static float SKY_FAR = 0.0F;
    private static float SKY_NEAR = 0.0F;

    private static boolean TERRAIN_CHUNK_LOADED = false;
    private static float TERRAIN_FAR = 0.0F;
    private static float TERRAIN_NEAR = 0.0F;
    private static float fogColor = 0F;

    @SubscribeEvent
    public static void computeFogColor(ViewportEvent.ComputeFogColor event) {
        boolean dagon = isDagon(Minecraft.getInstance().level, Minecraft.getInstance().player);
    }

    @SubscribeEvent
    public static void renderFog(ViewportEvent.RenderFog event) {
        if(event.getType().equals(FogType.NONE)  && Minecraft.getInstance().cameraEntity instanceof LocalPlayer player && player.level() instanceof ClientLevel clientLevel && clientLevel.effects() instanceof NecronomiconDimensionSpecialEffects.DagonEffects) {
            if(event.getMode().equals(FogRenderer.FogMode.FOG_SKY)) {
                if(SKY_CHUNK_LOADED) {
                    event.setCanceled(true);
                    boolean dagon = isDagon(clientLevel, player);

                    float far = dagon ? event.getFarPlaneDistance() * 0.5F : event.getFarPlaneDistance();
                    float near = dagon ? 0.0F : event.getNearPlaneDistance();

                    SKY_FAR = Mth.lerp(0.001F, SKY_FAR, far);
                    SKY_NEAR = Mth.lerp(0.001F, SKY_NEAR, near);

                    event.setFarPlaneDistance(SKY_FAR);
                    event.setNearPlaneDistance(SKY_NEAR);
                } else if(clientLevel.isLoaded(player.blockPosition())) {
                    SKY_CHUNK_LOADED = true;
                    SKY_FAR = isDagon(clientLevel, player) ? event.getFarPlaneDistance() * 0.5F : event.getFarPlaneDistance();
                    SKY_NEAR = isDagon(clientLevel, player) ? 0.0F : event.getNearPlaneDistance();
                }
            } else {
                if(TERRAIN_CHUNK_LOADED) {
                    event.setCanceled(true);

                    boolean dagon = isDagon(clientLevel, player);

                    float far = dagon ? event.getFarPlaneDistance() * 0.5F : event.getFarPlaneDistance();
                    float near = dagon ? far * 0.75F : event.getNearPlaneDistance();

                    TERRAIN_FAR = Mth.lerp(0.003F, TERRAIN_FAR, far);
                    TERRAIN_NEAR = Mth.lerp(0.003F * (TERRAIN_NEAR < near ? 0.5F : 2.0F), TERRAIN_NEAR, near);

                    event.setFarPlaneDistance(TERRAIN_FAR);
                    event.setNearPlaneDistance(TERRAIN_NEAR);
                } else if (SKY_CHUNK_LOADED || clientLevel.isLoaded(player.blockPosition())) {
                    TERRAIN_CHUNK_LOADED = true;
                    TERRAIN_FAR = isDagon(clientLevel, player) ? event.getFarPlaneDistance() * 0.5F : event.getFarPlaneDistance();
                    TERRAIN_NEAR = isDagon(clientLevel, player) ? TERRAIN_FAR * 0.75F : event.getNearPlaneDistance();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onUnload(LevelEvent.Unload event) { //As supernatural as the fog is, it shouldn't follow the player between worlds
        SKY_CHUNK_LOADED = false;
        TERRAIN_CHUNK_LOADED = false;
    }

    private static boolean isDagon(@Nullable ClientLevel level, @Nullable LocalPlayer player) {
        return level != null && player != null && level.dimension().equals(NecronomiconDimensions.DAGON);
    }
}
