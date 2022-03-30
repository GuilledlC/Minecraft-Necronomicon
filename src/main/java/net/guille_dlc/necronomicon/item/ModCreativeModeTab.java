package net.guille_dlc.necronomicon.item;

import com.ibm.icu.text.UTF16;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab NECRONOMICON_TAB = new CreativeModeTab("necronomicon_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.NECRONOMICON_BOOK.get());
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> pItems) {
            super.fillItemList(pItems);
            pItems.sort((x, y) -> x.getItem().getClass().getSimpleName().compareTo(y.getItem().getClass().getSimpleName()));
        }
    };
}
