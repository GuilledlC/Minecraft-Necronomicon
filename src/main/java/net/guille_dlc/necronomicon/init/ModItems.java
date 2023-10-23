package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.api.entity.NecronomiconEntities;
import net.guille_dlc.necronomicon.common.item.BeerItem;
import net.guille_dlc.necronomicon.common.item.ModArmorMaterials;
import net.guille_dlc.necronomicon.common.item.ModFoods;
import net.guille_dlc.necronomicon.common.item.NecronomiconBookItem;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.guille_dlc.necronomicon.api.item.NecronomiconItems.*;

public class ModItems {

    public static void setup() {
        registerItems();
    }

    private static void registerItems() {
        NECRONOMICON_BOOK = registerItem(() ->
                new NecronomiconBookItem(
                        new Item.Properties()
                                .fireResistant()
                                .stacksTo(1)
                                .rarity(Rarity.UNCOMMON)),
                "necronomicon_book");

        ANGLE_SPAWN_EGG = registerItem(() ->
                new ForgeSpawnEggItem(
                        NecronomiconEntities.ANGLE,
                        0x917443,
                        0x3d3923,
                        new Item.Properties()),
                "angle_spawn_egg");

        CTHULHU_HELMET = registerItem(() ->
                new ArmorItem(
                        ModArmorMaterials.CTHULHU,
                        ArmorItem.Type.HELMET,
                        new Item.Properties()),
                "cthulhu_helmet");

        CTHULHU_CHESTPLATE = registerItem(() ->
                new ArmorItem(
                        ModArmorMaterials.CTHULHU,
                        ArmorItem.Type.CHESTPLATE,
                        new Item.Properties()),
                "cthulhu_chestplate");

        CTHULHU_LEGGINGS = registerItem(() ->
                new ArmorItem(
                        ModArmorMaterials.CTHULHU,
                        ArmorItem.Type.LEGGINGS,
                        new Item.Properties()),
                "cthulhu_leggings");

        CTHULHU_BOOTS = registerItem(() ->
                new ArmorItem(
                        ModArmorMaterials.CTHULHU,
                        ArmorItem.Type.BOOTS,
                        new Item.Properties()),
                "cthulhu_boots");

        BATTERED_COD = registerItem(() ->
                new Item(
                        new Item.Properties()
                                .food(ModFoods.BATTERED_COD)),
                "battered_cod");

        FISH_N_CHIPS = registerItem(() ->
                        new Item(
                                new Item.Properties()
                                        .food(ModFoods.FISH_N_CHIPS)),
                "fish_n_chips");

        BEER = registerItem(() ->
                new BeerItem(
                        new Item.Properties()
                                .food(ModFoods.BEER)
                                .craftRemainder(Items.GLASS_BOTTLE)
                                .stacksTo(16)),
                "beer");

        BEATING_HEART = registerItem(() ->
                        new Item(
                                new Item.Properties()
                                        .food(ModFoods.BEATING_HEART)),
                "beating_heart");

        IRON_DAGGER = registerItem(() ->
                new SwordItem(
                        Tiers.IRON,
                        2,
                        3F,
                        (new Item.Properties())),
                "iron_dagger");
    }

    private static RegistryObject<Item> registerItem(Supplier<Item> itemSupplier, String name) {
        return Necronomicon.ITEM_REGISTER.register(name, itemSupplier);
    }

}
