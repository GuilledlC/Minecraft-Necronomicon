package net.guille_dlc.necronomicon.common.dimension;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

//https://minecraft.fandom.com/wiki/Effect_(dimension)
public class NecronomiconDimensionSpecialEffects {
    @OnlyIn(Dist.CLIENT)
    public static class DagonEffects extends DimensionSpecialEffects {
        public DagonEffects() {
            super(Float.NaN, //Cloud level
                    true, //Has ground
                    SkyType.NONE, //Sky type
                    false, //Force bright lightmap
                    false //Constant ambient light
            );
        }

        @Override
        public Vec3 getBrightnessDependentFogColor(Vec3 biomeFogColor, float daylight) {
            return biomeFogColor;
        }

        @Override
        public boolean isFoggyAt(int x, int y) {
            return y > 100;
        }

        @Override
        public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
            return super.renderSky(level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
        }
    }
}
