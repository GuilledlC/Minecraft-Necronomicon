package net.guille_dlc.necronomicon.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Iterator;
import java.util.List;

public class BeerItem extends DrinkableItem {
	public BeerItem(Properties properties) {
		super(properties);
	}

	@Override
	public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
		List<MobEffectInstance> effects = consumer.getActiveEffects().stream().toList();
		int slowDur = 500, slowAmp = 1, confDur = 500, confAmp = 1, absDur = 300, absAmp = 0;
		Iterator<MobEffectInstance> effectIterator = effects.iterator();

		//Both effects accumulate until a max of 2 and a half minutes
		while(effectIterator.hasNext()) {
			MobEffectInstance effect = effectIterator.next();
			if(effect.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
				slowDur = Math.min(slowDur + effect.getDuration(), 3000);
				slowAmp = effect.getAmplifier();
			} else if(effect.getEffect() == MobEffects.CONFUSION) {
				confDur = Math.min(confDur + effect.getDuration(), 3000);
				confAmp = effect.getAmplifier();
			} else if(effect.getEffect() == MobEffects.ABSORPTION) {
				absDur = Math.min(absDur + effect.getDuration(), 3000);
				absAmp = effect.getAmplifier();
			}
		}

		consumer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, slowDur, slowAmp));
		consumer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, confDur, confAmp));
		consumer.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, absDur, absAmp));
	}
}
