package net.guille_dlc.necronomicon.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BeerItem extends DrinkableItem {
    public BeerItem(Properties properties) {
        super(properties);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 500, 1));
        consumer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 500, 1));
        consumer.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 0));
    }
}
