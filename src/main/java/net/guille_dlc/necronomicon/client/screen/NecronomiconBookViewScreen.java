package net.guille_dlc.necronomicon.client.screen;

import net.minecraft.client.gui.screens.inventory.BookViewScreen;

public class NecronomiconBookViewScreen extends BookViewScreen {
    public NecronomiconBookViewScreen(BookAccess pBookAccess) {
        super(pBookAccess);
    }

    /*@Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if(super.mouseClicked(pMouseX, pMouseY, pButton)) {
            this.minecraft.player.removeEffect(MobEffects.BLINDNESS);
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public void onClose() {
        this.minecraft.player.removeEffect(MobEffects.BLINDNESS);
        super.onClose();
    }*/
}
