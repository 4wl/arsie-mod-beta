package xyz.maywr.arsie.impl.ui.panelgui;

import net.minecraft.client.gui.Gui;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.util.font.FontUtil;

import java.awt.*;

public class Panel {

    public Panel (Category category, int x, int y, int mouseX, int mouseY) {
        Gui.drawRect(x, y, 100, 100, Color.BLACK.getRGB());
        FontUtil.drawString(category.getName(), x, y, Color.WHITE.getRGB());
    }
}
