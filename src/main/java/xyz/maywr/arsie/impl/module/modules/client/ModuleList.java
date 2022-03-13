package xyz.maywr.arsie.impl.module.modules.client;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.maywr.arsie.Arsie;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;
import xyz.maywr.arsie.impl.util.RenderUtil;
import xyz.maywr.arsie.impl.util.font.FontUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static xyz.maywr.arsie.Arsie.mc;

public class ModuleList extends Module {

    public ModuleList() {
        super("ModuleList", "shows a list of enabled modules", Category.CLIENT);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event){
        ArrayList<String> moduleList = new ArrayList<>();
        final ScaledResolution sr = new ScaledResolution(mc);
        int y = 2;
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            for(Module m : Arsie.moduleManager.getModules()){
                if(m.isEnabled() && !m.getName().equalsIgnoreCase("ClickGUI")){
                    moduleList.add(m.getName());
                }
            }

            moduleList.sort(Comparator.comparingInt(FontUtil::getStringWidth));
            Collections.reverse(moduleList);

            int delay = 2;
            for(String moduleName : moduleList){
                FontUtil.drawStringWithShadow(moduleName, sr.getScaledWidth() - FontUtil.getStringWidth(moduleName), y, RenderUtil.generateRainbowFadingColor(delay, true));
                y += FontUtil.getFontHeight(); delay -= 2;
            }
        }
    }
}
