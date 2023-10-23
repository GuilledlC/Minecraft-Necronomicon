package net.guille_dlc.necronomicon.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.common.entity.custom.AngleEntity;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

//public class AngleRenderer<T extends AngleEntity> extends MobRenderer<T, AngleModel<T>> {
//@OnlyIn(Dist.CLIENT)
public class AngleRenderer extends IllagerRenderer<AngleEntity> {
    private static final ResourceLocation TEXTURE = Necronomicon.id("textures/entity/angle.png");

    public AngleRenderer(EntityRendererProvider.Context context) {
        //super(context, new AngleModel<>(context.bakeLayer(AngleModel.LAYER_LOCATION)), 0.5F);
        super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.VINDICATOR)), 0.5F);
        this.addLayer(new ItemInHandLayer<AngleEntity, IllagerModel<AngleEntity>>(this, context.getItemInHandRenderer()) {
            public void render(PoseStack p_116352_, MultiBufferSource p_116353_, int p_116354_, AngleEntity p_116355_, float p_116356_, float p_116357_, float p_116358_, float p_116359_, float p_116360_, float p_116361_) {
                if (p_116355_.isAggressive()) {
                    super.render(p_116352_, p_116353_, p_116354_, p_116355_, p_116356_, p_116357_, p_116358_, p_116359_, p_116360_, p_116361_);
                }

            }
        });
    }

    public ResourceLocation getTextureLocation(AngleEntity pEntity) {
        return TEXTURE;
    }
}
