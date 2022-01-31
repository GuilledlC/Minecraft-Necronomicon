package net.guille_dlc.necronomicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.item.ItemStack;

public class ClientboundOpenNecronomiconPacket implements Packet<ClientGamePacketListener> {
    ItemStack itemStack;
    public ClientboundOpenNecronomiconPacket(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void write(FriendlyByteBuf pBuffer) {

    }

    public void handle(ClientGamePacketListener pHandler) {
        System.out.println(Minecraft.getInstance().level.isClientSide);
        System.out.println(Minecraft.getInstance());
        Minecraft.getInstance().setScreen(new BookViewScreen(new BookViewScreen.WrittenBookAccess(itemStack)));
    }
}
