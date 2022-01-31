package net.guille_dlc.necronomicon.item;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.entity.ModEntityTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Necronomicon.MOD_ID);

    public static final RegistryObject<Item> NECRONOMICON = ITEMS.register("necronomicon",
            () -> new NecronomiconBookItem(
                    new Item.Properties().tab(ModCreativeModTab.NECRONOMICON_TAB).fireResistant().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> ANGLE_SPAWN_EGG = ITEMS.register("angle_spawn_egg",
            () -> new ForgeSpawnEggItem(
                    ModEntityTypes.ANGLE, 0x917443, 0x3d3923,
                    new Item.Properties().tab(ModCreativeModTab.NECRONOMICON_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
