package net.guille_dlc.necronomicon.item;

import net.guille_dlc.necronomicon.Necronomicon;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Necronomicon.MOD_ID);

    public static final RegistryObject<Item> NECRONOMICON = ITEMS.register("necronomicon",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.NECRONOMICON_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
