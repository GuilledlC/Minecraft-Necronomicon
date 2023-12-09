package net.guille_dlc.necronomicon.init;

import net.guille_dlc.necronomicon.Necronomicon;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import java.util.function.Supplier;
import static net.guille_dlc.necronomicon.common.block.NecronomiconBlocks.CORRUPTED_BONE_BLOCK;

public class ModBlocks {

    public static void setup() {
        registerBlocks();
    }

    private static void registerBlocks() {
        CORRUPTED_BONE_BLOCK = registerBlock(() ->
                new RotatedPillarBlock(
                        BlockBehaviour.Properties.of()
                                .mapColor(MapColor.SAND)
                                .instrument(NoteBlockInstrument.XYLOPHONE)
                                .requiresCorrectToolForDrops()
                                .strength(1.0F)
                                .sound(SoundType.BONE_BLOCK)
                ),
                "corrupted_bone_block"
        );
    }

    private static <T extends Block> Supplier<T> registerBlock(Supplier<T> blockSupplier, String name) {
        Supplier<T> ret = Necronomicon.BLOCK_REGISTER.register(name, blockSupplier);
        registerBlockItem(ret, name);
        return ret;
    }

    private static <T extends Block> Supplier<Item> registerBlockItem(Supplier<T> blockSupplier, String name) {
        return ModItems.registerItem(() -> new BlockItem(blockSupplier.get(), new Item.Properties()), name);
    }
}
