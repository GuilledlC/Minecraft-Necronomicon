package net.guille_dlc.necronomicon.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModTab {
    public static final CreativeModeTab NECRONOMICON_TAB = new CreativeModeTab("necronomicontab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.NECRONOMICON.get());
        }
    };
}
