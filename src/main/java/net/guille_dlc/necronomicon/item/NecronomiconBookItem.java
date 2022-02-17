package net.guille_dlc.necronomicon.item;

import com.google.common.collect.Lists;
import net.guille_dlc.necronomicon.events.ClientModEvents;
import net.guille_dlc.necronomicon.particles.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;


import java.util.List;

public class NecronomiconBookItem extends Item implements IForgeItem {

    public int coolDown = 0;

    public NecronomiconBookItem(Properties properties) {
        super(properties);
    }

    /*@Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));

        if(!pPlayer.isLocalPlayer()) {
        //if(false) { //remove
            MinecraftServer server = pLevel.getServer();
            if(server != null) {
                if(pLevel.dimension() == ModDimensions.LOVECRAFTCOUNTRY) {
                    ServerLevel overWorld = server.getLevel(Level.OVERWORLD);
                    if(overWorld != null)
                        pPlayer.changeDimension(overWorld, new ModTeleporters(new BlockPos(pPlayer.position()), false));
                }
                else {
                    ServerLevel LovecraftCountry = server.getLevel(ModDimensions.LOVECRAFTCOUNTRY);
                    if(LovecraftCountry != null)
                        pPlayer.changeDimension(LovecraftCountry, new ModTeleporters(new BlockPos(pPlayer.position()), false));
                }
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }*/

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("The Book of the Dead"));
    }





    /**TO DO**/
    public static boolean makeSureTagIsValid(@javax.annotation.Nullable CompoundTag pCompoundTag) {
        return  pCompoundTag != null;
        //The requirements are the requirements of the Written book:
        // a) The requirements of the writable book:
        //      i) Is not null
        //     ii) It doens't have pages?? (I think)
        //    iii) Each page isn't corrupted or something
        // b) It has to have a title
        // c) It has to have an author
    }

    /**
     * Gets the generation of the book (how many times it has been cloned)
     */
    public static int getGeneration(ItemStack pBookStack) {
        return pBookStack.getTag().getInt("generation");
    }

    /**
     * Gets the page count of the book
     */
    public static int getPageCount(ItemStack pBookSTack) {
        return 1;
        //My book only has one page
    }

    /**
     * Gets the title name of the book
     */
    public Component getName(ItemStack pStack) {
        return new TextComponent("Necronomicon");
        //No need to explain
    }

    /**
     * Called when this item is used when targetting a Block
     */
    public InteractionResult useOn(UseOnContext pContext) {
        return InteractionResult.PASS;
    }

    /*
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    public InteractionResultHolder<ItemStack> uses(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.openItemGui(itemstack, pHand);
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if(pLevel.isClientSide) {
            if (resolveBookComponents(itemStack, pPlayer.createCommandSourceStack(), pPlayer)) {
                pPlayer.containerMenu.broadcastChanges();
            }
            itemStack.setTag(bookTag());
            pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 1, 1);
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientModEvents.BookScreen(itemStack));
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    public static boolean resolveBookComponents(ItemStack pBookStack, @javax.annotation.Nullable CommandSourceStack pResolvingSource, @javax.annotation.Nullable Player pResolvingPlayer) {
        CompoundTag compoundtag = pBookStack.getTag();
        if (compoundtag != null && !compoundtag.getBoolean("resolved")) {
            compoundtag.putBoolean("resolved", true);
            if (!makeSureTagIsValid(compoundtag)) {
                return false;
            } else {
                ListTag listtag = compoundtag.getList("pages", 8);

                for(int i = 0; i < listtag.size(); ++i) {
                    listtag.set(i, (Tag) StringTag.valueOf(resolvePage(pResolvingSource, pResolvingPlayer, listtag.getString(i))));
                }

                if (compoundtag.contains("filtered_pages", 10)) {
                    CompoundTag compoundtag1 = compoundtag.getCompound("filtered_pages");

                    for(String s : compoundtag1.getAllKeys()) {
                        compoundtag1.putString(s, resolvePage(pResolvingSource, pResolvingPlayer, compoundtag1.getString(s)));
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    private static String resolvePage(@javax.annotation.Nullable CommandSourceStack pResolvingSource, @javax.annotation.Nullable Player pResolvingPlayer, String pResolvingPageContents) {
        Component component;
        try {
            component = Component.Serializer.fromJsonLenient(pResolvingPageContents);
            component = ComponentUtils.updateForEntity(pResolvingSource, component, pResolvingPlayer, 0);
        } catch (Exception exception) {
            component = new TextComponent(pResolvingPageContents);
        }

        return Component.Serializer.toJson(component);
    }

    /**
     * Returns true if this item has an enchantment glint. By default, this returns <code>stack.isItemEnchanted()</code>,
     * but other items can override it (for instance, written books always return true).
     *
     * Note that if you override this method, you generally want to also call the super version (on {@link Item}) to get
     * the glint for enchanted items. Of course, that is unnecessary if the overwritten version always returns true.
     */
    public boolean isFoil(ItemStack pStack) {
        return false;
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (this.allowdedIn(pCategory)) {
            ItemStack subItemStack = new ItemStack(this, 1);
            CompoundTag nbt = subItemStack.getOrCreateTag();
            nbt.equals(bookTag());
            pItems.add(subItemStack);
        }
    }

    public CompoundTag bookTag() {
        CompoundTag bookTag =  new CompoundTag();
        ListTag pages = new ListTag();
        List<String> pagesList = Lists.newArrayList();
        pagesList.add("§8Hello");
        //pagesList.add("{\"text\":\"Hello\nhi\"}");
        //pagesList.add("{\"text\":\"\\n§8To visit The Dead, one \\n\\nmust sacrifice their\\n\\nown mortal soul to \\n\\n§4§KThe Old Gods§8, while\\n\\nholding this book \\n\\nin their hands\"}");
        pagesList.stream().map(StringTag::valueOf).forEach(pages::add);
        bookTag.put("pages", pages);
        bookTag.put("author", StringTag.valueOf("The Dead"));
        bookTag.put("filtered_title", StringTag.valueOf("Necronomicon"));
        //bookTag.put("resolved", ByteTag.valueOf(true));
        bookTag.put("title", StringTag.valueOf("Necronomicon"));
        ItemStack book = new ItemStack(this, 1, bookTag);
        return bookTag;
    }

    public void rapture(Level level, Player player, InteractionHand pHand) {
        ItemStack itemStack = player.getItemInHand(pHand);

        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
        lightningbolt.moveTo(player.getX(), player.getY(), player.getZ());
        level.addFreshEntity(lightningbolt);

        level.explode(player, player.getX(), player.getY(), player.getZ(), 3.0F, true, Explosion.BlockInteraction.DESTROY);

        coolDown = 100;
        playActivateAnimation(itemStack, (Entity)player);
    }

    public void playActivateAnimation(ItemStack itemStack, Entity entity) {
        Minecraft mc = Minecraft.getInstance();
        //mc.particleEngine.createTrackingEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 10);
        mc.particleEngine.createTrackingEmitter(entity, ModParticles.BLOOD_PARTICLE.get(), 10);

        assert mc.level != null: "Level is null";
        mc.level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, entity.getSoundSource(), 1.0F, 1.0F, false);

        if(entity == mc.player) {
            mc.gameRenderer.displayItemActivation(itemStack);
        }
    }

    public void dim(Level pLevel, Entity pEntity) {
        if(coolDown == 1) {
            ResourceKey<Level> resourcekey = pEntity.getLevel().dimension() == Level.END ? Level.OVERWORLD : Level.END;
            ServerLevel serverlevel = ((ServerLevel)pEntity.getLevel()).getServer().getLevel(resourcekey);
            if (serverlevel == null) {
                return;
            }

            pEntity.changeDimension(serverlevel);
        }
        while(coolDown > 0)
            coolDown--;
    }
}
