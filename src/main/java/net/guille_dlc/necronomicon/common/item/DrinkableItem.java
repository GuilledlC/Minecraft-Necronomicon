package net.guille_dlc.necronomicon.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/**
 * Heavily inspired by https://github.com/vectorwing/FarmersDelight */
public class DrinkableItem extends Item {
	public DrinkableItem(Properties itemProperties) {
		super(itemProperties);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public SoundEvent getDrinkingSound() {
		return SoundEvents.GENERIC_DRINK;
	}

	@Override
	public SoundEvent getEatingSound() {
		return SoundEvents.GENERIC_DRINK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		return ItemUtils.startUsingInstantly(worldIn, playerIn, handIn);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
		if(!level.isClientSide)
			this.affectConsumer(stack, level, consumer);

		//ItemStack containerStack = stack.getContainerItem(); 1.18.2
		ItemStack containerStack = stack.copy(); //1.19.2

		if(stack.isEdible())
			super.finishUsingItem(stack, level, consumer);
		else {
			Player player = consumer instanceof Player ? (Player) consumer : null;
			if(player instanceof ServerPlayer serverPlayer)
				CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);

			if(player != null) {
				player.awardStat(Stats.ITEM_USED.get(this));
				if(!player.getAbilities().instabuild)
					stack.shrink(1);
			}
		}

		if(stack.isEmpty())
			return containerStack;
		else {
			if(consumer instanceof Player player && !player.getAbilities().instabuild) {
				if(!player.getInventory().add(containerStack))
					player.drop(containerStack, false);
			}
			return stack;
		}
	}

	/**
	 * Override this to apply changes to the consumer (e.g. curing effects).
	 */
	public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
	}
}
