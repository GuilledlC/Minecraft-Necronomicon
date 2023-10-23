package net.guille_dlc.necronomicon.common.item;

import com.google.common.collect.Lists;
import net.guille_dlc.necronomicon.api.particle.NecronomiconParticles;
import net.guille_dlc.necronomicon.common.events.ClientModEvents;
import net.guille_dlc.necronomicon.client.screen.NecronomiconBookViewScreen;
import net.guille_dlc.necronomicon.init.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;


import java.util.List;

public class NecronomiconBookItem extends Item implements IForgeItem {

    public int coolDown = 0;
    public boolean activated = false;

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
        pTooltipComponents.add(Component.translatable("entry.necronomicon.tooltip"));
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
     * Called when this item is used when targetting a Block
     */
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.is(Blocks.LECTERN)) {
            return LecternBlock.tryPlaceBook(pContext.getPlayer(), level, blockpos, blockstate, pContext.getItemInHand()) ? InteractionResult.sidedSuccess(level.isClientSide) : InteractionResult.PASS;
        } else {
            return InteractionResult.PASS;
        }
        //return InteractionResult.PASS;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if(pLevel.isClientSide) {
            if (resolveBookComponents(itemStack, pPlayer.createCommandSourceStack(), pPlayer)) {
                pPlayer.containerMenu.broadcastChanges();
            }
            itemStack.setTag(bookTag());
            pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 1.0F, 1.0F);
            pPlayer.playSound(SoundEvents.AMBIENT_CAVE.get(), 1.0F, 5.0F);
            pPlayer.playSound(SoundEvents.FIRE_AMBIENT, 1.5F, 1.0F);

            BookViewScreen screen = new NecronomiconBookViewScreen(new BookViewScreen.WrittenBookAccess(itemStack));
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientModEvents.BookScreen(screen));
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
            component = Component.literal(pResolvingPageContents);
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

    public CompoundTag bookTag() {
        CompoundTag bookTag =  new CompoundTag();
        ListTag pages = new ListTag();
        List<String> pagesList = Lists.newArrayList();
        pagesList.add(Component.translatable("entry.necronomicon.necronomicon_book_text").getString());
        //pagesList.add("{\"text\":\"\\n§8To visit The Dead, one \\n\\nmust sacrifice their\\n\\nown mortal soul to \\n\\n§4§KThe Old Gods§8, while\\n\\nholding this book \\n\\nin their hands\"}");
        pagesList.stream().map(StringTag::valueOf).forEach(pages::add);
        bookTag.put("pages", pages);
        bookTag.put("author", StringTag.valueOf("entry.necronomicon.necronomicon_book_author"));
        bookTag.put("filtered_title", StringTag.valueOf("Necronomicon"));
        //bookTag.put("resolved", ByteTag.valueOf(true));
        bookTag.put("title", StringTag.valueOf("Necronomicon"));
        ItemStack book = new ItemStack(this, 1, bookTag);
        return bookTag;
    }

    public void rapture(Level level, Player player, InteractionHand pHand) {
        ItemStack itemStack = player.getItemInHand(pHand);

        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
        lightningbolt.setDamage(0);
        lightningbolt.moveTo(player.getX(), player.getY(), player.getZ());
        level.addFreshEntity(lightningbolt);

        level.explode(player, player.getX(), player.getY(), player.getZ(), 3.0F, true, Level.ExplosionInteraction.TNT);

        coolDown = 60;
        activated = true;
        playActivateAnimation(itemStack, (Entity)player);
    }

    public void playActivateAnimation(ItemStack itemStack, Entity entity) {
        Minecraft mc = Minecraft.getInstance();
        //mc.particleEngine.createTrackingEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 10);
        mc.particleEngine.createTrackingEmitter(entity, NecronomiconParticles.BLOOD_PARTICLE.get(), 10);

        assert mc.level != null: "Level is null";
        mc.level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, entity.getSoundSource(), 1.0F, 1.0F, false);

        if(entity == mc.player) {
            mc.gameRenderer.displayItemActivation(itemStack);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        System.out.println("finished with it");
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
