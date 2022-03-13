package xyz.maywr.arsie.impl.module.modules.move;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import xyz.maywr.arsie.impl.module.Category;
import xyz.maywr.arsie.impl.module.Module;

import static xyz.maywr.arsie.Arsie.mc;

public class GuiMove extends Module {

    public GuiMove() {
        super("GUIMove", "makes your player moveable while in guis", Category.MOVE);
    }

}
