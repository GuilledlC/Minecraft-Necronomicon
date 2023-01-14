package net.guille_dlc.necronomicon.events;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class ForgeClientEvents {

    @SubscribeEvent
    public void onCreativeModeTab(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(Necronomicon.MOD_ID, "necronomicon_tab"),
                builder -> builder
                        .title(Component.translatable("necronomicon_tab"))
                        .icon(() -> new ItemStack(ModItems.NECRONOMICON_BOOK.get()))
                        .displayItems((enabledFlags, populator, hasPermissions) -> {
                            populator.accept(ModItems.NECRONOMICON_BOOK.get());
                            populator.accept(ModItems.ANGLE_SPAWN_EGG.get());
                            populator.accept(ModItems.CTHULHU_HELMET.get());
                            populator.accept(ModItems.CTHULHU_CHESTPLATE.get());
                            populator.accept(ModItems.CTHULHU_LEGGINGS.get());
                            populator.accept(ModItems.CTHULHU_BOOTS.get());
                            populator.accept(ModItems.BATTERED_COD.get());
                            populator.accept(ModItems.FISH_N_CHIPS.get());
                            populator.accept(ModItems.BEER.get());
                            populator.accept(ModItems.BEATING_HEART.get());
                            populator.accept(ModItems.IRON_DAGGER.get());
                        })
        );
    }

    @SubscribeEvent
    public void buildContents(CreativeModeTabEvent.BuildContents e) {
        if (e.getTab() == CreativeModeTabs.COMBAT) {
            e.accept(ModItems.NECRONOMICON_BOOK.get());
        }
    }

}
