package net.guille_dlc.necronomicon.screen;

import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.stringtemplate.v4.ST;

public class NecronomiconBookViewScreen extends BookViewScreen {
    public NecronomiconBookViewScreen(BookViewScreen.BookAccess pBookAccess) {
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
