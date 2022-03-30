package net.guille_dlc.necronomicon.item;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.entity.ModEntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
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

    public static final RegistryObject<Item> NECRONOMICON_BOOK = ITEMS.register("necronomicon_book",
            () -> new NecronomiconBookItem(
                    new Item.Properties().tab(ModCreativeModeTab.NECRONOMICON_TAB).fireResistant().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> ANGLE_SPAWN_EGG = ITEMS.register("angle_spawn_egg",
            () -> new ForgeSpawnEggItem(
                    ModEntityTypes.ANGLE, 0x917443, 0x3d3923,
                    new Item.Properties().tab(ModCreativeModeTab.NECRONOMICON_TAB)));

    public static final RegistryObject<Item> CTHULHU_HELMET = ITEMS.register("cthulhu_helmet",
            () -> new ArmorItem(ModArmorMaterials.CTHULHU, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModeTab.NECRONOMICON_TAB)));
    public static final RegistryObject<Item> CTHULHU_CHESTPLATE = ITEMS.register("cthulhu_chestplate",
            () -> new ArmorItem(ModArmorMaterials.CTHULHU, EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModCreativeModeTab.NECRONOMICON_TAB)));
    public static final RegistryObject<Item> CTHULHU_LEGGINGS = ITEMS.register("cthulhu_leggings",
            () -> new ArmorItem(ModArmorMaterials.CTHULHU, EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModCreativeModeTab.NECRONOMICON_TAB)));
    public static final RegistryObject<Item> CTHULHU_BOOTS = ITEMS.register("cthulhu_boots",
            () -> new ArmorItem(ModArmorMaterials.CTHULHU, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModeTab.NECRONOMICON_TAB)));

    public static final RegistryObject<Item> BATTERED_COD = ITEMS.register("battered_cod",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.NECRONOMICON_TAB).food(ModFoods.BATTERED_COD)));
    public static final RegistryObject<Item> FISH_N_CHIPS = ITEMS.register("fish_n_chips",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.NECRONOMICON_TAB).food(ModFoods.FISH_N_CHIPS)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
