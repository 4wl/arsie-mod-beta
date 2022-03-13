package xyz.maywr.arsie.impl.ui.clickgui.buttons;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.SoundEvents;
import xyz.maywr.arsie.impl.module.Module;
import xyz.maywr.arsie.impl.util.font.FontUtil;

import java.awt.*;

import static xyz.maywr.arsie.Arsie.mc;

public class ModuleButton {

    int x, y, mouseX, mouseY;
    private final Module module;

    public ModuleButton(final int x, final int y, final int mouseX, final int mouseY, Module module) {
        this.x = x;
        this.y = y;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.module = module;
        if (module.isEnabled())
            FontUtil.drawString(module.getName(), x, y, Color.WHITE.getRGB());
        else
            FontUtil.drawString(module.getName(), x, y, new Color(171, 174, 176, 255).getRGB());

        if(isHovered(mouseX, mouseY)){
            //Gui.drawRect(mouseX  , mouseY - FontUtil.getFontHeight(), FontUtil.getStringWidth(module.getDescription()), FontUtil.getFontHeight(), Color.BLACK.getRGB());
            FontUtil.drawString(module.getDescription(), mouseX, mouseY - FontUtil.getFontHeight(), Color.WHITE.getRGB());
        }
    }

    private boolean isHovered(int mouseX, int mouseY){
        if(mouseX > x && mouseX < (x + FontUtil.getStringWidth(module.getName())) && (mouseY > y - FontUtil.getFontHeight() && mouseY < y + FontUtil.getFontHeight())) return true;
        else return false;
    }


    public void mouseClicked(int mouseX, int mouseY, int button){
        if(button == 0) {
            if (this.isHovered(mouseX, mouseY)) {mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f)); this.module.toggle();}
        }
    }

    public Module getModule() {
        return module;
    }
}
