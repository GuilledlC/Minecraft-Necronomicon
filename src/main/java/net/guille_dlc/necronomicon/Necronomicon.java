package net.guille_dlc.necronomicon;

import net.guille_dlc.necronomicon.biome.TestBiomeProvider;
import net.guille_dlc.necronomicon.entity.ModEntityTypes;
import net.guille_dlc.necronomicon.entity.custom.AngleEntity;
import net.guille_dlc.necronomicon.item.ModItems;
import net.guille_dlc.necronomicon.item.NecronomiconBookItem;
import net.guille_dlc.necronomicon.particles.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.BiomeProvider;
import terrablender.api.BiomeProviders;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.concurrent.TimeUnit;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Necronomicon.MOD_ID)
public class Necronomicon
{
    public static final String MOD_ID = "necronomicon";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Necronomicon() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModEntityTypes.register(eventBus);
        BiomeProviders.register(new TestBiomeProvider(new ResourceLocation(MOD_ID, "biome_provider"), 2));
        ModParticles.PARTICLE_TYPES.register(eventBus);

        eventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::livingHurt);
        MinecraftForge.EVENT_BUS.addListener(this::playerTick);

    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void livingHurt(final LivingHurtEvent event) {
        if (event.getSource() == DamageSource.OUT_OF_WORLD) //Makes sure you get killed with "/kill" and The Void
            return;
        Entity entity = event.getEntity();
        if(entity.getLevel().isClientSide())
            return;
        if(event.getAmount() < event.getEntityLiving().getHealth())
            return;

        if(event.getEntityLiving() instanceof ServerPlayer player) {
            for(InteractionHand hand : InteractionHand.values()) {
                ItemStack itemStack = player.getItemInHand(hand);
                if(itemStack.getItem() instanceof NecronomiconBookItem item) {
                    if(item.coolDown == 0) {
                        item.rapture(event.getEntity().getLevel(), player, hand);

                        if(event.getAmount() > player.getMaxHealth())
                            event.setAmount(player.getHealth() - 1);
                        else
                            player.heal(event.getAmount());

                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 150, 0));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 150, 0));
                        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0));
                        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                    }
                }
            }
        }
    }

    private void playerTick(final TickEvent.PlayerTickEvent event)
    {
        if(event.player instanceof ServerPlayer player) {
            for(InteractionHand hand : InteractionHand.values()) {
                ItemStack itemStack = player.getItemInHand(hand);
                if(itemStack.getItem() instanceof NecronomiconBookItem item) {
                    if(item.coolDown != 0) {
                        if(item.coolDown == 1) {
                            ResourceKey<Level> resourcekey = player.getLevel().dimension() == Level.END ? Level.OVERWORLD : Level.END;
                            ServerLevel serverlevel = player.getLevel().getServer().getLevel(resourcekey);
                            if (serverlevel == null) {

                               return;
                            }

                            player.changeDimension(serverlevel);
                        }
                        item.coolDown--;
                    }
                }
            }
        }
    }
}
