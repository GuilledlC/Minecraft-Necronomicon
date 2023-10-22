package net.guille_dlc.necronomicon;

import net.guille_dlc.necronomicon.init.ModBiomes;
import net.guille_dlc.necronomicon.init.ModConfig;
import net.guille_dlc.necronomicon.old.biome.ModRegion;
import net.guille_dlc.necronomicon.old.entity.ModEntityTypes;
import net.guille_dlc.necronomicon.old.item.ModCreativeModeTab;
import net.guille_dlc.necronomicon.old.item.ModItems;
import net.guille_dlc.necronomicon.old.item.NecronomiconBookItem;
import net.guille_dlc.necronomicon.old.particles.ModParticles;
import net.guille_dlc.necronomicon.old.util.BetterBrewingRecipe;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.Regions;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Necronomicon.MOD_ID)
public class Necronomicon
{
    public static final String MOD_ID = "necronomicon";

    public static ResourceLocation id (String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static final DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(Registries.BIOME, MOD_ID);
    public static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(Registries.BLOCK, MOD_ID);
    public static final DeferredRegister<WorldCarver<?>> CARVER_REGISTER = DeferredRegister.create(Registries.CARVER, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredRegister<ConfiguredWorldCarver<?>> CONFIGURED_CARVER_REGISTER = DeferredRegister.create(Registries.CONFIGURED_CARVER, MOD_ID);
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE_REGISTER = DeferredRegister.create(Registries.CONFIGURED_FEATURE, MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURE_REGISTER = DeferredRegister.create(Registries.FEATURE, MOD_ID);
    public static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(Registries.ITEM, MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES_REGISTER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENT_REGISTER = DeferredRegister.create(Registries.SOUND_EVENT, MOD_ID);
    public static final DeferredRegister<DamageType> DAMAGE_TYPE_REGISTER = DeferredRegister.create(Registries.DAMAGE_TYPE, MOD_ID);

    public static Necronomicon instance;
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public Necronomicon() {

        instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        BIOME_REGISTER.register(modEventBus);
        BLOCK_REGISTER.register(modEventBus);
        CARVER_REGISTER.register(modEventBus);
        CREATIVE_TAB_REGISTER.register(modEventBus);
        CONFIGURED_CARVER_REGISTER.register(modEventBus);
        CONFIGURED_FEATURE_REGISTER.register(modEventBus);
        FEATURE_REGISTER.register(modEventBus);
        ITEM_REGISTER.register(modEventBus);
        PARTICLES_REGISTER.register(modEventBus);
        SOUND_EVENT_REGISTER.register(modEventBus);
        DAMAGE_TYPE_REGISTER.register(modEventBus);

        ModConfig.setup();

        /**Nos hemos quedado aqui*/

        ModCreativeModeTab.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModParticles.register(modEventBus);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::livingHurt);
        MinecraftForge.EVENT_BUS.addListener(this::playerTick);
        MinecraftForge.EVENT_BUS.addListener(this::playerRightClickItem);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModBiomes.setupBiomes();
            BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.AWKWARD, Items.WHEAT, ModItems.BEER.get()));
            /**Make wheat be able to be in a brewing stand*/
            /**Old: Regions.register(new ModRegion());*/
        });
    }

    private void livingHurt(final LivingHurtEvent event) {
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

    private void playerTick(final PlayerTickEvent event) {
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

    private void playerRightClickItem(final PlayerInteractEvent.RightClickItem event) {
        if(event.getEntity() instanceof ServerPlayer player) {
            if(event.getItemStack().getItem() instanceof NecronomiconBookItem item) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 300, 0));
            }
        }
    }

}
