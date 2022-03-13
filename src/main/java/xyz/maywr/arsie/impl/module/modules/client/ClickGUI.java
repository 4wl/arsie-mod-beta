package xyz.maywr.arsie.impl.module.modules.client;

import org.lwjgl.input.Keyboard;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;
import xyz.maywr.arsie.impl.ui.clickgui.ArsieGui;
import xyz.maywr.arsie.impl.ui.panelgui.PanelGUI;

import static xyz.maywr.arsie.Arsie.mc;

public class ClickGUI extends Module {

    ArsieGui gui;
    PanelGUI panelGUI;

    public ClickGUI(){
        super("ClickGUI", "shows module buttons", Category.CLIENT);
        this.setKeyBind(Keyboard.KEY_INSERT);
    }

    @Override
    public void onEnable(){
        if(panelGUI == null) panelGUI = new PanelGUI();
        mc.displayGuiScreen(panelGUI);
    }

}
