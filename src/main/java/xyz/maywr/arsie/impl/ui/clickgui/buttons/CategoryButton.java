package xyz.maywr.arsie.impl.ui.clickgui.buttons;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.ui.clickgui.ArsieGui;
import xyz.maywr.arsie.impl.util.ChatUtil;
import xyz.maywr.arsie.impl.util.font.FontUtil;

import java.awt.*;

import static xyz.maywr.arsie.Arsie.mc;

public class CategoryButton {

    int x, y, mouseX, mouseY;
    Category category;

    public CategoryButton(final int x, final int y, final int mouseX, final int mouseY, final Category category) {
        this.x = x;
        this.y = y;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.category = category;

        if(ArsieGui.currentCategory == category) {
            FontUtil.drawString(category.getName(), x, y, Color.WHITE.getRGB());
        } else {
            FontUtil.drawString(category.getName(), x, y, new Color(171, 174, 176 ,255).getRGB());
        }
    }

    private boolean isHovered(int mouseX, int mouseY){
        if(mouseX > x && mouseX < (x + FontUtil.getStringWidth(category.getName())) && (mouseY > y - FontUtil.getFontHeight() && mouseY < y + FontUtil.getFontHeight())) return true;
        else return false;
    }

    public void mouseClicked(int mouseX, int mouseY, int button){
        if(button == 0) {
            if (isHovered(mouseX, mouseY)) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                ArsieGui.currentCategory = category;
            }
        }
    }
}
