package net.guille_dlc.necronomicon.item;

import com.google.common.base.Suppliers;
import com.ibm.icu.text.UTF16;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ModCreativeModeTab {

    public static final CreativeModeTab NECRONOMICON_TAB = new CreativeModeTab(
            new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 10)
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
    )
    {};
}
