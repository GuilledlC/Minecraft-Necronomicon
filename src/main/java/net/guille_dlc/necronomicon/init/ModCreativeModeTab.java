package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.Necronomicon;
import net.guille_dlc.necronomicon.api.item.NecronomiconItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {

    public static void setup() {
        Necronomicon.CREATIVE_TAB_REGISTER.register("necronomicon_tab", () ->
                CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.necronomicon_tab"))
                        .icon(() -> new ItemStack(NecronomiconItems.NECRONOMICON_BOOK.get()))
                        .displayItems((pParameters, pOutput) -> {
                            pOutput.accept(NecronomiconItems.NECRONOMICON_BOOK.get());
                            pOutput.accept(NecronomiconItems.ANGLE_SPAWN_EGG.get());
                            pOutput.accept(NecronomiconItems.CTHULHU_HELMET.get());
                            pOutput.accept(NecronomiconItems.CTHULHU_CHESTPLATE.get());
                            pOutput.accept(NecronomiconItems.CTHULHU_LEGGINGS.get());
                            pOutput.accept(NecronomiconItems.CTHULHU_BOOTS.get());
                            pOutput.accept(NecronomiconItems.BATTERED_COD.get());
                            pOutput.accept(NecronomiconItems.FISH_N_CHIPS.get());
                            pOutput.accept(NecronomiconItems.BEER.get());
                            pOutput.accept(NecronomiconItems.BEATING_HEART.get());
                            pOutput.accept(NecronomiconItems.IRON_DAGGER.get());
                        })
                        .build());
    }
}
