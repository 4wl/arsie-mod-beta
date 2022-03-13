package xyz.maywr.arsie.impl.ui.panelgui;

import net.minecraft.client.gui.GuiScreen;
import xyz.maywr.arsie.Arsie;
import xyz.maywr.arsie.impl.module.Category;

public class PanelGUI extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(Category category : Category.values()){
            int x = 10;
            Panel panel = new Panel(category, x, 10, mouseX, mouseY);
            x += 110;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        Arsie.moduleManager.getModule("ClickGUI").disable();
    }
}
