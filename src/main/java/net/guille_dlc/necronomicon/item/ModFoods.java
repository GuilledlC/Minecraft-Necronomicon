package net.guille_dlc.necronomicon.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties BATTERED_COD = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).build();
    public static final FoodProperties FISH_N_CHIPS = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.8F).build();
    public static final FoodProperties BEER = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.5F).build();
    public static final FoodProperties BEATING_HEART = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.5F).build();
}