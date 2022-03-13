package xyz.maywr.arsie.impl.ui.clickgui;

import net.minecraft.client.gui.GuiScreen;
import xyz.maywr.arsie.Arsie;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;
import xyz.maywr.arsie.impl.ui.clickgui.buttons.CategoryButton;
import xyz.maywr.arsie.impl.ui.clickgui.buttons.ModuleButton;
import xyz.maywr.arsie.impl.ui.clickgui.buttons.SettingButton;
import xyz.maywr.arsie.impl.util.RenderUtil;
import xyz.maywr.arsie.impl.util.font.FontUtil;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ArsieGui extends GuiScreen {

    public static Category currentCategory = Category.COMBAT;
    protected static Module currentModule = Arsie.moduleManager.getModule("Sprint");

    private ArrayList<SettingButton> settingButtons;
    private ArrayList<ModuleButton> moduleButtons;
    private ArrayList<CategoryButton> categoryButtons;
    private int x, y, height, weight, dragX, dragY;
    private boolean drag = false;
    private boolean allowedToDrag = false;

    public ArsieGui(){
        weight = 450;
        height = 240;
        x= 30;
        y=30;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if (drag){
            x = dragX +mouseX;
            y = dragY + mouseY;
        }

        //image rendering
        /*
        ResourceLocation location = null;
        BufferedImage img = null;
        File file = new File("arsieMod/pic.jpg");
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        location = mc.getRenderManager().renderEngine.getDynamicTextureLocation(file.getName(), new DynamicTexture(img));
        mc.renderEngine.bindTexture(location);
        drawScaledCustomSizeModalRect(x, y, 100, 100, 100, 100, 100, 100, 100, 100);

        */


        categoryButtons = new ArrayList<>();
        moduleButtons = new ArrayList<>();
        drawDefaultBackground();

        RenderUtil.drawGradientSideways(x, y - 5, weight + x, y + 40, RenderUtil.generateRainbowFadingColor(1, true), RenderUtil.generateRainbowFadingColor(5, true));

        drawGradientRect(x, y, weight + x, height + y,new Color(51, 52, 51, 255).getRGB(), new Color(65, 65, 65, 255).getRGB() );
        //drawRect(x, y, weight, height, new Color(51, 52, 51, 255).getRGB());
        drawVerticalLine(x + 60, y, height + y, new Color(200, 200, 200, 180).getRGB());
        drawVerticalLine(x + 140, y, height + y, new Color(200, 200, 200, 180).getRGB());

        int CategoryY = y + 15;
        for (Category category : Category.values()) {
            CategoryButton button = new CategoryButton(x + 10, CategoryY, mouseX, mouseY, category);
            categoryButtons.add(button);
            CategoryY = CategoryY + 40;
        }

        int moduleY = y + 15;
        for (Module module : Arsie.moduleManager.getModulesInCategory(currentCategory)) {
            ModuleButton moduleButton = new ModuleButton(x + 70, moduleY, mouseX, mouseY, module);
            moduleButtons.add(moduleButton);
            moduleY += FontUtil.getFontHeight() * 1.5;
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

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for(CategoryButton button : categoryButtons){
            button.mouseClicked(mouseX, mouseY, mouseButton);
        }

        for(ModuleButton mb : moduleButtons){
            mb.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if(mouseButton == 0) {
            if (mouseHovered(mouseX, mouseY)) {
                dragX = x - mouseX;
                dragY = y - mouseY;
                drag = true;
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if(state == 0)
        drag = false;
    }

    private boolean mouseHovered(int mouseX, int mouseY){
        if(mouseX > x && mouseX < (x + width) && (mouseY > y - height && mouseY < y + height)) return true;
        else return false;
    }
}
