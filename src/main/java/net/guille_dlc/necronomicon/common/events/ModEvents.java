package net.guille_dlc.necronomicon.common.events;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.common.entity.NecronomiconEntities;
import net.guille_dlc.necronomicon.client.particle.NecronomiconParticles;
import net.guille_dlc.necronomicon.client.particle.BloodParticle;
import net.guille_dlc.necronomicon.common.entity.custom.AngleEntity;
import net.guille_dlc.necronomicon.common.item.NecronomiconBookItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Necronomicon.MOD_ID)
public class ModEvents {

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(NecronomiconEntities.ANGLE.get(), AngleEntity.createAttributes().build());
	}

	@SubscribeEvent
	public static void registerParticleTypes(RegisterParticleProvidersEvent event) {
		Minecraft.getInstance().particleEngine.register(NecronomiconParticles.BLOOD_PARTICLE.get(), BloodParticle.Provider::new);
	}

	public static void playerRightClickItem(final PlayerInteractEvent.RightClickItem event) {
		if(event.getEntity() instanceof ServerPlayer player) {
			if(event.getItemStack().getItem() instanceof NecronomiconBookItem item) {
				player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 300, 0));
			}
		}
	}

	public static void livingHurt(final LivingHurtEvent event) {
		if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) //Makes sure you get killed with "/kill" and The Void
			return;
		Entity entity = event.getEntity();
		if(entity.level().isClientSide())
			return;
		if(event.getAmount() < event.getEntity().getHealth())
			return;

		if(event.getEntity() instanceof ServerPlayer player) {
			for(InteractionHand hand : InteractionHand.values()) {
				ItemStack itemStack = player.getItemInHand(hand);
				if(itemStack.getItem() instanceof NecronomiconBookItem item) {

					if(event.getAmount() > player.getMaxHealth())
						event.setAmount(player.getHealth());
					player.heal(event.getAmount());

					if(item.coolDown == 0) {
						item.rapture(event.getEntity().level(), player, hand);

						player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 150, 0));
						player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 150, 0));
						player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0));
						player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
					}
				}
			}
		}
	}

	public static void playerTick(final TickEvent.PlayerTickEvent event) {
		if(event.player instanceof ServerPlayer player) {
			for(InteractionHand hand : InteractionHand.values()) {
				ItemStack itemStack = player.getItemInHand(hand);
				if(itemStack.getItem() instanceof NecronomiconBookItem item) {
					if(item.coolDown != 0 && item.activated) {
						if(item.coolDown == 1) {
							ResourceKey<Level> resourcekey = player.level().dimension() == Level.END ? Level.OVERWORLD : Level.END;
							ServerLevel serverlevel = player.level().getServer().getLevel(resourcekey);
							if (serverlevel == null) {
								return;
							}
							item.activated = false;
							player.changeDimension(serverlevel);
						}
						item.coolDown--;
					}
					break;
				}
			}
		}
	}

}
