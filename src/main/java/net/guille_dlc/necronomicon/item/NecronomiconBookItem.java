package net.guille_dlc.necronomicon.item;

import com.google.common.collect.Lists;
import net.guille_dlc.necronomicon.ClientboundOpenNecronomiconPacket;
import net.guille_dlc.necronomicon.dimension.ModDimensions;
import net.guille_dlc.necronomicon.dimension.ModTeleporters;
import net.guille_dlc.necronomicon.events.ClientModEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;


import javax.swing.*;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.util.List;

public class NecronomiconBookItem extends Item implements IForgeItem {
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

        //Substituting this -> pPlayer.openItemGui(itemstack, pHand);
        if(pPlayer instanceof ServerPlayer sPlayer) { //Only the ServerPlayer is gonna receive this
            if (resolveBookComponents(itemStack, pPlayer.createCommandSourceStack(), pPlayer)) {
                pPlayer.containerMenu.broadcastChanges();
            }
            /*  pPlayer.openItemGui(itemstack, pHand);
                    this.connection.send(new ClientboundOpenBookPacket(pHand));
                    is sent to the ClientPacketListener handleOpenBook()
                        this.minecraft.setScreen(new BookViewScreen(new BookViewScreen.WrittenBookAccess(itemstack)));
            */
            //sPlayer.connection.send(new ClientboundOpenBookPacket(pHand));
            itemStack.setTag(bookTag());
            sPlayer.connection.send(new ClientboundOpenNecronomiconPacket(itemStack));
            ClientModEvents.BookScreen(itemStack);
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this)); //Don't delete this (don't delete anything)
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide()); //I don't know what this does lol
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
        pagesList.add("{\"text\":\"Hello\"}");
        pagesList.stream().map(StringTag::valueOf).forEach(pages::add);
        bookTag.put("pages", pages);
        bookTag.put("author", StringTag.valueOf("The Dead"));
        bookTag.put("filtered_title", StringTag.valueOf("Necronomicon"));
        //bookTag.put("resolved", ByteTag.valueOf(true));
        bookTag.put("title", StringTag.valueOf("Necronomicon"));
        ItemStack book = new ItemStack(this, 1, bookTag);
        return bookTag;
    }
}
