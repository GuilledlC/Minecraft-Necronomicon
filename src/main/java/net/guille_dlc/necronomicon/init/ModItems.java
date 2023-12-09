package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.common.entity.NecronomiconEntities;
import net.guille_dlc.necronomicon.common.item.BeerItem;
import net.guille_dlc.necronomicon.common.item.NecronomiconArmorMaterials;
import net.guille_dlc.necronomicon.common.item.NecronomiconFoods;
import net.guille_dlc.necronomicon.common.item.NecronomiconBookItem;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import java.util.function.Supplier;
import static net.guille_dlc.necronomicon.common.item.NecronomiconItems.*;

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
                new DeferredSpawnEggItem(
                        NecronomiconEntities.ANGLE,
                        0x917443,
                        0x3d3923,
                        new Item.Properties()),
                "angle_spawn_egg");

        CTHULHU_HELMET = registerItem(() ->
                new ArmorItem(
                        NecronomiconArmorMaterials.CTHULHU,
                        ArmorItem.Type.HELMET,
                        new Item.Properties()),
                "cthulhu_helmet");

        CTHULHU_CHESTPLATE = registerItem(() ->
                new ArmorItem(
                        NecronomiconArmorMaterials.CTHULHU,
                        ArmorItem.Type.CHESTPLATE,
                        new Item.Properties()),
                "cthulhu_chestplate");

        CTHULHU_LEGGINGS = registerItem(() ->
                new ArmorItem(
                        NecronomiconArmorMaterials.CTHULHU,
                        ArmorItem.Type.LEGGINGS,
                        new Item.Properties()),
                "cthulhu_leggings");

        CTHULHU_BOOTS = registerItem(() ->
                new ArmorItem(
                        NecronomiconArmorMaterials.CTHULHU,
                        ArmorItem.Type.BOOTS,
                        new Item.Properties()),
                "cthulhu_boots");

        BATTERED_COD = registerItem(() ->
                new Item(
                        new Item.Properties()
                                .food(NecronomiconFoods.BATTERED_COD)),
                "battered_cod");

        FISH_N_CHIPS = registerItem(() ->
                        new Item(
                                new Item.Properties()
                                        .food(NecronomiconFoods.FISH_N_CHIPS)),
                "fish_n_chips");

        BEER = registerItem(() ->
                new BeerItem(
                        new Item.Properties()
                                .food(NecronomiconFoods.BEER)
                                .craftRemainder(Items.GLASS_BOTTLE)
                                .stacksTo(16)),
                "beer");

        BEATING_HEART = registerItem(() ->
                        new Item(
                                new Item.Properties()
                                        .food(NecronomiconFoods.BEATING_HEART)),
                "beating_heart");

        IRON_DAGGER = registerItem(() ->
                new SwordItem(
                        Tiers.IRON,
                        2,
                        3F,
                        (new Item.Properties())),
                "iron_dagger");
    }

    public static <T extends Item> Supplier<T> registerItem(Supplier<T> itemSupplier, String name) {
        return Necronomicon.ITEM_REGISTER.register(name, itemSupplier);
    }

}
