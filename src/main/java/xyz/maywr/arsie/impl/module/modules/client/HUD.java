package xyz.maywr.arsie.impl.module.modules.client;

import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;
import xyz.maywr.arsie.impl.util.RenderUtil;
import xyz.maywr.arsie.impl.util.font.FontUtil;

import java.awt.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import static xyz.maywr.arsie.Arsie.mc;

public class HUD extends Module {

    public HUD() {
        super("HUD", "renders some components on yo screen", Category.CLIENT);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            String serverPlaying = (mc.isSingleplayer() ? "singleplayer" : mc.getCurrentServerData().serverIP).toUpperCase();

            Format f = new SimpleDateFormat("HH:mm:ss");
            String time = f.format(new Date());

            String watermark = "ARSIE | " + mc.player.getName() + " | " + serverPlaying + " | " + time;

            //RenderUtil.drawGradientSideways(4, 3, FontUtil.getStringWidth(watermark) + 5, 16, new Color(214, 41, 230, 255).getRGB(), new Color(230, 142, 41, 255).getRGB());
            RenderUtil.drawGradientSideways(4, 4, FontUtil.getStringWidth(watermark) + 5, 16, RenderUtil.generateRainbowFadingColor(1, true), RenderUtil.generateRainbowFadingColor(4, true));

            Gui.drawRect(4, 70, FontUtil.getStringWidth(watermark) + 5, 16, Color.BLACK.getRGB());
            FontUtil.drawString(watermark, 5, 7, Color.WHITE.getRGB());
        }
    }
}
