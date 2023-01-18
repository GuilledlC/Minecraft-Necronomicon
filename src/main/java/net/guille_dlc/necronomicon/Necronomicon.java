package net.guille_dlc.necronomicon;

/*import net.guille_dlc.necronomicon.biome.TestBiomeProvider;*/
import net.guille_dlc.necronomicon.biome.ModBiomes;
import net.guille_dlc.necronomicon.biome.ModRegion;
import net.guille_dlc.necronomicon.entity.ModEntityTypes;
import net.guille_dlc.necronomicon.entity.custom.AngleEntity;
import net.guille_dlc.necronomicon.events.ClientModEvents;
import net.guille_dlc.necronomicon.item.ModItems;
import net.guille_dlc.necronomicon.item.NecronomiconBookItem;
import net.guille_dlc.necronomicon.particles.ModParticles;
import net.guille_dlc.necronomicon.util.BetterBrewingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/*import terrablender.api.BiomeProvider;
import terrablender.api.BiomeProviders;*/
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import terrablender.api.Region;
import terrablender.api.Regions;


import java.util.concurrent.TimeUnit;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Necronomicon.MOD_ID)
public class Necronomicon
{
    public static final String MOD_ID = "necronomicon";

    public static ResourceLocation id (String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Necronomicon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        ModItems.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        /*BiomeProviders.register(new TestBiomeProvider(new ResourceLocation(MOD_ID, "biome_provider"), 2));*/
        ModParticles.register(modEventBus);

        ModBiomes.BIOME_REGISTER.register(modEventBus);
        ModBiomes.registerBiomes();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::livingHurt);
        MinecraftForge.EVENT_BUS.addListener(this::playerTick);
        MinecraftForge.EVENT_BUS.addListener(this::playerRightClickItem);

    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getName()); //Blocks.DIRT.getRegistryName());


        event.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD, Items.WHEAT, ModItems.BEER.get()));
            Regions.register(new ModRegion());
        });
    }

    private void livingHurt(final LivingHurtEvent event) {
        if (event.getSource() == DamageSource.OUT_OF_WORLD) //Makes sure you get killed with "/kill" and The Void
            return;
        Entity entity = event.getEntity();
        if(entity.getLevel().isClientSide())
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
                        item.rapture(event.getEntity().getLevel(), player, hand);

                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 150, 0));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 150, 0));
                        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0));
                        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                    }
                }
            }
        }
    }

    private void playerTick(final PlayerTickEvent event) {
        if(event.player instanceof ServerPlayer player) {
            for(InteractionHand hand : InteractionHand.values()) {
                ItemStack itemStack = player.getItemInHand(hand);
                if(itemStack.getItem() instanceof NecronomiconBookItem item) {
                    if(item.coolDown != 0 && item.activated) {
                        if(item.coolDown == 1) {
                            ResourceKey<Level> resourcekey = player.getLevel().dimension() == Level.END ? Level.OVERWORLD : Level.END;
                            ServerLevel serverlevel = player.getLevel().getServer().getLevel(resourcekey);
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

    private void playerRightClickItem(final PlayerInteractEvent.RightClickItem event) {
        if(event.getEntity() instanceof ServerPlayer player) {
            if(event.getItemStack().getItem() instanceof NecronomiconBookItem item) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 300, 0));
            }
        }
    }

}
