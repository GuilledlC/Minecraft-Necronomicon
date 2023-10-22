package net.guille_dlc.necronomicon.old.item;

import net.guille_dlc.necronomicon.Necronomicon;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Necronomicon.MOD_ID);
    public static final RegistryObject<CreativeModeTab> NECRONOMICON_TAB = CREATIVE_MODE_TABS.register("necronomicon_tab", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.necronomicon_tab"))
                    .icon(() -> new ItemStack(ModItems.NECRONOMICON_BOOK.get()))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.NECRONOMICON_BOOK.get());
                        pOutput.accept(ModItems.ANGLE_SPAWN_EGG.get());
                        pOutput.accept(ModItems.CTHULHU_HELMET.get());
                        pOutput.accept(ModItems.CTHULHU_CHESTPLATE.get());
                        pOutput.accept(ModItems.CTHULHU_LEGGINGS.get());
                        pOutput.accept(ModItems.CTHULHU_BOOTS.get());
                        pOutput.accept(ModItems.BATTERED_COD.get());
                        pOutput.accept(ModItems.FISH_N_CHIPS.get());
                        pOutput.accept(ModItems.BEER.get());
                        pOutput.accept(ModItems.BEATING_HEART.get());
                        pOutput.accept(ModItems.IRON_DAGGER.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
